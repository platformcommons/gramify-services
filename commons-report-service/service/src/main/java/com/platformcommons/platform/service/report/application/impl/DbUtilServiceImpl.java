package com.platformcommons.platform.service.report.application.impl;


import com.platformcommons.platform.service.report.application.DbUtilService;
import com.platformcommons.platform.service.report.application.utility.DataSourceUtil;
import com.platformcommons.platform.service.report.application.utility.InputStreamResourceAccessor;
import com.platformcommons.platform.service.report.domain.DBAnalytics;
import com.platformcommons.platform.service.report.domain.repo.DBAnalyticsRepository;
import liquibase.Liquibase;
import liquibase.change.Change;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.database.OfflineConnection;
import liquibase.database.core.MySQLDatabase;
import liquibase.exception.LiquibaseException;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class DbUtilServiceImpl implements DbUtilService {

    public static  final String URL = "jdbc:mysql://localhost:3306";
    public static  final String USER_NAME = "root";
    public static  final String PASSWORD = "root";

    public static  final String DRIVER = "com.mysql.cj.jdbc.Driver";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DBAnalyticsRepository dbAnalyticsRepository;

    @Override
    public Set<String> getImpactedTables(String dbName, String tenantIdentifierColumn) throws Exception {


        Set<String> tablesImpacted = new LinkedHashSet<>();
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();

//        String query = "SELECT " +
//                " distinct TABLE_NAME , COLUMN_NAME " +
//                " FROM " +
//                " information_schema.COLUMNS  " +
//                " WHERE " +
//                " TABLE_NAME IN (SELECT " +
//                "    TABLE_NAME " +
//                "    FROM " +
//                "    INFORMATION_SCHEMA.TABLES" +
//                "        WHERE " +
//                "            TABLE_SCHEMA = ? ) " +
//                " AND  " +
//                " ( COLUMN_NAME = ? )";

        String query = "SELECT TABLE_NAME , COLUMN_NAME FROM information_schema.COLUMNS WHERE COLUMN_NAME = ? AND TABLE_SCHEMA = ? ";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(2, dbName);
            ps.setString(1, tenantIdentifierColumn);
            try(ResultSet resultSet = ps.executeQuery()){
                while (resultSet.next()){
                    tablesImpacted.add(resultSet.getString("TABLE_NAME"));
                }
            }
            catch (SQLException ex){
                throw  new RuntimeException(ExceptionUtils.getRootCauseMessage(ex));

            }
        }
        catch (SQLException ex){
            throw  new RuntimeException(ExceptionUtils.getRootCauseMessage(ex));
        }
        DataSourceUtil.closeConnection(connection);
        return tablesImpacted;
    }


    @Override
    public Map<String,Long> getCountsAffected(Long tenantId,String dbName,String tenantIdentifierColumn,Set<String> tableNames) throws Exception {
        Map<String,Long> map = new HashMap<>();
        Set<String> tables = this.getImpactedTables(dbName,tenantIdentifierColumn);
        if(tableNames!=null){
            tables.retainAll(tableNames);
        }
        String affectedRowsQuery = tables.stream().map(it-> "Select '"+it+"' as TABLE_NAME, " +
                "count(*)  as COUNT from "+dbName+"."+it+" where "+tenantIdentifierColumn+" = "+tenantId).collect(Collectors.joining( " union "));

        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        try (PreparedStatement ps = connection.prepareStatement(affectedRowsQuery)) {

            try (ResultSet resultSet = ps.executeQuery()){
                while (resultSet.next()) {
                    map.put(resultSet.getString("TABLE_NAME"),
                            resultSet.getLong("COUNT"));
                }
            }
            catch (SQLException ex){
                throw  new RuntimeException(ExceptionUtils.getRootCauseMessage(ex));
            }

        }
        catch (SQLException ex){
            throw  new RuntimeException(ExceptionUtils.getRootCauseMessage(ex));
        }
        DataSourceUtil.closeConnection(connection);
        map.forEach((k,v)-> System.out.println(k +" --> "+v));
        buildAndSaveDBAnalytics(map,tenantId,tenantIdentifierColumn);
        return  map;
    }

    @Override
    public byte[] getDumpScripts(String dbName, Long tenantId,String tenantIdentifierColumn) throws Exception {


        StringBuilder backUpDir = new StringBuilder()
                .append("$BACKUP_DIR/").append(tenantId).append("/").append(dbName).append("/");

        String mysqlDumpFormat =  "mysqldump   --host=$HOST --port=$PORT --user=$USER --password=$PASSWORD  "+dbName+" %s  -t  " +
                " --complete-insert --where=\"%s in ( "+tenantId+" )\"  | gzip > $BACKUP_DIR/%s ";
        String mysqlSourceFormat =  "mysql --host=$HOST --port=$PORT --user=$USER --password=$PASSWORD  "+dbName
                +" < $BACKUP_DIR/%s ";

        Set<String> tables = this.getImpactedTables(dbName,tenantIdentifierColumn);

        StringBuilder fileContentsForDump = new StringBuilder()
                .append("#!/bin/sh\n\n\n")
                .append("HOST=\"<host>\"\n" + "PORT=\"<port>\"\n" + "USER=\"<user>\"\n" + "PASSWORD=\"<password>\"\n" + "BACKUP_DIR=\"<backup_dir>/")
                .append(tenantId).append("/").append(dbName).append("/`date +%Y_%m_%d`\"\n").append("mkdir -p $BACKUP_DIR\n\n");

        StringBuilder fileContentForSourcing= new StringBuilder().append("#!/bin/sh");

        for (String table : tables){

            String fileName = table+".gz";
            fileContentsForDump.append("\n").append("echo \"Dumping  ").append(table).append(" \"").append("\n")
                    .append(String.format(mysqlDumpFormat, table, tenantIdentifierColumn,fileName));

            fileContentForSourcing.append("\n").append("#echo \"Sourcing  ").append(table).append(" \"").append("\n")
                    .append("#")
                    .append(String.format(mysqlSourceFormat,fileName));

        }

        fileContentsForDump.append("\n\n\n\n");
        fileContentsForDump.append(fileContentForSourcing);
        InputStream stream = new ByteArrayInputStream(fileContentsForDump.toString().getBytes
                (StandardCharsets.UTF_8));
        return  IOUtils.toByteArray(stream);
    }


    @Override
    public byte[] generateDeleteScripts(String dbName, Long tenantId,String tenantIdentifierColumn) throws Exception {

        String deleteQueryFormat = "Delete  From "+dbName+".%s where %s = "+tenantId+";";
        Set<String> tables = this.getImpactedTables(dbName,tenantIdentifierColumn);

        StringBuilder fileContents = new StringBuilder()
                .append("START TRANSACTION;\n")
                .append("SET FOREIGN_KEY_CHECKS = 0;\n\n");
        for (String table: tables){
            fileContents.append("\n").append(String.format(deleteQueryFormat,table,tenantIdentifierColumn));

        }

        fileContents.append("\n\nSET FOREIGN_KEY_CHECKS = 1;");
        fileContents.append("\nCOMMIT;");
        InputStream stream = new ByteArrayInputStream(fileContents.toString().getBytes
                (StandardCharsets.UTF_8));
        return  IOUtils.toByteArray(stream);

    }


    private DBAnalytics buildAndSaveDBAnalytics(Map<String,Long> columnRoeCounts, Long tenantId, String tenantColumnIdentifier){

        DBAnalytics analytics=DBAnalytics.builder()
                .id(null)
                .analyticsDate(new Date())
                .columnRowCountsForTenant(columnRoeCounts)
                .tenantId(tenantId)
                .tenantIdentifierColumn(tenantColumnIdentifier)
                .build();
        return dbAnalyticsRepository.save(analytics);
    }





    @Override
    public Map<String,String> getImpactedTablesV2(String dbName, List<String> tenantIdentifierColumn) throws Exception {

        int tenantIdentifierColumnCount = tenantIdentifierColumn.size();
        Map<String,String> tableColumn = new LinkedHashMap<>();
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();

        StringBuilder s = new StringBuilder(" ? ");
        String query = "SELECT TABLE_NAME , COLUMN_NAME FROM information_schema.COLUMNS WHERE  TABLE_SCHEMA = ?  AND COLUMN_NAME in ( %s ) ";
        for (int i = 2; i <= tenantIdentifierColumnCount; i++) {
            s.append(" , ? ");
        }
        String finalQuery = String.format(query, s);

        try (PreparedStatement ps = connection.prepareStatement(finalQuery)) {
            ps.setString(1, dbName);
            ps.setString(2,tenantIdentifierColumn.get(0));
            for (int i =2; i<= tenantIdentifierColumnCount ;i++){
                ps.setString(i+1, tenantIdentifierColumn.get(i-1));
            }
            try(ResultSet resultSet = ps.executeQuery()){
                while (resultSet.next()){
                    if(!tableColumn.containsKey(resultSet.getString("TABLE_NAME"))){
                        tableColumn.put(resultSet.getString("TABLE_NAME"),resultSet.getString("COLUMN_NAME"));
                    }
                }
            }
            catch (SQLException ex){
                throw  new RuntimeException(ExceptionUtils.getRootCauseMessage(ex));

            }
        }
        catch (SQLException ex){
            throw  new RuntimeException(ExceptionUtils.getRootCauseMessage(ex));
        }
        DataSourceUtil.closeConnection(connection);
        return tableColumn;
    }

    @Override
    public Map<String, Long> getCountsAffectedV2(Long tenantId, String dbName, List<String> tenantIdentifierColumn, Set<String> tableNames)  throws  Exception{
        Map<String,Long> map = new HashMap<>();
        Map<String,String> tablesMap = this.getImpactedTablesV2(dbName,tenantIdentifierColumn);

        Set<String> tables = tablesMap.keySet();
        if(tableNames!=null){
            tables.retainAll(tableNames);
        }
        String affectedRowsQuery = tables.stream().map(it-> "Select '"+it+"' as TABLE_NAME, " +
                "count(*)  as COUNT from "+dbName+"."+it+" where "+tablesMap.get(it)+" = "+tenantId).collect(Collectors.joining( " union "));

        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        try (PreparedStatement ps = connection.prepareStatement(affectedRowsQuery)) {

            try (ResultSet resultSet = ps.executeQuery()){
                while (resultSet.next()) {
                    map.put(resultSet.getString("TABLE_NAME"),
                            resultSet.getLong("COUNT"));
                }
            }
            catch (SQLException ex){
                throw  new RuntimeException(ExceptionUtils.getRootCauseMessage(ex));
            }

        }
        catch (SQLException ex){
            throw  new RuntimeException(ExceptionUtils.getRootCauseMessage(ex));
        }
        DataSourceUtil.closeConnection(connection);
        map.forEach((k,v)-> System.out.println(k +" --> "+v));
       // buildAndSaveDBAnalytics(map,tenantId,tenantIdentifierColumn);
        return  map;
    }

    @Override
    public byte[] getDumpScriptsV2(String dbName, Long tenantId, List<String> tenantIdentifierColumn) throws Exception {
        StringBuilder backUpDir = new StringBuilder()
                .append("$BACKUP_DIR/").append(tenantId).append("/").append(dbName).append("/");

        String mysqlDumpFormat =  "mysqldump   --host=$HOST --port=$PORT --user=$USER --password=$PASSWORD  "+dbName+" %s  -t  " +
                " --complete-insert --where=\"%s in ( "+tenantId+" )\"  | gzip > $BACKUP_DIR/%s ";
        String mysqlSourceFormat =  "mysql --host=$HOST --port=$PORT --user=$USER --password=$PASSWORD  "+dbName
                +" < $BACKUP_DIR/%s ";

        String mysqlSourceFormatV2=  "zcat $BACKUP_DIR/%s | mysql --host=$HOST --port=$PORT --user=$USER --password=$PASSWORD  "+dbName;

        Map<String,String> tablesMap = this.getImpactedTablesV2(dbName,tenantIdentifierColumn);

        Set<String> tables = tablesMap.keySet();

        StringBuilder fileContentsForDump = new StringBuilder()
                .append("#!/bin/sh\n\n\n")
                .append("HOST=\"<host>\"\n" + "PORT=\"<port>\"\n" + "USER=\"<user>\"\n" + "PASSWORD=\"<password>\"\n" + "BACKUP_DIR=\"<backup_dir>/")
                .append(tenantId).append("/").append(dbName).append("/`date +%Y_%m_%d`\"\n").append("mkdir -p $BACKUP_DIR\n\n");

        StringBuilder fileContentForSourcing= new StringBuilder().append("#!/bin/sh");

        for (String table : tables){

            String fileName = table+".gz";
            fileContentsForDump.append("\n").append("echo \"Dumping  ").append(table).append(" \"").append("\n")
                    .append(String.format(mysqlDumpFormat, table, tablesMap.get(table),fileName));

//            fileContentForSourcing.append("\n").append("#echo \"Sourcing  ").append(table).append(" \"").append("\n")
//                    .append("#")
//                    .append(String.format(mysqlSourceFormat,fileName));



            fileContentForSourcing.append("\n").append("#echo \"Sourcing  ").append(table).append(" \"").append("\n")
                    .append("#")
                    .append(String.format(mysqlSourceFormatV2,fileName));
        }

        fileContentsForDump.append("\n\n\n\n");
        fileContentsForDump.append(fileContentForSourcing);
        InputStream stream = new ByteArrayInputStream(fileContentsForDump.toString().getBytes
                (StandardCharsets.UTF_8));
        return  IOUtils.toByteArray(stream);
    }

    @Override
    public byte[] getSqlFromChangeLog(MultipartFile file, Boolean generateRollback) {
        if(!file.getOriginalFilename().endsWith(".yml")){
            throw new RuntimeException("Invalid file format");
        }

        StringBuilder sqlQueries = new StringBuilder("-- Liquibase SQL\n");
        StringBuilder rollBackQueries = new StringBuilder("\n\n-- Liquibase Rollback SQL\n");

        InputStreamResourceAccessor resourceAccessor;
        try {
            resourceAccessor=new InputStreamResourceAccessor(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Database db = new MySQLDatabase();


        try(Liquibase liquibase = new Liquibase(file.getOriginalFilename(),resourceAccessor,new OfflineConnection())){

            DatabaseChangeLog changeLog=liquibase.getDatabaseChangeLog();
            List<ChangeSet> changeSets=changeLog.getChangeSets();
            if(generateRollback==null) generateRollback=false;

            for (ChangeSet changeSet : changeSets) {

                for (Change change : changeSet.getChanges()) {
                    Arrays.stream(SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(db),db))
                                .forEach(sql->sqlQueries.append(sql.toSql()).append("\n"));
                    if(generateRollback)
                       Arrays.stream(SqlGeneratorFactory.getInstance().generateSql(change.generateRollbackStatements(db),db))
                                 .forEach(sql->rollBackQueries.append(sql.toSql()).append("\n"));
                }
            }
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }

        if(generateRollback)
            sqlQueries.append(rollBackQueries);

        return sqlQueries.toString().getBytes(StandardCharsets.UTF_8);
    }
}
