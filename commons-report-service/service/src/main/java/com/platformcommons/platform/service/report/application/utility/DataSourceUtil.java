package com.platformcommons.platform.service.report.application.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

public class DataSourceUtil {


    static final Logger LOGGER = LoggerFactory.getLogger(DataSourceUtil.class);
    public static Connection getConnection(String driver, String url,
                                           String user, String password) throws Exception {
        Connection conn = null;

        try {
            LOGGER.debug("getConnection>> before forName");
            Class.forName(driver);
            LOGGER.debug("getConnection>> after forName");
        } catch (ClassNotFoundException ex) {
            LOGGER.error("getConnection>> " + ex.getMessage());
            throw ex;
        }

        try {
            LOGGER.debug("getConnection>> before DriverManager.getConnection");
            conn = DriverManager.getConnection(url, user, password);
            LOGGER.debug("getConnection>> after DriverManager.getConnection");
        } catch (SQLException ex) {
            LOGGER.error("getConnection>> " + ex.getMessage());
            throw ex;
        }
        return conn;
    }
    public static Connection getConnection(String driver, String url,
                                           Properties props) throws Exception {
        Connection conn = null;

        try {
            LOGGER.debug("getConnection>> before forName");
            Class.forName(driver);
            LOGGER.debug("getConnection>> after forName");
        } catch (ClassNotFoundException ex) {
            LOGGER.error("getConnection>> " + ex.getMessage());
            throw ex;
        }

        try {
            LOGGER.debug("getConnection>> before DriverManager.getConnection");
            conn = DriverManager.getConnection(url, props);
            LOGGER.debug("getConnection>> after DriverManager.getConnection");
        } catch (SQLException ex) {
            LOGGER.error("getConnection>> " + ex.getMessage());
            throw ex;
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception ex) {
            LOGGER.error("closeConnection>> " + ex.getMessage());
        }
    }

    private static DataSource buildDataSource(String url, String userName, String password, String driverName){
        return DataSourceBuilder.create()
                .driverClassName(driverName)
                .url(url)
                .username(userName)
                .password(password)
                .build();

    }

    private  static  JdbcTemplate buildJdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    private static  NamedParameterJdbcTemplate buildNamedParameterJDBCTemplate(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }

    public static Object getResultSet(String url, String userName, String password, String driverName, String query,
                                       Map<String,Object> paramSource)  {
        Object result = null;

        CommonsJDBCTemplate jdbcTemplate=null;
        try {
             jdbcTemplate = new CommonsJDBCTemplate(getConnection(driverName,url,userName,password));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jdbcTemplate.query(query, paramSource, new RowMapperResultSetExtractor<>(new ColumnMapRowMapper()));

    }
}
