package com.platformcommons.platform.service.iam.facade.client;

import com.mindtree.bridge.platform.dto.RoleDTO;
import com.mindtree.bridge.platform.dto.UserRoleMapDTO;
import com.mindtree.bridge.platform.dto.UserWrapperDTO;
import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.iam.domain.vo.UserVO;
import com.platformcommons.platform.service.iam.dto.brbase.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(
        name = "TLDClient",
        url = "${commons.platform.tld.base-url}"
)
public interface TLDClient {

    @PostMapping("ctld/api/session/v1")
    ResponseEntity<Object> login(@RequestHeader("X-USER") String userName,
                                 @RequestHeader("X-PASS") String password,
                                 @RequestHeader(value = "X-APPID",required = false) String appId,
                                 @RequestHeader(value = "X-APPKEY", required = false) String appKey,
                                 @RequestHeader(value = "X-APPVERSION", required = false) String appVersion,
                                 @RequestHeader(value = "X-DEVICEID", required = false) String deviceId
    );

    @PostMapping("ctld/api/session/v3")
    ResponseEntity<Object> socialLogin(@RequestHeader("Authorization") String token,
                                 @RequestHeader(value = "X-APPID",required = false) String appId,
                                 @RequestHeader(value = "X-APPKEY", required = false) String appKey,
                                 @RequestHeader(value = "X-APPVERSION", required = false) String appVersion,
                                 @RequestHeader(value = "X-DEVICEID", required = false) String deviceId,
                                 @RequestParam(value = "platform", required = true) String platform,
                                 @RequestParam(value = "createUser", required = false) Boolean createUser,
                                 @RequestParam(value = "providerTenantId", required = false) String providerTenantId,
                                       @RequestParam(value = "tenantLogin", required = false) String tenantLogin


    );

    @GetMapping("ctld/api/tenant/user/v1")
    ResponseEntity<UserDTO> getLoggedInUserDetails(@RequestHeader(PlatformSecurityConstant.SESSIONID)String sessionId);

    @GetMapping("ctld/api/tenant/user/v1")
    ResponseEntity<UserDTO> getUserDetails(@RequestHeader(PlatformSecurityConstant.SESSIONID)String sessionId,
                                           @RequestParam(value = "userId", required = false) Integer userId,
                                           @RequestParam(value = "login", required = false) String login);

    @GetMapping("ctld/api/tenant/user/v1")
    ResponseEntity<com.mindtree.bridge.platform.dto.UserDTO>getBridgeUserDetails(@RequestHeader("X-SESSIONID")String sessionId,
                                                                                 @RequestParam(value = "userId", required = false) Integer userId,
                                                                                 @RequestParam(value = "login", required = false) String login);


    @PostMapping("ctld/api/user/selfregistration/v1")
    ResponseEntity<UserSelfRegistrationDTO> selfRegistration(@RequestHeader("X-PASS") String password,
                                                             @RequestHeader("X-APPID") String appId,
                                                             @RequestParam("tenantName") String tenantName,
                                                             @RequestBody UserSelfRegistrationDTO userSelfRegistrationDTO,
                                                             @RequestParam("sendAndAuthenticateDifferentOTPForEmailAndSms") Boolean sendAndAuthenticateDifferentOTPForEmailAndSms);

    @GetMapping("ctld/api/user/selfregistration/v1/activateinactive")
    ResponseEntity<UserSelfRegistrationDTO> activateInactive(@RequestParam("userName") String userName,
                                                             @RequestParam("tenantName") String tenantName,
                                                             @RequestHeader("X-APPID") String appId,
                                                             @RequestParam("appContext") String appContext,
                                                             @RequestParam("sendOTPForEmail") Boolean sendOTPForEmail);

    @GetMapping("/ctld/api/user/selfregistration/v1/activate")
    ResponseEntity<String> activateUser(@RequestHeader("otp") String otp,
                                        @RequestHeader("modKey") String modKey,
                                        @RequestHeader("tenantName") String tenantName,
                                        @RequestParam("userId") Integer userId,
                                        @RequestParam("isOTPAndModKeyOfEmail") Boolean isOTPAndModKeyOfEmail,
                                        @RequestParam("skipUserActiveValidation") Boolean skipUserActiveValidation);

    @GetMapping("/ctld/api/session/v1/pass")
    ResponseEntity<String> pass(@RequestHeader("X-USER") String userLogin);

    @PutMapping("/ctld/api/session/v1/pass")
    ResponseEntity<String> pass(@RequestHeader("X-USER") String userLogin,
                                @RequestHeader("X-NEWPASS") String newPassword,
                                @RequestParam("key") String key,
                                @RequestParam("otp") String otp);

    @GetMapping("ctld/api/session/v1/otp")
    ResponseEntity<String> getOTP(@RequestHeader("X-USER") String userLogin,
                                  @RequestParam(value = "appContext") String appContext,
                                  @RequestHeader("X-APPID") String appId);


    @GetMapping("ctld/api/user/selfregistration/v1/check")
    ResponseEntity<String> checkUser(@RequestParam("tenantName") String tenantName,
                                     @RequestParam("loginName") String loginName);


    @PostMapping("ctld/api/crosstenant/login/v1")
    ResponseEntity<String> crossTenantLogin(@RequestParam("TENANT") String tenant,
                                            @RequestHeader(value = "X-APPID",required = false) String appId,
                                            @RequestHeader(value = "X-APPKEY",required = false) String appKey,
                                            @RequestHeader(value = "X-APPVERSION",required = false) String appVersion,
                                            @RequestHeader(value = "X-DEVICEID",required = false) String deviceId,
                                            @RequestHeader(value = PlatformSecurityConstant.SESSIONID) String sessionId);


    @PostMapping("ctld/api/tenant/v1")
    TLDTenantDTO addTenant(@RequestBody TLDTenantDTO tldTenantDTO,
                           @RequestHeader(name = "X-ADMINPASS") String adminPass,
                           @RequestHeader(name = "X-SESSIONID") String sessionId);

    @PostMapping("ctld/api/tenant/user/v1")
    com.mindtree.bridge.platform.dto.UserDTO addUser(@RequestBody com.mindtree.bridge.platform.dto.UserDTO userDTO,
                    @RequestHeader(name = "X-PASS") String password,
                    @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);

    @PostMapping("ctld/api/tenant/user/v1")
    com.mindtree.bridge.platform.dto.UserDTO addUserForTenant(@RequestBody com.mindtree.bridge.platform.dto.UserDTO userDTO,
                                                              @RequestHeader(name = "X-PASS") String password,
                                                              @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId,
                                                              @RequestParam(name = "tenantId") Long tenantId);


    @GetMapping("ctld/api/tenant/v1")
    TLDTenantDTO getTenant(
            @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId,
            @RequestParam(name = "tenantLoginName") String tenantLogin,
            @RequestParam(name = "tenantId") Long tenantId);

    @PutMapping("/ctld/api/tenant/v1")
    TLDTenantDTO update(
            @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId,
            @RequestBody TLDTenantDTO tldTenantDTO);

    @PostMapping(value = "/ctld/api/tenant/v2/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void addTenantLogo(
            @RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token,
            @RequestPart(value = "id") Long currentTenantId,
            @RequestPart(value = "file") MultipartFile file);

    @GetMapping("ctld/api/tenant/contact/v1/all")
    List<TLDTenantContactDTO> getTenantContacts(
            @RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token);

    @PutMapping("ctld/api/tenant/contact/v1")
    void updateTenantContact(@RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token,
                             @RequestBody TLDTenantContactDTO tenantContactDTO);

    @PostMapping(value = "/ctld/api/tenant/contact/v1", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TLDTenantContactDTO addTenantContact(@RequestHeader(value = "X-SESSIONID") String token,
                                         @RequestBody TLDTenantContactDTO tldTenantContactDTO);


    @GetMapping(value = "ctld/api/tenant/role/v1/all")
    List<TLDRoleDTO> getAllTenantRoles(@RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token);


    @GetMapping(value = "/ctld/api/rolehierarchystructure/v1")
    List<TLDRoleHierarchyDTO> getRoleHierarchyStructure(
            @RequestHeader(value = "X-SESSIONID") String token,
            @RequestParam(value = "criteria") String criteria
    );

    @PostMapping(value = "/ctld/api/rolehierarchystructure/v1")
    TLDRoleHierarchyDTO addRoleHierarchyStructure(
            @RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token,
            @RequestBody TLDRoleHierarchyStructureDTO tldRoleHierarchyStructureDTO
    );


    @PostMapping(value = "/ctld/api/tenant/user/role/v1")
    void assignRole(@RequestParam(name = "roleCode") String roleCode,
                    @RequestParam(name = "userId") Integer userId,
                    @RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token);

    @PostMapping(value = "/ctld/api/tenant/user/role/v2")
    void assignRoleV2(@RequestParam(name = "roleCode") String roleCode,
                      @RequestParam(name = "userLogin") String userLogin,
                      @RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token);

    @PostMapping(value = "/ptld/api/ptldsignup/v1/vms")
    TLDTenantDTO onBoardTenantVMS(@RequestBody  ChangeMakerSignupDTO tldTenantDTO,
                                  @RequestParam(name = "referenceUserId",required = false) Long referenceUserId,
                                  @RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token);

    @PostMapping(value = "/ptld/api/ptldsignup/v1/markify")
    TLDTenantDTO onBoardTenantMarkify(@RequestBody  ChangeMakerSignupDTO tldTenantDTO,
                                  @RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token);

    @GetMapping(value = "/ctld/api/tenant/user/v2/login/map-userid-user")
    ResponseEntity<Map<Long,UserVO>> mapUserIdToUser(@RequestParam(name = "userIds") Set<Long> userIds,
                                                      @RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token);


    @PostMapping(value = "/ctld/api/tenant/user/v2/volunteer/invite")
    Set<UserVO> addVolunteer(@RequestBody List<UserSelfRegistrationDTO> users,
                                                                                 @RequestParam(required = true) String volunteerRoleCode,
                                                                                 @RequestParam(required = true) String password,
                                                                                 @RequestParam(required = false) String email, @RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token);

    @PostMapping(value = "/ctld/api/tenant/user/v2/volunteer/active")
    void activeUser(@RequestParam(required = true) String userLogin,
                                   @RequestParam(required = true) String tenantLogin,
                                   @RequestParam(required = true) String password,
                                   @RequestParam(required = true) Integer sourceUserId,
                                   @RequestParam(required = true)  Integer targetTenantId,
                                   @RequestParam(required = false) BigDecimal userSystemUUID,
                                   @RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token);

    @PostMapping("/ctld/api/session/v2/otp")
    ResponseEntity<Object> activateUserCrossTenantQuickLogin(
                                                             @RequestParam(value = "userLogin",required = true) String userLogin,
                                                             @RequestHeader(value = "otp",required = true) String otp,
                                                             @RequestHeader(value = "modKey",required = false) String modKey,
                                                             @RequestHeader(value = "tenantName",required = true) String tenantName,
                                                             @RequestHeader(value="X-APPID",required = false) Integer  X_APPID,
                                                             @RequestHeader(value="X-APPKEY",required = false) String X_APPKEY,
                                                             @RequestHeader(value="X-APPVERSION",required = false) String  X_APPVERSION,
                                                             @RequestHeader(value="X-DEVICEID",required = false) String x_DEVICEID);



    @PostMapping("/ctld/api/tenant/role/v1")
    ResponseEntity<Object> createTenantRole(@RequestParam Long tenantId, @RequestBody RoleDTO roleDTO, @RequestHeader(value = "X-SESSIONID") String token);

    @GetMapping(value = "/ctld/api/globalrefdata/v1/data")
    ResponseEntity<List<com.mindtree.bridge.platform.dto.GlobalRefDataDTO>> getGlobalRefData(@RequestParam(value = "criteria") String criteria,
                                                                                             @RequestHeader(value = "X-SESSIONID") String token);

    @PostMapping(value = "ctld/api/tenant/user/role/v2/self")
    ResponseEntity<UserRoleMapDTO> userSelfAssignRole(@RequestParam(value = "roleCode") String roleCode,
                                              @RequestHeader(value = "X-SESSIONID") String token);


    @GetMapping("/ctld/api/tenant/user/role/v2")
    ResponseEntity<UserRoleMapDTO> getUserRoleByRoleCode(@RequestParam(value = "roleCode") String roleCode,
                                                    @RequestParam(value = "userLogin") String userLogin,
                                                    @RequestHeader(value = "X-SESSIONID") String sessionId);

    @PostMapping("/ctld/api/tenant/user/role/v3")
    ResponseEntity<UserRoleMapDTO> assignRoleV3(@RequestParam(value = "roleCode") String roleCode,
                                                @RequestParam(value = "userId") Long userId,
                                                @RequestParam(value = "userLogin") String userLogin,
                                                @RequestParam(value = "returnIfExists") Boolean returnIfExists,
                                                @RequestHeader(value = "X-SESSIONID") String sessionId);

    @DeleteMapping("/ctld/api/tenant/user/role/v1")
    ResponseEntity<UserRoleMapDTO> deActivateRoleFromAnUser(@RequestParam(value = "roleCode") String roleCode,
                                                            @RequestParam(value = "userId",required = false) Long userId,
                                                            @RequestParam(value = "userLogin",required = false) String userLogin,
                                                            @RequestParam(value = "reason") String reason,
                                                            @RequestHeader(value = "X-SESSIONID") String sessionId);

    @RequestMapping(value = "/ctld/api/tenant/user/role/v1/status", method = RequestMethod.PUT)
    ResponseEntity<Object> changeUserRoleStatus(@RequestParam("roleCode") String roleCode,
                                                @RequestParam("userId") Long userId,
                                                @RequestParam("roleAssignmentStatus") String roleAssignmentStatus,
                                                @RequestHeader(value = "X-SESSIONID") String sessionId);

    @RequestMapping(value = "/ctld/api/tenant/user/v2/custom", method = RequestMethod.DELETE)
    void selfDeactivation(@RequestParam(value = "reason", required = false) String reason,
                          @RequestHeader(value = "X-SESSIONID") String sessionId);

    @PutMapping("ctld/api/user/selfregistration/v1/change/contact-value")
    ResponseEntity<UserSelfRegistrationDTO> changeContactValueAndTriggerOTP( @RequestBody UserContactVO userContactVO,
                                                                             @RequestParam(value = "tenantName", required = true) String tenantName,
                                                                             @RequestHeader(value = "X-APPID", required = false) String appId,
                                                                             @RequestParam(value = "appContext", required = false) String appContext);

    @PutMapping("ctld/api/user/selfregistration/v1/validate/primary-contact")
    ResponseEntity<UserSelfRegistrationDTO> validateAndAddPrimaryContactValueWithOTPTrigger(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "contactValue") String contactValue,
            @RequestParam(name = "tenantName", value = "tenantName") String tenantName,
            @RequestHeader(value = "X-APPID", required = false) String appId,
            @RequestParam(value = "appContext", required = false) String appContext,
            @RequestParam(value = "replaceContactValue", required = false) Boolean replaceContactValue);

    @GetMapping("ctld/api/user/selfregistration/v1/validate/primary-contact/activate")
    ResponseEntity<String> validateAndActivateUserContact(@RequestHeader("otp") String otp,
                                        @RequestHeader("modKey") String modKey,
                                        @RequestHeader("tenantName") String tenantName,
                                        @RequestParam("userId") Integer userId,
                                        @RequestParam("isOTPAndModKeyOfEmail") Boolean isOTPAndModKeyOfEmail,
                                        @RequestParam("skipUserActiveValidation") Boolean skipUserActiveValidation);


    @GetMapping("/ctld/api/tenant/user/v1/all")
    Set<UserDTO> getUsers(@RequestHeader(value = PlatformSecurityConstant.SESSIONID) String token,@RequestParam("tenantId") Long tenantId);



    @GetMapping("/ctld/api/workforce/userWrapper/v1")
    ResponseEntity<UserWrapperDTO> getUserViaWrapper(@RequestParam(value = "userId", required = false) Integer userId,
                                                  @RequestHeader(value = PlatformSecurityConstant.SESSIONID) String sessionId);



    @PostMapping("ptld/api/workforce/userWrapper/v1")
    ResponseEntity<UserWrapperDTO> createUserWrapper(@RequestBody UserWrapperDTO userWrapperDTO ,
                                                     @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId);

    @PutMapping("ctld/api/session/v1/pass/self")
    void setNewPasswordBySelf(@RequestHeader(value = "X-PASS") String newPass,
                              @RequestParam(value = "firstName", required = false) String firstName,
                              @RequestParam(value = "lastName", required = false) String lastName,
                              @RequestParam(value = "email", required = false) String email,
                              @RequestParam(value = "mobile", required = false) String mobile,
                              @RequestHeader(value = "X-SESSIONID")String sessionId);

    @GetMapping("ctld/api/session/v1/password/status")
    ResponseEntity<String> getPasswordStatusByUserName(@RequestHeader(value = "X-USER", required = true) String userName);

    @GetMapping("/ctld/api/tenant/user/role/criteria/v1")
    Set<UserRoleMapDTO> getUserRoleMap(@RequestParam(value = "criteria") String roleCode,
                                                @RequestHeader(value = "X-SESSIONID") String sessionId);

    @PutMapping("ctld/api/session/v1/obo/")
    ResponseEntity<Void> getUserSession(@RequestHeader(PlatformSecurityConstant.SESSIONID)String sessionId,
                                           @RequestHeader(value = "X-USER")String userName);

    @PostMapping("ctld/api/crosstenant/subscribe/v1")
    ResponseEntity<Boolean> addTenantSubscription(@RequestParam(value = "TENANT") String sourceTenantLogin,
                                               @RequestParam(value = "RoleCodes") Set<String> roleCodeSet,
                                               @RequestHeader(PlatformSecurityConstant.SESSIONID)String sessionId);


}
