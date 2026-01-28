package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.service.app.domain.AppConfig;
import com.platformcommons.platform.service.app.domain.AppVersion;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface AppVersionService {

    Long save(AppVersion appversion);

    void addAppVersionToApp(Long appId, Set<AppVersion> appVersions);

    AppVersion update(AppVersion appversion);

    void deleteById(Long id, String reason);

    AppVersion getById(Long id);

    Page<AppVersion> getByPage(Integer page, Integer size);

    AppVersion getByAppCodeVersion(String appCode, String version);

    Long copyAndCreateAppVersion(Long copyFromAppVersionId, AppVersion fromDTO, Set<AppConfig> appConfigs);

    Page<AppVersion> getAppVersionPageByAppId(Long appId, Integer page, Integer size);
}