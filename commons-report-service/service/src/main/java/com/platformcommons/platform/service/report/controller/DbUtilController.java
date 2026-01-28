package com.platformcommons.platform.service.report.controller;

import com.platformcommons.platform.service.report.facade.DbUtilFacade;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/v1/db-util")
@Slf4j
public class DbUtilController {

    @Autowired
    private DbUtilFacade facade;
    @Autowired
    private ResourceLoader loader;

    @GetMapping("/impact-tables/{dbName}")
    public ResponseEntity<Set<String>> getImpactedTables(@PathVariable String dbName, @RequestParam String tenantIdentifierColumn) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getImpactedTables(dbName,tenantIdentifierColumn));
    }

    @GetMapping("/impact-table-row-counts/{dbName}")
    public ResponseEntity<Map<String,Long>> getImpactedRowCounts(@PathVariable String dbName,@RequestParam Long tenantId,
                                                                 @RequestParam String tenantIdentifierColumn,
                                                                 @RequestParam(required = false) Set<String> tableNames) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getCountsAffected(tenantId,dbName,tenantIdentifierColumn,tableNames));
    }

    @GetMapping("/dump-scripts/{dbName}")
    public ResponseEntity<byte[]> getDumpScripts(@PathVariable String dbName,@RequestParam Long tenantId,
                                                 @RequestParam String tenantIdentifierColumn) throws Exception {
        String fileName = dbName+new Date().getTime()+"_backup.sh";
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(facade.getDumpScripts(dbName,tenantId,tenantIdentifierColumn));
    }

    @GetMapping("/delete-scripts/{dbName}")
    public ResponseEntity<byte[]> getDeleteScripts(@PathVariable String dbName,@RequestParam Long tenantId,
                                                   @RequestParam String tenantIdentifierColumn) throws Exception {
        String fileName = dbName+new Date().getTime()+"_delete.sql";
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(facade.getDeleteScripts(dbName,tenantId,tenantIdentifierColumn));
    }

    @PostMapping("/sql-generate")
    public ResponseEntity<byte[]> getSql(@RequestPart MultipartFile file, @RequestParam(required = false) Boolean rollBack){

        return ResponseEntity.ok(facade.getSqlFromChangeLog(file,rollBack));
    }

}
