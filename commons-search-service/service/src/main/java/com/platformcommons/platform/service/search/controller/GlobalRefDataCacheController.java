package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.search.facade.cache.GlobalRefDataCacheManager;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "GlobalRefDataCache")
public class GlobalRefDataCacheController {

    @Autowired(required = false)
    private GlobalRefDataCacheManager refDataCacheManager;

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/v1/global-ref-data-cache/code")
    @ApiOperation(value = "Clear cache By Code or clear all Cache by not entering code",tags = {"RefDataCache"})
    public ResponseEntity<Void> clearCacheByCode(@RequestParam(required = false) String refDataCode) {
        if(refDataCacheManager != null) {
            refDataCacheManager.clearCache(refDataCode);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/v1/global-ref-data-cache")
    @ApiOperation(value = "Clear All Cache",tags = {"RefDataCache"})
    public ResponseEntity<Void> clearAllCache() {
        if(refDataCacheManager != null) {
            refDataCacheManager.clearAllCache();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
