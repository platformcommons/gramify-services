package com.platformcommons.platform.service.iam.facade.impl.obo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindtree.bridge.platform.dto.UserDTO;
import com.mindtree.bridge.platform.dto.UserRoleMapDTO;
import com.mindtree.bridge.platform.dto.UserWrapperDTO;
import com.platformcommons.platform.exception.ErrorDTO;
import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.TenantMetaMasterService;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.application.utility.*;
import com.platformcommons.platform.service.iam.domain.Lead;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.domain.vo.UserSelfRegistrationExcelVO;
import com.platformcommons.platform.service.iam.dto.*;
import com.platformcommons.platform.service.iam.facade.*;
import com.platformcommons.platform.service.iam.facade.client.ChangemakerOpportunityClient;
import com.platformcommons.platform.service.iam.facade.client.TLDClient;
import com.platformcommons.platform.service.iam.facade.client.WorknodeClient;
import com.platformcommons.platform.service.iam.facade.client.utility.CommonsReportUtil;
import com.platformcommons.platform.service.iam.facade.mapper.UserWrapperExcelMapper;
import com.platformcommons.platform.service.iam.facade.obo.OBOFacade;
import com.platformcommons.platform.service.iam.facade.obo.OBOUserFacade;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.facade.BulkUploadFacade;
import com.platformcommons.platform.service.sdk.bulkupload.service.OperationExecutorV2;
import com.platformcommons.platform.service.worknode.dto.ActorVerticalAssociationDTO;
import com.platformcommons.platform.service.worknode.dto.CohortDTO;
import com.platformcommons.platform.service.worknode.dto.OrgVerticalDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import java.util.Base64;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional
public class OBOUserFacadeImpl implements OBOUserFacade {

    private final UserOnBoardUtil userOnBoardUtil;
    private final TLDClient tldClient;
    private final LeadFacade leadFacade;
    @Autowired
    @Lazy
    private TenantFacade tenantFacade;
    private final ChangemakerOpportunityClient changemakerOpportunityClient;
    private final OTPValidationUtil otpValidationUtil;
    private final TriggerNotification triggerNotification;
    private final TenantMetaConfigFacade tenantMetaConfigFacade;
    private final UserVerificationFacade userVerificationFacade;
    private final OBOSecurityUtil oboSecurityUtil;
    private final CommonsReportUtil commonsReportUtil;
    private final OBOFacade oboFacade;
    private final VMSSignupUtil vmsSignupUtil;
    private final TenantMetaMasterFacade tenantMetaMasterFacade;
    private final BulkUploadFacade bulkUploadFacade;
    private final TLDUtil tldUtil;
    private final WorknodeClient worknodeClient;
    private final UserWrapperExcelMapper userWrapperExcelMapper;

    @Value("${commons.platform.world-tenant-login:world}")
    private String worldTenantLogin;

    @Override
    public void addAminUser(Tenant tenant, String adminPass, String sessionId,String appContext) {
        String login = userOnBoardUtil.getUserLogin(tenant, appContext);
        UserDTO userDTO = userOnBoardUtil.getUserObject(tenant.getTenantName(),
                null, login, tenant.getEmail(),tenant.getMobile());
        UserDTO response = fetchUserByLogin(tenant.getEmail(), sessionId);
        if (response == null) {
            response = tldClient.addUser(userDTO, adminPass, sessionId);
        }
        tldClient.assignRole(RoleUtil.adminRoleCode(tenant.getTenantLogin()),
                response.getId(), sessionId);
    }


    @Override
    public void addAminUserWithContacts(Tenant tenant, String adminPass, String sessionId, String appContext, String marketContext) {
        if(Boolean.parseBoolean(getDefaultValueFromMaster(marketContext, IAMConstant.META_KEY_ADD_SUPPORT_ADMIN_USER,Boolean.FALSE.toString()))) {
            String supportUserFirstName = getDefaultValueFromMaster(marketContext, IAMConstant.META_KEY_SUPPORT_ADMIN_USER_FIRST_NAME, IAMConstant.META_KEY_SUPPORT_ADMIN_USER_FIRST_NAME_DEFAULT);
            String supportUserLastName = getDefaultValueFromMaster(marketContext, IAMConstant.META_KEY_SUPPORT_ADMIN_USER_LAST_NAME, null);
            boolean useMobileAsLogin = Boolean.parseBoolean(getDefaultValueFromMaster(marketContext, IAMConstant.META_KEY_USE_MOBILE_AS_LOGIN_FOR_MARKET, Boolean.FALSE.toString()));
            String mobileNumber = getDefaultValueFromMaster(marketContext, IAMConstant.META_KEY_SUPPORT_ADMIN_USER_MOBILE, null);
            String email = getDefaultValueFromMaster(marketContext, IAMConstant.META_KEY_SUPPORT_ADMIN_USER_EMAIL, null);

            String login = useMobileAsLogin ? mobileNumber : email;
            if (login != null) {
                UserDTO response = fetchUserByLogin(login, sessionId);
                if (response == null) {
                    UserDTO userDTO = userOnBoardUtil.getUserDTO(supportUserFirstName, supportUserLastName, login, email, mobileNumber);
                    adminPass = adminPass == null ?
                            new String(Base64.getDecoder().decode(getDefaultValueFromMaster(marketContext, IAMConstant.META_KEY_SUPPORT_ADMIN_USER_PASSWORD,
                                    IAMConstant.META_KEY_SUPPORT_ADMIN_USER_DEFAULT_PASSWORD))) :
                            adminPass;
                    response = tldClient.addUser(userDTO, adminPass, sessionId);

                }
                tldClient.assignRole(RoleUtil.adminRoleCode(tenant.getTenantLogin()),
                        response.getId(), sessionId);
            }
        }
    }



    private String getDefaultValueFromMaster(String marketUUID,String metaKey, String defaultValue) {
        if(tenantMetaMasterFacade.getMetaMasterValue(marketUUID,metaKey)!=null && !tenantMetaMasterFacade.getMetaMasterValue(marketUUID,metaKey).isEmpty()) {
            return tenantMetaMasterFacade.getMetaMasterValue(marketUUID, metaKey).stream().findFirst().orElse(defaultValue);
        }
        else {
            return defaultValue;
        }
    }


    @Override
    public Set<com.platformcommons.platform.service.iam.dto.brbase.UserDTO> getUsersFromLinkedSystem(Long tenantId, String sessionId) {
        return tldClient.getUsers(sessionId, tenantId);
    }

    @Override
    public Set<UserRoleMapDTO> getUserRolesFromLinkedSystem(Set<Long> users, String sessionId) {
        StringBuilder criteria = new StringBuilder("user.id in (");
        users.forEach(userId -> {
            criteria.append(userId);
            criteria.append(",");
        });
        criteria.deleteCharAt(criteria.lastIndexOf(","));
        criteria.append(")");
        return tldClient.getUserRoleMap(criteria.toString(), sessionId);
    }

    @Override
    public Long addUser(IAMUserDTO iamUserDTO, String userPassword, String sessionId) {
        UserDTO user = fetchUserByLogin(iamUserDTO.getLogin(), sessionId);
        if (null != user) {
            throw new DuplicateResourceException(String.format("User with login name %s already exist", iamUserDTO.getLogin()));
        }
        UserDTO userDTO = userOnBoardUtil.getUserObjectFromIAMUserDTO(iamUserDTO);
        return Long.valueOf(tldClient.addUser(userDTO, userPassword, sessionId).getId());
    }

    @Override
    public Long addUseForTenant(IAMUserDTO iamUserDTO, String userPassword, Long tenantId, String sessionId) {
        UserDTO userDTO = userOnBoardUtil.getUserObjectFromIAMUserDTO(iamUserDTO);
        return Long.valueOf(tldClient.addUserForTenant(userDTO, userPassword, sessionId,tenantId).getId());
    }

    private UserDTO fetchUserByLogin(String login, String sessionId) {
        String criteria = "user.login = '" + login + "'";
        Set<UserRoleMapDTO> users = tldClient.getUserRoleMap(criteria, sessionId);
        if (users.isEmpty())
            return null;
        else
            return users.stream().findFirst()
                    .orElseThrow(() -> new NotFoundException("Unable to find user")).getUser();
    }

    @Override
    public String createUserInLinkedSystemInVmsByLead(UserSignUpRequestDTO userSignUpRequestDTO, String password) {
        LeadDTO leadDTO = leadFacade.getByKey(userSignUpRequestDTO.getKey());
        if(!Objects.equals(leadDTO.getActivationStatus(), Lead.STATUS_REGISTERED)) {
            throw new InvalidInputException("Lead must be in Registered phase");
        }

        String userLogin = leadDTO.getLeadLogin();
        TenantDTO tenantDTO = tenantFacade.getTenantById(leadDTO.getTenantId());
        String marketUUID = leadDTO.getMarketContext();
        MarketConfigDTO marketConfigDTO = vmsSignupUtil.fetchMarketConfig(marketUUID);

        otpValidationUtil.validateLeadOTPs(userSignUpRequestDTO.getOtpForEmail(),userSignUpRequestDTO.getOtpForMobile(),
                userSignUpRequestDTO.getMessageIdForEmail(),userSignUpRequestDTO.getMessageIdForMobile(),leadDTO,
                marketConfigDTO,triggerNotification);

        getOrCreateUserFromLead(leadDTO,password,worldTenantLogin);
        String worldSessionId = oboSecurityUtil.fetchUserSessionViaTenantOpsSupport(userLogin,worldTenantLogin,tenantMetaConfigFacade);

        VMSSignupUtil.marketConfigOperationAccessible(marketConfigDTO.getSolutionSubscriptionCode(),marketUUID,
                VMSSignupUtil.BETTER_TOGETHER_OPERATION_USER_SELF_SIGN_UP);

        String sessionId = getCrossSessionIdFromWorldSession(tenantDTO.getTenantLogin(), worldSessionId);
        UserDTO userDTO = tldClient.getBridgeUserDetails(sessionId,null,null).getBody();


        leadFacade.leadToUser(leadDTO.getKey());
        userVerificationFacade.addEntryForUserVerification(tenantDTO.getTenantLogin(),userDTO.getId().longValue(),
                leadDTO.getAppContext(), leadDTO.getMarketContext());

        oboSecurityUtil.fetchTokenAndSetSecurityContext(sessionId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        triggerNotification.sendNotificationOnUserJoiningATenant(userDTO,tenantDTO,leadDTO.getAppContext(),
                leadDTO.getMarketContext(), authentication);
        return sessionId;
    }

    private String getCrossSessionIdFromWorldSession(String tenantLogin, String worldSessionId) {
        String sessionId = null;
        if(!tenantLogin.equals(worldTenantLogin)) {
            sessionId = oboFacade.getCrossTenantSessionId(tenantLogin, worldSessionId);
        }
        else {
             sessionId = worldSessionId;
        }
        return sessionId;
    }

    @Override
    public UserDTO getOrCreateUserFromLead(LeadDTO leadDTO,String password,String tenantLogin) {
        UserDTO userDTO = null;
        String userLogin = leadDTO.getLeadLogin();
        String sessionId = oboSecurityUtil.getOpsSupportTenantUserSession(tenantLogin,tenantMetaConfigFacade);
        Boolean userExists = commonsReportUtil.checkIfUserExistsInTenant(userLogin,tenantLogin);
        if(userExists) {
            userDTO = tldClient.getBridgeUserDetails(sessionId,null,userLogin).getBody();
        }
        else {
            userDTO = addUserInLinkedSystemFromLead(leadDTO,password,sessionId);
        }
        return userDTO;
    }


    public UserDTO addUserInLinkedSystemFromLead(LeadDTO leadDTO, String password,String sessionId) {
        UserDTO userDTO = null;
        UserWrapperDTO userWrapperDTO = userOnBoardUtil.createUserWrapperDTOFromLeadDTO(leadDTO,password);
        userWrapperDTO = tldClient.createUserWrapper(userWrapperDTO,sessionId).getBody();
        if(userWrapperDTO != null) {
            userDTO = userWrapperDTO.getUserDTO();
        }
        return userDTO;
    }

    @Override
    public void initiateProcessOfUserBulkUpload(Long bulkUploadRequestId, String appContext, String baseDomain,
                                                Boolean sendNotification, String marketId) {
        OperationExecutorV2<UserSelfRegistrationExcelVO> operationExecutorV2 =
                (userSelfRegistrationExcelVO, platformToken) -> {
            SecurityContextHolder.getContext().setAuthentication(platformToken);
            addUserFromExcel(userSelfRegistrationExcelVO, appContext, baseDomain, sendNotification, marketId);
        };

        bulkUploadFacade.processBulkUploadRequest(UserSelfRegistrationExcelVO.class, operationExecutorV2, bulkUploadRequestId,
                (PlatformToken) SecurityContextHolder.getContext().getAuthentication());

    }

    public void addUserFromExcel(UserSelfRegistrationExcelVO userSelfRegistrationExcelVO, String appContext, String baseDomain,
                                 Boolean sendNotification, String marketId) {

        String userLogin = userSelfRegistrationExcelVO.getLogin();
        if(userLogin == null) {
            throw new InvalidInputException("userLogin must not be null");
        }

        checkIfUserAlreadyExists(userLogin);

        UserWrapperDTO userWrapperDTO = userWrapperExcelMapper.toUserWrapperDTO(userSelfRegistrationExcelVO);
        validateUserSelfRegistrationDTO(userWrapperDTO);
        UserRoleMapDTO userRoleMapDTO;

        try {
            userWrapperDTO = tldClient.createUserWrapper(userWrapperDTO,PlatformSecurityUtil.getToken()).getBody();

            userRoleMapDTO = tldClient.getUserRoleByRoleCode(
                    userWrapperDTO.getUserRoleCode(),
                    userWrapperDTO.getUserDTO().getLogin(),
                    PlatformSecurityUtil.getToken()).getBody();
        } catch(FeignException exception) {
            ObjectMapper mapper = new ObjectMapper();
            ErrorDTO errorDTO = new ErrorDTO();
            try {
                errorDTO = mapper.readValue(exception.contentUTF8(), ErrorDTO.class);
            } catch (JsonProcessingException e) {
                errorDTO.setErrorMessage("User could not be created");
            }
            throw new InvalidInputException(errorDTO.getErrorMessage());
        }

        UserDTO userDTO = Objects.requireNonNull(userWrapperDTO).getUserDTO();
        Long userId = Long.valueOf(userWrapperDTO.getUserDTO().getId());

        createActorVerticalAssociation(userId, userSelfRegistrationExcelVO.getCohortId(), userSelfRegistrationExcelVO.getOrgVerticalId(),
                userSelfRegistrationExcelVO.getCohortStartDate(), userSelfRegistrationExcelVO.getCohortEndDate(),
                userSelfRegistrationExcelVO.getRoleCode());

        if(Objects.equals(sendNotification,Boolean.TRUE)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            triggerNotification.sendNotificationOnUserAdd(userDTO, userRoleMapDTO, authentication, appContext, baseDomain, marketId);
        }
    }

    public void validateUserSelfRegistrationDTO(UserWrapperDTO userWrapperDTO) throws InvalidInputException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserWrapperDTO>> violations = validator.validate(userWrapperDTO);
        StringBuilder violationString = new StringBuilder();
        for (ConstraintViolation<UserWrapperDTO> violation : violations) {
            violationString.append(violation.getMessage()).append(",");
        }
        if(violationString.length() > 0) {
            throw new InvalidInputException(String.format("%s - %s",userWrapperDTO.getUserDTO().getLogin(),violationString));
        }
    }

    @Override
    public Long addUser(UserWrapperCustomDTO userWrapperCustomDTO, String appContext, String baseDomain, Boolean sendNotification,
                        String marketId) {
        UserWrapperDTO userWrapperDTO = userWrapperCustomDTO.getUserWrapper();
        if(userWrapperDTO.getUserRoleCode()  == null) {
            throw new InvalidInputException("RoleCode must not be null");
        }
        if(userWrapperDTO.getUserDTO()  == null) {
            throw new InvalidInputException("UserDTO must not be null");
        }

        checkIfUserAlreadyExists(userWrapperDTO.getUserDTO().getLogin());

        userWrapperDTO = tldClient.createUserWrapper(userWrapperDTO,PlatformSecurityUtil.getToken()).getBody();
        UserRoleMapDTO userRoleMapDTO = tldClient.getUserRoleByRoleCode(
                userWrapperDTO.getUserRoleCode(),
                userWrapperDTO.getUserDTO().getLogin(),
                PlatformSecurityUtil.getToken()).getBody();

        userWrapperCustomDTO.setUserWrapper(userWrapperDTO);

        Long userId = Long.valueOf(userWrapperCustomDTO.getUserWrapper().getUserDTO().getId());

        createActorVerticalAssociation(userId, userWrapperCustomDTO.getCohortId(), userWrapperCustomDTO.getOrgVerticalId(),
                userWrapperCustomDTO.getCohortStartDate(), userWrapperCustomDTO.getCohortEndDate(),
                userWrapperCustomDTO.getUserWrapper().getUserRoleCode());


        if(Objects.equals(sendNotification,Boolean.TRUE)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Runnable runnable = ()-> triggerNotification.sendNotificationOnUserAdd(userWrapperCustomDTO.getUserWrapper().getUserDTO(),
                    userRoleMapDTO, authentication, appContext, baseDomain, marketId);
            PlatformUtil.registerTransactionSynchronization(runnable);
        }

        return userId;
    }

    public void checkIfUserAlreadyExists(String userLogin) {
        ResponseEntity<UserDTO> userDTOResponseEntity = null;
        try {
            userDTOResponseEntity = tldClient.getBridgeUserDetails(PlatformSecurityUtil.getToken(),null,
                    userLogin);
            if(userDTOResponseEntity != null && userDTOResponseEntity.getBody() != null) {
                throw new DuplicateResourceException(String.format("User with Login %s already exists", userLogin));
            }
        } catch (Exception exception) {
            if( userDTOResponseEntity != null && !userDTOResponseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND) ) {
                throw exception;
            }
        }
    }

    private void createActorVerticalAssociation(Long userId, Long cohortId, Long orgVerticalId, Date cohortStartDate,
                                                Date cohortEndDate, String roleCode) {
        if(orgVerticalId != null && cohortId != null && roleCode != null) {
            ActorVerticalAssociationDTO actorVerticalAssociationDTO = ActorVerticalAssociationDTO.builder()
                    .userId(userId)
                    .roleCode(roleCode)
                    .startDate(cohortStartDate)
                    .endDate(cohortEndDate)
                    .orgVertical(OrgVerticalDTO.builder()
                            .id(orgVerticalId)
                            .build())
                    .cohort(CohortDTO.builder()
                            .id(cohortId)
                            .build())
                    .build();

            worknodeClient.postOrPatchUpdateActorVerticalAssociation(actorVerticalAssociationDTO, PlatformSecurityUtil.getToken());
        }
    }
}
