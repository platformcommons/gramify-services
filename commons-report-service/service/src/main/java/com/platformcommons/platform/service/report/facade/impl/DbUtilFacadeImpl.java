package com.platformcommons.platform.service.report.facade.impl;


import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.report.application.DbUtilService;
import com.platformcommons.platform.service.report.facade.DbUtilFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class DbUtilFacadeImpl implements DbUtilFacade {

    @Autowired
    private DbUtilService dbUtilService;

    @Override
    public Set<String> getImpactedTables(String dbName, String tenantIdentifierColumn ) throws Exception {
        PlatformSecurityUtil.validatePlatformAdmin();
        return dbUtilService.getImpactedTables(dbName,tenantIdentifierColumn);
    }

    @Override
    public Map<String, String> getImpactedTablesV2(String dbName, List<String> tenantIdentifierColumn) throws Exception {
        PlatformSecurityUtil.validatePlatformAdmin();
        return dbUtilService.getImpactedTablesV2(dbName,tenantIdentifierColumn);
    }

    @Override
    public Map<String, Long> getCountsAffected(Long tenantId, String dbName,String tenantIdentifierColumn,Set<String> tableNames) throws Exception {
        PlatformSecurityUtil.validatePlatformAdmin();
        return dbUtilService.getCountsAffected(tenantId,dbName,tenantIdentifierColumn,tableNames);
    }

    @Override
    public byte[] getDumpScripts(String dbName, Long tenantId,String tenantIdentifierColumn) throws Exception {
        PlatformSecurityUtil.validatePlatformAdmin();
        return dbUtilService.getDumpScripts(dbName,tenantId,tenantIdentifierColumn);
    }

    @Override
    public byte[] getDumpScriptsV2(String dbName, Long tenantId, List<String> tenantIdentifierColumn) throws Exception {
        PlatformSecurityUtil.validatePlatformAdmin();
        return dbUtilService.getDumpScriptsV2(dbName,tenantId,tenantIdentifierColumn);
    }


    @Override
    public byte[] getDeleteScripts(String dbName, Long tenantId,String tenantIdentifierColumn) throws Exception {
        PlatformSecurityUtil.validatePlatformAdmin();
        return dbUtilService.generateDeleteScripts(dbName,tenantId,tenantIdentifierColumn);
    }

    @Override
    public Map<String, Long> getCountsAffectedV2(Long tenantId, String dbName, List<String> tenantIdentifierColumn, Set<String> tableNames) throws Exception {
        PlatformSecurityUtil.validatePlatformAdmin();
        return dbUtilService.getCountsAffectedV2(tenantId,dbName,tenantIdentifierColumn,tableNames);
    }

    @Override
    public byte[] getSqlFromChangeLog(MultipartFile file, Boolean rollBack) {
        return dbUtilService.getSqlFromChangeLog(file, rollBack);
    }

}
