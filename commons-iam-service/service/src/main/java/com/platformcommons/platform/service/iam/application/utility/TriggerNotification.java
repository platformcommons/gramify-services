package com.platformcommons.platform.service.iam.application.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.mindtree.bridge.platform.dto.UserDTO;
import com.mindtree.bridge.platform.dto.UserRoleMapDTO;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.TenantMetaConfigService;
import com.platformcommons.platform.service.iam.application.TenantMetaService;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.application.constant.NotificationConstant;
import com.platformcommons.platform.service.iam.application.constant.VerificationConstant;
import com.platformcommons.platform.service.iam.domain.vo.UserVO;
import com.platformcommons.platform.service.iam.dto.*;
import com.platformcommons.platform.service.iam.facade.TenantFacade;
import com.platformcommons.platform.service.iam.facade.TenantMetaConfigFacade;
import com.platformcommons.platform.service.iam.facade.UserVerificationFacade;
import com.platformcommons.platform.service.iam.facade.client.ChangemakerOpportunityClient;
import com.platformcommons.platform.service.iam.facade.client.NotificationClient;
import com.platformcommons.platform.service.iam.facade.client.OpportunityClient;
import com.platformcommons.platform.service.iam.facade.client.TLDClient;
import com.platformcommons.platform.service.iam.messaging.model.NotificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;

@Component
@Slf4j
@Transactional
public class TriggerNotification {

    @Value("${commons.config.lead.otp.mail.sender:hi@commons.team}")
    private String fromUser;
    @Value("${commons.config.lead.otp.mail.notif_code:EMAIL_OTP_VERIFICATION}")
    private String otpNotificationCode;

    @Value("${commons.config.lead.otp.sms.notif_code:SMS_OTP_VERIFICATION}")
    private String notificationCodeSMS;

    @Value("${commons.config.mail.user.approval.notif_code:USER_VERIFICATION_APPROVAL}")
    private String notificationCodeForUserApproval;

    @Value("${commons.config.mail.user.rejection.notif_code:USER_VERIFICATION_REJECTION}")
    private String notificationCodeForUserRejection;

    @Value("${commons.config.mail.user.approval.subject:Verification Mail}")
    private String defaultUserVerificationApprovalSubject;

    @Value("${commons.config.mail.user.approval.subject:Rejection Mail}")
    private String defaultUserVerificationRejectionSubject;

    @Value("${commons.config.mail.user.application:Assessment Org}")
    private String defaultApplicationName;
    @Value("${commons.config.mail.user.application:admin@platformcommons.com}")
    private String defaultSupportTeamEmail;

    @Value("${commons.config.mail.user.applicationadmin@platformcommons.com:admin@platformcommons.com}")
    private String defaultContactInformation;

    @Value("${commons.config.lead.welcome.mail.notif_code:EMAIL_WELCOME_TENANT_BT}")
    private String notificationCodeForWelcomeMail;

    @Value("${commons.config.lead.welcome.mail.subject:Welcome to the community of changemakers.}")
    private String welcomeMailSubject;
    @Value("${commons.config.lead.otp.mail.subject:OTP for activating account}")
    private String msgSubject;
    @Value("${commons.config.lead.otp.mail.apiKey:Apikey 8a034304-864e-4a39-b78f-b4cae0dbdf25}")
    private String apiKey;


    @Value("${commons.config.invite.link.mail.notif_code:EMAIL_INVITE_VOLUNTEER_BT}")
    private String notificationCodeForInvite;

    @Value("${commons.config.user.registration.mail.notif_code:EMAIL_USER_REGISTRATION}")
    private String defaultNotificationCodeForUserRegistration;
    @Value("${commons.config.user.registration.mail.subject:New User Registration - Approval Required}")
    private String defaultMessageSubjectForUserRegistration;

    @Value("${commons.config.user.registration.mail.from_user:hi@commons.team}")
    private String fromUserForUserRegistration;
    @Value("${commons.config.user.registration.mail.link:https://dev.platformcommons.org/commons-assessment-org/workplace/user-list}")
    private String defaultAppLink;


    @Value("${commons.config.invite.link.mail.subject:EMAIL_INVITE_VOLUNTEER_BT}")
    private String msgSubjectForInvite;

    @Value("${commons.config.trader.welcome.mail.subject:Welcome to the community of markify.}")
    private String welcomeTraderSubject;

    @Value("${commons.config.trader.welcome.mail.notif_code:EMAIL_WELCOME_TRADER_BT}")
    private String notificationCodeForTraderWelcomeMail;

    @Autowired
    private Environment environment;

    @Autowired
    private TenantMetaConfigService metaConfigService;

    @Autowired
    private TLDClient tldClient;

    private final NotificationClient notificationClient;

    @Autowired
    private OBOSecurityUtil oboSecurityUtil;

    @Value("${commons.config.iam.otp.mail.notif_code:IAM_EMAIL_OTP_VERIFICATION}")
    private String notificationCodeForGeneratedOTP;

    @Autowired
    private TriggerNotificationUtil triggerNotificationUtil;

    @Autowired
    private TenantMetaConfigFacade tenantMetaConfigFacade;

    @Autowired
    private ChangemakerOpportunityClient changemakerOpportunityClient;

    @Autowired
    @Lazy
    private TenantFacade tenantFacade;

    @Autowired
    private OpportunityClient opportunityClient;

    @Autowired
    @Lazy
    private UserVerificationFacade userVerificationFacade;
    @Autowired
    private TenantMetaService tenantMetaService;

    @Autowired
    private VMSSignupUtil vmsSignupUtil;

    public TriggerNotification(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    public String sendOTPNotification(String email,String name) {
        NotificationDTO notificationDTO = buildNotificationDTOForOTP(email,name, otpNotificationCode,msgSubject);
        return notificationClient.sendNotification(notificationDTO, apiKey);
    }

    public String sendMailOTPNotificationForLead(LeadDTO leadDTO) {
        String messageId = null;
        String email = leadDTO.getEmail();
        String fullName = TriggerNotificationUtil.computeFullName(leadDTO.getFirstName(),leadDTO.getLastName());
        String customNotificationCode = null;
        String subject = null;
        Long tenantId = null;
        Optional<TenantDTO> tenantDTOOptional = Optional.empty();
        Optional<TenantMetaConfigDTO> tenantMetaConfigDTOOptional = Optional.empty();

        if(leadDTO.getType().equals(IAMConstant.LEAD_TYPE_ORGANISATION)) {
            customNotificationCode = NotificationConstant.NOTIFICATION_CODE_TENANT_SIGN_UP_EMAIL_OTP;
            subject = "%s: Verify Your Signup with This OTP";
            tenantId = getTenantIdByMarketContextAndAppContext(leadDTO.getAppContext(),leadDTO.getMarketContext());
        }
        else if(leadDTO.getType().equals(IAMConstant.LEAD_TYPE_USER)) {
            customNotificationCode = NotificationConstant.NOTIFICATION_CODE_USER_SIGN_UP_EMAIL_OTP;
            subject = "%s: Verify Your Organization Signup with This OTP";
            tenantId = leadDTO.getTenantId();
        }
        else {
            throw new InvalidInputException("Lead type did not match standard value");
        }

        if(tenantId != null) {
            tenantDTOOptional = tenantFacade.getTenantByIdOptional(tenantId);
            tenantMetaConfigDTOOptional = tenantMetaConfigFacade.getTenantMetaConfigOptional(tenantId,null);
        }
        if(tenantDTOOptional.isPresent() && tenantMetaConfigDTOOptional.isPresent() ) {
            TenantDTO tenantDTO = tenantDTOOptional.get();
            TenantMetaConfigDTO tenantMetaConfigDTO = tenantMetaConfigDTOOptional.get();
            subject = String.format(subject,tenantDTO.getTenantName());
            String sessionId = oboSecurityUtil.getSessionIdFromLoginDetails(tenantMetaConfigDTO.getUserLogin(),tenantMetaConfigDTO.getPassword(),
                    tenantMetaConfigDTO.getTenantLogin(),null,null,null,null);
            NotificationDTO notificationDTO = buildNotificationDTOForOTPMailWithTenantContext(email,fullName,
                    customNotificationCode,subject,tenantDTO,tenantMetaConfigDTO);
            messageId = notificationClient.sendNotificationWithSessionId(notificationDTO, null, sessionId);
        }
        else {
            NotificationDTO notificationDTO = buildNotificationDTOForOTP(email,fullName,otpNotificationCode,msgSubject);
            messageId = notificationClient.sendNotification(notificationDTO, apiKey);
        }
        return messageId;
    }

    private NotificationDTO buildNotificationDTOForOTPMailWithTenantContext(String email,String name,String notificationCode,String subject,
                                                       TenantDTO tenantDTO,TenantMetaConfigDTO tenantMetaConfigDTO) {
        Map<String,String> params = new HashMap<>();
        params.put("MSG_SUBJECT",subject);
        params.put("NAME",name);
        params.put("TENANT_LOGO_URL",tenantDTO.getIconpic() != null ? tenantDTO.getIconpic() : "");
        params.put("TENANT_NAME",tenantDTO.getTenantName());

        triggerNotificationUtil.appendTenantSupportEmailAndMobileNumber(params,tenantMetaConfigDTO);

        return NotificationDTO.builder()
                .notificationCode(notificationCode)
                .params(params)
                .toUsers(Collections.singletonList(email))
                .build();
    }

    private NotificationDTO buildNotificationDTOForOTP(String email,String name,String notificationCode,String subject) {
        Map<String,String> params = new HashMap<>();
        params.put("MSG_SUBJECT",subject);
        params.put("NAME",name);

        return NotificationDTO.builder()
                .fromUser(fromUser)
                .notificationCode(notificationCode)
                .params(params)
                .toUsers(Collections.singletonList(email))
                .build();
    }

    public Long getTenantIdByMarketContextAndAppContext(String appContext,String marketContext) {
        Long tenantId = null;
        if(marketContext != null && appContext != null) {
            try{
                MarketConfigDTO marketConfigDTO = vmsSignupUtil.fetchMarketConfig(marketContext);
                tenantId = marketConfigDTO.getGovernorTenant();
            } catch (Exception e) {
                return tenantId;
            }
        }
        return tenantId;
    }

    public String sendOTPNotificationSMS(String mobile, String organizationName) {
        NotificationDTO notificationDTO = buildNotificationDTOForOTP("+91-"+mobile,organizationName,notificationCodeSMS,msgSubject);
        return notificationClient.sendNotification(notificationDTO, apiKey);
    }

    public boolean validateOTP(String messageId, String otp) {
        JsonNode response = notificationClient.validateOTP(messageId, otp, apiKey);
        return response.get("isValid").asBoolean();
    }


    public void sendTenantCreationMail(String appContext, String contactPersonName,String tenantName, String email,
                                       String tenantLogin,String baseDomain) {

        if(appContext.equals(IAMConstant.APP_CONTEXT_CM_ADMIN)) {
            Map<String,String> params = new HashMap<>();
            params.put("MSG_SUBJECT",welcomeMailSubject);
            params.put("NAME",contactPersonName);
            params.put("TENANT_NAME",tenantName);
            params.put("TENANT_LOGIN",tenantLogin);
            params.put("BASE_DOMAIN",baseDomain);
            params.put("TENANT_USER_LOGIN",PlatformSecurityUtil.getCurrentUserLogin());

            NotificationDTO notificationDTO = NotificationDTO.builder()
                    .fromUser(fromUser)
                    .notificationCode(notificationCodeForWelcomeMail)
                    .params(params)
                    .toUsers(Collections.singletonList(email))
                    .build();

            String messageId = notificationClient.sendNotification(notificationDTO, apiKey);
            log.debug(new Date() + "==Welcome MessageId for tenant  : " + tenantName + "==" + messageId);
        }
    }


    @Async
    public void sendNotificationOnUserAdd(UserDTO userDTO, UserRoleMapDTO userRoleMapDTO, Authentication authentication,
                                          String appContext, String baseDomain, String marketId) {
        PlatformSecurityUtil.setAuthenticationInSecurityContext(authentication);

        String notificationCode = NotificationConstant.NOTIFICATION_CODE_USER_ADD_WELCOME;

        if(appContext != null) {
            if(appContext.equals(IAMConstant.CM_ADMIN)) {
                notificationCode = NotificationConstant.NOTIFICATION_CODE_VMS_USER_ADD_WELCOME;
                MarketConfigDTO marketConfigDTO = opportunityClient.getByMarketUUID(marketId, null).getBody();
                if(marketConfigDTO != null) {
                    baseDomain = marketConfigDTO.getMarketAppBaseUrl();
                }
            }
        }

        String assignedUserRoleLabel = "member";
        if(userRoleMapDTO != null) {
            assignedUserRoleLabel = userRoleMapDTO.getRole().getRoleName();
        }

        UserDetailsDTO candidateUserDetailsDTO = TriggerNotificationUtil.getUserDetailsByUserDTO(userDTO);
        UserDetailsDTO currentUserDetailDTO = triggerNotificationUtil.getUserDetailsByUserId(PlatformSecurityUtil.getCurrentUserId());

        TenantDTO tenantDTO = tenantFacade.getTenantById(PlatformSecurityUtil.getCurrentTenantId());

        NotificationDTO notificationDTO = buildNotificationDTOForUserAdd(candidateUserDetailsDTO, currentUserDetailDTO,
                assignedUserRoleLabel, notificationCode, tenantDTO, baseDomain);

        String messageId = notificationClient.sendNotificationWithSessionId(notificationDTO, marketId, PlatformSecurityUtil.getToken());
        log.info("User Add Notification For User -{}. MessageId --> {}", userDTO.getLogin(), messageId);
    }


    private NotificationDTO buildNotificationDTOForUserAdd(UserDetailsDTO candidateUserDetailsDTO, UserDetailsDTO currentUserDetailDTO,
                                                           String assignedUserRoleLabel, String notificationCode,
                                                           TenantDTO tenantDTO, String baseDomain) {
        Map<String,String> params = new HashMap<>();

        String subject = String.format("Hi %s, Welcome to %s",candidateUserDetailsDTO.getFullName(),tenantDTO.getTenantName());

        params.put("MSG_SUBJECT",subject);
        params.put("Candidate",candidateUserDetailsDTO.getFullName());
        params.put("CurrentUserName", currentUserDetailDTO != null ? currentUserDetailDTO.getFullName() : "System Administrator");
        params.put("UserId",candidateUserDetailsDTO.getUserId().toString());
        params.put("RoleName",assignedUserRoleLabel);
        params.put("UserLogin",candidateUserDetailsDTO.getUserLogin());
        params.put("AppBaseUrl",baseDomain);
        params.put("TenantLogoUrl",tenantDTO.getIconpic() != null ? tenantDTO.getIconpic() : "");
        params.put("OrganisationName",tenantDTO.getTenantName());

        triggerNotificationUtil.addTenantSupportEmailAndMobileNumber(params, tenantDTO.getId());

        if( params.containsKey(NotificationConstant.TENANT_SUPPORT_EMAILS)
                && StringUtils.isBlank(params.get(NotificationConstant.TENANT_SUPPORT_EMAILS)) ) {
            params.put(NotificationConstant.TENANT_SUPPORT_EMAILS, "admin@platformcommons.com");
        }

        return NotificationDTO.builder()
                .notificationCode(notificationCode)
                .params(params)
                .toUsers(Collections.singletonList(candidateUserDetailsDTO.getEmailAddress()))
                .build();
    }


    @Async
    @Transactional
    public void sendApprovalMail(Map<Long, UserVO> userVO, Long tenantId) {
//        TenantMetaConfig tenantMetaConfig = metaConfigService.getTenantMetaConfig(tenantId, null);
//        User user = tenantMetaConfig.getAdminUsers().stream().findFirst().get();
//
//        Map<String,String> config=tenantMetaConfig.getConfig()==null?new HashMap<>():tenantMetaConfig.getConfig();
//
//        userVO.values().forEach(it-> {
//
//            JsonObject requestBody = new JsonObject();
//            requestBody.addProperty("fromUser", fromUser);
//            requestBody.addProperty("notificationCode", notificationCodeForUserApproval);
//            JsonObject params = new JsonObject();
//            params.addProperty("MSG_SUBJECT", config.get("APPROVAL_MAIL_SUBJECT") == null ? defaultUserVerificationApprovalSubject : config.get("APPROVAL_MAIL_SUBJECT"));
//            params.addProperty("APPROVAL_MAIL_ADMIN_NAME", config.get("APPROVAL_MAIL_ADMIN_NAME") == null ? user.getUserName() : config.get("APPROVAL_MAIL_ADMIN_NAME") );
//            params.addProperty("ASSESSMENT_APPLICATION", config.get("ASSESSMENT_APPLICATION") == null ? defaultApplicationName : config.get("ASSESSMENT_APPLICATION"));
//            params.addProperty("ASSESSMENT_APPLICATION_LINK", config.get("ASSESSMENT_APPLICATION_LINK") == null ? defaultApplicationName : config.get("ASSESSMENT_APPLICATION_LINK"));
//
//            params.addProperty("EMAIL_ADDRESS", it.getLogin());
//            params.addProperty("APPROVAL_MAIL_SUPPORT_TEAM_EMAIL", config.get("APPROVAL_MAIL_SUPPORT_TEAM_EMAIL") == null ? defaultSupportTeamEmail : config.get("APPROVAL_MAIL_SUPPORT_TEAM_EMAIL"));
//            params.addProperty("APPROVAL_MAIL_COMPANY_NAME", config.get("APPROVAL_MAIL_COMPANY_NAME")==null? tenantMetaConfig.getTenantName():config.get("APPROVAL_MAIL_COMPANY_NAME"));
//            params.addProperty("APPROVAL_MAIL_CONTACT_INFORMATION", config.get("APPROVAL_MAIL_CONTACT_INFORMATION") == null ? defaultContactInformation : config.get("APPROVAL_MAIL_CONTACT_INFORMATION"));
//            params.addProperty("FULL_NAME", (it.getFirstName() + (it.getLastName() == null ? "" : it.getLastName())));
//
//            requestBody.add("params", params);
//            JsonArray toUsers = new JsonArray();
//            toUsers.add(it.getLogin());
//            requestBody.add("toUsers", toUsers);
//            //String messageId = notificationClient.sendNotificationWithSessionId(requestBody.toString(),PlatformSecurityUtil.getToken());
//            //log.debug(new Date() + "==Approval MessageId for User  : " + it.getLogin() + "==" + messageId);
//        });
    }
    @Async
    @Transactional
    public void sendRejectionMail(Map<Long, UserVO> userVO, String rejectionReason, Long tenantId) {
//            TenantMetaConfig tenantMetaConfig = metaConfigService.getTenantMetaConfig(tenantId, null);
//            Map<String,String> config = tenantMetaConfig.getConfig();
//            User user = tenantMetaConfig.getAdminUsers().stream().findFirst().get();
//
//            userVO.values().forEach(
//                    it-> {
//                JsonObject requestBody = new JsonObject();
//                requestBody.addProperty("fromUser", fromUser);
//                requestBody.addProperty("notificationCode", notificationCodeForUserRejection);
//                JsonObject params = new JsonObject();
//                params.addProperty("MSG_SUBJECT", config.get("REJECTION_MAIL_SUBJECT")==null ? defaultUserVerificationRejectionSubject : config.get("REJECTION_MAIL_SUBJECT") );
//                params.addProperty("REJECTION_MAIL_ADMIN_NAME", config.get("REJECTION_MAIL_ADMIN_NAME") == null ? user.getUserName() : config.get("REJECTION_MAIL_ADMIN_NAME") );
//                params.addProperty("ASSESSMENT_APPLICATION", config.get("ASSESSMENT_APPLICATION")==null ? defaultApplicationName : config.get("ASSESSMENT_APPLICATION")  );
//                params.addProperty("FULL_NAME", (it.getFirstName()+(it.getLastName()==null?"":" "+it.getLastName())) );
//                params.addProperty("ASSESSMENT_APPLICATION_LINK", config.get("ASSESSMENT_APPLICATION_LINK") == null ? defaultApplicationName : config.get("ASSESSMENT_APPLICATION_LINK"));
//
//                params.addProperty("REJECTION_REASON",rejectionReason );
//                params.addProperty("REJECTION_MAIL_SUPPORT_TEAM_EMAIL",config.get("REJECTION_MAIL_SUPPORT_TEAM_EMAIL")==null?defaultSupportTeamEmail:config.get("REJECTION_MAIL_SUPPORT_TEAM_EMAIL"));
//                params.addProperty("REJECTION_MAIL_COMPANY_NAME", config.get("REJECTION_MAIL_COMPANY_NAME")==null? tenantMetaConfig.getTenantName():config.get("REJECTION_MAIL_COMPANY_NAME"));
//                params.addProperty("REJECTION_MAIL_CONTACT_INFORMATION", config.get("REJECTION_MAIL_CONTACT_INFORMATION")==null?defaultContactInformation:config.get("REJECTION_MAIL_CONTACT_INFORMATION"));
//
//
//                requestBody.add("params", params);
//                JsonArray toUsers = new JsonArray();
//                toUsers.add(it.getLogin());
//                requestBody.add("toUsers", toUsers);
//                //String messageId = notificationClient.sendNotificationWithSessionId(requestBody.toString(),PlatformSecurityUtil.getToken());
//                //log.debug(new Date() + "==Rejection MessageId for User  : " + it.getLogin() + "==" + messageId);
//            });
    }

    @Transactional
    public void notifyAdminOnRegistration(String tenantName, Long userId) {
//        TenantMetaConfig tenantMetaConfig = metaConfigService.getTenantMetaConfig(null, tenantName);
//        Set<User> users = tenantMetaConfig.getAdminUsers();
//        Map<String,String> config = tenantMetaConfig.getConfig()==null?new HashMap<>():tenantMetaConfig.getConfig();
//        UserVO it = tldClient.mapUserIdToUser(Collections.singleton(userId), PlatformSecurityUtil.getToken()).getBody().get(userId);
//        if (it != null) {
//        users.forEach( user ->{
//
//            JsonObject requestBody = new JsonObject();
//            requestBody.addProperty("fromUser", fromUser);
//            requestBody.addProperty("notificationCode", defaultNotificationCodeForUserRegistration);
//            JsonObject params = new JsonObject();
//            params.addProperty("MSG_SUBJECT", config.get("REGISTRATION_MAIL_SUBJECT")==null ? defaultNotificationCodeForUserRegistration : config.get("REGISTRATION_MAIL_SUBJECT") );
//
//            params.addProperty("ADMIN_NAME", user.getUserName() );
//            params.addProperty("ASSESSMENT_APPLICATION", config.get("ASSESSMENT_APPLICATION")==null ? defaultApplicationName : config.get("ASSESSMENT_APPLICATION")  );
//            params.addProperty("FULL_NAME", (it.getFirstName()+(it.getLastName()==null?"":" "+it.getLastName())) );
//            params.addProperty("EMAIL_ADDRESS", it.getLogin());
//            params.addProperty("REGISTRATION_MAIL_APPROVAL_PAGE_LINK",config.get("REGISTRATION_MAIL_APPROVAL_PAGE_LINK")==null ? defaultAppLink : config.get("REGISTRATION_MAIL_APPROVAL_PAGE_LINK") );
//            params.addProperty("REGISTRATION_MAIL_SUPPORT_TEAM_EMAIL",config.get("REGISTRATION_MAIL_SUPPORT_TEAM_EMAIL")==null?defaultSupportTeamEmail:config.get("REGISTRATION_MAIL_SUPPORT_TEAM_EMAIL"));
//            params.addProperty("REGISTRATION_MAIL_COMPANY_NAME", config.get("REGISTRATION_MAIL_COMPANY_NAME")==null? tenantMetaConfig.getTenantName():config.get("REGISTRATION_MAIL_COMPANY_NAME"));
//            params.addProperty("REGISTRATION_MAIL_CONTACT_INFORMATION", config.get("REGISTRATION_MAIL_CONTACT_INFORMATION")==null?defaultContactInformation:config.get("REGISTRATION_MAIL_CONTACT_INFORMATION"));
//
//
//            requestBody.add("params", params);
//            JsonArray toUsers = new JsonArray();
//            toUsers.add(user.getUserLogin());
//            requestBody.add("toUsers", toUsers);
//            //String messageId = notificationClient.sendNotificationWithSessionId(requestBody.toString(), PlatformSecurityUtil.getToken());
//            //log.debug(new Date() + "==UserRegistration MessageId for User  : " + it.getLogin() + "==" + messageId);
//        });
//        }
    }


    @Async
    public void sendNotificationOnTenantCreation(LeadDTO leadDTO, TenantDTO createdTenantDTO, String marketUUID, Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        MarketConfigDTO marketConfigDTO = vmsSignupUtil.fetchMarketConfig(marketUUID);
        TenantDTO ownerTenantDTO = tenantFacade.getByTenantIdFromCacheOrDB(marketConfigDTO.getGovernorTenant());
        oboSecurityUtil.initTenantSupportUserByTenantMetaConfig(marketConfigDTO.getGovernorTenantLogin(),tenantMetaConfigFacade);
        notificationToTenantAdminOnTenantCreation(leadDTO,createdTenantDTO,ownerTenantDTO,marketConfigDTO);
        notificationToMarketOwnerOnTenantCreationInMarket(createdTenantDTO,ownerTenantDTO,marketConfigDTO);
    }

    public void notificationToMarketOwnerOnTenantCreationInMarket(TenantDTO createdTenantDTO,TenantDTO ownerTenantDTO, MarketConfigDTO marketConfigDTO) {
        String createdTenantName = createdTenantDTO.getTenantName();
        String ownerTenantName = ownerTenantDTO.getTenantName();
        String marketTenantLogoUrl = ownerTenantDTO.getIconpic() != null ? ownerTenantDTO.getIconpic() : "";
        String marketAppBaseUrl = triggerNotificationUtil.deduceAppBaseUrl(marketConfigDTO.getMarketAppBaseUrl());

        Map<String,String> marketOwnerMailMap = triggerNotificationUtil.getTenantAdminsAndUsersToNotify(ownerTenantDTO.getTenantLogin());
        for(Map.Entry<String,String> mapEntry : marketOwnerMailMap.entrySet()) {
            Map<String,String> params = new HashMap<>();
            params.put(NotificationConstant.MSG_SUBJECT,String.format("New Signup Alert! %s Has Signed Up %s", createdTenantName,ownerTenantName));
            params.put(NotificationConstant.TENANT_NAME,createdTenantName);
            params.put(NotificationConstant.MARKET_NAME,ownerTenantName);
            params.put(NotificationConstant.MARKET_TENANT_LOGO_URL,marketTenantLogoUrl);
            params.put(NotificationConstant.MARKET_OWNER_NAME,mapEntry.getKey());
            params.put(NotificationConstant.MARKET_APP_BASE_URL, marketAppBaseUrl);
            triggerNotificationUtil.addTenantSupportEmailAndMobileNumber(params, ownerTenantDTO.getId());

            NotificationDTO notificationDTO = NotificationDTO.builder()
                    .notificationCode(NotificationConstant.NOTIFICATION_CODE_TENANT_CREATION_MAIL_TO_MARKET_OWNER)
                    .toUsers(Collections.singletonList(mapEntry.getValue()))
                    .params(params)
                    .build();

            String messageId = notificationClient.sendNotificationWithSessionId(notificationDTO, null, PlatformSecurityUtil.getToken());
            log.info("MessageId =>>>> {}", messageId);
        }
    }

    public void notificationToTenantAdminOnTenantCreation(LeadDTO leadDTO,TenantDTO createdTenantDTO,TenantDTO ownerTenantDTO, MarketConfigDTO marketConfigDTO) {
        String createdTenantName = createdTenantDTO.getTenantName();
        String ownerTenantName = ownerTenantDTO.getTenantName();
        String marketTenantLogoUrl = ownerTenantDTO.getIconpic() != null ? ownerTenantDTO.getIconpic() : "";
        String adminName = TriggerNotificationUtil.computeFullName(leadDTO.getFirstName(),leadDTO.getLastName());
        String marketAppBaseUrl = triggerNotificationUtil.deduceAppBaseUrl(marketConfigDTO.getMarketAppBaseUrl());

        Map<String,String> params = new HashMap<>();
        params.put(NotificationConstant.MSG_SUBJECT,String.format("Welcome to %s, %s! Your Journey Starts Here",ownerTenantName, adminName));
        params.put(NotificationConstant.ADMIN_NAME, adminName);
        params.put(NotificationConstant.MARKET_NAME,ownerTenantName);
        params.put(NotificationConstant.TENANT_NAME,createdTenantName);
        params.put(NotificationConstant.MARKET_TENANT_LOGO_URL,marketTenantLogoUrl);
        params.put(NotificationConstant.MARKET_APP_BASE_URL, marketAppBaseUrl);
        triggerNotificationUtil.addTenantSupportEmailAndMobileNumber(params, ownerTenantDTO.getId());

        NotificationDTO notificationDTO = NotificationDTO.builder()
                .notificationCode(NotificationConstant.NOTIFICATION_CODE_TENANT_CREATION_MAIL_TO_TENANT_ADMIN)
                .toUsers(Collections.singletonList(leadDTO.getEmail()))
                .params(params)
                .build();

        String messageId = notificationClient.sendNotificationWithSessionId(notificationDTO, null, PlatformSecurityUtil.getToken());
        log.info("MessageId =>>>> {}", messageId);
    }

    @Async
    public void sendNotificationOnUserJoiningATenant(UserDTO userDTO, TenantDTO tenantDTO,String appContext,
                                                     String marketContext,Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String userName = TriggerNotificationUtil.computeFullName(userDTO.getFirstName(),userDTO.getLastName());
        Map<String,String> userContacts = TriggerNotificationUtil.fetchUserContactsFromUserDTO(userDTO);
        String tenantName = tenantDTO.getTenantName();
        String tenantLogoUrl = tenantDTO.getIconpic() != null ? tenantDTO.getIconpic() : "";
        Map<String, String> tenantAdminMailMap = triggerNotificationUtil.getTenantAdminsAndUsersToNotify(tenantDTO.getTenantLogin());

        MarketConfigDTO marketConfigDTO = vmsSignupUtil.fetchMarketConfig(marketContext);
        String marketAppBaseUrl = triggerNotificationUtil.deduceAppBaseUrl(marketConfigDTO.getMarketAppBaseUrl());

        Optional<UserVerificationDTO> optionalUserVerificationDTO = userVerificationFacade.getOptionalByUserIdAndAppContext(
                userDTO.getId().longValue(), appContext, marketContext);
        String status = optionalUserVerificationDTO.isPresent() ? optionalUserVerificationDTO.get().getVerificationStatus()
                : VerificationConstant.VERIFICATION_STATUS_VERIFIED;

        String notificationCodeForUser = null;
        String notificationCodeForTenant = null;
        String subjectForUser = null;
        String subjectForTenant = null;
        switch (status) {
            case VerificationConstant.VERIFICATION_STATUS_VERIFIED :
                notificationCodeForUser = NotificationConstant.NOTIFICATION_CODE_USER_SIGN_UP_VERIFIED_MAIL_TO_USER;
                notificationCodeForTenant = NotificationConstant.NOTIFICATION_CODE_USER_SIGN_UP_VERIFIED_MAIL_TO_TENANT;
                subjectForUser = String.format("Welcome to %s, %s!",tenantName, userName);
                subjectForTenant = String.format("New User Signup: %s Approved", userName);
                break;
            case VerificationConstant.VERIFICATION_STATUS_NOT_REVIEWED :
                notificationCodeForUser = NotificationConstant.NOTIFICATION_CODE_USER_SIGN_UP_NOT_VERIFIED_MAIL_TO_USER;
                notificationCodeForTenant = NotificationConstant.NOTIFICATION_CODE_USER_SIGN_UP_NOT_VERIFIED_MAIL_TO_TENANT;
                subjectForUser = String.format("Welcome to %s, %s!",tenantName, userName);
                subjectForTenant = String.format("New User Signup: %s Pending Approval", userName);
                break;
            default:
                log.info("Status did not match any standard notification code");
        }

        //Notification To User
        if(userContacts.containsKey("CONTACT_TYPE.MAIL")) {
            notificationToUserOnJoiningATenant(userContacts.get("CONTACT_TYPE.MAIL"),notificationCodeForUser,subjectForUser,
                        tenantName,tenantLogoUrl,userName, tenantDTO.getId(), marketAppBaseUrl);
        }

        //Notification To Tenant
        notificationToTenantOnUserJoiningATenant(tenantAdminMailMap,notificationCodeForTenant,subjectForTenant, tenantName,
                         tenantLogoUrl,userName, tenantDTO.getId(), marketAppBaseUrl);
    }

    public void notificationToUserOnJoiningATenant(String userMailAddress,String notificationCode,String subject,
                                           String tenantName,String tenantLogoUrl,String userName, Long tenantId, String marketAppBaseUrl) {

            Map<String, String> params = new HashMap<>();
            params.put(NotificationConstant.MSG_SUBJECT, subject);
            params.put(NotificationConstant.USER_NAME, userName);
            params.put(NotificationConstant.TENANT_NAME, tenantName);
            params.put(NotificationConstant.TENANT_LOGO_URL, tenantLogoUrl);
            params.put(NotificationConstant.MARKET_APP_BASE_URL, marketAppBaseUrl);
            triggerNotificationUtil.addTenantSupportEmailAndMobileNumber(params, tenantId);

            NotificationDTO notificationDTO = NotificationDTO.builder()
                    .notificationCode(notificationCode)
                    .toUsers(Collections.singletonList(userMailAddress))
                    .params(params)
                    .build();

            String messageId = notificationClient.sendNotificationWithSessionId(notificationDTO, null, PlatformSecurityUtil.getToken());
            log.info("MessageId =>>>> {}", messageId);
    }

    public void notificationToTenantOnUserJoiningATenant(Map<String,String> tenantAdminMailMap,String notificationCode,String subject,
                                                         String tenantName,String tenantLogoUrl,String signUpUserName, Long tenantId,
                                                         String marketAppBaseUrl) {
        for (Map.Entry<String, String> mapEntry : tenantAdminMailMap.entrySet()) {
            Map<String, String> params = new HashMap<>();
            params.put(NotificationConstant.MSG_SUBJECT, subject);
            params.put(NotificationConstant.USER_NAME, signUpUserName);
            params.put(NotificationConstant.ADMIN_NAME, mapEntry.getKey());
            params.put(NotificationConstant.TENANT_NAME, tenantName);
            params.put(NotificationConstant.TENANT_LOGO_URL, tenantLogoUrl);
            params.put(NotificationConstant.MARKET_APP_BASE_URL, marketAppBaseUrl);
            triggerNotificationUtil.addTenantSupportEmailAndMobileNumber(params, tenantId);

            NotificationDTO notificationDTO = NotificationDTO.builder()
                    .notificationCode(notificationCode)
                    .toUsers(Collections.singletonList(mapEntry.getValue()))
                    .params(params)
                    .build();

            String messageId = notificationClient.sendNotificationWithSessionId(notificationDTO, null, PlatformSecurityUtil.getToken());
            log.info("MessageId =>>>> {}", messageId);
        }
    }

    @Async
    public void sendNotificationToUserOnVerificationStatusUpdate(UserVerificationDTO userVerificationDTO,Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Long userId = userVerificationDTO.getUserId();
        UserDetailsDTO userDetailsDTO = triggerNotificationUtil.getUserDetailsByUserId(userId);
        String userName = userDetailsDTO.getFullName();

        TenantDTO tenantDTO = tenantFacade.getByTenantIdFromCacheOrDB(PlatformSecurityUtil.getCurrentTenantId());
        String tenantName = tenantDTO.getTenantName();
        String tenantLogoUrl = tenantDTO.getIconpic() != null ? tenantDTO.getIconpic() : "";
        MarketConfigDTO marketConfigDTO = vmsSignupUtil.fetchMarketConfig(userVerificationDTO.getMarketContext());
        String marketAppBaseUrl = triggerNotificationUtil.deduceAppBaseUrl(marketConfigDTO.getMarketAppBaseUrl());

        String status = userVerificationDTO.getVerificationStatus();
        String notificationCode = null;
        String subject = null;
        switch (status) {
            case VerificationConstant.VERIFICATION_STATUS_VERIFIED :
                notificationCode = NotificationConstant.NOTIFICATION_CODE_USER_VERIFICATION_STATUS_CHANGE_TO_VERIFIED_MAIL_TO_USER;
                subject = String.format("Welcome to %s, %s!", tenantName, userName);
                break;
            case VerificationConstant.VERIFICATION_STATUS_REJECTED :
                notificationCode = NotificationConstant.NOTIFICATION_CODE_USER_VERIFICATION_STATUS_CHANGE_TO_REJECTED_MAIL_TO_USER;
                subject = String.format("Regarding Your Request to Join %s", tenantName);
                break;
            case VerificationConstant.VERIFICATION_STATUS_ON_HOLD :
                notificationCode = NotificationConstant.NOTIFICATION_CODE_USER_VERIFICATION_STATUS_CHANGE_TO_ON_HOLD_MAIL_TO_USER;
                subject = String.format("Regarding Your Request to Join %s", tenantName);
                break;
            default:
                log.info("Status did not match any standard notification code");
        }

        // Notification To User
        Map<String, String> params = new HashMap<>();
        params.put(NotificationConstant.MSG_SUBJECT, subject);
        params.put(NotificationConstant.USER_NAME, userName);
        params.put(NotificationConstant.TENANT_NAME, tenantName);
        params.put(NotificationConstant.TENANT_LOGO_URL, tenantLogoUrl);
        params.put(NotificationConstant.MARKET_APP_BASE_URL, marketAppBaseUrl);
        triggerNotificationUtil.addTenantSupportEmailAndMobileNumber(params, tenantDTO.getId());

        NotificationDTO notificationDTO = NotificationDTO.builder()
                .notificationCode(notificationCode)
                .toUsers(Collections.singletonList(userDetailsDTO.getEmailAddress()))
                .params(params)
                .build();

        String messageId = notificationClient.sendNotificationWithSessionId(notificationDTO, null, PlatformSecurityUtil.getToken());
        log.info("MessageId =>>>> {}", messageId);
    }

    public void sendNotificationOnForgetPassword(String email, String otp, String validity, String subject, @NotNull String code) {

        if(email!=null ){
            Map<String,String> params = new HashMap<>();
            params.put(NotificationConstant.MSG_SUBJECT, subject);
            params.put(NotificationConstant.PARAMS_OTP, otp);
            params.put("SECONDS", validity);

            NotificationDTO notificationDTO = NotificationDTO.builder()
                    .fromUser(fromUser)
                    .notificationCode(code)
                    .toUsers(Collections.singletonList(email))
                    .params(params)
                    .build();
            notificationClient.sendNotification(notificationDTO, apiKey);
        }

    }

    public void sendTraderCreationMail(String appContext, String contactPersonName,String tenantName, String email,
                                       String userLogin, String tenantLogin, String password, String url) {
            Map<String,String> params = new HashMap<>();
            params.put("MSG_SUBJECT",welcomeTraderSubject);
            params.put("NAME",contactPersonName);
            params.put("TENANT_NAME",tenantName);
            params.put("USER_LOGIN",userLogin);
            params.put("TENANT_LOGIN",tenantLogin);
            params.put("PASSWORD", password);
            params.put("LOGIN_URL", url);

            NotificationDTO notificationDTO = NotificationDTO.builder()
                    .fromUser(fromUser)
                    .notificationCode(notificationCodeForTraderWelcomeMail)
                    .params(params)
                    .toUsers(Collections.singletonList(email))
                    .build();

            String messageId = notificationClient.sendNotification(notificationDTO, apiKey);
            log.debug(new Date() + "==Welcome MessageId for tenant  : " + tenantName + "==" + messageId);
    }

}
