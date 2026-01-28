package com.platformcommons.platform.service.domain.controller;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.domain.dto.AuthorDTO;
import com.platformcommons.platform.service.domain.facade.AuthorFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Author")
@RequestMapping("api/v1/authors")
public class AuthorController {

    @Autowired
    private AuthorFacade facade;

    @ApiOperation(value = "", tags = {"Author"})
    @PostMapping
    public ResponseEntity<Long> createAuthor(@RequestBody AuthorDTO authorDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(authorDTO));
    }


    @ApiOperation(value = "", tags = {"Author"})
    @GetMapping("/tenant}")
    public ResponseEntity<AuthorDTO> getAuthorByTenant(@RequestParam(required = false,name = "tenantId") Long tenantId){
        tenantId = tenantId!=null?tenantId: PlatformSecurityUtil.getCurrentTenantId();
        return ResponseEntity.status(HttpStatus.OK).body(facade.getAuthorByTenant(tenantId));
    }

}
