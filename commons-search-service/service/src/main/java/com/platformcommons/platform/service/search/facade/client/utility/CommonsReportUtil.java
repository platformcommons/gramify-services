package com.platformcommons.platform.service.search.facade.client.utility;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.search.dto.GlobalRefDataDTO;
import com.platformcommons.platform.service.search.dto.MapLocationCoordinateDTO;
import com.platformcommons.platform.service.search.dto.RefDataCodeAndLabelDTO;
import com.platformcommons.platform.service.search.dto.UserIdInfoDTO;
import com.platformcommons.platform.service.search.facade.client.CommonsReportClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CommonsReportUtil {

    @Autowired
    private CommonsReportClient client;

    @Value("${commons.platform.cache.appkey:Appkey YXBwS2V5OjdiMDNlMWJlLTkwMjctNDMwNC05YjRjLTlkMTdiZmI4NTM2NyxhcHBDb2RlOkNPTU1PTlNfQVBQLkNIQU5HRU1BS0VS}")
    private String appKey;

    public Set<String> getRoleCodesByUserId(Long userId, Long tenantId) {
        Set<String> roleCodes = null;
        if(userId != null && tenantId != null) {
            StringBuilder params = new StringBuilder();
            params.append("TENANT_ID=").append(tenantId).append("--")
                    .append("USER_ID=").append(userId);
            List<Map<String,String>> roleCodesMapList =  client.getRoleCodesByUserId(appKey, params.toString(),0,0).getBody();
            if(roleCodesMapList != null && !roleCodesMapList.isEmpty()) {
                roleCodes = roleCodesMapList.stream()
                        .map(Map::values)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
            }
        }
        return roleCodes;
    }

    public Map<String, String> getUserDetailsForOpportunityRecommendation(Long userId, Set<String> opportunityTypes) {
        Map<String, String> userDetails = null;
        if(userId != null) {
            StringBuilder params = new StringBuilder();
            params.append("IN_PARAM_OPPORTUNITY_TYPE=").append(String.join(",", opportunityTypes)).append("--")
                    .append("USER_ID=").append(userId);
            List<Map<String,String>> response = client.getUserBasicDetailsByUserId(appKey, params.toString(), 0, 0).getBody();
            if(response != null && !response.isEmpty()) {
                userDetails = response.stream()
                        .findFirst().orElse(null);
            }
        }
        return userDetails;
    }

    public Set<String> getChangemakerRefDataLabelsFromCodes(Set<String> dataCodes) {
        Set<String> labels = null;
        if(dataCodes != null && !dataCodes.isEmpty()) {
            String params = "IN_PARAM_DATACODES=" +String.join(",",dataCodes);
            List<RefDataCodeAndLabelDTO> addressLabelAndCodeList = client.getLabelFromChangemakerRefDataCodes(appKey, params,0,0).getBody();
            labels = getLabelsFromRefData(addressLabelAndCodeList);
        }
        return labels;
    }


    private static Set<String> getLabelsFromRefData(List<RefDataCodeAndLabelDTO> addressLabelAndCodeList) {
        Set<String> labels = null;
        if(addressLabelAndCodeList != null && !addressLabelAndCodeList.isEmpty()) {
            labels = addressLabelAndCodeList.stream()
                    .map(RefDataCodeAndLabelDTO::getLabel)
                    .collect(Collectors.toSet());
        }
        return labels;
    }

    public Map<String, String> getWorkforceDetailsForUser(Long userId) {
        Map<String,String> result = null;
        if(userId != null) {
            String params = "USER_ID="+userId;
            List<Map<String,String>> response = client.getWorkForceDetailsByUserId(appKey,params,0,0).getBody();
            if(response != null && !response.isEmpty()) {
                result = response.stream()
                        .findFirst()
                        .orElse(null);
            }
        }
        return result;
    }

    public Set<Long> getUserIdsByProgramCodes(Set<String> userProgramCodes) {
        Set<Long> result = null;

        if (userProgramCodes == null || userProgramCodes.isEmpty()) {
            return null;
        }

        String params = "IN_PARAM_PROGRAM_CODES=" + String.join(",", userProgramCodes);
        List<UserIdInfoDTO> userIdInfoDTOList = client.getUserIdsByUserProgramCodes(appKey, params, 0, 0).getBody();;
        if(userIdInfoDTOList != null) {
            result = userIdInfoDTOList.stream()
                    .map(UserIdInfoDTO::getUserId)
                    .collect(Collectors.toSet());
        }

        return result;
    }

    public Boolean checkIfUserHasGivenRoleSuffixGlobally(Long userId, Set<String> roleSuffixCodes) {
        if (userId == null || CollectionUtils.isEmpty(roleSuffixCodes)) {
            return false;
        }

        String joinedSuffixes = roleSuffixCodes.stream()
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));

        String params = String.format("ROLE_CODES_SUFFIX=%s--USER_ID=%d", joinedSuffixes, userId);

        List<Map<String,String>> roleExistsMap = client
                .checkIfUserHasGivenRoleSuffixGlobally(PlatformSecurityUtil.getToken(), params)
                .getBody();

        // Return true if "hasRole" == "1"
        return roleExistsMap != null &&
                !roleExistsMap.isEmpty() &&
                Objects.equals(roleExistsMap.stream().findFirst().get().get("hasRole"), "1");
    }


    public GlobalRefDataDTO getGlobalRefDataFromCode(String code) {
        GlobalRefDataDTO globalRefDataDTO = null;
        if(code != null) {
            globalRefDataDTO =  getGlobalRefDataFromCodeInBulk(Collections.singleton(code))
                    .stream()
                    .findFirst()
                    .orElseThrow(()-> new NotFoundException(String.format("Global RefData for code %s not found",code)));
        }
        return globalRefDataDTO;
    }

    public List<GlobalRefDataDTO> getGlobalRefDataFromCodeInBulk(Set<String> dataCodes) {
        List<GlobalRefDataDTO> globalRefDataDTOSet = new ArrayList<>();
        if(dataCodes != null && !dataCodes.isEmpty()) {
            String params = "IN_PARAM_DATACODES=" +String.join(",",dataCodes);
            globalRefDataDTOSet = client.getGlobalRefDataByCodeInBulk(appKey, params,0,0).getBody();
        }
        return globalRefDataDTOSet;
    }

}
