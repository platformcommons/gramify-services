package com.platformcommons.platform.service.iam.application.utility;

import com.mindtree.bridge.platform.dto.RoleDTO;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.commons.RefDataDTO;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.domain.TenantAppAssociation;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.dto.TenantProfileDTO;
import com.platformcommons.platform.service.iam.facade.client.AppConfigClient;
import com.platformcommons.platform.service.iam.facade.client.DatasetGroupClient;
import com.platformcommons.platform.service.iam.facade.client.TLDClient;
import com.platformcommons.platform.service.report.dto.TenantInfoDTO;
import feign.FeignException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class TenantOnBoardUtil {

    private final DatasetGroupClient datasetGroupClient;
    private final AppConfigClient appConfigClient;
    private final TLDClient tldClient;
    private final OBOSecurityUtil oboSecurityUtil;

    public static void addLeadPropertiesToTenant(LeadDTO leadDTO, TenantDTO tenantDTO) {
        tenantDTO.setIconpic(leadDTO.getOrganisationLogo());
        tenantDTO.setWebsite(leadDTO.getWebsite());
        tenantDTO.setTenantProfile(TenantProfileDTO.builder().id(0L).type(leadDTO.getOrganisationType()).industries(leadDTO.getOrganisationIndustries()).size(leadDTO.getOrganisationSize() == null ? null : RefDataDTO.builder().code(leadDTO.getOrganisationSize()).build()).build());
    }

    public void addDataSetGroupPermission(Long tenantId, String tenantName, String datasetGroupCode) {
        try {
            String sessionID = PlatformSecurityUtil.getToken();
            if(null==sessionID){
                sessionID = oboSecurityUtil.getPrivilegeUserSession();
            }
            datasetGroupClient.addTenantInfoToDatasetGroupCode(datasetGroupCode, TenantInfoDTO.builder().id(0L).tenantId(tenantId).name(tenantName).build(), sessionID);
        } catch (FeignException feignException) {
            if (!(feignException.status() == HttpStatus.CONFLICT.value())) {
                throw new RuntimeException("Unable to create dataset permission");
            }
        }
    }

    public void addDefaultABATenantRoleMarkify(TenantDTO tenantDTO, String sessionId) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleName("ABA");
        roleDTO.setRoleType("TENANT");
        roleDTO.setCode("role." + tenantDTO.getTenantLogin() + ".aba");
        tldClient.createTenantRole(tenantDTO.getId(), roleDTO, sessionId);
    }

    public TenantAppAssociation buildTenantAppAssociation(Tenant tenant, String appContext, boolean isCompleted, String marketContext, String referralCode) {
        return TenantAppAssociation.builder().id(null).isComplete(isCompleted).stepStatus(new LinkedHashMap<>()).appContext(appContext).marketContext(marketContext).tenant(tenant).referralCode(referralCode).build();
    }

    public static String tenantLogin(String name) {
        log.debug("==>> Generating TenantLogin...");
        name = name.replaceAll("[^a-zA-Z0-9\\- ]", "");
        String tenantLogin = name.toLowerCase(Locale.ROOT).replace(" ", "_");
        if (tenantLogin.length() > 40) {
            tenantLogin = getInitials(name);
        }
        if (tenantLogin.length() <= 3) {
            tenantLogin += "-" + getRandomTwoDigitNumber();
        }
        log.debug("===> Generated Login : {}", tenantLogin);
        return  tenantLogin;
    }

    private static  String getInitials(String name) {
        StringBuilder initials = new StringBuilder();
        for (String part : name.split(" ")) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
            }
        }
        String initialLogin= initials.toString().toLowerCase(Locale.ROOT);
        if(initialLogin.length() > 40){
            throw  new InvalidInputException("TenantLogin could not be casted to  40 characters");
        }
        return  initialLogin;
    }

    private static String getRandomTwoDigitNumber() {
        return String.format("%02d", new Random().nextInt(100));
    }

    public void addTenantSubscription(String sourceTenantLogin, Set<String> roleCodeSet) {
        try {
            tldClient.addTenantSubscription(sourceTenantLogin,roleCodeSet,PlatformSecurityUtil.getToken());
        } catch( Exception e) {
            log.debug(String.format("Tenant Subscription could not be created for tenant with id %d . Error -%s",
                    PlatformSecurityUtil.getCurrentTenantId(),e.getMessage()));
        }
    }

}
