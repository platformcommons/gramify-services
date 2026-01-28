package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.iam.dto.LoginRequestDTO;
import com.platformcommons.platform.service.iam.dto.LoginWithContactRequestDTO;
import com.platformcommons.platform.service.iam.dto.OTPRequestDTO;
import com.platformcommons.platform.service.iam.dto.WrapperLoginRequestDTO;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {


    String login(LoginRequestDTO loginRequestDTO, String appId, String appVersion, String appKey, HttpServletRequest request);

    void logout(String sessionId);

    String loginWithContact(LoginWithContactRequestDTO dto, String appId, String appVersion, String appKey, String deviceId, HttpServletRequest request);

    String forgetPassword(WrapperLoginRequestDTO dto, String appContext);

    void resetPassword(OTPRequestDTO dto, String appContext);

    String loginWithOTPRequest(WrapperLoginRequestDTO wrapperLoginRequestDTO, String appContext);

    String loginWithOTP(OTPRequestDTO otpRequestDTO, String appContext);

    String crossUserId(Long crossUserId, PlatformToken platformTokenActual, String tenantLogin);
}
