package com.platformcommons.platform.service.report.facade;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DbUtilFacade {


    Set<String> getImpactedTables(String dbName, String tenantIdentifierColumn) throws Exception;


    Map<String,String> getImpactedTablesV2(String dbName, List<String> tenantIdentifierColumn) throws Exception;

    Map<String, Long> getCountsAffected(Long tenantId, String dbName,String tenantIdentifierColumn,Set<String> tableName) throws Exception;


    byte[] getDumpScripts(String dbName, Long tenantId, String tenantIdentifierColumn) throws Exception;

    byte[] getDumpScriptsV2(String dbName, Long tenantId, List<String> tenantIdentifierColumn) throws Exception;

    byte[] getDeleteScripts(String dbName, Long tenantId, String tenantIdentifierColumn) throws Exception;

    Map<String, Long> getCountsAffectedV2(Long tenantId, String dbName, List<String> tenantIdentifierColumn, Set<String> tableNames) throws Exception;

    byte[] getSqlFromChangeLog(MultipartFile file, Boolean rollBack);
}