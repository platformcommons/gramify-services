package com.platformcommons.platform.service.app.controller;

import com.platformcommons.platform.service.app.client.AppVersionAPI;
import com.platformcommons.platform.service.app.domain.AppVersion;
import com.platformcommons.platform.service.app.dto.AppVersionDTO;
import com.platformcommons.platform.service.app.facade.AppVersionFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Tag(name = "AppVersion" )
public class AppVersionController implements AppVersionAPI {

    @Autowired
    private AppVersionFacade appVersionFacade;


    @Override
    public ResponseEntity<Void> deleteAppVersion(Long id, String reason) {
        appVersionFacade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<AppVersionDTO> getAppVersion(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(appVersionFacade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<AppVersionDTO>> getAppVersionPage(Integer page, Integer size) {
        PageDTO<AppVersionDTO> results= appVersionFacade.getAllPage(page-1,size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @Override
    public ResponseEntity<Void> postApiV1AppVersionAdd(Long appId,Set<AppVersionDTO> body) {
        appVersionFacade.addAppVersionToApp(appId,body);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Long> postAppVersion(AppVersionDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appVersionFacade.save(body));
    }

    @Override
    public ResponseEntity<AppVersionDTO> putAppVersion(AppVersionDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appVersionFacade.update(body));
    }

    @ApiOperation(value = "copyAndCreateAppVersion", nickname = "copyAndCreateAppVersion", notes = "", response = Long.class, tags={ "AppVersion", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Long.class) })
    @RequestMapping(value = "/api/v1/app-version/create-copy",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<Long> copyAndCreateAppVersion(@RequestParam("appVersionId") Long appVersionId, @RequestBody AppVersionDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appVersionFacade.copyAndCreateAppVersion(appVersionId,body));
    }


    @ApiOperation(value = "get AppVersionPage By AppId", nickname = "getAppVersionPageByappId", notes = "", response = AppVersion.class, tags={ "AppVersion", })
    @GetMapping("/api/v1/app-version/app/{appId}/page")
    public ResponseEntity<PageDTO<AppVersionDTO>> getAppVersionPageByAppId(
            @PathVariable("appId") Long appId,
            @RequestParam() Integer page,
            @RequestParam() Integer size)
    {
        PageDTO<AppVersionDTO> results= appVersionFacade.getAppVersionPageByAppId(appId,page-1,size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }
}
