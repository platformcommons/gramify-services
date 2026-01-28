package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthenticatedAccessException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.AuthenticationService;
import com.platformcommons.platform.service.iam.application.UserSecurityService;
import com.platformcommons.platform.service.iam.application.UserService;
import com.platformcommons.platform.service.iam.application.UserSessionService;
import com.platformcommons.platform.service.iam.application.utility.IAMSecurityUtility;
import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.domain.UserSecurity;
import com.platformcommons.platform.service.iam.dto.LoginRequestDTO;
import com.platformcommons.platform.service.iam.dto.LoginWithContactRequestDTO;
import com.platformcommons.platform.service.iam.dto.OTPRequestDTO;
import com.platformcommons.platform.service.iam.dto.WrapperLoginRequestDTO;
import com.platformcommons.platform.service.iam.exception.RequestThrottledException;
import com.platformcommons.platform.service.iam.facade.assembler.AppDetailsFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final UserSecurityService userSecurityService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserSessionService userSessionService;
    private final IAMSecurityUtility utility;
    private final AppDetailsFacade appDetailsFacade;

    @Override
//    @Transactional(noRollbackFor = UnAuthenticatedAccessException.class)
    //todo check why UnAuthenticatedAccessException is not thrown when noRollbackFor written
    @Transactional
    public String login(LoginRequestDTO dto,  String appId, String appVersion, String appKey, HttpServletRequest request) {
        User user;
        try {
            user = userService.findByUserAndTenantLogin(dto.getUserLogin(), dto.getTenantLogin());
        } catch (NotFoundException ex) {
            throw new UnAuthenticatedAccessException("Invalid credentials");
        }
        return login(dto.getTenantLogin(), dto.getPassword(), appId, appVersion, appKey,  user);
    }

    @Override
    @Transactional
    public String loginWithContact(LoginWithContactRequestDTO dto, String appId, String appVersion, String appKey, String deviceId, HttpServletRequest request) {
        User user = userService.findByUserContactAndTenantLogin(dto.getTenantLogin(), dto.getContact());
        return login(dto.getTenantLogin(), dto.getPassword(), appId, appVersion, appKey, user);
    }

    @Override
    public String forgetPassword(WrapperLoginRequestDTO dto, String appContext) {
        appContext = utility.getAppContext(appContext, null, null);
        User user = userService.findByUserAndTenantLogin(dto.getUserLogin(), dto.getTenantLogin());
        return userSecurityService.generateOTPForgetPassword(user, appContext);
    }

    @Override
    @Transactional(noRollbackFor = {RequestThrottledException.class,UnAuthorizedAccessException.class})
    public void resetPassword(OTPRequestDTO dto, String appContext) {
        appContext = utility.getAppContext(appContext, null, null);
        User user = userService.findByUserAndTenantLogin(dto.getUserLogin(), dto.getTenantLogin());
        userSecurityService.resetPassword(dto, user, appContext);
    }

    @Override
    public String loginWithOTPRequest(WrapperLoginRequestDTO wrapperLoginRequestDTO, String appContext) {
        appContext = utility.getAppContext(appContext, null, null);
        User user = userService.findByUserAndTenantLogin(wrapperLoginRequestDTO.getUserLogin(), wrapperLoginRequestDTO.getTenantLogin());
        return userSecurityService.generateOTPForLoginRequest(user, appContext);
    }

    @Override
    @Transactional(noRollbackFor = UnAuthorizedAccessException.class)
    public String loginWithOTP(OTPRequestDTO otpRequestDTO, String appContext) {
        appContext = utility.getAppContext(appContext, null, null);
        User user = userService.findByUserAndTenantLogin(otpRequestDTO.getUserLogin(), otpRequestDTO.getTenantLogin());
        PlatformSecurityUtil.mimicTenant(user.getTenantId(), user.getId());
        try {
            return userSecurityService.validateOTPAndLogin(user, otpRequestDTO, appContext);
        } catch (UnAuthorizedAccessException e) {
            userSecurityService.updateFailedLoginAttempts(otpRequestDTO.getTenantLogin(), userSecurityService.getUserSecurity(user), appContext);
            throw e;
        }
    }

    @Override
    public void logout(String sessionId) {
        utility.invalidateSession(sessionId);
        userSessionService.invalidateSessionDetails(sessionId);
    }


    @Override
    public String crossUserId(Long crossUserId, PlatformToken platformTokenActual, String tenantLogin) {
        User user = userService.findByUserIdAndTenantLogin(crossUserId,tenantLogin);
        PlatformToken platformToken = utility.generatePlatformTokenAndSetToContext(user,platformTokenActual);
        return platformToken.getToken();
    }

    public String login(String tenantLogin, String password, String appId, String appVersion, String appKey,
                         User user) {
       String appContext = utility.getAppContext(appId,appVersion,appKey);
        String token;
        UserSecurity userSecurity;
        boolean passwordMatched;
        userSecurity = userSecurityService.getUserSecurity(user);
        passwordMatched = validatePassword(password, userSecurity.getPassword());
        if (user != null)
            PlatformSecurityUtil.mimicTenant(user.getTenant().getId(), user.getId());
        if (!passwordMatched) {
            userSecurityService.updateFailedLoginAttempts(tenantLogin, userSecurity, appContext);
            throw new UnAuthenticatedAccessException("Invalid Credentials");
        }
        PlatformToken platformToken = utility.generatePlatformTokenAndSetToContext(user, null);
        token = platformToken.getToken();
        userSecurityService.resetFailedLoginAttempts(userSecurity);
        return token;
    }


    private boolean validatePassword(String password, String encoded) {
        return passwordEncoder.matches(password, encoded);
    }


}
