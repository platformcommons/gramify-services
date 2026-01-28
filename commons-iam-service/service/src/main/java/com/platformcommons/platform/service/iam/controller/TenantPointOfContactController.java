package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.iam.dto.TenantFinanceDetailDTO;
import com.platformcommons.platform.service.iam.dto.TenantPointOfContactDTO;
import com.platformcommons.platform.service.iam.facade.TenantPointOfContactFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "TenantPointOfController")
public class TenantPointOfContactController {

    @Autowired
    private TenantPointOfContactFacade tenantPointOfContactFacade;

    @ApiOperation(value = "Get For Logged In Tenant", tags = "TenantPointOfController")
    @GetMapping("/api/v1/tenant-point-of-contact/{id}")
    public ResponseEntity<TenantPointOfContactDTO> getForLoggedInTenant(@PathVariable Long id) {
        TenantPointOfContactDTO tenantPointOfContactDTO = tenantPointOfContactFacade.getForLoggedInTenant(id);
        return ResponseEntity.status(HttpStatus.OK).body(tenantPointOfContactDTO);
    }

    @ApiOperation(value = "Create For Logged In Tenant", tags = "TenantPointOfController")
    @PostMapping("/api/v1/tenant-point-of-contact")
    public ResponseEntity<TenantPointOfContactDTO> createForLoggedInTenant(@RequestBody @Valid TenantPointOfContactDTO tenantPointOfContactDTO) {
        TenantPointOfContactDTO created = tenantPointOfContactFacade.createForLoggedInTenant(tenantPointOfContactDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @ApiOperation(value = "Update For Logged In Tenant", tags = "TenantPointOfController")
    @PatchMapping("/api/v1/tenant-point-of-contact")
    public ResponseEntity<Void> patchForLoggedInTenant(@RequestBody @Valid TenantPointOfContactDTO tenantPointOfContactDTO) {
        tenantPointOfContactFacade.patchForLoggedInTenant(tenantPointOfContactDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Delete By Id For Logged In Tenant", tags = "TenantPointOfController")
    @DeleteMapping("/api/v1/tenant-point-of-contact/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id,@RequestParam String inactiveReason) {
        tenantPointOfContactFacade.deleteById(id,inactiveReason);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Get By Tenant Id", tags = "TenantPointOfController")
    @GetMapping("/api/v1/tenant/{tenantId}/tenant-point-of-contact")
    public ResponseEntity<PageDTO<TenantPointOfContactDTO>> getPageByTenantId(
            @PathVariable Long tenantId,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) String direction) {
        PageDTO<TenantPointOfContactDTO> result = tenantPointOfContactFacade.getPageByTenantId(tenantId, page, size, sortBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
