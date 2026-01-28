package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.security.filter.appkey.AppDetailsDTO;
import com.platformcommons.platform.service.iam.dto.AppDetailDTO;

import java.util.Set;

public interface AppDetailsFacade {

    AppDetailDTO createAppDetails(AppDetailDTO appDetailDTO);

    AppDetailsDTO getAppDetails(String apiKey,String appCode);

    void addOpenApisToAppDetails(Long appDetailsId, Set<String> openApis);

    AppDetailDTO getSessionByApiKey(String apiKey, String tenantLogin);
}
