package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.iam.dto.*;
import com.platformcommons.platform.service.iam.facade.AuthenticationFacade;
import com.platformcommons.platform.service.iam.facade.TenantFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "tenant-controller")
public class TenantController {

    private final TenantFacade tenantFacade;
    private final AuthenticationFacade authenticationFacade;

    @ApiOperation(value = "Add Tenant")
    @PostMapping("/api/v1/tenants")
    public ResponseEntity<Long> addTenant(@RequestBody TenantDTO tenantDTO, @RequestHeader("X-PASS") String adminPass,
                                          @RequestParam(name = "appContext", required = false) String appContext,
                                          @RequestParam(name = "addInLinkedSystem", required = false) Boolean addInLinkedSystem) {
        log.info("TenantController.createTenant() .. {}", tenantDTO);
        if (addInLinkedSystem == null) {
            addInLinkedSystem = false;
        }
        Long tenantId = tenantFacade.addTenant(tenantDTO, adminPass, appContext,null, addInLinkedSystem,null);
        return new ResponseEntity<>(tenantId, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Tenant")
    @PutMapping("/api/v1/tenants")
    public ResponseEntity<Long> updateTenant(@RequestBody TenantDTO tenantDTO) {
        log.info("TenantController.updateTenant() .. {}", tenantDTO);
        tenantFacade.updateTenant(tenantDTO, false);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Patch Tenant")
    @PatchMapping("/api/v1/tenants")
    public ResponseEntity<Void> patchUpdateTenant(@RequestBody TenantDTO tenantDTO) {
        log.info("TenantController.patchUpdateTenant() .. {}", tenantDTO);
        tenantFacade.updateTenant(tenantDTO, true);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Get Current Logged In Tenant")
    @GetMapping("/api/v1/tenants/me")
    public ResponseEntity<TenantDTO> getCurrentTenant() {
        log.info("TenantController.getCurrentTenant() .. ");
        return new ResponseEntity<>(tenantFacade.getCurrentTenant(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get By Tenant Login")
    @GetMapping("/api/v1/tenants/login")
    public ResponseEntity<TenantDTO> getTenantByLogin(
            @RequestParam(name = "loginName") String tenantLogin) {
        log.info("TenantController.getTenantByLogin() .. {}", tenantLogin);
        return new ResponseEntity<>(tenantFacade.getTenantByLogin(tenantLogin), HttpStatus.OK);
    }

    @ApiOperation(value = "Get By TenantId")
    @GetMapping("/api/v1/tenants/{id}")
    public ResponseEntity<TenantDTO> getTenantById(
            @PathVariable(name = "id") Long id) {
        log.info("TenantController.getTenantById() .. {}", id);
        return new ResponseEntity<>(tenantFacade.getTenantById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Set iconpic")
    @PostMapping("/api/v1/tenants/logo")
    public void setIconpic(@RequestPart(value = "file") MultipartFile file) {
        log.info("Adding logo");
        tenantFacade.addTenantLogo(file);
    }

    @ApiOperation(value = "Exists Domain For Tenant")
    @GetMapping("/api/v1/tenants/exists/domain/check")
    public ResponseEntity<Boolean> existsDomainForTenant(@RequestParam(name = "domain") String domain) {
        return ResponseEntity.ok(tenantFacade.existsDomainForTenant(domain));
    }

    @ApiOperation(value = "Set cover image")
    @PostMapping("/api/v1/tenants/cover-image")
    public void setCoverImage(@RequestPart(value = "file") MultipartFile file) {
        log.info("Adding Cover Image");
        tenantFacade.addTenantCoverImage(file);
    }

    @ApiOperation(value = "Add Domain For Tenant")
    @PostMapping("/domain/{domain}")
    public ResponseEntity<Void> addDomainForTenant(@PathVariable(name = "domain")
                                                   String domain) {
        tenantFacade.addDomainForTenant(domain);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Add Tenant Solution Subscription V1")
    @PostMapping("/api/v1/tenants/solution")
    @Deprecated
    public ResponseEntity<Void> addTenantSolution(@RequestBody @Valid SolutionSubscriptionDTO solutionSubscriptionDTO) {
        tenantFacade.addTenantSolution(solutionSubscriptionDTO);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Add Tenant Solution Subscription V2")
    @PostMapping("/api/v2/tenants/solution")
    public ResponseEntity<Void> addTenantSolutionV2(@RequestBody @Valid SolutionSubscriptionDTO solutionSubscriptionDTO) {
        tenantFacade.addTenantSolutionV2(solutionSubscriptionDTO);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Get Tenant App Association")
    @GetMapping("/api/v1/tenants/solution/status")
    public ResponseEntity<Map<String, Object>> getTenantAppAssociation(@RequestParam String appContext) {
        return ResponseEntity.ok().body(tenantFacade.getTenantAppAssociationStatus(appContext));
    }

    @ApiOperation(value = "Create Tenant VMS")
    @PostMapping("/api/v1/tenants/signup/vms")
    public ResponseEntity<Void> createTenantVMS(@RequestHeader(name = "X-PASS") String adminPassword,
                                                @RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
        tenantFacade.createTenantVMS(signUpRequestDTO, adminPassword);
        String sessionId = PlatformSecurityUtil.getToken();
        return ResponseEntity.status(HttpStatus.CREATED).header(PlatformSecurityConstant.SESSIONID, sessionId).build();
    }

    @ApiOperation(value = "Get Market Config and Lead Present By Params")
    @GetMapping("/api/v1/tenants/exists/login-resources")
    public ResponseEntity<TenantLoginResourcesResponse> getMarketConfigsAndLeadPresentByParams(@RequestParam(name = "appContext") String appContext,
                                                                                               @RequestParam(name = "marketUUID") String marketUUID,
                                                                                               @RequestParam(name = "email", required = false) String email,
                                                                                               @RequestParam(name = "mobile", required = false) String mobile,
                                                                                               HttpServletRequest request) {
        Map<String, String> httpHeaders = new HashMap<>();
        String authorizationHeader = request.getHeader(PlatformSecurityConstant.AUTHORIZATION);
        if (authorizationHeader != null) {
            httpHeaders.put(PlatformSecurityConstant.AUTHORIZATION, authorizationHeader);
        }
        String sessionIdHeader = request.getHeader(PlatformSecurityConstant.SESSIONID);
        if (sessionIdHeader != null) {
            httpHeaders.put(PlatformSecurityConstant.SESSIONID, sessionIdHeader);
        }
        TenantLoginResourcesResponse response = tenantFacade.getMarketConfigsAndLeadPresentByParams(appContext, email, mobile, marketUUID,
                httpHeaders);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @ApiOperation(value = "Add Attachments To Tenant", response = AttachmentDTO.class)
    @PostMapping("api/v1/tenants/attachments")
    public ResponseEntity<AttachmentDTO> addAttachmentToTenant(@RequestPart(value = "file") MultipartFile file,
                                                               @RequestParam(value = "attachmentKind", required = false) String attachmentKind,
                                                               @RequestParam(value = "entityType", required = false) String entityType,
                                                               @RequestParam(value = "attachmentKindMeta") String attachmentKindMeta,
                                                               @RequestParam(value = "attachmentKindIdentifier") String attachmentKindIdentifier,
                                                               @RequestParam(value = "isPublic", required = false) Boolean isPublic,
                                                               @RequestParam(value = "name", required = false) String name) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tenantFacade.addAttachmentToTenant(file, attachmentKind, entityType,
                attachmentKindMeta, attachmentKindIdentifier, isPublic, name));
    }

    @ApiOperation(value = "Get Tenant By Slug")
    @GetMapping("/api/v1/tenants/slug")
    public ResponseEntity<TenantDTO> getTenantBySlug(@RequestParam(name = "slug") String slug) {
        return ResponseEntity.status(HttpStatus.OK).body(tenantFacade.getTenantBySlug(slug));
    }

    @ApiOperation(value = "Delete Attachment From Tenant")
    @DeleteMapping("/api/v1/tenants/attachment")
    public ResponseEntity<Void> deleteAttachmentFromTenant(@RequestParam(name = "attachment_id") Long attachmentId,
                                                           @RequestParam(name = "reason") String reason) {
        tenantFacade.deleteAttachmentFromTenant(attachmentId, reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Check For Existing Slug")
    @GetMapping("/api/v1/tenants/check/slug")
    public ResponseEntity<Boolean> checkForExistingSlug(@RequestParam(name = "tenantId") Long tenantId,
                                                        @RequestParam(name = "slug") String slug) {
        return ResponseEntity.status(HttpStatus.OK).body(tenantFacade.checkForExistingSlug(tenantId, slug));
    }

    @PostMapping("/api/v1/tenants/partners/user")
    public ResponseEntity<?> convertToTenant(@RequestBody TenantDTO tenantDTO,
                                             @RequestParam(name = "context") String context,
                                             @RequestParam(name = "isPrimary") Boolean isPrimary,
                                             @RequestParam(name = "contextValue") String contextValue) {
        PlatformToken platformTokenActual = PlatformSecurityUtil.getContext();
        Long crossUserId = tenantFacade.convertToTenant(tenantDTO, context,contextValue, platformTokenActual,isPrimary);
        String sessionId = authenticationFacade.crossUserId(crossUserId, platformTokenActual, tenantDTO.getTenantLogin());
        return ResponseEntity.status(HttpStatus.ACCEPTED).header(PlatformSecurityConstant.SESSIONID, sessionId).build();
    }

    @GetMapping("/api/v1/tenants/partners")
    public ResponseEntity<Set<TenantPartnerVO>> getTenantPartners() {
        Set<TenantPartnerVO> tenants = tenantFacade.getTenantPartners();
        String sessionId = authenticationFacade.crossUserId(PlatformSecurityUtil.getContext(),tenants);
        return ResponseEntity.status(HttpStatus.OK).header(PlatformSecurityConstant.SESSIONID, sessionId).body(tenants);
    }

    @ApiOperation(value = "Get Tenant By TenantId And Load Into Cache", nickname = "getTenantByTenantIdAndLoadCache", notes = "", response = TenantDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TenantDTO.class) })
    @RequestMapping(value = "/api/v1/tenant/cache",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<TenantDTO> getTenantByTenantIdAndLoadCache(@NotNull @Valid @RequestParam(value = "tenantId") Long tenantId){
        return ResponseEntity.status(HttpStatus.OK).body(tenantFacade.getTenantByTenantIdAndLoadCache(tenantId));
    }

    @ApiOperation(value = "Delete Tenant Cache By TenantId", nickname = "deleteTenantCacheByTenantId", notes = "Deletes the cache for a specific tenant id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cache cleared successfully") })
    @RequestMapping(value = "/api/v1/tenant/cache",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTenantCache(@RequestParam(value = "tenantId", required = false) Long tenantId) {
        tenantFacade.deleteTenantCacheByTenantId(tenantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Delete All Tenant Cache", nickname = "deleteAllTenantCache", notes = "Deletes all tenant cache.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All cache cleared successfully") })
    @RequestMapping(value = "/api/v1/tenant/cache/all",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAllTenantCache() {
        tenantFacade.deleteAllTenantCache();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
