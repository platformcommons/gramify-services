package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.service.app.domain.AppConfig;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AppConfigService {

    Long save(AppConfig appconfig);

    void addOrUpdateAppConfigToAppVersionIndividually(Long appVersionId, Set<AppConfig> appConfigList);

    void addOrUpdateAppConfigToAppVersion(Long appVersionId, String configType, String scopeCode, String scopeValue,
                                          Set<AppConfig> appConfigList);

    AppConfig update(AppConfig appconfig);

    void deleteById(Long id, String reason);

    AppConfig getById(Long id);

    Page<AppConfig> getByPage(Integer page, Integer size);

    Map<String, AppConfig> getConfigByAppCodeAndVersionCodeForLanguage(String appCode, String version,
                                                                       Map<String, String> scopeCodeScopeValueMap);

    Map<String, AppConfig> getConfigByAppCodeAndVersionCodeForLanguageAndConfigType(String appCode, String version,
                                                                       Map<String, String> scopeCodeScopeValueMap,String configType);
    Set<AppConfig> getAppConfigsForAppCodeAndAppVersion(String appCode, String version);

    void createDefaultConfig(Map<String, Map<String, Object>>  configKeyValueMap, String tenantScopeValue, String appCode,
                             String defaultVersion,String existingAppVersion);

    List<AppConfig> getConfigsForAppAppCodeAndAppVersionWithScopeValue(String appCode,String appVersion,String scopeValue);

    Attachment uploadAttachment(MultipartFile multipartFile, String entityType);
}