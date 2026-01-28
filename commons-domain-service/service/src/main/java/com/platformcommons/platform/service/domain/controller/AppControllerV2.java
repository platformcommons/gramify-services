package com.platformcommons.platform.service.domain.controller;

import com.platformcommons.platform.service.domain.dto.AppDTO;
import com.platformcommons.platform.service.domain.facade.AppFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "App")
@RequestMapping("api/v2/apps")
public class AppControllerV2 {


    @Autowired
    private AppFacade facade;

    @ApiOperation(value = "", tags = {"App"})
    @GetMapping
    public ResponseEntity<AppDTO> getById(@RequestParam(name = "id") String idOrSlug){
        return ResponseEntity.status(HttpStatus.OK).body(facade.getByIdOrSlug(idOrSlug));
    }
}
