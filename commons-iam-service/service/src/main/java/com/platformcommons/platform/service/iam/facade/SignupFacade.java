package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.iam.dto.*;

public interface SignupFacade {

    OTPResponse registerIntentTenant(LeadDTO leadDTO, Boolean sendOTP);

    OTPResponse registerIntentUser(LeadDTO leadDTO, Boolean sendOTP);

    OTPResponse resendOTP(String key, String email);

    OTPResponse resendOTPMobile(String key, String mobile);

    LeadExistResponse existsWithEmail(String email, String appContext, String marketUUID);

    LeadExistResponse existsWithMobile(String mobile, String appContext, String marketUUID);

    LeadExistResponse exists(String email, String mobile, String appContext);

    TenantDTO addTenantVMS(SignUpRequestDTO signUpRequestDTO, String adminPassword);

    TenantDTO onBoardTenantFromLead(SignUpRequestDTO signUpRequestDTO, String adminPassword,
                                    Boolean createdInLinkedSystem);

    IAMUserDTO onBoardUserFromLead(SignUpRequestDTO signUpRequestDTO, String password, String tenantLogin, String login);

    String generateSessionIdForTenant(TenantDTO tenantDTO, SignUpRequestDTO signUpRequestDTO, String adminPassword, Boolean createdInLinkedSystem);


    TenantDTO convertLeadToTenantMarkify(Long leadId, Long marketId, String url);
}
