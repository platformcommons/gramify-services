package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.iam.dto.TenantFinanceDetailDTO;
import com.platformcommons.platform.service.iam.facade.TenantFinanceDetailFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "TenantFinanceDetail")
public class TenantFinanceDetailController {

    @Autowired
    private TenantFinanceDetailFacade tenantFinanceDetailFacade;

    @ApiOperation(value = "Create For Logged In Tenant", tags = "TenantFinanceDetail")
    @PostMapping("/api/v1/tenant-finance-details")
    public ResponseEntity<TenantFinanceDetailDTO> createForLoggedInTenant(@RequestBody @Valid TenantFinanceDetailDTO dto) {
        TenantFinanceDetailDTO created = tenantFinanceDetailFacade.createForLoggedInTenant(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @ApiOperation(value = "Update For Logged In Tenant", tags = "TenantFinanceDetail")
    @PatchMapping("/api/v1/tenant-finance-details")
    public ResponseEntity<Void> patchForLoggedInTenant(@RequestBody @Valid TenantFinanceDetailDTO dto) {
        tenantFinanceDetailFacade.patchForLoggedInTenant(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Delete By Id For Logged In Tenant", tags = "TenantFinanceDetail")
    @DeleteMapping("/api/v1/tenant-finance-details/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id,@RequestParam String inactiveReason) {
        tenantFinanceDetailFacade.deleteById(id,inactiveReason);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Get Page By Tenant Id", tags = "TenantFinanceDetail")
    @GetMapping("/api/v1/tenant/{tenantId}/tenant-finance-details")
    public ResponseEntity<PageDTO<TenantFinanceDetailDTO>> getPageByTenantId(
            @PathVariable Long tenantId,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) String direction) {
        PageDTO<TenantFinanceDetailDTO> result = tenantFinanceDetailFacade.getPageByTenantId(tenantId, page, size, sortBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
