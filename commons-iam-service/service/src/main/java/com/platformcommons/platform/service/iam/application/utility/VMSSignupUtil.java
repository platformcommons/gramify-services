package com.platformcommons.platform.service.iam.application.utility;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.app.dto.AppConfigDTO;
import com.platformcommons.platform.service.iam.application.constant.IAMConstant;
import com.platformcommons.platform.service.iam.application.constant.TenantConfigConstant;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.domain.TenantAppAssociation;
import com.platformcommons.platform.service.iam.domain.TenantMetaAdditionalProperty;
import com.platformcommons.platform.service.iam.domain.TenantMetaMaster;
import com.platformcommons.platform.service.iam.dto.*;
import com.platformcommons.platform.service.iam.facade.client.AppConfigClient;
import com.platformcommons.platform.service.iam.facade.client.OpportunityClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class VMSSignupUtil {
    public static final String BETTER_TOGETHER_SYSTEMS_CHANGE_LEADER = "BETTER_TOGETHER.SYSTEMS_CHANGE_LEADER";
    public static final String BETTER_TOGETHER_VMS_MODEL = "BETTER_TOGETHER.VMS_MODEL";
    public static final String BETTER_TOGETHER_TRAILBLAZER = "BETTER_TOGETHER.TRAILBLAZER";
    public static final String BETTER_TOGETHER_OPERATION_TENANT_SELF_SIGN_UP = "BETTER_TOGETHER.OPERATION.TENANT_SELF_SIGN_UP";
    public static final String BETTER_TOGETHER_OPERATION_USER_SELF_SIGN_UP = "BETTER_TOGETHER.OPERATION.USER_SELF_SIGN_UP";
    protected static final Map<String, List<String>> VMS_SUBSCRIPTION_OPERATION_MODEL;

    static {
        Map<String, List<String>> tempMap = new HashMap<>();
        tempMap.put(BETTER_TOGETHER_VMS_MODEL, new ArrayList<>());
        tempMap.put(BETTER_TOGETHER_TRAILBLAZER, Collections.singletonList(BETTER_TOGETHER_OPERATION_USER_SELF_SIGN_UP));
        tempMap.put(BETTER_TOGETHER_SYSTEMS_CHANGE_LEADER, Arrays.asList(BETTER_TOGETHER_OPERATION_TENANT_SELF_SIGN_UP, BETTER_TOGETHER_OPERATION_USER_SELF_SIGN_UP));
        VMS_SUBSCRIPTION_OPERATION_MODEL = tempMap;
    }

    private final OpportunityClient opportunityClient;
    private final AppConfigClient appConfigClient;
    private final TenantOnBoardUtil tenantOnBoardUtil;

    @Value("${commons.platform.service.vms.changemaker-app-version-id:1}")
    public Long changemakerAppVersionId;
    @Value("${commons.platform.service.vms.changemaker-admin-version-id:3}")
    public Long changemakerAdminAppVersionId;
    @Value("${commons.platform.service.vms.iam-app-version-id:4}")
    public Long iamAppVersionId;
    @Value("${commons.platform.config.ops-support.login:bridgeopssupport}")
    public String opsSupportLogin;
    @Value("${commons.platform.config.ops-support.password:password1}")
    public String opsSupportPassword;

    public MarketConfigDTO createMarketConfigDTO(String solutionSubscriptionCode, Long tenantId, String tenantLogin,
                                                 String appContext, String domainUrl, Long sourceMarketConfigId) {
        return MarketConfigDTO.builder()
                .id(0L)
                .governorTenant(tenantId)
                .governorTenantLogin(tenantLogin)
                .solutionSubscriptionCode(solutionSubscriptionCode)
                .appContext(appContext)
                .marketAppBaseUrl(domainUrl)
                .sourceMarketConfigId(sourceMarketConfigId)
                .marketUUID(convertToMarketUUIDWithTenantLogin(tenantLogin))
                .build();
    }

    public static void marketConfigOperationAccessible(String solutionSubscriptionCode, String marketUUID, String operationCode) {
        if (solutionSubscriptionCode == null) {
            throw new NotFoundException(String.format("Subscription Code not present for market config with UUID %s", marketUUID));
        }

        List<String> operationList = VMS_SUBSCRIPTION_OPERATION_MODEL.getOrDefault(solutionSubscriptionCode, Collections.emptyList());
        if (!operationList.contains(operationCode)) {
            throw new NotFoundException(String.format("Operation of code %s is not added for the market with UUID %s",
                    operationCode, marketUUID));
        }
    }

    private String convertToMarketUUIDWithTenantLogin(String tenantLogin) {
        int currentYear = LocalDate.now().getYear();
        return "COMMONS-" + tenantLogin.toUpperCase() + "-" + currentYear;
    }

    public MarketConfigDTO fetchMarketConfig(String marketUUID) {
        MarketConfigDTO marketConfigDTO = null;
        marketConfigDTO = opportunityClient.getByMarketUUID(marketUUID,null).getBody();
        if (marketConfigDTO == null) {
            throw new NotFoundException(String.format("Market with uuid %s not found", marketUUID));
        }
        return marketConfigDTO;
    }

    public void handleTrailblazerOrSystemLeaderSubscriptionCode(SolutionSubscriptionDTO solutionSubscriptionDTO,
                                                                MarketConfigDTO ownerMarketConfigDTO) {
        if (StringUtils.isEmpty(solutionSubscriptionDTO.getDomainURL())) {
            throw new InvalidInputException("Domain must not be null");
        }
        if (Boolean.FALSE.equals(ownerMarketConfigDTO.getMarketCreationAllowed())) {
            throw new UnAuthorizedAccessException("New market creation is not allowed for the current market");
        }
        MarketConfigDTO marketConfigDTO = createMarketConfigDTO(solutionSubscriptionDTO.getSolutionSubscriptionCode(),
                PlatformSecurityUtil.getCurrentTenantId(), PlatformSecurityUtil.getCurrentTenantLogin(),
                solutionSubscriptionDTO.getAppContext(), solutionSubscriptionDTO.getDomainURL(),
                ownerMarketConfigDTO.getId());
        opportunityClient.save(marketConfigDTO, PlatformSecurityUtil.getToken());
        addVMSAppConfig(marketConfigDTO.getMarketUUID(), extractDomain(solutionSubscriptionDTO.getDomainURL()), PlatformSecurityUtil.getCurrentTenantLogin());
        addMarketDefaultAppRbac(marketConfigDTO.getMarketUUID(),IAMConstant.CM_ADMIN);
        addMarketDefaultAppRbac(marketConfigDTO.getMarketUUID(),IAMConstant.IAM);
    }

    private void addMarketDefaultAppRbac(String marketUUID,String appCode) {
        try{
            appConfigClient.getOrCreatePrimaryRbacByAppCodeAndMarketContext(appCode,marketUUID,PlatformSecurityUtil.getToken());
        } catch(Exception e) {
            log.debug(e.getMessage());
        }
    }

    public void handleVmsModelSubscriptionCode(SolutionSubscriptionDTO solutionSubscriptionDTO, MarketConfigDTO marketConfigDTO) {
        String marketGovernorTenantLogin = marketConfigDTO.getGovernorTenantLogin();
        Set<String> roleCodeSet = Collections.singleton("PROLE.VOLUNTEER");
        MarketPartnerDTO marketPartnerDTO = createMarketPartnerDTO(solutionSubscriptionDTO.getSolutionSubscriptionCode(),
                PlatformSecurityUtil.getCurrentTenantId(),PlatformSecurityUtil.getCurrentTenantLogin());
        opportunityClient.addMarketPartnerToMarketByMarketUUID(marketPartnerDTO, marketConfigDTO.getMarketUUID(), PlatformSecurityUtil.getToken());
        tenantOnBoardUtil.addTenantSubscription(marketGovernorTenantLogin,roleCodeSet);
    }



    private MarketPartnerDTO createMarketPartnerDTO(String solutionSubscriptionCode, Long tenantId,String tenantLogin) {
        return MarketPartnerDTO.builder()
                .id(0L)
                .tenantId(tenantId)
                .tenantLogin(tenantLogin)
                .solutionSubscriptionCode(solutionSubscriptionCode)
                .build();
    }

    public SolutionSubscriptionDTO buildSolutionDTO(String appContext, SignUpRequestDTO signUpRequestDTO) {
        return SolutionSubscriptionDTO.builder()
                .appContext(appContext)
                .solutionSubscriptionCode(BETTER_TOGETHER_VMS_MODEL)
                .domainURL(signUpRequestDTO.getDomainURL())
                .defaultMarketUUID(signUpRequestDTO.getDefaultMarketUUID())
                .build();
    }

    public void validateSolutionSubscriptionInput(SolutionSubscriptionDTO dto) {
        if (StringUtils.isEmpty(dto.getDomainURL()) && StringUtils.isEmpty(dto.getDefaultMarketUUID())) {
            throw new InvalidInputException("Either of domainURL or defaultMarketUUID should be used");
        }
    }

    public String processMarketConfiguration(SolutionSubscriptionDTO dto, TenantMetaMaster defaultUSerRegistrationStatus) {
        validateSolutionSubscriptionInput(dto);
        String domain = dto.getDomainURL();
        boolean isPartnerOnBoardEnabled = isPartnerOnboardingEnabled(dto);
        if (!StringUtils.isEmpty(domain) && StringUtils.isEmpty(dto.getDefaultMarketUUID())) {
            return addNewMarketAndAddConfigForMarket(domain, isPartnerOnBoardEnabled);
        } else {
            return addPartnerToExistingMarket(dto, defaultUSerRegistrationStatus);
        }
    }

    private String addPartnerToExistingMarket(SolutionSubscriptionDTO dto, TenantMetaMaster defaultUSerRegistrationStatus) {
        addVMSMarketPartnerToMarketConfig(dto.getDefaultMarketUUID(), (PlatformSecurityUtil.getCurrentTenantId()));
        return defaultUSerRegistrationStatus.getMetaDefaultValues().stream().findFirst().orElse(null);
    }

    public void sendWelcomeEmail(TenantAppAssociation association, String baseDomainForMail) {
    }

    private String addNewMarketAndAddConfigForMarket(String domain, boolean isPartnerOnBoardEnabled) {
        String marketUUID = addVMSMarketConfig(isPartnerOnBoardEnabled);
        addVMSAppConfig(marketUUID, extractDomain(domain), PlatformSecurityUtil.getCurrentTenantLogin());
        return domain;
    }

    public void triggerWelcomeMail(TenantAppAssociation tenantAppAssociation, String baseDomainForMail,
                                   TenantMetaMaster sendWelcomeMailMeta, TriggerNotification triggerNotification) {

        if (sendWelcomeMailMeta != null && sendWelcomeMailMeta.getMetaDefaultValues().stream().findFirst().isPresent()
                && Boolean.parseBoolean(sendWelcomeMailMeta.getMetaDefaultValues().stream().findFirst().get())) {
            Tenant tenant = tenantAppAssociation.getTenant();
            triggerNotification.sendTenantCreationMail(tenantAppAssociation.getAppContext(), PlatformSecurityUtil.getCurrentUserName(),
                    tenant.getTenantName(), tenant.getEmail(), tenant.getTenantLogin(), baseDomainForMail);
            sendWelcomeEmail(tenantAppAssociation, baseDomainForMail);
        }
    }

    public void addVMSAppConfig(String marketUUID, String scopeValue, String tenantLogin) {
        addVMSAppConfigToChangemakerApp(marketUUID,scopeValue,tenantLogin);
        addVMSAppConfigToAdminApp(marketUUID,scopeValue,tenantLogin);
        addVMSAppConfigToIAMApp(marketUUID,scopeValue,tenantLogin);
    }

    public void addVMSAppConfigToChangemakerApp(String marketUUID, String scopeValue, String tenantLogin) {
        Set<AppConfigDTO> appConfigSet = new HashSet<>();
        appConfigSet.add(AppConfigDTO.builder()
                .id(0L)
                .configKey("CHANGEMAKER_APP_CONFIG.MARKET_UUID")
                .configValue(marketUUID)
                .scopeCode("TENANT")
                .scopeValue(scopeValue)
                .configType("CONFIG")
                .build());
        appConfigSet.add(AppConfigDTO.builder()
                .id(0L)
                .configKey("CHANGEMAKER_APP_CONFIG.CREATE_LINKED_USER_FOR_TENANT")
                .configValue(tenantLogin)
                .scopeCode("TENANT")
                .scopeValue(scopeValue)
                .configType("CONFIG")
                .build());
        appConfigClient.postApiV1AppConfigAdd(changemakerAppVersionId, appConfigSet, PlatformSecurityUtil.getToken());
    }

    public void addVMSAppConfigToIAMApp(String marketUUID, String scopeValue, String tenantLogin) {
        Set<AppConfigDTO> appConfigSet = new HashSet<>();
        appConfigSet.add(AppConfigDTO.builder()
                .id(0L)
                .configKey("CHANGEMAKER_IAM_APP_CONFIG.MARKET_UUID")
                .configValue(marketUUID)
                .scopeCode("TENANT")
                .scopeValue(scopeValue)
                .configType("CONFIG")
                .build());
        appConfigSet.add(AppConfigDTO.builder()
                .id(0L)
                .configKey("CHANGEMAKER_IAM_APP_CONFIG.TENANT_LOGIN")
                .configValue(tenantLogin)
                .scopeCode("TENANT")
                .scopeValue(scopeValue)
                .configType("CONFIG")
                .build());
        appConfigClient.postApiV1AppConfigAdd(iamAppVersionId, appConfigSet, PlatformSecurityUtil.getToken());
    }

    public void addVMSAppConfigToAdminApp(String marketUUID, String scopeValue, String tenantLogin) {
        Set<AppConfigDTO> appConfigSet = new HashSet<>();
        appConfigSet.add(AppConfigDTO.builder()
                .id(0L)
                .configKey("CHANGEMAKER_ADMIN_APP_CONFIG.MARKET_UUID")
                .configValue(marketUUID)
                .scopeCode("TENANT")
                .scopeValue(scopeValue)
                .configType("CONFIG")
                .build());

        appConfigSet.add(AppConfigDTO.builder()
                .id(0L)
                .configKey("CHANGEMAKER_ADMIN_APP_CONFIG.TENANT_LOGIN")
                .configValue(tenantLogin)
                .scopeCode("TENANT")
                .scopeValue(scopeValue)
                .configType("CONFIG")
                .build());
        appConfigClient.postApiV1AppConfigAdd(changemakerAdminAppVersionId, appConfigSet, PlatformSecurityUtil.getToken());
    }

    public String extractDomain(String url) {
        String pattern = "(?:https?://)?(?:www\\.)?([a-zA-Z0-9.-]+)\\b";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(url);
        if (m.find()) {
            return m.group(1);
        } else {
            return url;
        }
    }

    public String addVMSMarketConfig(Boolean isPartnerSignupAllowed) {
        MarketConfigDTO marketConfigDTO = MarketConfigDTO.builder()
                .id(0L)
                .governorTenant(PlatformSecurityUtil.getCurrentTenantId())
                .marketPartners(Collections.emptySet())
                .marketUUID(convertToMarketUUIDWithTenantLogin(PlatformSecurityUtil.getCurrentTenantLogin()))
                .build();
        opportunityClient.save(marketConfigDTO, PlatformSecurityUtil.getToken());
        return marketConfigDTO.getMarketUUID();

    }

    public void addVMSMarketPartnerToMarketConfig(String marketUUId, Long tenantId) {
        MarketPartnerDTO marketPartnerDTO = MarketPartnerDTO.builder()
                .id(0L)
                .tenantId(tenantId)
                .build();
        opportunityClient.addMarketPartnerToMarketByMarketUUID(marketPartnerDTO, marketUUId,
                PlatformSecurityUtil.getToken());
    }

    public boolean isPartnerOnboardingEnabled(SolutionSubscriptionDTO dto) {
        return dto.getSolutionSubscriptionCode() != null &&
                dto.getSolutionSubscriptionCode().equals(BETTER_TOGETHER_SYSTEMS_CHANGE_LEADER);
    }

    public static Set<TenantMetaAdditionalProperty> createDefaultTenantAdditionalPropertyOnSignUpVMS(TenantDTO tenantDTO,String appContext) {
        List<TenantMetaAdditionalProperty> list = new ArrayList<>();

        list.add(TenantMetaAdditionalProperty.createPropertyObjectForSingleValue(TenantConfigConstant.CONFIG_KEY_USER_VERIFICATION_REQUIRED,"1",appContext));
        list.add(TenantMetaAdditionalProperty.createPropertyObjectForSingleValue(TenantConfigConstant.CONFIG_KEY_WORKFORCE_LINKED_APPLICANTS_FILTER,"1",IAMConstant.DEFAULT_APP_CONTEXT));
        list.add(TenantMetaAdditionalProperty.createPropertyObjectForSingleValue(TenantConfigConstant.CONFIG_KEY_COMMUNITY_LINKED_APPLICANT_FILTER,"1",IAMConstant.DEFAULT_APP_CONTEXT));
        list.add(TenantMetaAdditionalProperty.createPropertyObjectForSingleValue(TenantConfigConstant.CONFIG_KEY_SUPERVISOR_LINKED_APPLICANT_FILTER,"1",IAMConstant.DEFAULT_APP_CONTEXT));

        if(!StringUtils.isBlank(tenantDTO.getEmail())) {
            list.add(TenantMetaAdditionalProperty.createPropertyObjectForMultiValues(TenantConfigConstant.CONFIG_KEY_TENANT_SUPPORT_EMAILS,
                    tenantDTO.getEmail(),",",IAMConstant.DEFAULT_APP_CONTEXT));
        }
        if(!StringUtils.isBlank(tenantDTO.getMobile())) {
            list.add(TenantMetaAdditionalProperty.createPropertyObjectForMultiValues(TenantConfigConstant.CONFIG_KEY_TENANT_SUPPORT_CONTACT_NUMBERS,
                    tenantDTO.getMobile(),",",IAMConstant.DEFAULT_APP_CONTEXT));
        }

        return new HashSet<>(list);
    }

}
