package com.platformcommons.platform.service.report.application.utility;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.SQLWarningException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentLruCache;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CommonsJDBCTemplate  {

    static final Logger logger = LoggerFactory.getLogger(CommonsJDBCTemplate.class);


    private final Connection connection;

    public  CommonsJDBCTemplate(Connection connection){
        this.connection = connection;
    }

    public static final int DEFAULT_CACHE_LIMIT = 256;

    private volatile ConcurrentLruCache<String, ParsedSql> parsedSqlCache =
            new ConcurrentLruCache<>(DEFAULT_CACHE_LIMIT, NamedParameterUtils::parseSqlStatement);

    public <T> T query(String sql, Map<String, ?> paramMap, ResultSetExtractor<T> rse)
            throws DataAccessException {

        return query(sql, new MapSqlParameterSource(paramMap), rse);
    }

    public <T> T query(String sql, SqlParameterSource paramSource, ResultSetExtractor<T> rse)
            throws DataAccessException {

        return query(getPreparedStatementCreator(sql, paramSource), rse);
    }


    protected PreparedStatementCreator getPreparedStatementCreator(String sql, SqlParameterSource paramSource) {
        return getPreparedStatementCreator(sql, paramSource, null);
    }

    /**
     * Build a {@link PreparedStatementCreator} based on the given SQL and named parameters.
     * <p>Note: Used for the {@code update} variant with generated key handling, and also
     * delegated from {@link #getPreparedStatementCreator(String, SqlParameterSource)}.
     * @param sql the SQL statement to execute
     * @param paramSource container of arguments to bind
     * @param customizer callback for setting further properties on the
     * {@link PreparedStatementCreatorFactory} in use), applied before the
     * actual {@code newPreparedStatementCreator} call
     * @return the corresponding {@link PreparedStatementCreator}
     * @since 5.0.5
     * @see #getParsedSql(String)
     * @see PreparedStatementCreatorFactory#PreparedStatementCreatorFactory(String, List)
     * @see PreparedStatementCreatorFactory#newPreparedStatementCreator(Object[])
     */
    protected PreparedStatementCreator getPreparedStatementCreator(String sql, SqlParameterSource paramSource,
                                                                   @Nullable Consumer<PreparedStatementCreatorFactory> customizer) {

        ParsedSql parsedSql = getParsedSql(sql);
        PreparedStatementCreatorFactory pscf = getPreparedStatementCreatorFactory(parsedSql, paramSource);
        if (customizer != null) {
            customizer.accept(pscf);
        }
        Object[] params = NamedParameterUtils.buildValueArray(parsedSql, paramSource, null);
        return pscf.newPreparedStatementCreator(params);
    }

    /**
     * Obtain a parsed representation of the given SQL statement.
     * <p>The default implementation uses an LRU cache with an upper limit of 256 entries.
     * @param sql the original SQL statement
     * @return a representation of the parsed SQL statement
     */
    protected ParsedSql getParsedSql(String sql) {
        return this.parsedSqlCache.get(sql);
    }

    /**
     * Build a {@link PreparedStatementCreatorFactory} based on the given SQL and named parameters.
     * @param parsedSql parsed representation of the given SQL statement
     * @param paramSource container of arguments to bind
     * @return the corresponding {@link PreparedStatementCreatorFactory}
     * @since 5.1.3
     * @see #getPreparedStatementCreator(String, SqlParameterSource, Consumer)
     * @see #getParsedSql(String)
     */
    protected PreparedStatementCreatorFactory getPreparedStatementCreatorFactory(
            ParsedSql parsedSql, SqlParameterSource paramSource) {

        String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
        List<SqlParameter> declaredParameters = NamedParameterUtils.buildSqlParameterList(parsedSql, paramSource);
        return new PreparedStatementCreatorFactory(sqlToUse, declaredParameters);
    }

    public <T> T query(PreparedStatementCreator psc, ResultSetExtractor<T> rse) throws DataAccessException {
        return query(psc, null, rse);
    }

    @Nullable
    public <T> T query(
            PreparedStatementCreator psc, @Nullable final PreparedStatementSetter pss, final ResultSetExtractor<T> rse)
            throws DataAccessException {

        Assert.notNull(rse, "ResultSetExtractor must not be null");


        return execute(psc, new PreparedStatementCallback<T>() {
            @Override
            @Nullable
            public T doInPreparedStatement(@NotNull PreparedStatement ps) throws SQLException {
                ResultSet rs = null;
                try {
                    if (pss != null) {
                        pss.setValues(ps);
                    }
                    rs = ps.executeQuery();
                    return rse.extractData(rs);
                }
                finally {
                    JdbcUtils.closeResultSet(rs);
                    if (pss instanceof ParameterDisposer) {
                        ((ParameterDisposer) pss).cleanupParameters();
                    }
                }
            }
        }, true);
    }

    @Nullable
    private <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> action, boolean closeResources)
            throws DataAccessException {

        Assert.notNull(psc, "PreparedStatementCreator must not be null");
        Assert.notNull(action, "Callback object must not be null");
        if (logger.isDebugEnabled()) {
            String sql = getSql(psc);
            logger.debug("Executing prepared SQL statement" + (sql != null ? " [" + sql + "]" : ""));
        }

        Connection con = this.connection;
        PreparedStatement ps = null;
        try {
            ps = psc.createPreparedStatement(con);
           // applyStatementSettings(ps);
            T result = action.doInPreparedStatement(ps);
            handleWarnings(ps);
            return result;
        }
        catch (SQLException ex) {
            // Release Connection early, to avoid potential connection pool deadlock
            // in the case when the exception translator hasn't been initialized yet.
            if (psc instanceof ParameterDisposer) {
                ((ParameterDisposer) psc).cleanupParameters();
            }
            String sql = getSql(psc);
            psc = null;
            JdbcUtils.closeStatement(ps);
            ps = null;

            con = null;
            throw new RuntimeException(ExceptionUtils.getRootCauseMessage(ex));
        }
        finally {
            if (closeResources) {
                if (psc instanceof ParameterDisposer) {
                    ((ParameterDisposer) psc).cleanupParameters();
                }
                JdbcUtils.closeStatement(ps);
                try {
                    if(con!=null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Nullable
    private static String getSql(Object sqlProvider) {
        if (sqlProvider instanceof SqlProvider) {
            return ((SqlProvider) sqlProvider).getSql();
        }
        else {
            return null;
        }
    }

    protected void handleWarnings(Statement stmt) throws SQLException {
        if (true) {
            if (logger.isDebugEnabled()) {
                SQLWarning warningToLog = stmt.getWarnings();
                while (warningToLog != null) {
                    logger.debug("SQLWarning ignored: SQL state '" + warningToLog.getSQLState() + "', error code '" +
                            warningToLog.getErrorCode() + "', message [" + warningToLog.getMessage() + "]");
                    warningToLog = warningToLog.getNextWarning();
                }
            }
        }
        else {
            handleWarnings(stmt.getWarnings());
        }
    }
    protected void handleWarnings(@Nullable SQLWarning warning) throws SQLWarningException {
        if (warning != null) {
            throw new SQLWarningException("Warning not ignored", warning);
        }
    }

}
