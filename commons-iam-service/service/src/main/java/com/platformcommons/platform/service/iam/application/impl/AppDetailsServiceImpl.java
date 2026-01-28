package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.iam.application.AppDetailsService;
import com.platformcommons.platform.service.iam.domain.AppDetail;
import com.platformcommons.platform.service.iam.domain.repo.AppDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AppDetailsServiceImpl implements AppDetailsService {

    @Autowired
    private AppDetailsRepository appDetailsRepository;

    @Override
    public AppDetail createAppDetails(AppDetail appDetail) {

        return appDetailsRepository.save(appDetail);
    }

    @Override
    public AppDetail getAppDetails(String appKey, String appCode) {
        return appDetailsRepository.findByAppKeyAndAppCode(appKey,appCode)
                .orElseThrow(()-> new NotFoundException(String.format("App with code %s and appKey %s  not found ",appCode,appKey)));
    }

    @Override
    public AppDetail addOpenApisToAppDetails(Long appDetailsId, Set<String> openApis) {
        AppDetail appDetail = appDetailsRepository.findById(appDetailsId).orElseThrow(()->
                new NotFoundException(String.format("AppDetails with id %d not found",appDetailsId)));
        appDetail.getOpenApis().addAll(openApis);
        appDetail =appDetailsRepository.save(appDetail);
        return appDetail;

    }

    @Override
    public AppDetail getSessionByApiKey(String apiKey, String tenantLogin) {
        return appDetailsRepository.findByApiKeyAndTenantLogin(apiKey,tenantLogin)
                .orElseThrow(()-> new NotFoundException(String.format("Record with ApiKey %s and TenantLogin %s  not found ",apiKey,tenantLogin)));
    }
}
