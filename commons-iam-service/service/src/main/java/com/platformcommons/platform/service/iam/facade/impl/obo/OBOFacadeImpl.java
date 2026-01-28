package com.platformcommons.platform.service.iam.facade.impl.obo;

import com.google.gson.Gson;
import com.mindtree.bridge.platform.dto.UserRoleMapDTO;
import com.platformcommons.platform.cache.manager.AuthCacheManager;
import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.exception.generic.*;
import com.platformcommons.platform.security.filter.session.CurrentExecutiveDTO;
import com.platformcommons.platform.security.filter.session.TLDPlatformTokenProvider;
import com.platformcommons.platform.security.filter.session.TLDUserClient;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.IAmUserDetailsMergeService;
import com.platformcommons.platform.service.iam.application.LegacyUserMigrationService;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.application.utility.*;
import com.platformcommons.platform.service.iam.domain.vo.UserSelfRegistrationExcelVO;
import com.platformcommons.platform.service.iam.facade.client.utility.CommonsReportUtil;
import com.platformcommons.platform.service.iam.application.utility.TriggerNotification;
import com.platformcommons.platform.service.iam.application.utility.OBOSecurityUtil;
import com.platformcommons.platform.service.iam.application.utility.TLDUtil;
import com.platformcommons.platform.service.iam.domain.vo.UserVO;
import com.platformcommons.platform.service.iam.dto.*;
import com.platformcommons.platform.service.iam.dto.brbase.*;
import com.platformcommons.platform.service.iam.dto.brbase.UserDTO;
import com.platformcommons.platform.service.iam.facade.TenantMetaConfigFacade;
import com.platformcommons.platform.service.iam.facade.UserMetaDataFacade;
import com.platformcommons.platform.service.iam.facade.UserVerificationFacade;
import com.platformcommons.platform.service.iam.facade.client.*;
import com.platformcommons.platform.service.iam.facade.mapper.UserSyncCustomDTOMapper;
import com.platformcommons.platform.service.iam.facade.obo.OBOFacade;
import com.platformcommons.platform.service.iam.facade.assembler.obo.TLDTenantDTOAssembler;
import com.platformcommons.platform.service.iam.messaging.notifier.EmailNotifier;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.assesmbler.BulkUploadRequestDTOAssembler;
import com.platformcommons.platform.service.sdk.bulkupload.service.BulkUploadService;
import com.platformcommons.platform.service.worknode.dto.*;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Slf4j
public class OBOFacadeImpl implements OBOFacade {

    @Autowired
    private TLDClient tldClient;

    @Autowired
    private TLDUserClient tldUserClient;

    @Autowired
    private TLDPlatformTokenProvider tldPlatformTokenProvider;

    @Autowired
    private IAmUserDetailsMergeService userDetailsMergeService;

    @Autowired(required = false)
    private AuthCacheManager<PlatformToken> authCacheManager;

    @Value("${commons.platform.cache.expiration.session:3600}")
    protected long sessionCacheTTL;


    @Value("${commons.platform.legacy-user-migration.enabled:false}")
    private Boolean legacyUserMigrationEnabled;

    @Value("${commons.platform.legacy-user-context-merge.enabled:false}")
    private Boolean legacyUserContextMergeEnabled;




    private static final String SEPARATOR = "@@";


    @Autowired
    private TLDRestTemplate tldRestTemplate;

    @Autowired
    private TLDTenantDTOAssembler tldTenantDTOAssembler;

    @Autowired
    private UserVerificationFacade userVerificationFacade;

    @Autowired
    @Lazy
    private TriggerNotification notificationUtil;

    @Autowired
    private OBOSecurityUtil oboSecurityUtil;

    @Autowired
    private BulkUploadService bulkUploadService;

    @Autowired
    private BulkUploadRequestDTOAssembler bulkUploadRequestDTOAssembler;

    @Autowired
    private LinkedInOAuthClient linkedInOAuthClient;

    @Autowired
    private Environment env;

    @Autowired
    private TLDUtil tldUtil;

    @Autowired
    private CommonsReportUtil commonsReportUtil;

    @Autowired
    private TFIClientUtil tfiClientUtil;

    @Autowired
    private WorknodeClient worknodeClient;

    @Autowired
    private WorknodeRestTemplate worknodeRestTemplate;

    @Autowired
    private ChangemakerOpportunityClient changemakerOpportunityClient;

    @Autowired
    private EmailNotifier emailNotifier;

    @Autowired
    private UserMetaDataFacade userMetaDataFacade;

    @Autowired
    private TenantMetaConfigFacade tenantMetaConfigFacade;

    @Autowired
    private CustomRestTemplate customRestTemplate;

    @Autowired
    private UserSyncCustomDTOMapper userSyncCustomDTOMapper;

    @Autowired
    @Lazy
    private OBOFacade oboFacade;

    @Autowired
    private CompanyMasterDataUtil companyMasterDataUtil;

    @Autowired
    @Lazy
    private LegacyUserMigrationService legacyUserMigrationService;


    @Override
    public String getSessionId(LoginRequestDTO loginRequestDTO, String appId, String appVersion, String appKey, String deviceId) {
        String userName = loginRequestDTO.getUserLogin()+SEPARATOR+loginRequestDTO.getTenantLogin();
        String sessionId= Objects.requireNonNull(tldClient.login(userName, loginRequestDTO.getPassword(), appId, appKey,
                appVersion, deviceId).getHeaders().get(PlatformSecurityConstant.SESSIONID)).get(0);
        getAndCachePlatformToken(sessionId);
        return sessionId;
    }

    @Override
    public UserDTO getLoggedInUserDetails(String sessionId) {
        return tldClient.getLoggedInUserDetails(sessionId).getBody();
    }

    @Override
    public UserSelfRegistrationDTO register(UserSelfRegistrationDTO userSelfRegistrationDTO, String password, String tenantName,
                                            String appId, Boolean sendAndAuthenticateDifferentOTPForEmailAndSms,String appCode, String marketContext) {
        userSelfRegistrationDTO = tldClient.selfRegistration(password,appId,tenantName,userSelfRegistrationDTO,
                sendAndAuthenticateDifferentOTPForEmailAndSms).getBody();
        if(appCode != null && userSelfRegistrationDTO != null) {
            userVerificationFacade.addEntryForUserVerification(tenantName,userSelfRegistrationDTO.getId().longValue(), appCode, marketContext);
        }
        return userSelfRegistrationDTO;
    }

    @Override
    @Transactional
    public String activate(String otp, String modKey, String tenantName, Integer userId, Boolean isOTPAndModKeyOfEmail, Boolean skipUserActiveValidation) {
        String msg = tldClient.activateUser(otp, modKey, tenantName, userId, isOTPAndModKeyOfEmail, skipUserActiveValidation).getBody();
        return msg;
    }

    @Override
    public String pass(String userLogin, String tenant, String appContext, String appId) {
        String userLoginWithTenant= userLogin+SEPARATOR+tenant;
        String result= tldClient.pass(userLoginWithTenant).getBody();
        String key;
        try {
            assert result != null;
             key = URLDecoder.decode(Objects.requireNonNull(UriComponentsBuilder.fromUriString(result).build()
                     .getQueryParams().getFirst("key")),StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        tldClient.getOTP(userLoginWithTenant,appContext,appId);
        return  key;
    }

    @Override
    public String resetPassword(String userLogin,String tenant, String newPassword, String key, String otp) {
        String userLoginWithTenant= userLogin+SEPARATOR+tenant;
        return tldClient.pass(userLoginWithTenant,newPassword,key,otp).getBody();
    }

    @Override
    public String checkUser(String tenantName, String loginName) {
        return tldClient.checkUser(tenantName,loginName).getBody();
    }

    @Override
    public String resendOTP(String userLogin, String tenant, String appContext, String appId) {
        String userLoginWithTenant= userLogin+SEPARATOR+tenant;
        return  tldClient.getOTP(userLoginWithTenant, appContext, appId).getBody();
    }

    @Override
    public UserSelfRegistrationDTO activateInactive(String userName, String tenantName, String appId, String appContext, Boolean sendOTPForEmail){
        return tldClient.activateInactive(userName, tenantName, appId, appContext, sendOTPForEmail).getBody();
    }

    @Override
    public String getCrossTenantSessionId(String tenant, String appId, String appKey, String appVersion, String deviceId, String sessionId) {

        if(tenant.equals(PlatformSecurityUtil.getCurrentTenantLogin())){
            return sessionId;
        }
        else {
            String crossTenantSessionId = Objects.requireNonNull(
                            tldClient.crossTenantLogin(tenant, appId, appKey, appVersion,deviceId, sessionId)
                                     .getHeaders()
                                     .get(PlatformSecurityConstant.SESSIONID)
                        ).get(0);
            getAndCachePlatformToken(crossTenantSessionId);
            return crossTenantSessionId;
        }
    }

    @Override
    public String getCrossTenantSessionId(String tenantLogin, String sessionId) {
          return Objects.requireNonNull(
                    tldClient.crossTenantLogin(tenantLogin, null,null,null,null, sessionId)
                            .getHeaders().getFirst(PlatformSecurityConstant.SESSIONID));
    }

    @Override
    public UserDTO updateUserProfessionalHistoryList(Long userId, PersonProfessionalHistoryDTO professionalHistoryDTO) {
        UserDTO fetchedUserDTO = tldClient.getUserDetails(PlatformSecurityUtil.getToken(), Math.toIntExact(userId), null).getBody();
        if(fetchedUserDTO!=null && fetchedUserDTO.getPerson()!=null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(fetchedUserDTO.getId());
            PersonDTO fetchedPerson= fetchedUserDTO.getPerson();
            PersonDTO personDTO = new PersonDTO();
            personDTO.setId(fetchedPerson.getId());
            personDTO.setIsActive(fetchedPerson.getIsActive());

            if(fetchedPerson.getPersonProfile()!=null){
                PersonProfileDTO fetchedPersonProfile = fetchedPerson.getPersonProfile();
                PersonProfileDTO personProfileDTO= new PersonProfileDTO();
                personProfileDTO.setId(fetchedPersonProfile.getId());
                personProfileDTO.setFirstName(fetchedPersonProfile.getFirstName());
                personDTO.setPersonProfile(personProfileDTO);
            }
            List<PersonProfessionalHistoryDTO> personProfessionalHistoryDTOList = new ArrayList<>();
            personProfessionalHistoryDTOList.add(professionalHistoryDTO);
            if(fetchedPerson.getPersonProfessionalHistoryList()!=null && !fetchedPerson.getPersonProfessionalHistoryList().isEmpty())
                personProfessionalHistoryDTOList.addAll(fetchedPerson.getPersonProfessionalHistoryList());
            personDTO.setPersonProfessionalHistoryList(personProfessionalHistoryDTOList);
            userDTO.setPerson(personDTO);
            return tldRestTemplate.patchUser(userDTO).getBody();
        }
        else {
            throw  new InvalidInputException("Invalid Inputs");
        }

    }

    @Transactional
    @Override
    public Map<String, String> crossLogin(LoginRequestDTO loginRequestDTO, String crossTenant, String appId, String appVersion, String appKey, String deviceId) {
        try {
            Map<String, String> sessionMap = new LinkedHashMap<>();
            loginToTenantWithCrossTenantFallback(loginRequestDTO, crossTenant, appId, appVersion, appKey, deviceId, sessionMap);
            if (crossTenant != null && !sessionMap.containsKey(CROSS_SESSION)) {
                String tenantLoginSessionId = sessionMap.getOrDefault(SESSION, null);
                if (crossTenant.equals(loginRequestDTO.getTenantLogin())) {
                    sessionMap.put(CROSS_SESSION, tenantLoginSessionId);
                } else {
                    String crossTenantSessionId = getCrossTenantSessionId(
                            crossTenant, appId, appKey, appVersion, deviceId, tenantLoginSessionId);
                    sessionMap.put(CROSS_SESSION, crossTenantSessionId);
                }
            }
            return sessionMap;
        } catch (FeignException exception) {
            log.error(exception.getMessage());
            throw new UnAuthenticatedAccessException("User is not allowed to login, Please check for valid login credentials");
        }
    }

    public void loginToTenantWithCrossTenantFallback(LoginRequestDTO loginRequestDTO, String crossTenant, String appId, String appVersion,
                                                 String appKey, String deviceId, Map<String, String> sessionMap) {
        try {
            String tenantLoginSessionId = getSessionId(loginRequestDTO, appId, appVersion, appKey, deviceId);
            sessionMap.put(SESSION, tenantLoginSessionId);
        } catch (FeignException exception) {
            if (crossTenant == null) {
                throw exception;
            }
            loginRequestDTO.setTenantLogin(crossTenant);
            String crossTenantSessionId = getSessionId(loginRequestDTO, appId, appVersion, appKey, deviceId);
            sessionMap.put(CROSS_SESSION, crossTenantSessionId);
        }
    }

    @Override
    public Map<String, String> crossLoginV2(LoginRequestDTO loginRequestDTO,String appId, String appVersion, String appKey, String deviceId) {
        TenantLoginVO userTenantLogin = commonsReportUtil.validateTenantAndUserLogin(loginRequestDTO.getUserLogin(), loginRequestDTO.getTenantLogin(), appKey);
        String crossTenant = null;
        if (!userTenantLogin.getTenantLogin().equals(loginRequestDTO.getTenantLogin())) {
            crossTenant = loginRequestDTO.getTenantLogin();
            loginRequestDTO.setTenantLogin(userTenantLogin.getTenantLogin());
        } else {
            crossTenant = loginRequestDTO.getTenantLogin();
        }
        try {
            String loginTenantSessionId = this.getSessionId(loginRequestDTO, appId, appVersion, appKey, deviceId);
            getAndCachePlatformToken(loginTenantSessionId);
            Map<String, String> sessionMap = new LinkedHashMap<>();
            sessionMap.put(SESSION, loginTenantSessionId);
            if (crossTenant != null) {
                String crossTenantSessionId = this.getCrossTenantSessionId(crossTenant, appId, appKey, appVersion, deviceId, loginTenantSessionId);
                sessionMap.put(CROSS_SESSION, crossTenantSessionId);
            }

            return sessionMap;
        }catch (FeignException feignException){
            log.error(feignException.getMessage());
            throw new UnAuthenticatedAccessException("User is not allowed to login, Please check for valid login credentials");
        }
    }

    @Override
    public CurrentExecutiveDTO getLoggedInUserSessionDetails() {
        return tldUserClient.getUserInfo(PlatformSecurityUtil.getToken());
    }

    @Override
    public String getSessionForSocialLoginTLD(String token,String platform,String providerTenantId,String appId, String appVersion,
                                              String appKey, String deviceId,String tenantLogin,Boolean createUser) {
        HttpHeaders headers = Objects.requireNonNull(tldClient.socialLogin(token, appId, appKey,
                appVersion, deviceId,platform,createUser,providerTenantId,tenantLogin).getHeaders());
        String sessionId = headers.containsKey(PlatformSecurityConstant.SESSIONID) ? headers.get(PlatformSecurityConstant.SESSIONID).get(0): null;
        if(sessionId!=null) {
            getAndCachePlatformToken(sessionId);
        }
        return sessionId;
    }


    @Override
    public void activateUser(String userLogin, String tenantLogin, String password, Integer sourceUserId,
                             Integer targetTenantId, String OTP, String messageId, BigDecimal userSystemUUID) {

        tenantLogin = tenantLogin!=null ? tenantLogin : "world";
        Boolean isValid;
        if(messageId!=null && OTP!=null){
            isValid =notificationUtil.validateOTP(messageId,OTP);
        }
        else if(userSystemUUID!=null){
            isValid = Boolean.TRUE;
        }
        else {
            isValid= Boolean.FALSE;
        }
        if(isValid) {
            oboSecurityUtil.initPrivilegeUser();
            tldClient.activeUser(userLogin, tenantLogin, password, sourceUserId, targetTenantId,userSystemUUID,
                    PlatformSecurityUtil.getToken());
        }
        else {
            throw  new InvalidInputException("Invalid OTP");
        }
        oboSecurityUtil.clearContext();
    }

    @Override
    public String sendOTP(String email, String name) {
       return notificationUtil.sendOTPNotification(email,name);
    }

    @Override
    public Map<String, String> activateUserCrossTenantQuickLogin(CrossLoginRequestDTO dto, Integer x_APPID, String x_APPKEY, String x_APPVERSION, String x_DEVICEID) {
        String sessionId = Objects.requireNonNull(tldClient.activateUserCrossTenantQuickLogin(
                                                                        dto.getUserLogin(),
                                                                        dto.getOtp(),
                                                                        dto.getModKey(),
                                                                        dto.getTenantLogin(),
                                                                        x_APPID,
                                                                        x_APPKEY,
                                                                        x_APPVERSION,
                                                                        x_DEVICEID
                                                           )
                                                           .getHeaders()
                                                           .get(PlatformSecurityConstant.SESSIONID)).get(0);
        getAndCachePlatformToken(sessionId);
//        TO DO: to know the user is new share the details with the client
//        userVerificationFacade.createUserVerificationForRegisteredUser(PlatformSecurityUtil.getCurrentUserId());

        Map<String, String> sessionMap = new LinkedHashMap<>();
        sessionMap.put(SESSION, sessionId);
        if(dto.getCrossTenantLogin()!=null) {
            String crossTenant = getCrossTenantSessionId(dto.getCrossTenantLogin(),null,null,null,null,sessionId);
            if(crossTenant!=null) sessionMap.put(CROSS_SESSION, crossTenant);
        }

        return sessionMap;
    }


    @Override
    public void selfAssignRolesForLoggedInUser(Set<String> roleCodes,Boolean isFlowComplete) {
        Long currentUserId = PlatformSecurityUtil.getCurrentUserId();
        if(roleCodes != null) {
            String autoApprovalRole = tenantMetaConfigFacade.getMetaPropertyValueByMetaKeyAndHierarchy(
                    IAMConstant.CONFIG_KEY_AUTO_APPROVAL_OF_SELF_ASSIGNED_ROLE, PlatformSecurityUtil.getCurrentTenantLogin(),null);
            if (Objects.equals(autoApprovalRole, "1")) {
                //User Self Assign Role With isActive True
                assignRolesToUser(PlatformSecurityUtil.getCurrentUserId(),roleCodes);
            } else {
                //User Self Assign Role With isActive False
                userSelfAssignRoleWithIsActiveFalse(roleCodes);
            }
            if (Objects.equals(isFlowComplete, Boolean.TRUE)) {
                UserMetaDataDTO userMetaDataDTO = UserMetaDataDTO.builder()
                        .id(0L)
                        .metaKey(IAMConstant.META_KEY_SIGN_UP_FLOW_COMPLETED)
                        .metaValue("true")
                        .build();
                userMetaDataFacade.postOrUpdateInBulkForLoggedInUser(Collections.singleton(userMetaDataDTO));
            }
        }
     }

    @Override
    public Object userSelfAssignRole(String roleCode) {
        return tldClient.userSelfAssignRole(roleCode,PlatformSecurityUtil.getToken()).getBody();
    }

    @Override
    public UserRoleMapDTO assignRoleToUser(String roleCode, String userLogin) {
        return tldClient.assignRoleV3(roleCode,null,userLogin,Boolean.TRUE,PlatformSecurityUtil.getToken()).getBody();
    }

    @Override
    public Map<String,String> assignRoleToUsersInBulk(Set<UserRoleAssignDTO> userRoleAssignDTOSet) {
        Map<String,String> result = new HashMap<>();
        userRoleAssignDTOSet.forEach(it -> {
            try{
                UserRoleMapDTO userRoleMapDTO = assignRoleToUser(it.getRoleCode(),it.getUserLogin());
                if(userRoleMapDTO != null && userRoleMapDTO.getRole() != null) {
                    result.put(it.getUserLogin(),userRoleMapDTO.getRole().getCode());
                }
            } catch(Exception e) {
                log.debug(String.format("Role of - %s could not be assigned to user with userLogin - %s",it.getRoleCode(),it.getUserLogin()));
            }
        });
        return result;
    }

    @Override
    public void patchUpdateUserWithWorkNode(UserDTO userDTO, String role,String cohort,String placementCity) {
        Long userId = (long) userDTO.getId();
        String userLogin = userDTO.getLogin();

        try {
            String token = PlatformSecurityUtil.getToken();
            if(userDTO.getPerson() != null ) {
                PersonDTO personDTO = userDTO.getPerson();
                if(Objects.equals(personDTO.getId(),0)
                        || (personDTO.getPersonProfile() != null && Objects.equals(personDTO.getPersonProfile().getId(),0)) ) {
                    UserPersonProfileDTO userPersonProfileDTO = commonsReportUtil.getPersonAndPersonProfileIds(Long.valueOf(userDTO.getId()))
                            .stream()
                            .findFirst()
                            .orElseThrow(()-> new NotFoundException(String.format("User with id - %d not found",userDTO.getId())));
                    if(Objects.equals(personDTO.getId(),0)) {
                        personDTO.setId((Math.toIntExact(userPersonProfileDTO.getPersonId())));
                    }
                    if(personDTO.getPersonProfile() != null
                            && Objects.equals(personDTO.getPersonProfile().getId(),0)) {
                        personDTO.getPersonProfile().setId((Math.toIntExact(userPersonProfileDTO.getPersonProfileId())));
                    }
                }
                appendCompanyCodeInPersonProfessionalHistory(personDTO.getPersonProfessionalHistoryList());
            }

            try {
                tldRestTemplate.patchUser(userDTO).getBody();
                log.error("User with id {} patched. Request from {} for user patch and role sync and auto approval. Role: {}",
                        userId,PlatformSecurityUtil.getCurrentTenantLogin(),role);
            } catch(Exception e) {
                throw new InvalidInputException(String.format("User with id %d could not be patched. Exception - %s",userDTO.getId(),
                        ExceptionUtils.getMessage(e)));
            }

            if(role != null) {
                try {
                    assignRoleToUser(role,userDTO.getLogin());
                } catch(Exception e) {
                    throw new InvalidInputException(String.format("Role Code %s could not be added to User with id %d. " +
                            "Exception - %s",role,userDTO.getId(), ExceptionUtils.getMessage(e)));
                }
                UserRoleMapDTO userRoleMapDTO = tldClient.getUserRoleByRoleCode(role,userLogin,token).getBody();
                Objects.requireNonNull(userRoleMapDTO);
                Long roleId = (long)userRoleMapDTO.getId();
                if( (placementCity == null && cohort != null) || (placementCity != null && cohort == null) ) {
                    throw new InvalidInputException("Both placementCity and cohort param has to be passed together");
                }
                else if(placementCity != null && cohort != null) {
                    WorknodeDTO worknodeDTO = Objects.requireNonNull(worknodeClient.getWorknodeByNameAndType(placementCity,IAMConstant.WN_TYPE_CITY, token).getBody());
                    WorkforceDTO workforceDTO = Objects.requireNonNull(worknodeClient.getWorkforcesByUserIdAndRoleId(Long.valueOf(userDTO.getId()),
                                    Long.valueOf(userRoleMapDTO.getId()),worknodeDTO.getId(),token).getBody())
                            .stream()
                            .findFirst()
                            .orElse(null);
                    if(workforceDTO == null) {
                        workforceDTO = WorkforceDTO.builder()
                                .id(0L)
                                .accessType("WR")
                                .cohort(cohort)
                                .context("VMS")
                                .roleId(roleId)
                                .userId(userId)
                                .build();
                        worknodeClient.createWorkForce(worknodeDTO.getId(),workforceDTO,token);
                    }
                    else {
                        workforceDTO.setCohort(cohort);
                        worknodeRestTemplate.patchWorkforce(workforceDTO);
                    }
                }
            }
            changemakerOpportunityClient.welcomeMailForTfiUserAutoApproval(userId,PlatformSecurityUtil.getToken());
        } catch(Exception exception) {
            String userDTOJsonString = new Gson().toJson(userDTO);
            log.error("UserDTO-> "+ userDTOJsonString + "exceptionDetails -> " + ExceptionUtils.getMessage(exception));
            emailNotifier.sendNotificationForTFIUserPatchFailure(userDTOJsonString,exception,role,cohort,placementCity);
            throw exception;
        }
    }

    public void appendCompanyCodeInPersonProfessionalHistory(List<PersonProfessionalHistoryDTO> personProfessionalHistoryDTOList) {
        if(personProfessionalHistoryDTOList != null && !personProfessionalHistoryDTOList.isEmpty()) {
            personProfessionalHistoryDTOList.forEach(personProfessionalHistoryDTO -> {
                if(personProfessionalHistoryDTO.getWorkPlace() != null) {
                    List<ExtraAttributeDTO> extraAttributeDTOList = personProfessionalHistoryDTO.getExtraAttributeList() == null
                            ? new ArrayList<>() : personProfessionalHistoryDTO.getExtraAttributeList();
                    boolean isMasterCompanyLinkingPresent = extraAttributeDTOList.stream()
                            .filter(it->it.getIsActive() != null && it.getIsActive().equals(Boolean.TRUE))
                            .anyMatch(it->Objects.equals(it.getMetadata(),IAMConstant.PERSON_WORK_ORGANISATION_CODE));

                    if(!isMasterCompanyLinkingPresent) {
                        CompanyDTO companyDTO = companyMasterDataUtil.getCompanyDTOByCompanyName(personProfessionalHistoryDTO.getWorkPlace());
                        if (companyDTO != null) {
                            if (companyDTO.getCode() != null) {
                                extraAttributeDTOList.add(buildExtraAttributeDTO(IAMConstant.PERSON_WORK_ORGANISATION_CODE, companyDTO.getCode()));
                            }
                            if (companyDTO.getLogo() != null) {
                                extraAttributeDTOList.add(buildExtraAttributeDTO(IAMConstant.PERSON_WORK_LOGO_URL, companyDTO.getLogo()));
                            }
                            personProfessionalHistoryDTO.setExtraAttributeList(extraAttributeDTOList);
                        }
                    }
                }
            });
        }
    }

    public static ExtraAttributeDTO buildExtraAttributeDTO(String metadata, String attributeValue) {
        ExtraAttributeDTO extraAttributeDTO = null;
        if (metadata != null && attributeValue != null) {
            extraAttributeDTO = new ExtraAttributeDTO();
            extraAttributeDTO.setId(0);
            extraAttributeDTO.setAttributeValue(attributeValue);
            extraAttributeDTO.setAttributeType(IAMConstant.ATTRIBUTE_TYPE_GLOBAL);
            extraAttributeDTO.setMetadata(metadata);
        }
        return extraAttributeDTO;
    }

    @Override
    @Transactional
    public com.mindtree.bridge.platform.dto.UserDTO patchUpdateUser(com.mindtree.bridge.platform.dto.UserDTO userDTO) {
        userDTO = tldRestTemplate.patchBridgeUser(userDTO).getBody();
        if (userDTO != null) {
            oboFacade.produceChangesToTenantEndPoint(userDTO);
        }
        return userDTO;
    }


    public void patchUpdateUserViaWebhook(UserSyncCustomDTO userSyncCustomDTO) {
        Long userId = userSyncCustomDTO.getUserId();
        if(userId == null) {
            throw new InvalidInputException("UserId must not be null");
        }
        com.mindtree.bridge.platform.dto.UserDTO userDTO = tldClient.getBridgeUserDetails(PlatformSecurityUtil.getToken(),userId.intValue(),null).getBody();
        if(userDTO != null) {
            userSyncCustomDTOMapper.syncUserSyncCustomDTOChangesIntoUserDTO(userDTO,userSyncCustomDTO);
            try {
                tldRestTemplate.patchBridgeUser(userDTO).getBody();
            } catch(Exception e) {
                throw new InvalidInputException(String.format("User with id %d could not be patched. Exception - %s",userDTO.getId(),
                        ExceptionUtils.getMessage(e)));
            }
        }
        else {
            throw new NotFoundException(String.format("User with id %d not found",userId));
        }
    }

    @Async
    public void produceChangesToTenantEndPoint(com.mindtree.bridge.platform.dto.UserDTO userDTO) {
        TenantMetaAdditionalPropertyDTO tenantMetaAdditionalPropertyDTO = tenantMetaConfigFacade
                .getTenantMetaAdditionalPropertyByMetaKeyAndHierarchy(IAMConstant.CONFIG_KEY_PRODUCE_CHANGES_TO_TENANT_END_POINT,
                        PlatformSecurityUtil.getCurrentTenantLogin(),null);
        if (tenantMetaAdditionalPropertyDTO != null && Objects.equals(tenantMetaAdditionalPropertyDTO.getMetaValue(), "1")) {
            Map<String, String> attributes = tenantMetaAdditionalPropertyDTO.getAttributes();
            if (attributes != null) {
                String clientBaseUrl = attributes.getOrDefault("CLIENT_BASE_URL", null);
                String clientEndPoint = attributes.getOrDefault("CLIENT_END_POINT", null);
                String authorizationKey = attributes.getOrDefault("AUTORIZATION_HEADER_KEY", null);
                String authorizationValue = attributes.getOrDefault("AUTORIZATION_HEADER_VALUE", null);
                if (clientBaseUrl != null && clientEndPoint != null && authorizationKey != null && authorizationValue != null) {
                    String completeUrl = clientBaseUrl + clientEndPoint;
                    UserSyncCustomDTO userSyncCustomDTO = userSyncCustomDTOMapper.convertUserDTOToUserSyncCustomDTO(userDTO);
                    ResponseEntity<Object> response = null;
                    try {
                        response = customRestTemplate.produceUserUpdateChangesToTenantCustomEndPoint(
                                userSyncCustomDTO, completeUrl, authorizationKey, authorizationValue);
                        log.error("UserDTO successfully produced to client webhook with userId {}, Webhook Response: {}, Webhook Status: {}",
                                userDTO.getId(), response.getBody(), response.getStatusCode());

                    } catch (Exception exception) {
                        String responseBody = response != null && response.getBody() != null ? (String) response.getBody() : "No Response Body";
                        String statusCode = response != null ? response.getStatusCode().toString() : "No Status Code";
                        log.error("UserDTO could not be produced to client webhook for tenant login {} and userId {}. Error: {}. Webhook Response: {}. Webhook Status: {}",
                                PlatformSecurityUtil.getCurrentTenantLogin(), userDTO.getId(),
                                ExceptionUtils.getRootCauseMessage(exception),
                                responseBody, statusCode);
                    }
                }
            }
        }
    }


    @Override
    public TFIResponseDTO tfiUserVerification(TFIUserVerificationDTO tfiUserVerificationDTO) {
        TFIResponseDTO tfiResponseDTO =  tfiClientUtil.tfiUserVerification(tfiUserVerificationDTO);
        if(tfiResponseDTO.getReturnCode() != null && tfiResponseDTO.getReturnCode().startsWith("300")) {
            UserMetaDataDTO userMetaDataDTO = UserMetaDataDTO.builder()
                    .id(0L)
                    .metaKey(IAMConstant.META_KEY_SIGN_UP_FLOW_COMPLETED)
                    .metaValue("true")
                    .build();
            userMetaDataFacade.postOrUpdateInBulkForLoggedInUser(Collections.singleton(userMetaDataDTO));
        }
        return tfiResponseDTO;
    }


    private void loadSessionToCache(PlatformToken platformToken){
        authCacheManager.getSessionCache().put(platformToken.getToken(),platformToken, sessionCacheTTL);
    }

    // If no prior Transaction in progress
    private void getAndCachePlatformToken(String sessionId) {
        PlatformToken platformToken= tldPlatformTokenProvider.getPlatformTokenFromSessionId(sessionId);
        SecurityContextHolder.getContext().setAuthentication(platformToken);
        if(legacyUserMigrationEnabled!=null && legacyUserMigrationEnabled) {
            legacyUserMigrationService.migrateCurrentSessionUser();
        }
        if(legacyUserContextMergeEnabled!=null && legacyUserContextMergeEnabled) {
            userDetailsMergeService.addUserContext(platformToken);
        }
        loadSessionToCache(platformToken);
    }


    @Override
    public Object getOAuthIdToken(String authCode, String clientId, String redirectURI, String platform) {
        String grantType = "authorization_code";
        String clientSecret= env.getProperty("commons.platform.oauth."+platform.toLowerCase()+"."+clientId);
        if(clientSecret==null){
            throw  new InvalidInputException("invalid platform & clientId");
        }
        return linkedInOAuthClient.getAccessToken(grantType,authCode,clientId,clientSecret,redirectURI).getBody();
    }


    @Override
    public void approveOrRejectUserRoles(Long userId, Set<String> assignRoleCodes, Set<String> rejectRoleCodes) {
        assignRolesToUser(userId,assignRoleCodes);
        rejectedRolesToAnUser(userId, rejectRoleCodes);
    }

    @Override
    public void selfDeactivation(String reason) {
        tldClient.selfDeactivation(reason, PlatformSecurityUtil.getToken());
    }

    @Override
    public UserSelfRegistrationDTO changeContactValueAndTriggerOTP(UserContactVO userContactVO, String tenantName, String appId, String appContext) {
        return tldClient.changeContactValueAndTriggerOTP(userContactVO, tenantName, appId, appContext).getBody();

    }

    @Override
    public UserSelfRegistrationDTO validateAndAddPrimaryContactValueWithOTPTrigger(Long userId, String contactValue, String tenantName, String appId, String appContext, Boolean replaceContactValue) {
        return tldClient.validateAndAddPrimaryContactValueWithOTPTrigger(userId, contactValue, tenantName, appId, appContext, replaceContactValue).getBody();
    }

    @Override
    public String validateAndActivateUserContact(String otp, String modKey, String tenantName, Integer userId, Boolean isOTPAndModKeyOfEmail, Boolean skipUserActiveValidation) {
        return tldClient.validateAndActivateUserContact(otp, modKey, tenantName, userId, isOTPAndModKeyOfEmail, skipUserActiveValidation).getBody();
    }

    @Override
    public void setNewPasswordBySelf(String newPass, String firstName, String lastName, String email, String mobile) {
        tldClient.setNewPasswordBySelf(newPass, firstName, lastName, email, mobile, PlatformSecurityUtil.getToken());
    }

    @Override
    public String getPasswordStatusByUserName(String userName) {
        return tldClient.getPasswordStatusByUserName(userName).getBody();
    }

    @Override
    @Transactional
    public UserTenantCheckDTO computeTenantForUserLogin(String userLogin, String primaryTenantLogin, String crossTenantLogin) {
        UserTenantCheckDTO userTenantCheckDTO = commonsReportUtil.computeTenantForUserLogin(userLogin,primaryTenantLogin,crossTenantLogin);
        if(userTenantCheckDTO != null) {
            userTenantCheckDTO.setUserLogin(userLogin);
            if(Objects.equals(userTenantCheckDTO.getIsActive(),Boolean.TRUE)) {
                String userLoginWithTenant = userLogin + SEPARATOR + userTenantCheckDTO.getPreferredTenantLogin();
                String passwordSetStatus = getPasswordStatusByUserName(userLoginWithTenant);
                if(passwordSetStatus.equals("Default password set")) {
                    userTenantCheckDTO.setPasswordPresent(Boolean.FALSE);
                }
                else if(passwordSetStatus.equals("Password already set")) {
                    userTenantCheckDTO.setPasswordPresent(Boolean.TRUE);
                }
                else {
                    throw new NotFoundException(String.format("%s is not of standard value",passwordSetStatus));
                }
            }
        }
        return userTenantCheckDTO;
    }

    @Override
    public void assignRolesToUser(Long userId, Set<String> roleCodes) {
        if(roleCodes != null && !roleCodes.isEmpty()) {
            roleCodes.forEach(roleCode -> {
                try {
                    tldClient.assignRoleV3(roleCode, userId,null, Boolean.TRUE, PlatformSecurityUtil.getToken());
                } catch (Exception e) {
                    throw new InvalidInputException(String.format("Role of code %s could not be assigned to the user", roleCode));
                }
            });
        }
    }

    public void userSelfAssignRoleWithIsActiveFalse(Set<String> roleCodes) {
        if(roleCodes != null && !roleCodes.isEmpty()) {
            roleCodes.forEach(roleCode -> {
                try {
                    tldClient.userSelfAssignRole(roleCode, PlatformSecurityUtil.getToken());
                } catch (Exception e) {
                    throw new InvalidInputException(String.format("Role of code %s could not be assigned to the user", roleCode));
                }
            });
        }
    }

    @Override
    public void deActivateRolesFromAnUser(Long userId, Set<String> roleCodes,String reason,Boolean ignoreError) {
        if(roleCodes != null && !roleCodes.isEmpty()) {
            roleCodes.forEach(roleCode -> {
                try {
                    tldClient.deActivateRoleFromAnUser(roleCode, userId, null,reason ,PlatformSecurityUtil.getToken());
                } catch (Exception e) {
                    if(ignoreError == null || ignoreError.equals(Boolean.FALSE)) {
                        throw new InvalidInputException(String.format("Role of code %s could not be deleted to the user", roleCode));
                    }
                }
            });
        }
    }


    public void rejectedRolesToAnUser(Long userId, Set<String> rejectRoleCodes) {
        if(rejectRoleCodes != null && !rejectRoleCodes.isEmpty()) {
            rejectRoleCodes.forEach(roleCode -> {
                try{
                    tldClient.changeUserRoleStatus(roleCode, userId,"REJECTED",PlatformSecurityUtil.getToken()).getBody();
                } catch(Exception e) {
                    log.error(e.getMessage());
                    throw new DataAccessException(String.format("Role of - %s could not be rejected from user with id - %d",
                            roleCode, userId));
                }
            });
        }
    }
}
