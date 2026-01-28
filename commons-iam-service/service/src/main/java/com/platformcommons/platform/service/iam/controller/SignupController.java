package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.iam.application.utility.TenantOnBoardUtil;
import com.platformcommons.platform.service.iam.dto.*;
import com.platformcommons.platform.service.iam.facade.AuthenticationFacade;
import com.platformcommons.platform.service.iam.facade.SignupFacade;
import com.platformcommons.platform.service.iam.facade.UserFacade;
import com.platformcommons.platform.service.iam.facade.obo.OBOFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/signup")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "signup-controller")
public class SignupController {
    private final SignupFacade signupFacade;
    private final OBOFacade oboFacade;
    private final UserFacade userFacade;
    private final TenantOnBoardUtil tenantOnBoardUtil;
    private final AuthenticationFacade authenticationFacade;


    @GetMapping("/exists/{email}")
    public ResponseEntity<LeadExistResponse> existsLeadWithEmail(
            @PathVariable(name = "email") String email,
            @RequestParam(name = "app-context", required = false) String appContext) {
        return ResponseEntity.ok(signupFacade.existsWithEmail(email, appContext, null));
    }

    @GetMapping("/exists")
    public ResponseEntity<LeadExistResponse> exists(
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "mobile", required = false) String mobile,
            @RequestParam(name = "app-context", required = false) String appContext) {
        return ResponseEntity.ok(signupFacade.exists(email, mobile, appContext));
    }

    @GetMapping("/exists/user")
    public ResponseEntity<UserExistResponse> existsUser(
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "mobile", required = false) String mobile,
            @RequestParam(name = "tenantLogin") String tenantLogin,
            @RequestParam(name = "login") String login) {
        return ResponseEntity.ok(userFacade.existsUser(email, mobile, login, tenantLogin));
    }


    @PostMapping("/register")
    public ResponseEntity<OTPResponse> registerIntentTenant(@RequestBody @Valid LeadDTO leadDTO,
                                                            @RequestParam(required = false) Boolean sendOTP) {
        sendOTP = sendOTP != null ? sendOTP : Boolean.TRUE;
        return ResponseEntity.ok(signupFacade.registerIntentTenant(leadDTO, sendOTP));
    }


    @PostMapping("/register/user")
    public ResponseEntity<OTPResponse> registerIntentUser(@RequestBody @Valid LeadDTO leadDTO,
                                                          @RequestParam(required = false) Boolean sendOTP) {
        sendOTP = sendOTP != null ? sendOTP : Boolean.TRUE;
        return ResponseEntity.ok(signupFacade.registerIntentUser(leadDTO, sendOTP));
    }


    @PutMapping("/otp")
    public ResponseEntity<OTPResponse> reSendOTP(@RequestParam(name = "key") String key,
                                                 @RequestParam(name = "email") String email) {
        return ResponseEntity.ok(signupFacade.resendOTP(key, email));
    }

    @PutMapping("/otp/mobile")
    public ResponseEntity<OTPResponse> reSendOTPMobile(@RequestParam(name = "key") String key,
                                                       @RequestParam(name = "mobile") String mobile) {
        return ResponseEntity.ok(signupFacade.resendOTPMobile(key, mobile));
    }


    @PostMapping("/tenant/vms")
    public ResponseEntity<TenantDTO> addTenant(@RequestHeader(name = "X-PASS") String adminPassword,
                                               @RequestBody @Valid SignUpRequestDTO signUpRequestDTO
    ) {
        TenantDTO tenantDTO = signupFacade.addTenantVMS(signUpRequestDTO, adminPassword);
        String sessionId = signupFacade.generateSessionIdForTenant(tenantDTO, signUpRequestDTO, adminPassword, true);
        return ResponseEntity.status(HttpStatus.CREATED).header(PlatformSecurityConstant.SESSIONID, sessionId).body(tenantDTO);
    }


    @PostMapping("/tenant/assessment-org")
    public ResponseEntity<TenantDTO> onBoardTenantAssessmentOrg(@RequestHeader(name = "X-PASS") String adminPassword,
                                                                @RequestBody @Valid SignUpRequestDTO signUpRequestDTO,
                                                                @RequestParam(name = "createdInLinkedSystem", required = false)
                                                                Boolean createdInLinkedSystem) {
        TenantDTO tenantDTO = signupFacade.onBoardTenantFromLead(signUpRequestDTO, adminPassword, createdInLinkedSystem);
        String sessionId = signupFacade.generateSessionIdForTenant(tenantDTO, signUpRequestDTO, adminPassword, createdInLinkedSystem);
        return ResponseEntity.status(HttpStatus.CREATED).header(PlatformSecurityConstant.SESSIONID, sessionId).body(tenantDTO);
    }

    @PostMapping("/tenant/markify2")
    public ResponseEntity<TenantDTO> onBoardTenantMarkifyV2(@RequestHeader(name = "X-PASS") String adminPassword,
                                                            @RequestBody @Valid SignUpRequestDTO signUpRequestDTO,
                                                            @RequestParam(name = "createdInLinkedSystem", required = false)
                                                            Boolean createdInLinkedSystem) {
        Boolean useMobileAsLogin = signUpRequestDTO.getUseMobileAsUserLogin()!=null ? signUpRequestDTO.getUseMobileAsUserLogin() : Boolean.TRUE;
        createdInLinkedSystem = createdInLinkedSystem!=null ? createdInLinkedSystem : Boolean.TRUE;
        signUpRequestDTO.setUseMobileAsUserLogin(useMobileAsLogin);
        TenantDTO tenantDTO = signupFacade.onBoardTenantFromLead(signUpRequestDTO, adminPassword, createdInLinkedSystem);
        String sessionId = signupFacade.generateSessionIdForTenant(tenantDTO, signUpRequestDTO, adminPassword, createdInLinkedSystem);
        tenantOnBoardUtil.addDefaultABATenantRoleMarkify(tenantDTO,sessionId);
        return ResponseEntity.status(HttpStatus.CREATED).header(PlatformSecurityConstant.SESSIONID, sessionId).body(tenantDTO);
    }


    @PostMapping("/tenant")
    public ResponseEntity<TenantDTO> onBoardTenant(@RequestHeader(name = "X-PASS") String adminPassword,
                                                            @RequestBody @Valid SignUpRequestDTO signUpRequestDTO,
                                                            @RequestParam(name = "createdInLinkedSystem", required = false)
                                                            Boolean createdInLinkedSystem) {
        TenantDTO tenantDTO = signupFacade.onBoardTenantFromLead(signUpRequestDTO, adminPassword, createdInLinkedSystem);
        String sessionId = signupFacade.generateSessionIdForTenant(tenantDTO, signUpRequestDTO, adminPassword, createdInLinkedSystem);
        return ResponseEntity.status(HttpStatus.CREATED).header(PlatformSecurityConstant.SESSIONID, sessionId).body(tenantDTO);
    }


    
    
    
    @PostMapping("/user/{tenant-login}/activate")
    public ResponseEntity<IAMUserDTO> addUser(@RequestHeader(name = "X-PASS") String password,
                                              @RequestBody @Valid SignUpRequestDTO signUpRequestDTO,
                                              @PathVariable(name = "tenant-login") String tenantLogin,
                                              @RequestHeader(value = "X-APPID", required = false)String appId,
                                              @RequestHeader(value = "X-APPVERSION",required = false)String appVersion,
                                              @RequestHeader(value = "X-APPKEY", required = false)String appKey,
                                              HttpServletRequest request) {
        String primaryLogin = signUpRequestDTO.getUseMobileAsUserLogin() != null && signUpRequestDTO.getUseMobileAsUserLogin()
                ? signUpRequestDTO.getMobile():signUpRequestDTO.getEmail();
        IAMUserDTO iamUserDTO = signupFacade.onBoardUserFromLead(signUpRequestDTO,password, tenantLogin, primaryLogin);

        LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder().userLogin(primaryLogin).tenantLogin(tenantLogin).password(password).build();
        String sessionId = null;
        if(signUpRequestDTO.isAddInLinkedSystem()){
           sessionId= oboFacade.getSessionId(loginRequestDTO,appId, appVersion, appKey,null);
        }
        else {
             sessionId = authenticationFacade.login(LoginRequestDTO.builder()
                            .userLogin(primaryLogin)
                            .tenantLogin(tenantLogin)
                            .password(password)
                            .build(),
                    appId, appVersion, appKey, request);
        }
        return ResponseEntity.status(HttpStatus.CREATED).header(PlatformSecurityConstant.SESSIONID,sessionId)
                .body(iamUserDTO);
    }


    @PostMapping("/lead/tenant/markify")
    public ResponseEntity<TenantDTO> leadToTenantMarkify(@RequestParam(name = "leadId") Long leadId,
                                                         @RequestParam(name = "marketId") Long marketId,
                                                         @RequestParam(name = "url") String url) {
        TenantDTO tenantDTO = signupFacade.convertLeadToTenantMarkify(leadId,marketId,url);
        return ResponseEntity.status(HttpStatus.CREATED).body(tenantDTO);
    }

    @PostMapping("/user/activate")
    ResponseEntity<Void> activate(@RequestParam(required = true) String userLogin,
                                  @RequestParam(required = false) String tenantLogin,
                                  @RequestParam(required = true) String password,
                                  @RequestParam(required = true) Integer sourceUserId,
                                  @RequestParam(required = true)  Integer targetTenantId,
                                  @RequestParam(required = false)  String OTP,
                                  @RequestParam(required = false) String messageId,
                                  @RequestParam(required = false) BigDecimal userSystemUUId) {
        oboFacade.activateUser(userLogin, tenantLogin, password, sourceUserId, targetTenantId, OTP, messageId, userSystemUUId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("/user/otp")
    ResponseEntity<String> sendOTPToUser(@RequestParam() String email, @RequestParam(name = "name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.sendOTP(email, name));
    }

    @PostMapping("/quick-login")
    ResponseEntity<Map<String, String>> activateUserCrossTenantQuickLogin(@RequestBody @Valid CrossLoginRequestDTO crossLoginRequestDTO,
                                                                          @RequestHeader(value = "X-APPID", required = false) Integer X_APPID,
                                                                          @RequestHeader(value = "X-APPKEY", required = false) String X_APPKEY,
                                                                          @RequestHeader(value = "X-APPVERSION", required = false) String X_APPVERSION,
                                                                          @RequestHeader(value = "X-DEVICEID", required = false) String X_DEVICEID) {
        return ResponseEntity.status(HttpStatus.OK).body(oboFacade.activateUserCrossTenantQuickLogin(crossLoginRequestDTO, X_APPID, X_APPKEY, X_APPVERSION, X_DEVICEID));
    }


}
