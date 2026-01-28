package com.platformcommons.platform.service.iam.facade;


import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.iam.dto.LoginRequestDTO;
import com.platformcommons.platform.service.iam.dto.TenantPartnerVO;
import com.platformcommons.platform.service.iam.dto.WrapperLoginRequestDTO;
import com.platformcommons.platform.service.iam.dto.OTPRequestDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface AuthenticationFacade {
    String login(LoginRequestDTO loginRequestDTO, String appId, String appVersion, String appKey, HttpServletRequest request);

    void logout(String sessionId);

    String forgetPassword(WrapperLoginRequestDTO dto, String appContext);

    void resetPassword(OTPRequestDTO dto, String appContext);

    String loginWithOTPRequest(WrapperLoginRequestDTO wrapperLoginRequestDTO, String appContext);

    String loginWithOTP(OTPRequestDTO resetPasswordRequestDTO,String appContext);

    String crossUserId(Long crossUserId, PlatformToken platformTokenActual, String tenantLogin);

    String crossUserId(PlatformToken context, Set<TenantPartnerVO> tenants);
}
