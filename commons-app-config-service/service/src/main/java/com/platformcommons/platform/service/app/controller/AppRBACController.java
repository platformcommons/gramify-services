package com.platformcommons.platform.service.app.controller;

import com.platformcommons.platform.service.app.client.AppRbacAPI;
import com.platformcommons.platform.service.app.domain.AppRbac;
import com.platformcommons.platform.service.app.domain.AppVersion;
import com.platformcommons.platform.service.app.dto.AppMenuDTO;
import com.platformcommons.platform.service.app.dto.AppOperationDTO;
import com.platformcommons.platform.service.app.dto.AppRbacDTO;
import com.platformcommons.platform.service.app.facade.AppRbacFacade;
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
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "AppRbac")
public class AppRBACController implements AppRbacAPI {

    @Autowired
    private AppRbacFacade facade;

    @Override
    public ResponseEntity<Void> addAppMenuToAppRbac(Long appRbacId, AppMenuDTO appMenuDTO) {
        facade.addAppMenuToAppRbac(appRbacId,appMenuDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> syncAdminRbacToChildRoles(String appCode,Set<String> adminRoleCodes) {
        facade.syncAdminRbacToChildRoles(appCode,adminRoleCodes);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> syncDefaultContextRbacToContextRbacs(String appCode, String context,String entityType) {
        facade.syncDefaultContextRbacToContextRbacs(appCode, context,entityType);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> syncDefaultRbacToAdminRbacs(String appCode, Set<String> adminRoleCodes) {
        facade.syncDefaultRbacToAdminRbacs(appCode,adminRoleCodes);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Override
    public ResponseEntity<Void> addAppOperationToAppRbac(Long appRbacId, AppOperationDTO appOperationDTO) {
        facade.addAppOperationToAppRbac(appRbacId,appOperationDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Override
    public ResponseEntity<AppRbacDTO> createRbacFromDefaultRbac(String appCode, String role, String marketContext) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.createRbacFromDefaultRbac(appCode,role,marketContext));
    }

    @Override
    public ResponseEntity<Void> deleteAppRbac(Long id, String reason) {
        facade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<AppRbacDTO> getAppRbac(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getById(id));
    }

    @Override
    public ResponseEntity<AppRbacDTO> getAppRbacByAppCodeAndContext(String appCode, String context,
                                                                                     String entityType, String marketContext) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getAppRbacByAppCodeAndContext(appCode,
                context,entityType,marketContext));
    }


    @Override
    public ResponseEntity<AppRbacDTO> getAppRbacByCodeAndRole(String appCode, String role,String marketContext, Boolean includeDisabled) {

        return ResponseEntity.status(HttpStatus.OK).body(facade.getByAppCodeAndRole(appCode,role,marketContext,includeDisabled));
    }


    @Override
    public ResponseEntity<AppRbacDTO> getAppRbacByAppcodeAndContextAndHierarchy(String appCode, String context, String marketContext,
                                                                                String entityType) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getAppRbacByAppcodeAndContextAndHierarchy(appCode, context,
                entityType,marketContext));
    }


    @Override
    public ResponseEntity<AppRbacDTO> getOrCreatePrimaryRbacByAppCode(String appCode, String marketContext) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getOrCreatePrimaryRbacByAppCode(appCode, marketContext));
    }

    @Override
    public ResponseEntity<AppRbacDTO> patchAppRbac(AppRbacDTO body, Boolean syncHigherRbacMenu, Boolean syncLowerRbacMenu) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.patchUpdate(body, syncHigherRbacMenu, syncLowerRbacMenu));
    }

    @Override
    public ResponseEntity<AppRbacDTO> getOrCreateAppRbacByAppCodeAndContext(String appCode,String context,
                                                                                             String entityType,String marketContext) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getOrCreateAppRbacByAppCodeAndContext(appCode,
                context,entityType,marketContext));
    }

    @Override
    public ResponseEntity<AppRbacDTO> getAppRbacByAppCodeAndMarketAndPrimaryRbacCheck(String appCode, String marketContext) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getAppRbacByAppCodeAndMarketAndPrimaryRbacCheck(appCode, marketContext));
    }


    @Override
    public ResponseEntity<PageDTO<AppRbacDTO>> getAppRbacPage(Integer page, Integer size) {
        PageDTO<AppRbacDTO> results = facade.getAllPage(page,size);
        return ResponseEntity.status(results.hasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }


    @ApiOperation(value = "Patch Bulk AppRbac", nickname = "patchAppRbacInBulk", notes = "", tags={ "AppRbac", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "NO CONTENT") })
    @RequestMapping(value = "/api/v1/app-rbac/bulk-patch",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<Void> patchAppRbacInBulk(@Valid @RequestBody Set<AppRbacDTO> appRbacDTOS){
        facade.patchAppRbacInBulk(appRbacDTOS);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Long> postAppRbac(AppRbacDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(body));
    }


    @Override
    public ResponseEntity<Long> copyRole(Long copyFromRbac, AppRbacDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.copy(copyFromRbac,body));
    }

    @Override
    public ResponseEntity<Long> createAppRbacByAppCodeAndContext(AppRbacDTO appRbacDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.createAppRbacByAppCodeAndContext(appRbacDTO));
    }

    @ApiOperation(value = "Check If RBAC exists for the roleCodes And AppContext",  response = Map.class, tags={ "AppRbac", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class) })
    @RequestMapping(value = "/api/v1/app-rbac/app-code/{appCode}/role-codes/exists",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Map<String,Boolean>> checkIfRbacExistsForRoleCodes(@PathVariable("appCode") String appCode,
                                                             @RequestParam Set<String> roleCodes,
                                                             @RequestParam(value = "marketContext", required = false) String marketContext) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.checkIfRbacExistsForRoleCodes(appCode,roleCodes,marketContext));
    }

    @ApiOperation(value = "get AppRbac Page By AppCode", nickname = "getAppRbacPageByAppCode", notes = "", response = AppRbac.class, tags={ "AppRbac", })
    @GetMapping("/api/v1/app-rbac/app/{appCode}/page")
    public ResponseEntity<PageDTO<AppRbacDTO>> getAppRbacPageByAppCode(
            @PathVariable("appCode") String appCode,
            @RequestParam() Integer page,
            @RequestParam() Integer size) {

        PageDTO<AppRbacDTO> results = facade.getAppRbacPageByAppCode(appCode,page-1,size);
        return ResponseEntity.status(results.hasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

}
