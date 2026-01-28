package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.service.app.application.AppService;
import com.platformcommons.platform.service.app.domain.App;
import com.platformcommons.platform.service.app.domain.AppConfig;
import com.platformcommons.platform.service.app.domain.AppVersion;
import com.platformcommons.platform.service.app.domain.repo.AppRepository;
import org.springframework.data.domain.Page;
import com.platformcommons.platform.service.app.application.AppVersionService;
import com.platformcommons.platform.service.app.domain.repo.AppVersionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    public AppVersionRepository appversionRepository;

    @Autowired
    private AppService appService;

    @Autowired
    private AppRepository appRepository;


    @Override
    public Long save(AppVersion appversion) {
        return appversionRepository.save(appversion).getId();
    }

    @Override
    public void addAppVersionToApp(Long appId, Set<AppVersion> appVersions) {
        App app= appService.getById(appId);
        appVersions.forEach(it->{
            it.setId(null);
            it.setAppId(app);
        });
        appversionRepository.saveAll(appVersions);
    }

    @Override
    public AppVersion update(AppVersion appversion) {
        AppVersion fetchedAppVersion = appversionRepository.findById(appversion.getId())
		.orElseThrow(()-> new NotFoundException("Request id  not found"));
        return appversionRepository.save(appversion);
    }

    @Override
    public void deleteById(Long id, String reason) {
        AppVersion fetchedAppVersion = appversionRepository.findById(id)
		.orElseThrow(()-> new NotFoundException("Request id  not found"));
       // fetchedAppVersion.deactivate(reason);
        appversionRepository.save(fetchedAppVersion);
    }

    @Override
    public AppVersion getById(Long id) {
        return appversionRepository.findById(id)
		.orElseThrow(()-> new NotFoundException("Request id  not found"));
    }

    @Override
    public Page<AppVersion> getByPage(Integer page, Integer size) {
        return appversionRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public AppVersion getByAppCodeVersion(String appCode,String version){
        return appversionRepository.findByIdAppId_codeAndVersion(appCode,version).orElseThrow(()->
                new NotFoundException(String.format("AppVersion for the appCode %s and version %s not found",appCode,version)));
    }

    @Override
    public Long copyAndCreateAppVersion(Long copyFromAppVersionId, AppVersion appVersion,Set<AppConfig> appConfigs) {
        AppVersion  copyFromAppVersion= this.getById(copyFromAppVersionId);
        appConfigs.stream().forEach(it->{
            it.setId(null);
            it.setAppVersion(appVersion);
        });
        appVersion.setAppConfigList(appConfigs);
        appVersion.setId(null);
        appVersion.setAppId(copyFromAppVersion.getAppId());
        return appversionRepository.save(appVersion).getId();

    }

    @Override
    public Page<AppVersion> getAppVersionPageByAppId(Long appId, Integer page, Integer size) {
        App app = appRepository.findById(appId)
                .orElseThrow(() -> new IllegalArgumentException("App not found with id "+appId));
        return appversionRepository.findByAppId(app,PageRequest.of(page,size));
    }
}