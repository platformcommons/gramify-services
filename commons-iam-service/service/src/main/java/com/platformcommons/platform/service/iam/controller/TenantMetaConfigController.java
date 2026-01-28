package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaAdditionalPropertyDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.iam.facade.TenantMetaConfigFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@Slf4j
@Tag(name = "tenant-meta-config-controller")
public class TenantMetaConfigController {

    @Autowired
    private TenantMetaConfigFacade facade;

    @ApiOperation(value = "Add TenantMetaAdditionalProperties", nickname = "addTenantMetaConfigProperties", tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content") })
    @RequestMapping(value = "/api/v1/tenant-meta-config/additional-properties/me",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> addTenantMetaConfigProperties(@NotNull @Valid @RequestParam(value = "tenantLogin", required = true) String tenantLogin, @Valid @RequestBody Set<TenantMetaAdditionalPropertyDTO> body){
        facade.addTenantMetaAdditionalProperty(tenantLogin, body);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Patch TenantMetaAdditionalProperties", nickname = "patchTenantMetaConfigProperties", tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content") })
    @RequestMapping(value = "/api/v1/tenant-meta-config/additional-properties/me",
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<Void> patchTenantMetaConfigProperties(@NotNull @Valid @RequestParam(value = "tenantLogin", required = true) String tenantLogin, @Valid @RequestBody Set<TenantMetaAdditionalPropertyDTO> body){
        facade.patchTenantMetaAdditionalProperty(tenantLogin, body);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Get TenantMetaAdditionalProperties By MetaKeys And TenantLogin", nickname = "getTenantMetaConfigForLoggedInTenant", notes = "", response = TenantMetaConfigDTO.class,tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TenantMetaAdditionalPropertyDTO.class) })
    @RequestMapping(value = "/api/v1/tenant-meta-config/additional-properties",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Set<TenantMetaAdditionalPropertyDTO>> getTenantMetaAdditionalPropertiesByMetaKeysAndTenantLogin(
            @RequestParam(name = "metaKeys") Set<String> metaKeys,
            @RequestParam(name = "tenantLogin") String tenantLogin,
            @RequestParam(name = "appContext",required = false) String appContext){
        return ResponseEntity.status(HttpStatus.OK).body(facade.getTenantMetaAdditionalPropertiesByMetaKeysAndTenantLogin(
                metaKeys,tenantLogin,appContext));
    }

    @ApiOperation(value = "Delete TenantMetaConfig By Id", nickname = "deleteTenantMetaConfig",tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content") })
    @RequestMapping(value = "/api/v1/tenant-meta-config",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTenantMetaConfig(@NotNull  @Valid @RequestParam(value = "id", required = true) Long id,@NotNull  @Valid @RequestParam(value = "reason", required = true) String reason){
        facade.deleteTenantMetaConfig(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Get By Id", nickname = "getTenantMetaConfig", notes = "", response = TenantMetaConfigDTO.class,tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TenantMetaConfigDTO.class) })
    @RequestMapping(value = "/api/v1/tenant-meta-config",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<TenantMetaConfigDTO> getTenantMetaConfig(@NotNull  @Valid @RequestParam(value = "id", required = true) Long id){
        return ResponseEntity.status(HttpStatus.OK).body(facade.getById(id));
    }

    @ApiOperation(value = "Patch TenantMetaConfig For Logged In Tenant", nickname = "patchTenantMetaConfig", notes = "", response = TenantMetaConfigDTO.class,tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TenantMetaConfigDTO.class) })
    @RequestMapping(value = "/api/v1/tenant-meta-config/me",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<TenantMetaConfigDTO> patchTenantMetaConfigForLoggedInTenant(@Valid @RequestBody TenantMetaConfigDTO body){
        return ResponseEntity.status(HttpStatus.OK).body(facade.patchTenantMetaConfigForLoggedInTenant(body));

    }
    @ApiOperation(value = "Post TenantMetaConfig For Logged In Tenant", nickname = "postTenantMetaConfig", notes = "", response = Long.class,tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Long.class) })
    @RequestMapping(value = "/api/v1/tenant-meta-config/me",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Long> postTenantMetaConfigForLoggedInTenant(@Valid @RequestBody TenantMetaConfigDTO body){
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.postTenantMetaConfigForLoggedInTenant(body));
    }

    @ApiOperation(value = "Get TenantMetaConfig For Logged In Tenant", nickname = "getTenantMetaConfigForLoggedInTenant", notes = "", response = TenantMetaConfigDTO.class,tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TenantMetaConfigDTO.class) })
    @RequestMapping(value = "/api/v1/tenant-meta-config/me",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<TenantMetaConfigDTO> getTenantMetaConfigForLoggedInTenant(){
        return ResponseEntity.status(HttpStatus.OK).body(facade.getForLoggedInTenant());
    }


    @ApiOperation(value = "Get Tenant Meta Config By TenantLogin And Load Into Cache", nickname = "getTenantMetaConfigByTenantLoginAndLoadCache", notes = "", response = TenantMetaConfigDTO.class,tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TenantMetaConfigDTO.class) })
    @RequestMapping(value = "/api/v1/tenant-meta-config/cache",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<TenantMetaConfigDTO> getTenantMetaConfigByTenantLoginAndLoadCache(@NotNull  @Valid @RequestParam(value = "tenantLogin") String tenantLogin){
        return ResponseEntity.status(HttpStatus.OK).body(facade.getTenantMetaConfigByTenantLoginAndLoadCache(tenantLogin));
    }

    @ApiOperation(value = "Delete Tenant Meta Config Cache By TenantLogin", nickname = "deleteTenantMetaConfigCacheByTenantLogin", notes = "Deletes the cache for a specific tenant login.",tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cache cleared successfully") })
    @RequestMapping(value = "/api/v1/tenant-meta-config/cache",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTenantMetaConfigCache(@RequestParam(value = "tenantLogin", required = false) String tenantLogin) {
        facade.deleteTenantMetaConfigCacheByTenantLogin(tenantLogin);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Delete All Tenant Meta Config Cache", nickname = "deleteAllTenantMetaConfigCache", notes = "Deletes all tenant meta configuration cache.",tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All cache cleared successfully") })
    @RequestMapping(value = "/api/v1/tenant-meta-config/cache/all",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAllTenantMetaConfigCache() {
        facade.deleteAllTenantMetaConfigCache();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Get All Tenant Meta Config ",tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success") })
    @RequestMapping(value = "/api/v1/tenant-meta-config/all/page",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<PageDTO<TenantMetaConfigDTO>> getAllTenantMetaConfig(@RequestParam(value = "metaKey",required = false) String metaKey,
                                                                               @RequestParam(value = "metaValue",required = false) String metaValue,
                                                                               @RequestParam(value = "page") Integer page,
                                                                               @RequestParam(value = "size") Integer size,
                                                                               @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                               @RequestParam(value = "direction", required = false) String direction) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getAllTenantMetaConfig(metaKey,metaValue, page, size, sortBy, direction));
    }

    @ApiOperation(value = "Get All Tenant Meta Config ",tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success") })
    @RequestMapping(value = "/api/v1/tenant-meta-config/all",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<Set<TenantMetaConfigDTO>> getAllTenantMetaConfigSet(@RequestParam(value = "metaKey",required = false) String metaKey,
                                                                               @RequestParam(value = "metaValue",required = false) String metaValue) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getAllTenantMetaConfigSet(metaKey,metaValue));
    }

    @ApiOperation(value = "Get TenantMetaConfig By TenantLogin", nickname = "getTenantMetaConfigTenantLogin", notes = "", response = TenantMetaConfigDTO.class,tags = {"tenant-meta-config-controller"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TenantMetaConfigDTO.class) })
    @RequestMapping(value = "/api/v1/tenant-meta-config/tenant-login",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<TenantMetaConfigDTO> getTenantMetaConfigByTenantLogin(@RequestParam(value = "tenantLogin") String tenantLogin){
        return ResponseEntity.status(HttpStatus.OK).body(facade.getTenantMetaConfig(null,tenantLogin));
    }

}
