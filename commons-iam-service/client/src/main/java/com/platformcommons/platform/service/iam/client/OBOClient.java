package com.platformcommons.platform.service.iam.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.iam.dto.LoginRequestDTO;
import com.platformcommons.platform.service.iam.dto.brbase.PersonProfessionalHistoryDTO;
import com.platformcommons.platform.service.iam.dto.brbase.UserDTO;
import com.platformcommons.platform.service.iam.dto.brbase.UserSelfRegistrationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "commons-iam-service${commons.service.iam.context-path:}",
        contextId = "OBOClient"
)
public interface OBOClient {

    @PostMapping("/api/v1/obo/login")
    ResponseEntity getTLDSessionId(@RequestBody LoginRequestDTO loginRequestDTO,
                                          @RequestHeader("X-APPID")String appId,
                                          @RequestHeader(value = "X-APPVERSION",required = false)String appVersion,
                                          @RequestHeader("X-APPKEY")String appKey,
                                          @RequestHeader(value = "X-DEVICE-ID",required = false)String deviceId);

    @GetMapping("/api/v1/obo/me")
    ResponseEntity<UserDTO> getTLDSessionId(@RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId);

    @PostMapping("/api/v1/obo/register")
    ResponseEntity<UserSelfRegistrationDTO> register(@RequestHeader("X-PASS") String password,
                                                            @RequestParam("tenantName") String tenantName,
                                                            @RequestBody UserSelfRegistrationDTO userSelfRegistrationDTO);


    @GetMapping("/api/v1/obo/register/check")
    ResponseEntity<String> register(@RequestParam("tenantName") String tenantName,
                                           @RequestParam("loginName") String loginName);


    @PostMapping("/api/v1/obo/activate-user")
    ResponseEntity<String> activateUser(@RequestHeader("otp") String otp,
                                        @RequestHeader("modKey") String modKey,
                                        @RequestHeader("tenantName") String tenantName,
                                        @RequestParam("userId") Integer userId);



    @GetMapping("/api/v1/obo/activate-inactive-user")
    ResponseEntity<UserSelfRegistrationDTO> activateInactiveUser(@RequestParam("userName") String userName,
                                                                 @RequestParam("tenantName") String tenantName);



    @GetMapping(value = "/api/v1/obo/reset-password")
    ResponseEntity<String> pass(@RequestHeader("X-USER")String userLogin,@RequestHeader("tenantName") String tenant);


    @PutMapping("/api/v1/obo/reset-password")
    ResponseEntity<String> resetPassword(@RequestHeader("X-USER") String userLogin,
                                         @RequestHeader("tenantName") String tenant,
                                         @RequestHeader("X-NEWPASS") String newPassword,
                                         @RequestParam("key") String key,
                                         @RequestParam("otp") String otp);

    @GetMapping("/api/v1/obo/resend-otp")
    ResponseEntity<String> resendOTP(@RequestHeader("X-USER")String userLogin,@RequestHeader("tenantName") String tenant);


    @PostMapping("/api/v1/obo/cross-tenant/login")
    ResponseEntity<Void> crossTenantLogin(@RequestParam("TENANT") String tenant,
                                          @RequestHeader(value = "X-APPID",required = false) String appId,
                                          @RequestHeader(value = "X-APPKEY",required = false) String appKey,
                                          @RequestHeader(value = "X-APPVERSION",required = false) String appVersion,
                                          @RequestHeader(value = "X-DEVICEID",required = false) String deviceId,
                                          @RequestHeader(value = "X-SESSIONID") String sessionId);


    @PostMapping("/api/v1/obo/profesional-histories/{userId}")
    ResponseEntity<Void> addProfessionalHistory(@PathVariable("userId")Long userId,
                                                @RequestBody PersonProfessionalHistoryDTO professionalHistoryDTO);


}
