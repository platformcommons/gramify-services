package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.AuthenticationService;
import com.platformcommons.platform.service.iam.application.constant.TenantMetaConstant;
import com.platformcommons.platform.service.iam.dto.LoginRequestDTO;
import com.platformcommons.platform.service.iam.dto.OTPRequestDTO;
import com.platformcommons.platform.service.iam.dto.TenantPartnerVO;
import com.platformcommons.platform.service.iam.dto.WrapperLoginRequestDTO;
import com.platformcommons.platform.service.iam.facade.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Component
@RequiredArgsConstructor

public class AuthenticationFacadeImpl implements AuthenticationFacade {


    private final AuthenticationService authenticationService;

    @Override
    public String login(LoginRequestDTO loginRequestDTO,
                        String appId,
                        String appVersion,
                        String appKey,
                        HttpServletRequest request) {
        return authenticationService.login(loginRequestDTO, appId, appVersion, appKey, request);
    }

    @Override
    public void logout(String sessionId) {
        authenticationService.logout(sessionId);
    }


    @Override
    @Transactional
    public String forgetPassword(WrapperLoginRequestDTO dto, String appContext) {
        return authenticationService.forgetPassword(dto, appContext);
    }

    @Override
    public void resetPassword(OTPRequestDTO dto, String appContext) {
        authenticationService.resetPassword(dto, appContext);
    }

    @Override
    public String loginWithOTPRequest(WrapperLoginRequestDTO wrapperLoginRequestDTO, String appContext) {
        return authenticationService.loginWithOTPRequest(wrapperLoginRequestDTO, appContext);
    }

    @Override
    public String loginWithOTP(OTPRequestDTO otpRequestDTO, String appContext) {
        return authenticationService.loginWithOTP(otpRequestDTO, appContext);
    }

    @Override
    public String crossUserId(Long crossUserId, PlatformToken platformTokenActual, String tenantLogin) {
        return authenticationService.crossUserId(crossUserId, platformTokenActual, tenantLogin);
    }

    @Override
    public String crossUserId(PlatformToken context, Set<TenantPartnerVO> tenants) {
        TenantPartnerVO primaryTenantPartnerVO =
                tenants.stream().filter(tenantPartnerVO -> tenantPartnerVO.getIsPrimary()
                                && tenantPartnerVO.getStatus()
                                .equals(TenantMetaConstant.META_MASTER_PARTNER_SIGNUP_DEFAULT_STATUS)).
                        findFirst().orElse(null);
        if (primaryTenantPartnerVO == null)
            return null;
        else
            return crossUserId(primaryTenantPartnerVO.getPrimaryTenantUserId(),
                    PlatformSecurityUtil.getContext(), primaryTenantPartnerVO.getLogin());
    }

}
