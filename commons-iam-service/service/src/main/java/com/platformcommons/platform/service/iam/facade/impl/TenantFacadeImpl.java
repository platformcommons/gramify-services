package com.platformcommons.platform.service.iam.facade.impl;

import com.mindtree.bridge.platform.dto.PersonDTO;
import com.mindtree.bridge.platform.dto.UserRoleMapDTO;
import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.iam.application.*;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.application.constant.MarketConstant;
import com.platformcommons.platform.service.iam.application.constant.TenantMetaConstant;
import com.platformcommons.platform.service.iam.application.utility.*;
import com.platformcommons.platform.service.iam.domain.*;
import com.platformcommons.platform.service.iam.dto.*;
import com.platformcommons.platform.service.iam.dto.brbase.UserDTO;
import com.platformcommons.platform.service.iam.facade.*;
import com.platformcommons.platform.service.iam.facade.assembler.AttachmentDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.IAMUserDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.TenantDTOAssembler;
import com.platformcommons.platform.service.iam.facade.cache.TenantCacheManager;
import com.platformcommons.platform.service.iam.facade.client.ChangemakerOpportunityClient;
import com.platformcommons.platform.service.iam.facade.client.OpportunityClient;
import com.platformcommons.platform.service.iam.facade.client.utility.CommonsReportUtil;
import com.platformcommons.platform.service.iam.facade.obo.OBOTenantFacade;
import com.platformcommons.platform.service.iam.facade.obo.OBOUserFacade;
import com.platformcommons.platform.service.iam.messaging.producer.TenantProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.platformcommons.platform.service.iam.application.utility.VMSSignupUtil.*;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TenantFacadeImpl implements TenantFacade {

    private final TenantDTOAssembler tenantDTOAssembler;
    private final TenantService tenantService;
    @Autowired
    @Lazy
    private UserFacade userFacade;
    @Autowired
    @Lazy
    private  UserRoleMapFacade userRoleMapFacade;
    private final OBOTenantFacade oboTenantFacade;
    private final TenantMetaMasterService tenantMetaMasterService;
    private final TenantAppAssociationService tenantAppAssociationService;
    private final TenantOnBoardUtil tenantOnBoardUtil;
    private final RoleFacade roleFacade;
    @Autowired
    @Lazy
    private OBOUserFacade oboUserFacade;
    private final IAMUserDTOAssembler iamUserDTOAssembler;
    private final LeadFacade leadFacade;
    private final OBOSecurityUtil oboSecurityUtil;
    private final TenantMetaConfigFacade tenantMetaConfigFacade;
    private final CommonsReportUtil commonsReportUtil;
    private final OTPValidationUtil otpValidationUtil;
    private final AttachmentFacade attachmentFacade;
    private final AttachmentDTOAssembler attachmentDTOAssembler;
    private final OpportunityClient opportunityClient;
    private final VMSSignupUtil vmsSignupUtil;
    private final TriggerNotification triggerNotification;
    private final TenantMetaService tenantMetaService;
    private final UserMapService userMapService;
    private final TenantPartnerService tenantPartnerService;

    @Autowired(required = false)
    private TenantCacheManager tenantCacheManager;

    @Autowired
    @Lazy
    private UserOnBoardUtil userOnBoardUtil;

    @Autowired(required = false)
    private TenantProducer tenantProducer;

    @Value("${commons.platform.service.vms.dataset-group:VMS_TEST}")
    public String vmsDatasetGroupCode;
    @Value("${commons.config.lead.welcome.mail.enabled:false}")
    private boolean isWelcomeMailEnabled;
    @Value("${commons.platform.world-tenant-login:world}")
    private String worldTenantLogin;

    @Override
    public Long addTenant(TenantDTO tenantDTO, String adminPass, String appContext,String marketContext, boolean addInLinkedSystem, String referralCode) {
        PlatformSecurityUtil.validatePlatformAdmin();
        Long id = addTenantInternal(tenantDTO, adminPass, appContext,marketContext ,addInLinkedSystem, referralCode);
        if (tenantCacheManager != null){
            tenantCacheManager.clearCache(id);
        }
        return id;
    }

    public Long addTenantInternal(TenantDTO tenantDTO, String adminPass, String appContext, String marketContext,boolean addInLinkedSystem,String referralCode) {
        String sessionId = null;
        Tenant tenant = createTenant(tenantDTO, adminPass, addInLinkedSystem);
        PlatformSecurityUtil.mimicTenant(tenant.getId(), 1L);
        Long adminRoleId = roleFacade.addRole(RoleUtil.createAdminRole(tenant.getTenantLogin()),
                null, null, null);
        if (addInLinkedSystem)
            sessionId = oboSecurityUtil.linkedSystemToken("admin@@" + tenant.getTenantLogin(), adminPass);

        Set<User> users = createUsers(tenant, adminPass, sessionId, addInLinkedSystem,appContext,marketContext);

        addUserRoles(tenant, users, sessionId, adminRoleId, addInLinkedSystem);

        addTenantMetaInformation(tenant, appContext, marketContext,referralCode);
        return tenant.getId();
    }

    @Override
    public TenantDTO getTenantByLogin(String login) {
        TenantDTO tenantDTO;
        Tenant tenant = tenantService.getTenantByLogin(login);
        tenantDTO = tenantDTOAssembler.toDTO(tenant);
        if (tenantCacheManager != null){
            tenantCacheManager.clearCache(tenantDTO.getId());
        }
        return tenantDTO;
    }

    @Override
    public TenantDTO getTenantById(Long tenantId) {
        TenantDTO tenantDTO;
        Tenant tenant = tenantService.getTenantById(tenantId);
        tenantDTO = tenantDTOAssembler.toDTO(tenant);
        return tenantDTO;
    }

    @Override
    public Optional<TenantDTO> getTenantByIdOptional(Long tenantId) {
        Optional<TenantDTO> tenantDTOOptional = Optional.empty();
        Optional<Tenant> tenantOptional = tenantService.getTenantByIdOptional(tenantId);
        if(tenantOptional.isPresent()) {
            Tenant tenant = tenantOptional.get();
            tenantDTOOptional = Optional.of(tenantDTOAssembler.toDTO(tenant));
        }
        return tenantDTOOptional;
    }

    @Override
    public TenantDTO getCurrentTenant() {
        return getTenantById(PlatformSecurityUtil.getCurrentTenantId());
    }

    @Override
    public void updateTenant(TenantDTO tenantDTO, boolean isPatch) {
        Tenant tenant = tenantService.updateTenant(tenantDTOAssembler.fromDTO(tenantDTO), isPatch);
        tenantDTO = tenantDTOAssembler.toDTO(tenant);
        if (tenantProducer != null){
            tenantProducer.tenantUpdated(tenantDTO);
        }
        if (tenantCacheManager != null){
            tenantCacheManager.clearCache(tenant.getId());
        }
    }


    @Override
    public void addTenantLogo(MultipartFile file) {
        Long tenantId = PlatformSecurityUtil.getCurrentTenantId();
        AttachmentDTO attachmentDTO = attachmentFacade.createAttachment(tenantId, "ENTITY_TYPE.TENANT",
                "Logo", "Logo", file, Boolean.TRUE, "ATTACHMENT_KIND_META.LOGO", "Logo");
        Attachment attachment = attachmentDTOAssembler.fromDTO(attachmentDTO);
        Tenant tenant = tenantService.addTenantLogo(tenantId, attachment.getCompleteURL());
        if (tenantProducer != null){
            TenantDTO tenantDTO = tenantDTOAssembler.toDTO(tenant);
            tenantProducer.tenantUpdated(tenantDTO);
        }
    }

    @Override
    public void addTenantCoverImage(MultipartFile file) {
        Long tenantId = PlatformSecurityUtil.getCurrentTenantId();
        AttachmentDTO attachmentDTO = attachmentFacade.createAttachment(tenantId, "ENTITY_TYPE.TENANT",
                "Cover Image", file.getName(), file, Boolean.TRUE,
                "ATTACHMENT_KIND_META.COVER_IMAGE", null);
        Attachment attachment = attachmentDTOAssembler.fromDTO(attachmentDTO);
        Tenant tenant = tenantService.addTenantCoverImage(tenantId, attachment.getCompleteURL());
        if (tenantProducer != null){
            TenantDTO tenantDTO = tenantDTOAssembler.toDTO(tenant);
            tenantProducer.tenantUpdated(tenantDTO);
        }
    }

    @Override
    public List<TenantVO> existsTenantWithEmail(String email) {
        List<Tenant> tenants = tenantService.getTenantByEmail(email);
        return tenants.stream().map(this::mapTenantToVO).collect(Collectors.toList());
    }

    @Override
    public List<TenantVO> existsTenantWithMobile(String mobile) {
        List<Tenant> tenants = tenantService.getTenantByMobile(mobile);
        return tenants.stream().map(this::mapTenantToVO).collect(Collectors.toList());
    }

    @Override
    public Boolean existsDomainForTenant(String domain) {
        return tenantService.existsDomainForTenant(domain);
    }

    @Override
    public void addDomainForTenant(String domain) {
        tenantService.addDomainForTenant(domain);
    }

    @Override
    public void addTenantAndOperationsPostCreation(TenantDTO tenantDTO, String appContext, String marketContext,
                                                   String leadKey, String datasetGroupCode) {
        Tenant tenant = tenantService.addTenant(tenantDTOAssembler.fromDTO(tenantDTO), leadKey);
        leadFacade.leadToTenant(leadKey);
        addAppAssociation(tenant, appContext, true, marketContext,null);
        tenantOnBoardUtil.addDataSetGroupPermission(tenant.getId(), tenant.getTenantName(), datasetGroupCode);
        tenantMetaConfigFacade.createTenantMetaConfigOnTenantSignupVMS(tenantDTO,appContext);
    }


    @Transactional
    @Override
    public void addTenantSolution(SolutionSubscriptionDTO solutionSubscriptionDTO) {
        Optional<TenantMetaMaster> defaultUSerRegistrationStatus = tenantMetaMasterService.
                getByAppContextAndCode(solutionSubscriptionDTO.getAppContext(), TenantMetaConstant.META_MASTER_DATA_DEFAULT_DOMAIN);
        String baseDomainForMail = vmsSignupUtil.processMarketConfiguration(solutionSubscriptionDTO, defaultUSerRegistrationStatus.orElse(null));
        addDomainForTenant(baseDomainForMail);
        TenantAppAssociation tenantAppAssociation = processTenantAppAssociation(solutionSubscriptionDTO, baseDomainForMail);
        Optional<TenantMetaMaster> sendWelcomeMailMeta = tenantMetaMasterService.
                getByAppContextAndCode(solutionSubscriptionDTO.getAppContext(),
                        TenantMetaConstant.META_MASTER_DATA_SEND_WELCOME_MAIL_ON_SIGNUP);
        vmsSignupUtil.triggerWelcomeMail(tenantAppAssociation, baseDomainForMail, sendWelcomeMailMeta.orElse(null),triggerNotification);
    }

    @Transactional
    @Override
    public void addTenantSolutionV2(SolutionSubscriptionDTO solutionSubscriptionDTO) {
        MarketConfigDTO ownerMarketConfigDTO = vmsSignupUtil.fetchMarketConfig(solutionSubscriptionDTO.getDefaultMarketUUID());
        VMSSignupUtil.marketConfigOperationAccessible(ownerMarketConfigDTO.getSolutionSubscriptionCode(),
                ownerMarketConfigDTO.getMarketUUID(), BETTER_TOGETHER_OPERATION_TENANT_SELF_SIGN_UP);
        String solutionSubscriptionCode = solutionSubscriptionDTO.getSolutionSubscriptionCode();
        if (BETTER_TOGETHER_TRAILBLAZER.equals(solutionSubscriptionCode)
                || BETTER_TOGETHER_SYSTEMS_CHANGE_LEADER.equals(solutionSubscriptionCode)) {
            vmsSignupUtil.handleTrailblazerOrSystemLeaderSubscriptionCode(solutionSubscriptionDTO, ownerMarketConfigDTO);
        } else if (BETTER_TOGETHER_VMS_MODEL.equals(solutionSubscriptionCode)) {
            vmsSignupUtil.handleVmsModelSubscriptionCode(solutionSubscriptionDTO, ownerMarketConfigDTO);
        } else {
            throw new InvalidInputException("Subscription Code is not valid");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getTenantAppAssociationStatus(String appContext) {
        TenantAppAssociation tenantAppAssociation = tenantAppAssociationService.getByAppContextAndTenantId(appContext, PlatformSecurityUtil.getCurrentTenantId());
        Map<String, Boolean> stepStatus = tenantAppAssociation.getStepStatus();
        Map<String, Object> stringObjectMap = new HashMap<>(stepStatus);
        stringObjectMap.put("isComplete", tenantAppAssociation.getIsComplete());
        stringObjectMap.put("domainURL", tenantAppAssociation.getAppDomainURL());
        return stringObjectMap;
    }


    @Override
    public void createTenantVMS(SignUpRequestDTO signUpRequestDTO, String adminPassword) {
        LeadDTO leadDTO = leadFacade.fetchLeadForTenantCreation(signUpRequestDTO.getKey());
        MarketConfigDTO marketConfigDTO = vmsSignupUtil.fetchMarketConfig(leadDTO.getMarketContext());
        otpValidationUtil.validateLeadOTPs(signUpRequestDTO.getOtpForEmail(),signUpRequestDTO.getOtpForMobile(),
                signUpRequestDTO.getMessageIdForEmail(),signUpRequestDTO.getMessageIdForMobile(),leadDTO, marketConfigDTO,triggerNotification);

        com.mindtree.bridge.platform.dto.UserDTO userDTO = oboUserFacade.getOrCreateUserFromLead(leadDTO,adminPassword,
                              worldTenantLogin);
        Long worldUserId = Long.valueOf(userDTO.getId());

        TenantDTO tenantDTO = createTenantInLinkedSystemAndInitializeAdmin(leadDTO, adminPassword,worldUserId);
        addTenantAndOperationsPostCreation(tenantDTO, leadDTO.getAppContext(), marketConfigDTO.getMarketUUID()
                , leadDTO.getKey(), vmsDatasetGroupCode);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        triggerNotification.sendNotificationOnTenantCreation(leadDTO,tenantDTO,marketConfigDTO.getMarketUUID(),authentication);

    }

    @Override
    public TenantLoginResourcesResponse getMarketConfigsAndLeadPresentByParams(String appContext, String email,
                                                                               String mobile, String marketUUID,
                                                                               Map<String, String> httpHeaders) {
        Set<MarketConfigWithTenantsDTO> marketConfigWithTenantsDTOSet = null;
        Set<LeadDTO> leadDTOSet = null;
        if (PlatformUtil.isSession()) {
            Set<Long> tenantIds = Collections.singleton(PlatformSecurityUtil.getCurrentTenantId());
            marketConfigWithTenantsDTOSet = getMarketConfigsWithTenantsLinkedToTenantIds(tenantIds,
                    appContext, httpHeaders);
        } else {
            String userLogin = null;
            if ((StringUtils.isEmpty(email) && StringUtils.isEmpty(mobile)) || (!StringUtils.isEmpty(email) && !StringUtils.isEmpty(mobile))) {
                throw new InvalidInputException("Either email or mobile must be provided");
            }
            if (!StringUtils.isEmpty(email)) {
                userLogin = email;
            } else {
                userLogin = mobile;
            }
            Set<Long> tenantIds = commonsReportUtil.getTenantIdsByUserLogin(userLogin);
            marketConfigWithTenantsDTOSet = getMarketConfigsWithTenantsLinkedToTenantIds(tenantIds,
                    appContext, httpHeaders);
            leadDTOSet = leadFacade.getLeadsInPendingState(email, mobile, appContext, marketUUID);
        }

        Boolean tenantIsPartOfMarket = Boolean.FALSE;
        if (marketConfigWithTenantsDTOSet != null) {
            tenantIsPartOfMarket = marketConfigWithTenantsDTOSet.stream()
                    .anyMatch(it -> Objects.equals(it.getMarketUUID(), marketUUID));
        }
        return TenantLoginResourcesResponse.builder()
                .leads(leadDTOSet)
                .marketConfigWithTenantsSet(marketConfigWithTenantsDTOSet)
                .marketConfigPresent(marketConfigWithTenantsDTOSet != null && !marketConfigWithTenantsDTOSet.isEmpty())
                .leadPresent(leadDTOSet != null && !leadDTOSet.isEmpty())
                .tenantIsPartOfCurrentMarket(tenantIsPartOfMarket)
                .build();
    }


    @Override
    public AttachmentDTO addAttachmentToTenant(MultipartFile file, String attachmentKind, String entityType, String attachmentKindMeta, String attachmentKindIdentifier, Boolean isPublic, String name) {
        Long tenantId = PlatformSecurityUtil.getCurrentTenantId();
        String finalEntityType = entityType != null ? entityType : "ENTITY_TYPE.TENANT";
        AttachmentDTO attachmentDTO = attachmentFacade.createAttachment(tenantId, finalEntityType, attachmentKind, name, file, isPublic, attachmentKindMeta, attachmentKindIdentifier);
        Attachment attachment = attachmentDTOAssembler.fromDTO(attachmentDTO);
        tenantService.addAttachmentToTenant(tenantId, attachment);
        return attachmentDTO;
    }


    @Override
    public void deleteAttachmentFromTenant(Long attachmentId, String reason) {
        PlatformUtil.validateTenantAdmin();
        attachmentFacade.deleteAttachment(attachmentId, reason);
    }

    @Override
    public Boolean checkForExistingSlug(Long tenantId, String slug) {
        return tenantService.checkForExistingSlug(tenantId, slug);
    }


    @Override
    public Long convertToTenant(TenantDTO tenantDTO, String context, String contextValue,
                                PlatformToken platformTokenActual, Boolean isPrimary) {
        String parentTenantLogin = PlatformSecurityUtil.getCurrentTenantLogin();

        //validate
        validateUserTenantConversion(parentTenantLogin);

        //create tenant
        tenantDTO.setEmail(PlatformSecurityUtil.getCurrentUserLogin());
        Long newTenantId = addTenantInternal(tenantDTO, null, contextValue,null ,false,null);
        User newUser = userFacade.getUserReferenceByLoginAndTenantId(tenantDTO.getEmail(), newTenantId);

        //addTenantPartner
        TenantMeta tenantMeta = tenantMetaService.getTenantMetaData(parentTenantLogin,
                TenantMetaConstant.META_MASTER_PARTNER_SIGNUP_DEFAULT_STATUS);
        String defaultStatus = tenantMeta == null ? TenantMetaConstant.META_MASTER_PARTNER_SIGNUP_DEFAULT_STATUS
                : tenantMeta.getMetaValue();
        Long tenantPartnerId = tenantPartnerService.addTenantPartner(newTenantId,
                platformTokenActual.getTenantContext().getTenantId()
                , defaultStatus, isPrimary);

        //add UserMap
        userMapService.addUserMap(tenantPartnerId, newUser.getId(),
                platformTokenActual.getUserContext().getUserId(), defaultStatus);

        return newUser.getId();

    }

    @Override
    public Set<TenantPartnerVO> getTenantPartners() {
        Set<UserMap> userMapSet = userMapService.getTenantPartnersForLoggedInContext();
        return userMapSet.stream().map(TenantFacadeImpl::userMapToTenantPartnerVO)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean existsTenantWithLogin(String tenantLogin) {
        return tenantService.existsByTenantLogin(tenantLogin);
    }

    @Override
    public TenantDTO getTenantByTenantIdAndLoadCache(Long tenantId) {
        TenantDTO tenantDTO = null;
        Optional<Tenant> tenantOptional = tenantService.getTenantByIdOptional(tenantId);
        if(tenantOptional.isPresent()) {
            tenantDTO = tenantDTOAssembler.toDTO(tenantOptional.get());
            if (tenantCacheManager != null){
                tenantCacheManager.setValueToCache(tenantDTO);
            }
        }
        return tenantDTO;
    }

    @Override
    public TenantDTO getByTenantIdFromCacheOrDB(Long tenantId) {
        TenantDTO tenantDTO = null;
        if(tenantCacheManager != null) {
            tenantDTO = tenantCacheManager.getValueForKey(tenantId);
        }
        else {
            tenantDTO = getTenantByIdOptional(tenantId).orElse(null);
        }
        if(tenantDTO == null) {
            throw new NotFoundException(String.format("Tenant not found with id %d",tenantId));
        }
        return tenantDTO;
    }

    @Override
    public void deleteTenantCacheByTenantId(Long tenantId) {
        if (tenantCacheManager != null) {
            tenantCacheManager.clearCache(tenantId);
        }
    }

    @Override
    public void deleteAllTenantCache() {
        if (tenantCacheManager != null) {
            tenantCacheManager.clearAllCache();
        }
    }


    public void validateUserTenantConversion(String parentTenantLogin) {
        TenantMeta tenantMeta = tenantMetaService.getTenantMetaData(parentTenantLogin,
                TenantMetaConstant.META_MASTER_ALLOWED_PARTNER_SIGNUP);
        if (tenantMeta == null || !Boolean.parseBoolean(tenantMeta.getMetaValue())) {
            throw new InvalidInputException("Tenant has not allowed to be converted to partner");
        }
    }

    /* Tenant OnBoarding Helper Methods*/

    public TenantDTO validateTenantDetail(TenantDTO tenantDTO) {
        if (StringUtils.isEmpty(tenantDTO.getTenantUUID())) {
            tenantDTO.setTenantUUID(UUID.randomUUID().toString());
        }
        boolean tenantExistInIAM = tenantService.existsByTenantLogin(tenantDTO.getTenantLogin());
        if (tenantExistInIAM) {
            throw new DuplicateResourceException(String.format("A tenant with login name %s already exist.", tenantDTO.getTenantLogin()));
        }
        return oboTenantFacade.getTenantFromLinkedSystem(tenantDTO.getTenantLogin(), tenantDTO.getEmail(), tenantDTO.getMobile());
    }

    public Tenant createTenant(TenantDTO tenantDTO, String adminPass, boolean addInLinkedSystem) {
        TenantDTO linkedSystemTenantDTO = validateTenantDetail(tenantDTO);
        if (addInLinkedSystem && linkedSystemTenantDTO == null) {
            log.debug("====> Creating Tenant {} in TLD.....", tenantDTO.getTenantLogin());
            linkedSystemTenantDTO = oboTenantFacade.addTenantInLinkedSystem(tenantDTO, adminPass);
        } else {
            log.debug("====> Skipping Tenant Creation {} in TLD.....", tenantDTO.getTenantLogin());
            linkedSystemTenantDTO = tenantDTO;
        }
        return tenantService.addTenant(tenantDTOAssembler.fromDTO(linkedSystemTenantDTO), null);
    }

    private Set<User> createUsers(Tenant tenant, String adminPass, String sessionId, boolean addInLinkedSystem, String appContext, String marketContext) {
        Set<UserDTO> users = new HashSet<>();
        if (addInLinkedSystem) {
            oboUserFacade.addAminUser(tenant, adminPass, sessionId,appContext);

            //ADD supportUser with Admin Role for that Market
            oboUserFacade.addAminUserWithContacts(tenant,null,sessionId,appContext,marketContext);
            users = oboUserFacade.getUsersFromLinkedSystem(tenant.getId(), sessionId);

        } else {
            users.add(iamUserDTOAssembler.buildUserDTO(IAMConstant.ADMIN, IAMConstant.ADMIN));
            users.add(iamUserDTOAssembler.buildUserDTO(IAMConstant.OPS_SUPPORT, IAMConstant.OPS_SUPPORT));
            users.add(iamUserDTOAssembler.buildUserDTO(userOnBoardUtil.getUserLogin(tenant,appContext), tenant.getTenantName()));
        }
        return userFacade.addDefaultUsers(users, tenant, adminPass);
    }


    public void addUserRoles(Tenant tenant, Set<User> users, String sessionId, Long adminRoleId, boolean addInLinkedSystem) {
        List<IAMUserRoleMapDTO> userRoleMapDTOList = new ArrayList<>();
        if (addInLinkedSystem) {
            Set<Long> userIds = users.stream().map(User::getId).collect(Collectors.toSet());
            Set<UserRoleMapDTO> userRoleMapDTOs = oboUserFacade.getUserRolesFromLinkedSystem(userIds, sessionId);
            userRoleMapDTOs.forEach(userRoleMapDTO -> userRoleMapDTOList.add(IAMUserRoleMapDTO.builder().id(Long.valueOf(userRoleMapDTO.getId())).userId(Long.valueOf(userRoleMapDTO.getUser().getId())).roleCode(userRoleMapDTO.getRole().getCode()).build()));
        } else {
            users.forEach(user -> userRoleMapDTOList.add(IAMUserRoleMapDTO.builder().id(0L).userId(user.getId()).roleCode(RoleUtil.adminRoleCode(tenant.getTenantLogin())).build()));
        }
        userRoleMapFacade.addUserRoles(userRoleMapDTOList, adminRoleId, tenant.getTenantLogin());
    }


    public void addTenantMetaInformation(Tenant tenant, String appContext, String marketContext, String referralCode) {
        addTenantMetaData(tenant, appContext);
        addAppAssociation(tenant, appContext, true, marketContext, referralCode);
    }

    public void addTenantMetaData(Tenant tenant, String appContext) {
        List<TenantMetaMaster> tenantMetaMasterList = tenantMetaMasterService.getTenantMetaMasterForAppContext(appContext);
        tenantMetaMasterList.forEach(tenantMetaMaster -> insertMetaDataValues(tenantMetaMaster, tenant));
    }

    public void addAppAssociation(Tenant tenant, String appContext, boolean isCompleted, String marketContext, String referralCode) {
        TenantAppAssociation tenantAppAssociation = tenantOnBoardUtil.buildTenantAppAssociation(tenant, appContext,
                isCompleted, marketContext,referralCode);
        tenantAppAssociationService.save(tenantAppAssociation);
    }

    public void insertMetaDataValues(TenantMetaMaster tenantMetaMaster, Tenant tenant) {
        switch (tenantMetaMaster.getMetaCode()) {
            case TenantMetaConstant.META_MASTER_ROLE_CODES:
                createTenantRoles(tenantMetaMaster.getMetaDefaultValues());
                break;
            case TenantMetaConstant.META_MASTER_DATA_SET_GROUPS:
                addTenantToDataSetGroups(tenantMetaMaster.getMetaDefaultValues(), tenant);
                break;
            default:
                copyDefaultMeta(tenantMetaMaster);
                break;
        }
    }

    public void createTenantRoles(Set<String> metaDefaultValues) {
        metaDefaultValues.forEach(role -> roleFacade.addRole(RoleUtil.role(role), null, null, null));
    }

    public void addTenantToDataSetGroups(Set<String> metaDefaultValues, Tenant tenant) {
        metaDefaultValues.forEach(dataSetGroupCode -> tenantOnBoardUtil.addDataSetGroupPermission(tenant.getId(), tenant.getTenantName(), dataSetGroupCode));
    }

    public void copyDefaultMeta(TenantMetaMaster tenantMetaMaster) {
        tenantMetaMasterService.saveTenantMetaMaster(tenantMetaMaster);
    }

    private TenantAppAssociation processTenantAppAssociation(SolutionSubscriptionDTO dto, String baseDomainForMail) {
        TenantAppAssociation association = tenantAppAssociationService.getByAppContextAndTenantId(dto.getAppContext(),
                PlatformSecurityUtil.getCurrentTenantId());
        association.getStepStatus().clear();
        association.setSolutionSubscriptionCode(dto.getSolutionSubscriptionCode());
        association.setAppDomainURL(baseDomainForMail);
        association.setIsComplete(Boolean.TRUE);
        tenantAppAssociationService.save(association);
        return association;
    }


    private TenantVO mapTenantToVO(Tenant tenant) {
        return TenantVO.builder().id(tenant.getId()).login(tenant.getTenantLogin()).name(tenant.getTenantName()).domainURL(tenant.getTenantDomain()).build();
    }

    public Set<MarketConfigWithTenantsDTO> getMarketConfigsWithTenantsLinkedToTenantIds(Set<Long> tenantIds, String appContext, Map<String, String> httpHeaders) {
        String partnerStatus = MarketConstant.PARTNER_ASSOCIATION_STATUS_APPROVE;
        if (tenantIds == null) {
            return new HashSet<>();
        }
        // Fetch market configurations and linked partner information
        Set<MarketConfigDTO> marketConfigSet = opportunityClient.getAllMarketConfigsAndPartnerLinkedToTenantIds(
                tenantIds, appContext, partnerStatus, httpHeaders).getBody();
        marketConfigSet = marketConfigSet == null ? new HashSet<>() : marketConfigSet;

        // Fetch TenantDTO map for all relevant tenant IDs
        Map<Long, TenantDTO> tenantDTOMap = collectTenantDTOMap(tenantIds, marketConfigSet);

        // Build the response set of MarketConfigWithTenantsDTO
        return buildMarketConfigWithTenantsDTOSet(marketConfigSet, tenantIds, tenantDTOMap);
    }

    private Map<Long, TenantDTO> collectTenantDTOMap(Set<Long> tenantIds, Set<MarketConfigDTO> marketConfigSet) {
        Set<Long> tenantIdsCombined = new HashSet<>();
        if (tenantIds != null && marketConfigSet != null) {
            Set<Long> tenantIdsOfMarketGovernors = marketConfigSet.stream()
                    .map(MarketConfigDTO::getGovernorTenant)
                    .collect(Collectors.toSet());
            tenantIdsCombined.addAll(tenantIds);
            tenantIdsCombined.addAll(tenantIdsOfMarketGovernors);
        }
        return getTenantDTOMapByTenantIds(tenantIdsCombined);
    }

    private Set<MarketConfigWithTenantsDTO> buildMarketConfigWithTenantsDTOSet(Set<MarketConfigDTO> marketConfigSet, Set<Long> tenantIds,
                                                                               Map<Long, TenantDTO> tenantDTOMap) {
        Set<MarketConfigWithTenantsDTO> marketConfigWithTenantsDTOSet = new HashSet<>();
        for (MarketConfigDTO marketConfigDTO : marketConfigSet) {
            MarketConfigWithTenantsDTO marketConfigWithTenantsDTO = new MarketConfigWithTenantsDTO();
            Set<TenantDTO> tenantDTOSet = new HashSet<>();
            TenantDTO governorTenantDTO = tenantDTOMap.getOrDefault(marketConfigDTO.getGovernorTenant(), null);
            if (governorTenantDTO != null) {
                marketConfigWithTenantsDTO.setGovernorTenantName(governorTenantDTO.getTenantName());
                marketConfigWithTenantsDTO.setGovernorTenantLogin(governorTenantDTO.getTenantLogin());
                marketConfigWithTenantsDTO.setMarketLogo(governorTenantDTO.getIconpic());
                marketConfigWithTenantsDTO.setMarketUUID(marketConfigDTO.getMarketUUID());
                marketConfigWithTenantsDTO.setDomainUrl(marketConfigDTO.getMarketAppBaseUrl());
            }
            if (tenantIds.contains(marketConfigDTO.getGovernorTenant()) && tenantDTOMap.containsKey(marketConfigDTO.getGovernorTenant())) {
                tenantDTOSet.add(tenantDTOMap.get(marketConfigDTO.getGovernorTenant()));
            }
            for (MarketPartnerDTO marketPartnerDTO : marketConfigDTO.getMarketPartners()) {
                if (tenantDTOMap.containsKey(marketPartnerDTO.getTenantId())) {
                    tenantDTOSet.add(tenantDTOMap.get(marketPartnerDTO.getTenantId()));
                }
            }
            marketConfigWithTenantsDTO.setTenantSet(tenantDTOSet);
            marketConfigWithTenantsDTOSet.add(marketConfigWithTenantsDTO);
        }
        return marketConfigWithTenantsDTOSet;
    }

    public Map<Long, TenantDTO> getTenantDTOMapByTenantIds(Set<Long> tenantIds) {
        List<TenantDTO> tenantDTOSet = tenantDTOAssembler.toDTOs(tenantService.getTenantByIds(tenantIds));
        return tenantDTOSet.stream()
                .collect(Collectors.toMap(TenantDTO::getId, Function.identity(), (a, b) -> a));
    }

    public TenantDTO createTenantInLinkedSystemAndInitializeAdmin(LeadDTO leadDTO, String adminPassword, Long worldUserId) {
        TenantDTO tenantDTO = oboTenantFacade.addTenantInLinkedSystemVMS(leadDTO, adminPassword,worldUserId);
        TenantOnBoardUtil.addLeadPropertiesToTenant(leadDTO, tenantDTO);
        String userLogin = Objects.equals(leadDTO.getUseMobileAsUserLogin(), Boolean.TRUE) ?
                leadDTO.getMobile() : leadDTO.getEmail();
        oboSecurityUtil.initUserAndSetSecurityContext(userLogin, adminPassword, tenantDTO.getTenantLogin(), null, null, null, null);
        return tenantDTO;
    }

    @Transactional
    @Override
    public TenantDTO getTenantBySlug(String slug) {
        Tenant tenant = tenantService.getTenantBySlug(slug);
        TenantDTO tenantDTO = tenantDTOAssembler.toDTO(tenant);
        if(PlatformUtil.isSession() && !PlatformSecurityUtil.getCurrentTenantId().equals(tenantDTO.getId())) {
            tenantDTO.maskSensitiveInfo();
        }
        return tenantDTO;
    }

    private static TenantPartnerVO userMapToTenantPartnerVO(UserMap userMap) {
        return TenantPartnerVO.builder()
                .name(userMap.getTenantPartner().getTenant().getTenantName())
                .login(userMap.getTenantPartner().getTenant().getTenantLogin())
                .id(userMap.getTenantPartner().getTenant().getId())
                .domainURL(userMap.getTenantPartner().getTenant().getTenantDomain())
                .status(userMap.getTenantPartner().getStatus())
                .isPrimary(userMap.getTenantPartner().getIsPrimary())
                .primaryTenantUserId(userMap.getTargetUser().getId())
                .primaryTenantUserLogin(userMap.getTargetUser().getUserLogin())
                .primaryTenantUserName(userMap.getTargetUser().getFirstName())
                .build();
    }
}
