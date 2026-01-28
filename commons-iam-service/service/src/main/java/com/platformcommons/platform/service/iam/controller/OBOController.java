package com.platformcommons.platform.service.iam.controller;

import com.mindtree.bridge.platform.dto.UserRoleMapDTO;
import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.security.filter.session.CurrentExecutiveDTO;
import com.platformcommons.platform.service.iam.domain.vo.UserSelfRegistrationExcelVO;
import com.platformcommons.platform.service.iam.dto.*;
import com.platformcommons.platform.service.iam.domain.vo.UserVO;
import com.platformcommons.platform.service.iam.dto.brbase.PersonProfessionalHistoryDTO;
import com.platformcommons.platform.service.iam.dto.brbase.UserContactVO;
import com.platformcommons.platform.service.iam.dto.brbase.UserDTO;
import com.platformcommons.platform.service.iam.dto.brbase.UserSelfRegistrationDTO;
import com.platformcommons.platform.service.iam.facade.obo.OBOFacade;
import com.platformcommons.platform.service.iam.facade.obo.OBOUserFacade;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.dto.BulkUploadRequestDTO;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.RequiredTypes;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "obo-controller")
public class OBOController {

    private final OBOFacade oboFacade;
    private final OBOUserFacade oboUserFacade;


    @SuppressWarnings("rawtypes")
    @PostMapping("/v1/obo/login")
    public ResponseEntity getTLDSessionId(@RequestBody LoginRequestDTO loginRequestDTO,
                                                  @RequestHeader(value = "X-APPID", required = false)String appId,
                                                  @RequestHeader(value = "X-APPVERSION",required = false)String appVersion,
                                                  @RequestHeader(value = "X-APPKEY", required = false)String appKey,
                                                  @RequestHeader(value = "X-DEVICE-ID",required = false)String deviceId){
        return ResponseEntity.status(HttpStatus.OK)
                .header(PlatformSecurityConstant.SESSIONID,oboFacade.getSessionId(loginRequestDTO,appId,appVersion,appKey,deviceId))
                .build();
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/v1/obo/social/login")
    public ResponseEntity getTLDSocialSessionID(@RequestHeader(value = "Authorization", required = true) String token,
                                          @RequestParam(value = "platform", required = true) String platform,
                                          @RequestParam(value = "providerTenantId", required = false) String providerTenantId,
                                                @RequestParam(value = "tenantLogin", required = false) String tenantLogin,
                                          @RequestHeader(value = "X-APPID", required = false)String appId,
                                          @RequestHeader(value = "X-APPVERSION",required = false)String appVersion,
                                          @RequestHeader(value = "X-APPKEY", required = false)String appKey,
                                          @RequestHeader(value = "X-DEVICE-ID",required = false)String deviceId,
                                                @RequestParam(value = "createUser", required = false) Boolean createUser){
        createUser = createUser!=null ? createUser : Boolean.TRUE;
        return ResponseEntity.status(HttpStatus.OK)
                .header(PlatformSecurityConstant.SESSIONID,oboFacade.getSessionForSocialLoginTLD(token,platform,
                        providerTenantId,appId,appVersion,appKey,deviceId,tenantLogin, createUser))
                .build();
    }

    @GetMapping("/v1/obo/me")
    public ResponseEntity<UserDTO> getTLDSessionId(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(oboFacade.getLoggedInUserDetails(request.getHeader(PlatformSecurityConstant.SESSIONID)));
    }

    @GetMapping("/v1/obo/session/me")
    public ResponseEntity<CurrentExecutiveDTO> getTLDSessionDetails(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(oboFacade.getLoggedInUserSessionDetails());
    }

    @PostMapping("/v1/obo/register")
    public ResponseEntity<UserSelfRegistrationDTO> register(@RequestHeader("X-PASS") String password,
                                                            @RequestHeader(value = "X-APPID", required = false) String appId,
                                                            @RequestParam("tenantName") String tenantName,
                                                            @RequestParam(value = "appCode",required = false) String appCode,
                                                            @RequestParam(value = "marketContext", required = false) String marketContext,
                                                            @RequestBody UserSelfRegistrationDTO userSelfRegistrationDTO,
                                                            @RequestParam(value = "sendAndAuthenticateDifferentOTPForEmailAndSms", required = false) Boolean sendAndAuthenticateDifferentOTPForEmailAndSms){
        return ResponseEntity.status(HttpStatus.CREATED).body(oboFacade.register(userSelfRegistrationDTO, password, tenantName,
                appId, sendAndAuthenticateDifferentOTPForEmailAndSms,appCode, marketContext));
    }

    @GetMapping("/v1/obo/register/check")
    public ResponseEntity<String> register(@RequestParam("tenantName") String tenantName,
                                                            @RequestParam("loginName") String loginName){
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.checkUser(tenantName,loginName));
    }

    @PostMapping("/v1/obo/activate-user")
    ResponseEntity<String> activateUser(@RequestHeader("otp") String otp,
                                        @RequestHeader("modKey") String modKey,
                                        @RequestHeader("tenantName") String tenantName,
                                        @RequestParam("userId") Integer userId,
                                        @RequestParam(value = "isOTPAndModKeyOfEmail", required = false) Boolean isOTPAndModKeyOfEmail,
                                        @RequestParam(value = "skipUserActiveValidation", required = false) Boolean skipUserActiveValidation){
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.activate(otp,modKey,tenantName,userId, isOTPAndModKeyOfEmail, skipUserActiveValidation));
    }


    @GetMapping("/v1/obo/activate-inactive-user")
    ResponseEntity<UserSelfRegistrationDTO> activateInactiveUser(@RequestParam("userName") String userName,
                                                                 @RequestParam("tenantName") String tenantName,
                                                                 @RequestHeader(value = "X-APPID",required = false) String appId,
                                                                 @RequestParam(value = "appContext", required = false) String appContext,
                                                                 @RequestParam(value = "sendOTPForEmail", required = false) Boolean sendOTPForEmail) {
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.activateInactive(userName, tenantName, appId, appContext, sendOTPForEmail));

    }

    @GetMapping(value = "/v1/obo/reset-password")
    ResponseEntity<String> pass(@RequestHeader("X-USER") String userLogin, @RequestHeader("tenantName") String tenant,
                                @RequestParam(value = "appContext", required = false) String appContext,
                                @RequestHeader(value = "X-APPID", required = false) String appId) {
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.pass(userLogin, tenant, appContext, appId));
    }

    @PutMapping("/v1/obo/reset-password")
    ResponseEntity<String> resetPassword(@RequestHeader("X-USER") String userLogin,
                                         @RequestHeader("tenantName") String tenant,
                                @RequestHeader("X-NEWPASS") String newPassword,
                                @RequestParam("key") String key,
                                @RequestParam("otp") String otp){
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.resetPassword(userLogin,tenant,newPassword ,key,otp));
    }

    @GetMapping("/v1/obo/resend-otp")
    ResponseEntity<String> resendOTP(@RequestHeader("X-USER") String userLogin, @RequestHeader("tenantName") String tenant,
                                     @RequestParam(value = "appContext", required = false) String appContext,
                                     @RequestHeader(value = "X-APPID", required = false) String appId) {
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.resendOTP(userLogin, tenant, appContext, appId));
    }


    @PostMapping("/v1/obo/crosstenant/login")
    ResponseEntity<Void> crossTenantLogin(@RequestParam("TENANT") String tenant,
                                            @RequestHeader(value = "X-APPID",required = false) String appId,
                                            @RequestHeader(value = "X-APPKEY",required = false) String appKey,
                                            @RequestHeader(value = "X-APPVERSION",required = false) String appVersion,
                                            @RequestHeader(value = "X-DEVICEID",required = false) String deviceId,
                                            @RequestHeader(value = "X-SESSIONID") String sessionId){
        return ResponseEntity.status(HttpStatus.OK).header(PlatformSecurityConstant.SESSIONID,
                oboFacade.getCrossTenantSessionId(tenant,appId,appKey,appVersion,deviceId,sessionId)).build();
    }

    @PostMapping("/v1/obo/profesional-histories/{userId}")
    ResponseEntity<Void> addProfessionalHistory(@PathVariable("userId")Long userId,
                                                   @RequestBody PersonProfessionalHistoryDTO professionalHistoryDTO){
        oboFacade.updateUserProfessionalHistoryList(userId,professionalHistoryDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/v1/obo/cross/login")
    ResponseEntity<Map<String,String>> crossLogin(@RequestBody LoginRequestDTO loginRequestDTO,
                                                  @RequestHeader(value = "X-APPID",required = false) String appId,
                                                  @RequestHeader(value = "X-APPKEY",required = false) String appKey,
                                                  @RequestHeader(value = "X-APPVERSION",required = false) String appVersion,
                                                  @RequestHeader(value = "X-DEVICEID",required = false) String deviceId,
                                                  @RequestParam(value = "crossTenant",required = false) String crossTenant){
        Map<String,String> sessionsMap =oboFacade.crossLogin(loginRequestDTO,crossTenant,appId,appVersion,appKey,deviceId);
        return ResponseEntity.status(HttpStatus.OK).header(PlatformSecurityConstant.SESSIONID,sessionsMap.get(OBOFacade.CROSS_SESSION)).body(sessionsMap);
    }

    @PostMapping("/v2/obo/cross/login")
    ResponseEntity<Map<String,String>> crossLoginV2(@RequestBody LoginRequestDTO loginRequestDTO,
                                                  @RequestHeader(value = "X-APPID",required = false) String appId,
                                                  @RequestHeader(value = "X-APPKEY",required = true) String appKey,
                                                  @RequestHeader(value = "X-APPVERSION",required = false) String appVersion,
                                                  @RequestHeader(value = "X-DEVICEID",required = false) String deviceId){
        Map<String,String> sessionsMap =oboFacade.crossLoginV2(loginRequestDTO,appId,appVersion,appKey,deviceId);
        return ResponseEntity.status(HttpStatus.OK).header(PlatformSecurityConstant.SESSIONID,sessionsMap.get(OBOFacade.CROSS_SESSION)).body(sessionsMap);
    }

    @PostMapping("/v1/obo/user")
    ResponseEntity<Long> addUser(@RequestBody UserWrapperCustomDTO userWrapperCustomDTO,
                                 @RequestParam(required = false) String appContext,
                                 @RequestParam(required = false) Boolean sendNotification,
                                 @RequestParam(required = false) String marketId,
                                 @RequestParam String baseDomain){
        return ResponseEntity.status(HttpStatus.OK).body(oboUserFacade.addUser(userWrapperCustomDTO, appContext, baseDomain,
                sendNotification,marketId));
    }

    @ApiOperation(value = "Initiate Process Of User Bulk Upload",  notes = "",
            response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = BulkUploadRequestDTO.class) })
    @RequestMapping(value = "/v1/bulk-upload-request/{bulkUploadRequestId}/user/process",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<Void> initiateProcessOfUserBulkUpload(@PathVariable(name = "bulkUploadRequestId") Long bulkUploadRequestId,
                                                                @RequestParam(required = false) String appContext,
                                                                @RequestParam(required = false) Boolean sendNotification,
                                                                @RequestParam(required = false) String marketId,
                                                                @RequestParam String baseDomain){
        oboUserFacade.initiateProcessOfUserBulkUpload(bulkUploadRequestId,appContext,baseDomain,sendNotification,
                marketId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/v1/obo/role/self")
    ResponseEntity<Object> userSelfAssignRole(@RequestParam(value = "roleCode") String roleCode){
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.userSelfAssignRole(roleCode));
    }

    @PostMapping("/v1/obo/user/role")
    ResponseEntity<UserRoleMapDTO> assignRoleToUser(@RequestParam(value = "roleCode") String roleCode, @RequestParam(value = "userLogin") String userLogin){
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.assignRoleToUser(roleCode,userLogin));
    }

    @PostMapping("/v1/obo/users/bulk/role")
    ResponseEntity<Map<String,String>> assignRoleToUsersInBulk(@NotNull @Valid @RequestBody Set<UserRoleAssignDTO> userRoleAssignDTOSet){
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.assignRoleToUsersInBulk(userRoleAssignDTOSet));
    }

    @PatchMapping("/v1/obo/user")
    ResponseEntity<Void> patchUpdateUserWithWorkNode(@RequestBody com.platformcommons.platform.service.iam.dto.brbase.UserDTO userDTO,
                                                     @RequestParam(value = "role",required = false) String role,
                                                     @RequestParam(value = "cohort",required = false) String cohort,
                                                     @RequestParam(value = "placementCity",required = false) String placementCity){
        oboFacade.patchUpdateUserWithWorkNode(userDTO,role,cohort,placementCity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/v1/obo/users")
    ResponseEntity<com.mindtree.bridge.platform.dto.UserDTO > patchUpdateUser(@RequestBody com.mindtree.bridge.platform.dto.UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.patchUpdateUser(userDTO));
    }

    @PatchMapping("/v1/obo/webhook/user")
    ResponseEntity<Void> patchUpdateUserViaWebhook(@Valid @NotNull @RequestBody UserSyncCustomDTO userSyncCustomDTO){
        oboFacade.patchUpdateUserViaWebhook(userSyncCustomDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/v1/obo/user/tfi/user-verification")
    ResponseEntity<TFIResponseDTO> tfiUserVerification(@NotNull @Valid @RequestBody TFIUserVerificationDTO tfiUserVerificationDTO){
        TFIResponseDTO tfiResponseDTO = oboFacade.tfiUserVerification(tfiUserVerificationDTO);
        return ResponseEntity.status(HttpStatus.OK).body(tfiResponseDTO);
    }

    @PostMapping("/v1/obo/oauth/access-token")
    ResponseEntity<Object> getOAuthIdToken(@RequestParam(required = true) String authCode,
                                           @RequestParam(required = true) String clientId,
                                           @RequestParam(required = true) String redirectURI,
                                           @RequestParam(value = "platform", required = true) String platform) {
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.getOAuthIdToken(authCode,clientId,redirectURI,platform));
    }

    @PostMapping("/v1/obo/roles/self/")
    ResponseEntity<Void> selfAssignRolesForLoggedInUser(@RequestParam(value = "roleCodes") Set<String> roleCodes,
                                                        @RequestParam(value = "isFlowComplete",required = false) Boolean isFlowComplete){
        oboFacade.selfAssignRolesForLoggedInUser(roleCodes,isFlowComplete);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/v1/obo/roles/approval")
    ResponseEntity<Void> approveOrRejectUserRoles(@RequestParam(value = "userId") Long userId,
                                                  @RequestParam(value = "assignRoleCodes",required = false) Set<String> assignRoleCodes,
                                                  @RequestParam(value = "rejectRoleCodes",required = false) Set<String> rejectRoleCodes) {
        oboFacade.approveOrRejectUserRoles(userId,assignRoleCodes,rejectRoleCodes);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/v1/obo/user/self-deactivation")
    ResponseEntity<Void> selfDeactivation(@RequestParam(value = "reason",required = false) String reason) {
        oboFacade.selfDeactivation(reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/v1/obo/change/contact-value")
    ResponseEntity<UserSelfRegistrationDTO> changeContactValueAndTriggerOTP(@RequestBody UserContactVO userContactVO,
                                                                            @ApiParam(value = "[Mandatory] tenantName required for activation")
                                                                            @RequestParam(value = "tenantName", required = true) String tenantName,
                                                                            @RequestHeader(value = "X-APPID", required = false) String appId,
                                                                            @RequestParam(value = "appContext", required = false) String appContext) {
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.changeContactValueAndTriggerOTP(userContactVO, tenantName, appId, appContext));
    }


    @PutMapping("/v1/obo/validate/primary-contact")
    ResponseEntity<UserSelfRegistrationDTO> validateAndAddPrimaryContactValueWithOTPTrigger(@RequestParam(name = "userId") Long userId,
                                                                                            @RequestParam(name = "contactValue") String contactValue,
                                                                                            @RequestParam(name = "tenantName", value = "tenantName") String tenantName,
                                                                                            @RequestHeader(value = "X-APPID", required = false) String appId,
                                                                                            @RequestParam(value = "appContext", required = false) String appContext,
                                                                                            @RequestParam(value = "replaceContactValue", required = false) Boolean replaceContactValue) {
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.validateAndAddPrimaryContactValueWithOTPTrigger(userId, contactValue, tenantName, appId, appContext, replaceContactValue));
    }

    @PostMapping("/v1/obo/validate/primary-contact/activate")
    ResponseEntity<String> validateAndActivateUserContact(@RequestHeader("otp") String otp,
                                                          @RequestHeader("modKey") String modKey,
                                                          @RequestHeader("tenantName") String tenantName,
                                                          @RequestParam("userId") Integer userId,
                                                          @RequestParam(value = "isOTPAndModKeyOfEmail", required = false) Boolean isOTPAndModKeyOfEmail,
                                                          @RequestParam(value = "skipUserActiveValidation", required = false) Boolean skipUserActiveValidation) {
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.validateAndActivateUserContact(otp, modKey, tenantName, userId, isOTPAndModKeyOfEmail, skipUserActiveValidation));
    }

    @ApiOperation(value = "Reset pass by self",  notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK") })
    @PutMapping("/v1/obo/pass/self")
    ResponseEntity<Void> setNewPasswordBySelf(@ApiParam(value = "[Mandatory] Password in unencrypted form")
                                              @RequestHeader(value = "X-PASS") String newPass,
                                              @ApiParam(value = "[Optional] firstName")
                                              @RequestParam(value = "firstName", required = false) String firstName,
                                              @ApiParam(value = "[Optional] lastName")
                                              @RequestParam(value = "lastName", required = false) String lastName,
                                              @ApiParam(value = "[Optional] email")
                                              @RequestParam(value = "email", required = false) String email,
                                              @ApiParam(value = "[Optional] mobile")
                                              @RequestParam(value = "mobile", required = false) String mobile) {
        oboFacade.setNewPasswordBySelf(newPass, firstName, lastName, email, mobile);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Get password status by loginName", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    @GetMapping("/v1/obo/password/status")
    ResponseEntity<String> getPasswordStatusByUserName(@ApiParam(value = "[Mandatory] x-user should be of the form user@@tenant")
                                                     @RequestHeader(value = "X-USER") String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.getPasswordStatusByUserName(userName));
    }

    @GetMapping("/v1/obo/user/login-tenant-check")
    ResponseEntity<UserTenantCheckDTO> computeTenantForUserLogin(@RequestParam("userLogin") String userLogin,
                                                      @RequestParam(value = "primaryTenantLogin") String primaryTenantLogin,
                                                      @RequestParam(value = "crossTenantLogin", required = false) String crossTenantLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.computeTenantForUserLogin(userLogin,primaryTenantLogin,crossTenantLogin));
    }

    @PostMapping("/v1/user/vms/signup")
    public ResponseEntity<UserDTO> createUserInLinkedSystemInVmsByLead(@RequestHeader(name = "X-PASS") String password,
                                                                  @RequestBody @Valid UserSignUpRequestDTO userSignUpRequestDTO) {
        String sessionId = oboUserFacade.createUserInLinkedSystemInVmsByLead(userSignUpRequestDTO, password);
        return ResponseEntity.status(HttpStatus.CREATED).header(PlatformSecurityConstant.SESSIONID, sessionId).build();
    }
}
