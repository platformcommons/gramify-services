package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.service.iam.dto.IAMUserDTO;
import com.platformcommons.platform.service.iam.dto.UserConsentDTO;
import com.platformcommons.platform.service.iam.dto.UserMetaDataDTO;
import com.platformcommons.platform.service.iam.facade.UserFacade;
import com.platformcommons.platform.service.iam.facade.UserRoleMapFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "user-controller")
public class UserController {

    private final UserFacade userFacade;
    private final UserRoleMapFacade userRoleMapFacade;


    @PostMapping
    public ResponseEntity<Long> addUser(@RequestBody @Valid IAMUserDTO iamUserDTO, @RequestHeader(name = "X-PASS") String password,
                                        @RequestParam(name = "addInLinkedSystem", required = false) Boolean addInLinkedSystem) {
        log.trace("addUser() method accessed");
        if (null == addInLinkedSystem) addInLinkedSystem = Boolean.FALSE;
        return ResponseEntity.ok(userFacade.addUser(iamUserDTO, password, addInLinkedSystem));
    }

    @ApiOperation(value = "Return logged in user if both user-id and user-login is null. If both are present, preference is given to userId",  response = UserConsentDTO.class,responseContainer = "Set")
    @GetMapping
    public ResponseEntity<IAMUserDTO> getUser(@RequestParam(name = "userId", required = false) Long userId,
                                              @RequestParam(name = "userLogin", required = false) String userLogin,
                                              @RequestParam(name = "includeRoles", required = false) Boolean includeRole
    ) {
        log.trace("getUser() method accessed");
        if(includeRole == null) includeRole = Boolean.FALSE;
        return ResponseEntity.status(HttpStatus.OK).body(userFacade.getUser(userId,userLogin, includeRole));
    }


    @PostMapping("/role")
    @ApiOperation(value = "Assign role to user")
    public ResponseEntity<Void> assignRoleToUser(@RequestParam(name = "userId", required = false) Long userId,
                                                 @RequestParam(name = "userLogin", required = false) String userLogin
            , @RequestParam Set<String> roleCodes) {
        log.trace("assignRoleToUser() method accessed");
        userRoleMapFacade.assignRolesToUser(userId, userLogin, roleCodes);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @PatchMapping
    public ResponseEntity<IAMUserDTO> patch(@RequestBody @Valid IAMUserDTO iamUserDTO) {
        return ResponseEntity.ok(userFacade.patch(iamUserDTO));
    }

    @PostMapping(value = "/join/{tenant_login}")
    public ResponseEntity<IAMUserDTO> joinToTenancy(
                                        @RequestBody  Set<UserMetaDataDTO> userMetaData,
                                        @PathVariable(name = "tenant_login") String tenantLogin,
                                        @RequestParam(name = "appContext", required = false) String appContext,
                                        @RequestParam(name = "addInLinkedSystem", required = false) Boolean addInLinkedSystem) {
        log.trace("joinToTenancy() method accessed");
        if (null == addInLinkedSystem) addInLinkedSystem = Boolean.FALSE;
        return ResponseEntity.ok(userFacade.joinToTenancy(userMetaData,tenantLogin, appContext,addInLinkedSystem));
    }

}
