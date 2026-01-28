package com.platformcommons.platform.service.iam.facade.client.utility;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.utility.PlatformUtil;
import com.platformcommons.platform.service.iam.dto.*;
import com.platformcommons.platform.service.iam.facade.client.DatasetClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CommonsReportUtil {

    @Autowired
    private DatasetClient datasetClient;

    @Autowired
    private PlatformUtil platformUtil;

    public List<RefDataCodeAndLabelDTO> getRefDataCodeFromLabel(String label,List<String> refDataClassCodes) {
        StringBuilder params = new StringBuilder();
        String refDataClassCodesString = StringUtils.join(refDataClassCodes,',');
        params.append("IN_PARAM_CLASS_CODES=").append(refDataClassCodesString).append("--");
        params.append("LABEL=").append(label);
        return datasetClient.getRefDataCodeFromLabel(PlatformSecurityUtil.getToken(),
                                                            params.toString()).getBody();
    }

    public List<UserDetailsDTO> getUserDetailsByUserIdsInBulk(Set<Long> userIds) {
        String userIdsString = org.apache.commons.lang.StringUtils.join(userIds,",");
        StringBuilder params = new StringBuilder();
        params.append("IN_PARAM_USER_ID=").append(userIdsString);
        return datasetClient.getUserDetailsInBulk(PlatformSecurityUtil.getToken(),
                params.toString()).getBody();
    }

    public List<UserPersonProfileDTO> getPersonAndPersonProfileIds(Long userId) {
        StringBuilder params = new StringBuilder();
        params.append("USER_ID=").append(userId);
        return datasetClient.getPersonAndPersonProfileIds(PlatformSecurityUtil.getToken(),
                params.toString()).getBody();
    }

    public List<UserRoleDTO> getUserRoleByRoleCode(String roleCode, Long userId) {
        StringBuilder params = new StringBuilder();
        params.append("USER_ID=").append(userId).append("--");
        params.append("ROLE_CODE=").append(roleCode);
        return datasetClient.getUserRoleByRoleCode(PlatformSecurityUtil.getToken(),
                params.toString()).getBody();
    }

    public TenantLoginVO validateTenantAndUserLogin(String userLogin, String tenantLogin, String appKey) {
        StringBuilder params = new StringBuilder();
        params.append("USER_LOGIN=").append(userLogin).append("--");
        params.append("TENANT_LOGIN=").append(tenantLogin);
        List<TenantLoginVO> tenantLoginVOS = datasetClient.getUserLoginV2AppKey(params.toString(), 0, 1, appKey).getBody() ;
        if (tenantLoginVOS == null || tenantLoginVOS.isEmpty()) {
            throw new NotFoundException("User not authorize to Login") ;
        }
        return tenantLoginVOS.get(0);
    }

    public UserTenantCheckDTO computeTenantForUserLogin(String userLogin, String primaryTenantLogin,String crossTenantLogin) {
        UserTenantCheckDTO result = null;
        StringBuilder params = new StringBuilder();
        params.append("USER_LOGIN=").append(userLogin)
                .append("--").append("CROSS_TENANT_LOGIN=").append(crossTenantLogin)
                .append("--").append("PRIMARY_TENANT_LOGIN=").append(primaryTenantLogin);
        List<UserTenantCheckDTO> userTenantCheckDTOList = datasetClient.computeTenantForUserLogin(params.toString(), 0, 1, platformUtil.getAdminAppKey()).getBody() ;
        if(userTenantCheckDTOList != null) {
            result = userTenantCheckDTOList.stream()
                    .findFirst()
                    .orElse(null);
        }
        return result;
    }

    public Boolean checkForUserActivation(Long userId) {
        Boolean result = Boolean.FALSE;
        if(userId != null) {
            String params = "USER_ID="+userId;
            List<Map<String,Object>> response = datasetClient.checkForUserActivation(platformUtil.getAdminAppKey(),params,0,0).getBody();
            if(response != null && !response.isEmpty()) {
                result = response.stream()
                        .map(it->it.getOrDefault("isActivated",null))
                        .map(it -> it instanceof Boolean ? (Boolean) it : Boolean.FALSE)
                        .findFirst()
                        .orElse(Boolean.FALSE);
            }
        }
        return result;
    }

    public Set<Long> getTenantIdsByUserLogin(String userLogin) {
        Set<Long> tenantIdSet = new HashSet<>();
        if(userLogin != null) {
            String params = "USER_LOGIN="+userLogin;
            List<TenantIdDTO> response = datasetClient.getTenantIdsByUserLogin(platformUtil.getAdminAppKey(),params,0,0).getBody();
            if(response != null && !response.isEmpty()) {
                tenantIdSet = response.stream()
                        .map(TenantIdDTO::getTenantId)
                        .collect(Collectors.toSet());
            }
        }
        return tenantIdSet;
    }

    public Map<String,String> getTenantAdminNameAndEmail(String tenantLogin) {
        Map<String,String> result = new HashMap<>();
        if(tenantLogin != null) {
            String params = "TENANT_LOGIN="+tenantLogin;
            List<Map<String,String>> response = datasetClient.getTenantAdminNameAndEmail(platformUtil.getAdminAppKey(),params,0,0).getBody();
            if(response != null && !response.isEmpty()) {
                for (Map<String, String> map : response) {
                    result.put(map.get("name"),map.get("email"));
                }
            }
        }
        return result;
    }

    public Boolean checkIfUserExistsInTenant(String userLogin,String tenantLogin) {
        Boolean result = Boolean.FALSE;
        if(tenantLogin != null && userLogin != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TENANT_LOGIN=").append(tenantLogin)
                    .append("--").append("USER_LOGIN=").append(userLogin);
            List<Map<String,Object>> response = datasetClient.checkIfUserExistsInTenant(platformUtil.getAdminAppKey(),stringBuilder.toString(),0,0).getBody();
            if(response != null && !response.isEmpty()) {
                result = response.stream()
                        .map(it->it.getOrDefault("userExists",null))
                        .map(it -> {
                            if (it instanceof Boolean) {
                                return (Boolean) it;
                            } else if (it instanceof Integer) {
                                return (Integer) it == 1;
                            }
                            return Boolean.FALSE;
                        })                        .findFirst()
                        .orElse(Boolean.FALSE);
            }
        }
        return result;
    }
}
