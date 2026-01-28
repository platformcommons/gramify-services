package com.platformcommons.platform.service.app.controller;

import com.platformcommons.platform.service.app.client.AppMenuAPI;
import com.platformcommons.platform.service.app.domain.AppMenu;
import com.platformcommons.platform.service.app.dto.AppMenuDTO;
import com.platformcommons.platform.service.app.facade.AppMenuFacade;
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
import java.util.Set;


@RestController
@Tag(name = "AppMenu")
public class AppMenuController implements AppMenuAPI {

    @Autowired
    private AppMenuFacade facade;

    @Override
    public ResponseEntity<Long> addSubMenuToAppMenu(Long appMenuId, AppMenuDTO appSubMenuDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.addSubMenuToAppMenu(appMenuId,appSubMenuDTO));
    }

    @Override
    public ResponseEntity<Void> deleteAppMenu(Long id, String reason) {
        facade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<AppMenuDTO> getAppMenu(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<AppMenuDTO>> getAppMenuPage(Integer page, Integer size) {
        PageDTO<AppMenuDTO> results = facade.getAllPage(page,size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @Override
    public ResponseEntity<Long> postAppMenu(AppMenuDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(body));
    }

    @Override
    public ResponseEntity<AppMenuDTO> putAppMenu(AppMenuDTO body,Boolean isPatch) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.update(body,isPatch));
    }

    @Override
    public ResponseEntity<PageDTO<AppMenuDTO>> getAppMenuByAppCodeAppRoleAndMenuCode(String appCode, Integer page,
                                                                                     Integer size, String role, String menuCode) {
        PageDTO<AppMenuDTO> results = facade.getAppMenuByAppCodeAppRoleAndMenuCode(appCode,page,size,role,menuCode);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @Override
    public ResponseEntity<PageDTO<AppMenuDTO>> getAppMenuByIdOfAppRbac(Long rbacId, Integer page, Integer size) {
        PageDTO<AppMenuDTO> results = facade.getAppMenuByIdOfAppRbac(rbacId,page,size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @Override
    public ResponseEntity<AppMenuDTO> toggleAppMenu(String appCode, String role, String menuCode) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.toggleAppMenu(appCode,role,menuCode));
    }

    @Override
    public ResponseEntity<Void> toggleAppMenusInBulk(String appCode, String role, Set<AppMenuDTO> body) {
        facade.toggleAppMenusInBulk(appCode,role,body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @Override
    public ResponseEntity<AppMenuDTO> toggleAppSubMenu(String appCode, String role, String menuCode, String subMenuCode) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.toggleAppSubMenu(appCode,role,menuCode,subMenuCode));
    }

    @ApiOperation(value = "Get AppMenu By appCode,App role of AppRbac and menuCode and subMenuCode of AppMenu",
            nickname = "getAppMenuByAppCodeAppRoleAndMenuCodeAndSubMenu", notes = "", response = AppMenuDTO.class,
            responseContainer = "Set", tags={ "AppMenu", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AppMenuDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/app-menu/sub-menu-code",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<AppMenuDTO>> getAppMenuByAppCodeAppRoleAndMenuCodeAndSubMenu(@NotNull @Valid @RequestParam(value = "appCode", required = true) String appCode,
                                                                                        @NotNull  @Valid @RequestParam(value = "page", required = true) Integer page,
                                                                                        @NotNull  @Valid @RequestParam(value = "size", required = true) Integer size,
                                                                                        @Valid @RequestParam(value = "role", required = false) String role,
                                                                                        @Valid @RequestParam(value = "menuCode", required = false) String menuCode,
                                                                                        @Valid @RequestParam(value = "subMenuCode", required = false) String subMenuCode){
        PageDTO<AppMenuDTO> results = facade.getAppMenuByAppCodeAppRoleAndMenuCodeAndSubMenu(appCode,page,size,role,menuCode,subMenuCode);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }


}
