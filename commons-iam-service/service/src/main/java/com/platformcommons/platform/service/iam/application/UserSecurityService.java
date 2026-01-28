package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.domain.UserSecurity;
import com.platformcommons.platform.service.iam.dto.OTPRequestDTO;


public interface UserSecurityService {
    UserSecurity create(User user,String password);

    UserSecurity getUserSecurity(User id);

    void updateFailedLoginAttempts(String tenantLogin, UserSecurity userSecurity, String appContext);

    void resetFailedLoginAttempts(UserSecurity userSecurity);
    
    String generateOTPForgetPassword(User user, String appContext);

    void resetPassword(OTPRequestDTO dto, User user, String appContext);

    String generateOTPForLoginRequest(User user, String appContext);

    String validateOTPAndLogin(User user, OTPRequestDTO otpRequestDTO, String appContext);

    UserSecurity createForSelfRegisteredUser(User createdUser, Long id, String password, boolean createOTP, String appContext);

    void validateOTPForActivateUser(User user, String otp, String modKey);

    void resendOTP(User user, String appContext);

}
