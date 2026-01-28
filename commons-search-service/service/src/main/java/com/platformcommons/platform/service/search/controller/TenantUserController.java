package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.client.TenantUserAPI;
import com.platformcommons.platform.service.search.dto.SearchDTO;
import com.platformcommons.platform.service.search.dto.TenantUserDTO;
import com.platformcommons.platform.service.search.facade.TenantUserFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "TenantUser")
public class TenantUserController implements TenantUserAPI {

    @Autowired
    TenantUserFacade tenantUserFacade;
    @Override
    public ResponseEntity<PageDTO<TenantUserDTO>> readTenantUser(String keyword) {
        return new ResponseEntity<>(tenantUserFacade.readTenantUser(keyword), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageDTO<TenantUserDTO>> readTenantUserByDetails(SearchDTO body) {
        return new ResponseEntity<>(tenantUserFacade.readTenantUserByDetails(body), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TenantUserDTO> saveTenantUser(TenantUserDTO body) {
        return new ResponseEntity<>(tenantUserFacade.saveTenantUser(body), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateTenantUser(TenantUserDTO body) {
        return new ResponseEntity<>(tenantUserFacade.updateTenantUser(body), HttpStatus.OK);
    }

    @ApiOperation(value = "", nickname = "uploadTenantUser", notes = "", response = TenantUserDTO.class, responseContainer = "Set", tags={ "TenantUser", })
    @PostMapping("api/v1/tenant-users/upload")
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
        tenantUserFacade.readExcel(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
