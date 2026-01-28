package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.AppDetail;

import java.util.Set;

public interface AppDetailsService {

    AppDetail createAppDetails(AppDetail appDetail);

    AppDetail getAppDetails(String apiKey, String appCode);

    AppDetail addOpenApisToAppDetails(Long appDetailsId, Set<String> openApis);

    AppDetail getSessionByApiKey(String apiKey, String tenantLogin);
}
