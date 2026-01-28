package com.platformcommons.platform.service.iam.application.utility;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.MarketConfigDTO;
import com.platformcommons.platform.service.iam.dto.SignUpRequestDTO;
import com.platformcommons.platform.service.iam.facade.LeadFacade;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OTPValidationUtil {

    private final LeadFacade leadFacade;

    public void validateOTP(String otpForEmail, String otpForMobile, String messageIdForEmail, String messageIdForMobile,
                            LeadDTO leadDTO,TriggerNotification triggerNotification) {
        if (!StringUtils.isBlank(otpForEmail) && !StringUtils.isBlank(messageIdForEmail)) {
            boolean isValid = triggerNotification.validateOTP(messageIdForEmail, otpForEmail);
            if (!isValid) {
                throw new InvalidInputException("Unable to validate Email OTP");
            }
            leadDTO.setIsEmailVerified(true);
        }
        if (!StringUtils.isBlank(otpForMobile) && !StringUtils.isBlank(messageIdForMobile)) {
            boolean isValid = triggerNotification.validateOTP(messageIdForMobile, otpForMobile);
            if (!isValid) {
                throw new InvalidInputException("Unable to validate Mobile OTP");
            }
            leadDTO.setIsMobileVerified(true);
        }
        leadFacade.updateContactVerification(leadDTO);
    }

    public void checkForLeadVerification(LeadDTO leadDTO, MarketConfigDTO marketConfig) {
        String signupValidationMethod = marketConfig.getSignupValidationMethod();
        if (signupValidationMethod == null) {
            return;
        }

        boolean isEmailVerified = Boolean.TRUE.equals(leadDTO.getIsEmailVerified());
        boolean isMobileVerified = Boolean.TRUE.equals(leadDTO.getIsMobileVerified());
        boolean useMobileAsLogin = Boolean.TRUE.equals(leadDTO.getUseMobileAsUserLogin());

        switch (signupValidationMethod) {
            case IAMConstant.SIGN_UP_VALIDATION_LOGIN:
                if (useMobileAsLogin) {
                    if (!isMobileVerified) {
                        throw new UnAuthorizedAccessException("Mobile Number is not verified");
                    }
                } else {
                    if (!isEmailVerified) {
                        throw new UnAuthorizedAccessException("Email is not verified");
                    }
                }
                break;

            case IAMConstant.SIGN_UP_VALIDATION_EMAIL_AND_MOBILE:
                if (!isEmailVerified) {
                    throw new UnAuthorizedAccessException("Email is not verified");
                }
                if (!isMobileVerified) {
                    throw new UnAuthorizedAccessException("Mobile Number is not verified");
                }
                break;

            default:
                throw new NotFoundException("SignUpValidationMethod did not match standard values");
        }
    }

    public void validateLeadOTPs(String otpForEmail,String otpForMobile,String messageIdForEmail, String messageIdForMobile,
                                 LeadDTO leadDTO, MarketConfigDTO marketConfigDTO,
                                 TriggerNotification triggerNotification) {
        validateOTP(otpForEmail,otpForMobile,messageIdForEmail,messageIdForMobile ,leadDTO, triggerNotification);
        checkForLeadVerification(leadDTO, marketConfigDTO);
    }
}
