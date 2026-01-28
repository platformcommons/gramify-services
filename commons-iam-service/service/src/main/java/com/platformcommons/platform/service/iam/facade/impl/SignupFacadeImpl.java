package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.service.iam.application.TenantMetaMasterService;
import com.platformcommons.platform.service.iam.application.TenantMetaService;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.LeadService;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.application.constant.TenantMetaConstant;
import com.platformcommons.platform.service.iam.application.utility.OBOSecurityUtil;
import com.platformcommons.platform.service.iam.application.utility.TriggerNotification;
import com.platformcommons.platform.service.iam.application.utility.PasswordGeneratorUtil;
import com.platformcommons.platform.service.iam.domain.Lead;
import com.platformcommons.platform.service.iam.domain.TenantMeta;
import com.platformcommons.platform.service.iam.dto.*;
import com.platformcommons.platform.service.iam.dto.brbase.TLDTenantDTO;
import com.platformcommons.platform.service.iam.facade.*;
import com.platformcommons.platform.service.iam.facade.assembler.IAMUserDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.obo.TLDTenantDTOAssembler;
import com.platformcommons.platform.service.iam.facade.client.TLDClient;
import com.platformcommons.platform.service.iam.facade.obo.OBOFacade;
import com.platformcommons.platform.service.iam.facade.assembler.LeadDTOAssembler;
import com.platformcommons.platform.service.iam.facade.client.MarketClient;
import com.platformcommons.platform.service.iam.facade.obo.OBOTenantFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import com.platformcommons.platform.service.market.dto.TraderDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Optional;

import com.platformcommons.platform.service.iam.domain.TenantMetaMaster;

@Component
@Transactional
@RequiredArgsConstructor
public class SignupFacadeImpl implements SignupFacade {

    private final LeadFacade leadFacade;
    private final TenantFacade tenantFacade;
    private final UserFacade userFacade;
    private final OBOTenantFacade oboTenantFacade;
    private final OBOFacade oboFacade;
    private final TriggerNotification notificationUtil;
    private final TLDTenantDTOAssembler tldTenantDTOAssembler;
    private final IAMUserDTOAssembler iamUserDTOAssembler;
    private final TenantMetaService tenantMetaService;
    private final OBOSecurityUtil oboSecurityUtil;
    private final LeadDTOAssembler leadDTOAssembler;
    private final LeadService leadService;
    private final PasswordGeneratorUtil passwordGeneratorUtil;
    private final MarketClient marketClient;
    private final TLDClient tldClient;
    private final UserVerificationFacade userVerificationFacade;

    private final TenantMetaMasterService tenantMetaMasterService;

    @Value("${commons.platform.service.vms.dataset-group:VMS_TEST}")
    public String vmsDatasetGroupCode;

    @Override
    public OTPResponse registerIntentTenant(LeadDTO leadDTO, Boolean sendOTP) {
        String leadType = sendOTP ? leadDTO.getType() : IAMConstant.LEAD_TYPE_CONTACT_LEAD;
        leadDTO.setType(leadType);
        String key = leadFacade.saveOrUpdateLead(leadDTO);
        String messageId = null;
        if (sendOTP) {
            if (leadDTO.getUseMobileAsUserLogin() != null && leadDTO.getUseMobileAsUserLogin()) {
                messageId = notificationUtil.sendOTPNotificationSMS(leadDTO.getMobile(), leadDTO.getOrganizationName());
            } else {
                messageId = notificationUtil.sendOTPNotification(leadDTO.getEmail(),
                        leadDTO.getOrganizationName());
            }
        }
        return new OTPResponse(key, messageId);
    }

    @Override
    public OTPResponse registerIntentUser(LeadDTO leadDTO, Boolean sendOTP) {
        String leadType = IAMConstant.LEAD_TYPE_USER;
        leadDTO.setType(leadType);
        String key = leadFacade.saveOrUpdateLead(leadDTO);
        String messageId = null;
        if (sendOTP) {
            if (leadDTO.getUseMobileAsUserLogin() != null && leadDTO.getUseMobileAsUserLogin()) {
                messageId = notificationUtil.sendOTPNotificationSMS(leadDTO.getMobile(), leadDTO.getOrganizationName());
            } else {
                messageId = notificationUtil.sendOTPNotification(leadDTO.getEmail(),
                        leadDTO.getOrganizationName());
            }
        }
        return new OTPResponse(key, messageId);
    }

    @Override
    public OTPResponse resendOTP(String key, String email) {
        LeadDTO leadDTO = leadFacade.getLeadByKeyEmailAndActivationStatus(key, email, Lead.STATUS_REGISTERED);
        String messageId = notificationUtil.sendOTPNotification(leadDTO.getEmail(),
                leadDTO.getOrganizationName());
        return new OTPResponse(key, messageId);
    }


    @Override
    public OTPResponse resendOTPMobile(String key, String mobile) {
        LeadDTO leadDTO = leadFacade.getLeadByKeyMobileAndActivationStatus(key, mobile, Lead.STATUS_REGISTERED);
        String messageId = notificationUtil.sendOTPNotificationSMS(leadDTO.getMobile(),
                leadDTO.getOrganizationName());
        return new OTPResponse(key, messageId);
    }

    @Override
    public LeadExistResponse existsWithEmail(String email, String appContext, String marketUUID) {
        LeadExistResponse leadExistResponse = new LeadExistResponse();
        getExistingTenant(email, leadExistResponse);
        getExistingLeads(email, appContext, leadExistResponse, marketUUID);
        return leadExistResponse;
    }


    @Override
    public LeadExistResponse existsWithMobile(String mobile, String appContext, String marketUUID) {
        LeadExistResponse leadExistResponse = new LeadExistResponse();
        getExistingTenantByMobile(mobile, leadExistResponse);
        getExistingLeadsByMobile(mobile, appContext, leadExistResponse, marketUUID);
        return leadExistResponse;
    }


    @Override
    public TenantDTO addTenantVMS(SignUpRequestDTO signUpRequestDTO, String adminPassword) {
        LeadDTO leadDTO = validateLeadInput(signUpRequestDTO);
        validateOTP(signUpRequestDTO.getOtp(), signUpRequestDTO.getMessageId());
        TenantDTO tenantDTO = oboTenantFacade.addTenantInLinkedSystemVMS(leadDTO, adminPassword,null);
        tenantFacade.addTenantAndOperationsPostCreation(tenantDTO, leadDTO.getAppContext(), null, leadDTO.getKey(),
                vmsDatasetGroupCode);
        SecurityContextHolder.clearContext();
        return tenantDTO;
    }

    @Override
    public TenantDTO onBoardTenantFromLead(SignUpRequestDTO signUpRequestDTO, String adminPassword,
                                           Boolean createdInLinkedSystem) {
        LeadDTO leadDTO = validateLeadInput(signUpRequestDTO);
        validateOTP(signUpRequestDTO.getOtp(), signUpRequestDTO.getMessageId());
        return onBoardTenant(adminPassword, createdInLinkedSystem, leadDTO, signUpRequestDTO.getReferralCode());
    }

    public LeadDTO validateLeadInput(SignUpRequestDTO signUpRequestDTO) {
        LeadDTO leadDTO;
        if (signUpRequestDTO.getUseMobileAsUserLogin() != null && signUpRequestDTO.getUseMobileAsUserLogin()) {
            leadDTO = leadFacade.getLeadByKeyMobileAndActivationStatus(signUpRequestDTO.getKey(), signUpRequestDTO.getMobile()
                    , Lead.STATUS_REGISTERED);
        } else {
            leadDTO = leadFacade.getLeadByKeyEmailAndActivationStatus(signUpRequestDTO.getKey(), signUpRequestDTO.getEmail()
                    , Lead.STATUS_REGISTERED);
        }
        return leadDTO;
    }


    public TenantDTO onBoardTenant(String adminPassword, Boolean createdInLinkedSystem, LeadDTO leadDTO, String referralCode) {
        if (createdInLinkedSystem == null)
            createdInLinkedSystem = true;
        oboSecurityUtil.initPrivilegeUser();
        TenantDTO tenantDTO = tldTenantDTOAssembler.fromLeadDTO(leadDTO);
        String tenantLogin = ensureTenantLoginUniqueness(tenantDTO.getTenantLogin());
        tenantDTO.setTenantLogin(tenantLogin);
        tenantFacade.addTenant(tenantDTO, adminPassword, leadDTO.getAppContext(),leadDTO.getMarketContext(), createdInLinkedSystem,referralCode);
        leadFacade.leadToTenant(leadDTO.getKey());
        SecurityContextHolder.clearContext();
        return tenantDTO;
    }




    @Override
    public LeadExistResponse exists(String email, String mobile, String appContext) {
        if (email == null && mobile == null) {
            throw new InvalidInputException("Either Email Or mobile should be provided");
        } else if (email != null) {
            return existsWithEmail(email, appContext, null);
        } else {
            return existsWithMobile(mobile, appContext, null);
        }
    }


    @Override
    public IAMUserDTO onBoardUserFromLead(SignUpRequestDTO signUpRequestDTO, String password, String tenantLogin, String userLogin) {
        LeadDTO leadDTO = validateLeadInput(signUpRequestDTO);
        if (leadDTO == null) {
            throw new InvalidInputException("Lead details are not valid");
        }
        if (!Objects.equals(leadDTO.getType(), IAMConstant.LEAD_TYPE_USER)) {
            throw new UnAuthorizedAccessException("Not authorized to use api to register use of lead type other than user");
        }
        validateOTP(signUpRequestDTO.getOtp(), signUpRequestDTO.getMessageId());
        return onBoardUser(signUpRequestDTO, password, tenantLogin, userLogin, leadDTO);
    }

    @Override
    public String generateSessionIdForTenant(TenantDTO tenantDTO, SignUpRequestDTO signUpRequestDTO,
                                             String adminPassword, Boolean createdInLinkedSystem) {
        String sessionID;
        String primaryLogin = tenantDTO.getUseMobileAsLogin() != null && tenantDTO.getUseMobileAsLogin() ? signUpRequestDTO.getMobile() :
                signUpRequestDTO.getEmail();

        if (createdInLinkedSystem)
            sessionID = oboFacade.getSessionId(LoginRequestDTO.builder()
                            .tenantLogin(tenantDTO.getTenantLogin())
                            .userLogin(primaryLogin)
                            .password(adminPassword).build(),
                    null, null, null, null);
        else
            sessionID = null;
        return sessionID;
    }


    public IAMUserDTO onBoardUser(SignUpRequestDTO signUpRequestDTO, String password, String tenantLogin,
                                  String userLogin, LeadDTO leadDTO) {
        String userStatus = validateIfSelfRegistrationIsAllowedForTenant(tenantLogin, signUpRequestDTO.getDefaultMarketUUID());
        TenantDTO tenantDTO = tenantFacade.getTenantByLogin(tenantLogin);
        IAMUserDTO iamUserDTO = iamUserDTOAssembler.fromDTO(leadDTO, userLogin, userStatus);
        Long userId = userFacade.selfRegisterUser(iamUserDTO, password, tenantDTO, signUpRequestDTO.isAddInLinkedSystem());
        leadFacade.leadToUser(leadDTO.getKey());
        iamUserDTO.setId(userId);
        if (!IAMConstant.USER_STATUS_ACTIVE.equals(iamUserDTO.getStatus())) {
            userVerificationFacade.addEntryForUserVerificationOnSelfSignUp(tenantLogin, userId, leadDTO.getAppContext(), leadDTO.getMarketContext());
        }
        return iamUserDTO;
    }


    private void getExistingLeads(String email, String appContext, LeadExistResponse leadExistResponse, String marketUUID) {
        List<LeadDTO> leads = leadFacade.existsLeadDTOWithEmail(email, appContext, marketUUID);
        if (leads != null && !leads.isEmpty()) {
            leadExistResponse.setLeadPresent(true);
            leadExistResponse.setLeads(leads);
        }
    }


    private void getExistingLeadsByMobile(String mobile, String appContext, LeadExistResponse leadExistResponse, String marketUUID) {
        List<LeadDTO> leads = leadFacade.existsLeadDTOWithMobile(mobile, appContext, marketUUID);
        if (leads != null && !leads.isEmpty()) {
            leadExistResponse.setLeadPresent(true);
            leadExistResponse.setLeads(leads);
        }
    }

    private void getExistingTenant(String email, LeadExistResponse leadExistResponse) {
        List<TenantVO> tenants = tenantFacade.existsTenantWithEmail(email);
        if (tenants != null && !tenants.isEmpty()) {
            leadExistResponse.setTenantPresent(true);
            leadExistResponse.setTenants(tenants);
        }
    }

    private void getExistingTenantByMobile(String mobile, LeadExistResponse leadExistResponse) {
        List<TenantVO> tenants = tenantFacade.existsTenantWithMobile(mobile);
        if (tenants != null && !tenants.isEmpty()) {
            leadExistResponse.setTenantPresent(true);
            leadExistResponse.setTenants(tenants);
        }
    }

    private void validateOTP(String otp, String messageId) {
        boolean isValid = notificationUtil.validateOTP(messageId, otp);
        if (!isValid) {
            throw new InvalidInputException("Unable to validate entered OTP");
        }
    }

    private String validateIfSelfRegistrationIsAllowedForTenant(String tenantLogin, String appContext) {
        validateSelfRegistration(tenantLogin, appContext);
        return getDefaultUserStatus(tenantLogin, appContext);
    }

    private void validateSelfRegistration(String tenantLogin, String appContext) {
        TenantMeta tenantMeta = tenantMetaService.getTenantMetaData(tenantLogin, TenantMetaConstant.META_MASTER_ALLOWED_SELF_REGISTER);
        if(tenantMeta == null){
            String value = getDefaultValueFromMaster(appContext,TenantMetaConstant.META_MASTER_ALLOWED_SELF_REGISTER ,"false");
            if (!Boolean.parseBoolean(value)) {
                throw new UnAuthorizedAccessException("Self registration is not allowed for tenant");
            }
        }
        else if (!Boolean.parseBoolean(tenantMeta.getMetaValue())) {
            throw new UnAuthorizedAccessException("Self registration is not allowed for tenant");
        }
    }

    private String getDefaultValueFromMaster(String marketUUID,String metaKey, String defaultStatus) {
        return tenantMetaMasterService.getByAppContextAndCode( marketUUID,metaKey)
                .map(master -> master.getMetaDefaultValues().stream()
                        .findAny()
                        .orElse(defaultStatus))
                .orElse(defaultStatus);
    }

    private String getDefaultUserStatus(String tenantLogin, String marketUUID) {
        TenantMeta tenantMeta = tenantMetaService.getTenantMetaData(tenantLogin,
                TenantMetaConstant.META_MASTER_DEFAULT_USER_STATUS);
        if (tenantMeta != null) {
            return tenantMeta.getMetaValue();
        }
        return getDefaultValueFromMaster(marketUUID,TenantMetaConstant.META_MASTER_DEFAULT_USER_STATUS, IAMConstant.USER_STATUS_ACTIVE);
    }

    private String ensureTenantLoginUniqueness( String tenantLoginName) {
        boolean tenantLoginExist = tenantFacade.existsTenantWithLogin(tenantLoginName);
        boolean tenantLoginExistsInTLD= existsInTLD(tenantLoginName);
        if(tenantLoginExist || tenantLoginExistsInTLD){
            tenantLoginName = tenantLoginName+"-"+new Random().nextInt(123);
            ensureTenantLoginUniqueness(tenantLoginName);
        }
        return tenantLoginName;
    }

    private boolean existsInTLD(String tenantLogin){
        TLDTenantDTO tldTenantDTO= oboTenantFacade.getTenantFromLinkedSystem(tenantLogin);
        return tldTenantDTO==null ? Boolean.FALSE : Boolean.TRUE;
    }

    @Override
    public TenantDTO convertLeadToTenantMarkify(Long leadId, Long marketId, String url) {
        LeadDTO leadDTO = leadDTOAssembler.toDTO(leadService.getLeadById(leadId));
        String adminPassword = passwordGeneratorUtil.generatePassword();

        TenantDTO tenantDTO = onBoardTenant(adminPassword, null, leadDTO,null);
        String sessionId = Objects.requireNonNull(tldClient.login("admin@@" + tenantDTO.getTenantLogin(),adminPassword
                , null, null,
                null, null).getHeaders().get(PlatformSecurityConstant.SESSIONID)).get(0);

        Long traderId = marketClient.onBoardTraderWithDefaultMarketConfig(buildTraderFromTenant(tenantDTO),marketId,
                false, sessionId).getBody();
        if(traderId != null && tenantDTO.getEmail() != null){
            notificationUtil.sendTraderCreationMail(leadDTO.getAppContext(), leadDTO.getLeadContactPersonName(),
                    tenantDTO.getTenantName(), tenantDTO.getEmail(), tenantDTO.getUseMobileAsLogin()?tenantDTO.getMobile():tenantDTO.getEmail(),
                    tenantDTO.getTenantLogin(),adminPassword, url);
        }
        return tenantDTO;
    }

    private TraderDTO buildTraderFromTenant(TenantDTO tenantDTO) {
        return TraderDTO.builder()
                .id(0L)
                .traderDisplayName(tenantDTO.getTenantName())
                .traderOrganisationName(tenantDTO.getTenantName())
                .traderPrimaryMobile(tenantDTO.getMobile())
                .traderTenantId(tenantDTO.getId())
                .traderType("MARKIFY_TRADER_TYPE.FPO").build();
    }

}
