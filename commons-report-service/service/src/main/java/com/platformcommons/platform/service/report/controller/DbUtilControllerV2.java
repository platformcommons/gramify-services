package com.platformcommons.platform.service.report.controller;

import com.platformcommons.platform.service.report.facade.DbUtilFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v2/db-util")
public class DbUtilControllerV2 {

    @Autowired
    private DbUtilFacade facade;



    @GetMapping("/impact-tables-columns/{dbName}")
    public ResponseEntity<Map<String,String>> getImpactedTablesV2(@PathVariable String dbName, @RequestParam List<String> tenantIdentifierColumns) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getImpactedTablesV2(dbName,tenantIdentifierColumns));
    }

    @GetMapping("/impact-table-row-counts/{dbName}")
    public ResponseEntity<Map<String,Long>> getImpactedRowCounts(@PathVariable String dbName,@RequestParam Long tenantId,
                                                                 @RequestParam List<String> tenantIdentifierColumn,
                                                                 @RequestParam(required = false) Set<String> tableNames) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getCountsAffectedV2(tenantId,dbName,tenantIdentifierColumn,tableNames));
    }

    @GetMapping("/dump-scripts/{dbName}")
    public ResponseEntity<byte[]> getDumpScripts(@PathVariable String dbName,@RequestParam Long tenantId,
                                                 @RequestParam List<String> tenantIdentifierColumn) throws Exception {
        String fileName = dbName+new Date().getTime()+"_backup_v2.sh";
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(facade.getDumpScriptsV2(dbName,tenantId,tenantIdentifierColumn));
    }


}
