package com.platformcommons.platform.service.iam.controller;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.iam.dto.LoginRequestDTO;
import com.platformcommons.platform.service.iam.dto.OTPRequestDTO;
import com.platformcommons.platform.service.iam.dto.WrapperLoginRequestDTO;
import com.platformcommons.platform.service.iam.facade.AuthenticationFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/security")
@Tag(name = "authentication-controller")
@Slf4j
public class AuthenticationController {

    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO,
                                          @RequestHeader(value = "X-APPID", required = false)String appId,
                                          @RequestHeader(value = "X-APPVERSION",required = false)String appVersion,
                                          @RequestHeader(value = "X-APPKEY", required = false)String appKey,
                                          HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .header(PlatformSecurityConstant.SESSIONID, authenticationFacade.login(loginRequestDTO,appId,appVersion,appKey,request))
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = PlatformSecurityConstant.SESSIONID) String sessionId){
        authenticationFacade.logout(sessionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/forget")
    public ResponseEntity<String> forgetPassword(@RequestBody WrapperLoginRequestDTO dto,
                                                 @RequestHeader(value = "X-APPCONTEXT",required = false) String appContext){
        return ResponseEntity.status(HttpStatus.OK)
                             .body(authenticationFacade.forgetPassword(dto,appContext));
    }

    @PatchMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody OTPRequestDTO dto,
                                        @RequestHeader(value = "X-APPCONTEXT",required = false) String appContext){
        authenticationFacade.resetPassword(dto,appContext);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login/otp/request")
    public ResponseEntity<?> loginWithOTPRequest(@RequestBody WrapperLoginRequestDTO wrapperLoginRequestDTO,
                                                 @RequestHeader(value = "X-APPCONTEXT",required = false) String appContext){
        return ResponseEntity.status(HttpStatus.OK)
                .body(authenticationFacade.loginWithOTPRequest(wrapperLoginRequestDTO,appContext));
    }

    @PostMapping("/login/otp")
    public ResponseEntity<?> loginWithOTP(@RequestBody OTPRequestDTO otpRequestDTO,
                                       @RequestHeader(value = "X-APPCONTEXT",required = false) String appContext){
        return ResponseEntity.status(HttpStatus.OK)
                .header(PlatformSecurityConstant.SESSIONID, authenticationFacade.loginWithOTP(otpRequestDTO,appContext))
                .build();
    }
}
