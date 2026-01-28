package com.platformcommons.platform.service.app.controller;

import com.platformcommons.platform.service.app.client.PlatformAppVersionAPI;
import com.platformcommons.platform.service.app.domain.Enum.Platform;
import com.platformcommons.platform.service.app.dto.PlatformAppVersionDTO;
import com.platformcommons.platform.service.app.facade.PlatformAppVersionFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Tag(name = "PlatformAppVersion" )
public class PlatformAppVersionController implements PlatformAppVersionAPI {

    @Autowired
    private PlatformAppVersionFacade platformAppVersionFacade;

    @Override
    public ResponseEntity<Void> deletePlatformAppVersion(Long id, String reason) {
        platformAppVersionFacade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<PlatformAppVersionDTO> getPlatformAppVersion(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(platformAppVersionFacade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<PlatformAppVersionDTO>> getPlatformAppVersionPage(Integer page, Integer size) {
        PageDTO<PlatformAppVersionDTO> results= platformAppVersionFacade.getAllPage(page,size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @Override
    public ResponseEntity<Long> postPlatformAppVersion(PlatformAppVersionDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(platformAppVersionFacade.save(body));
    }

    @Override
    public ResponseEntity<PlatformAppVersionDTO> putPlatformAppVersion(PlatformAppVersionDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(platformAppVersionFacade.update(body));
    }

    @ApiOperation(value = "Get By latest PlatformAppVersion", nickname = "getLatestPlatformAppVersion", notes = "",
            response = PlatformAppVersionDTO.class, tags={ "PlatformAppVersion", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PlatformAppVersionDTO.class) })
    @RequestMapping(value = "/api/v1/platform-app-version/latest",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PlatformAppVersionDTO> getPlatformAppVersionLatest(@NotNull @Valid @RequestParam(value = "platform", required = true) Platform platform,
                                                                @NotNull @Valid @RequestParam(value = "identifier", required = true) String identifier
                                                                ){
        return ResponseEntity.status(HttpStatus.OK).body(platformAppVersionFacade.getPlatformAppVersionLatest(platform,identifier));
    }




}
