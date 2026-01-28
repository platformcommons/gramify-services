package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.app.application.AppConfigService;
import com.platformcommons.platform.service.app.application.AppVersionService;
import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.app.domain.AppConfig;
import com.platformcommons.platform.service.app.domain.AppConfigScopeMaster;
import com.platformcommons.platform.service.app.domain.AppVersion;
import com.platformcommons.platform.service.app.domain.repo.AppConfigRepository;
import com.platformcommons.platform.service.app.domain.repo.AppConfigScopeMasterRepository;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.entity.common.MLText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AppConfigServiceImpl implements AppConfigService {


    @Autowired
    public AppConfigRepository appconfigRepository;

    @Autowired
    private AppVersionService appVersionService;

    @Autowired
    private AppConfigScopeMasterRepository appConfigScopeMasterRepository;

    @Autowired
    private AttachmentService attachmentService;


    private static final String DEFAULT_SCOPE = "DEFAULT";
    private static final String TENANT_SCOPE = "TENANT";

    private static final String ENTITY_TYPE_APP_CONFIG = "APP_CONFIG";



    @Override
    public Long save(AppConfig appconfig) {
        PlatformUtil.validateTenantAdmin();
        return appconfigRepository.save(appconfig).getId();
    }

    @Override
    public void addOrUpdateAppConfigToAppVersionIndividually(Long appVersionId, Set<AppConfig> appConfigList) {
        PlatformUtil.validateTenantAdmin();
        AppVersion appVersion= appVersionService.getById(appVersionId);
        Set<AppConfig> finalAppConfigSet = new HashSet<>();
        appConfigList.forEach(appConfig -> {
            AppConfig finalAppConfig = null;
            Optional<AppConfig> optionalFetchedAppConfig = appconfigRepository.findByConfigKeyAndScopeAndAppVersion(appVersionId,
                    appConfig.getScopeCode(),appConfig.getScopeValue(),appConfig.getConfigKey(),appConfig.getConfigType());
            if(optionalFetchedAppConfig.isPresent()) {
                finalAppConfig = optionalFetchedAppConfig.get();
                finalAppConfig.patchUpdate(appConfig);
            }
            else {
                finalAppConfig = appConfig;
                finalAppConfig.init();
                finalAppConfig.setAppVersion(appVersion);
            }
            finalAppConfigSet.add(finalAppConfig);
        });
        appconfigRepository.saveAll(finalAppConfigSet);
    }

    @Override
    public void addOrUpdateAppConfigToAppVersion(Long appVersionId, String configType, String scopeCode, String scopeValue,
                                                 Set<AppConfig> appConfigList) {
        PlatformUtil.validateTenantAdmin();
        AppVersion appVersion= appVersionService.getById(appVersionId);
        Set<AppConfig> finalAppConfigSet = new HashSet<>();
        Set<String> configKeySet = appConfigList.stream()
                .map(AppConfig::getConfigKey)
                .collect(Collectors.toSet());
        Set<AppConfig> fetchedAppConfigSet = appconfigRepository.findAllByConfigKeysAndScopeAndAppVersion(appVersionId,
                scopeCode,scopeValue,configKeySet,configType);
        Map<String,AppConfig> fetchedAppConfigMap = fetchedAppConfigSet.stream()
                .collect(Collectors.toMap(AppConfig::getConfigKey, Function.identity(),(a,b)->a));
        appConfigList.forEach(appConfig -> {
            AppConfig finalAppConfig = null;
            String configKey = appConfig.getConfigKey();
            if(fetchedAppConfigMap.containsKey(configKey)) {
                finalAppConfig = fetchedAppConfigMap.get(configKey);
                finalAppConfig.patchUpdate(appConfig);
            }
            else {
                finalAppConfig = appConfig;
                finalAppConfig.init();
                finalAppConfig.setAppVersion(appVersion);
            }
            finalAppConfigSet.add(finalAppConfig);
        });
        appconfigRepository.saveAll(finalAppConfigSet);
    }

    @Override
    public AppConfig update(AppConfig appconfig) {
        AppConfig fetchedAppConfig = appconfigRepository.findById(appconfig.getId())
		.orElseThrow(()-> new NotFoundException(String.format("Request id %d  not found",appconfig.getId())));
        fetchedAppConfig.update(appconfig);
        return appconfigRepository.save(fetchedAppConfig);
    }

    @Override
    public void deleteById(Long id, String reason) {
        AppConfig fetchedAppConfig = appconfigRepository.findById(id)
		.orElseThrow(()-> new NotFoundException(String.format("Request id %d  not found",id)));
        //fetchedAppConfig.deactivate(reason);
        appconfigRepository.save(fetchedAppConfig);
    }

    @Override
    public AppConfig getById(Long id) {
        return appconfigRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Request id  not found"));
    }

    @Override
    public Page<AppConfig> getByPage(Integer page, Integer size) {
        return appconfigRepository.findAll(PageRequest.of(page, size));
    }

    private Map<String, AppConfig> getConfigByVersionId(Long versionId, Map<String, String> scopeCodeScopeValueMap, String configType) {

        List<AppConfigScopeMaster> appConfigScopeMasters = appConfigScopeMasterRepository.findAll(PageRequest.of(0, 100))
                .getContent().stream().sorted(Comparator.comparingLong(AppConfigScopeMaster::getPriority)).collect(Collectors.toList());
        Map<String, Long> scopeCodePriorityMap = new LinkedHashMap<>();
        appConfigScopeMasters.forEach(it -> scopeCodePriorityMap.put(it.getCode(), it.getPriority()));
        Map<String, String> sortedMap = this.convertRequestInOrderOfPriority(scopeCodeScopeValueMap, scopeCodePriorityMap);
        Map<String, AppConfig> results = new LinkedHashMap<>();
        List<AppConfig> appConfigList = new ArrayList<>();
        List<String> existingKeys = new ArrayList<>();
        for (Map.Entry<String, String> map : sortedMap.entrySet()) {
            List<AppConfig> appConfigs = appconfigRepository.findAllByScopeCodeWithValueForConfigType(versionId, map.getKey(),
                    map.getValue(), existingKeys, configType);
            appConfigs.forEach(it -> existingKeys.add(it.getConfigKey()));
            appConfigList.addAll(appConfigs);
        }
        if (!sortedMap.containsKey(DEFAULT_SCOPE)) {
            appConfigList.addAll(appconfigRepository.findAllByScopeCodeAndConfigType(versionId, DEFAULT_SCOPE, existingKeys,configType));
        }
        appConfigList.forEach(it -> results.put(it.getConfigKey(), it));
        return results;
    }

    @Override
    public Map<String, AppConfig> getConfigByAppCodeAndVersionCodeForLanguage(String appCode, String version,
                                                                              Map<String, String> scopeCodeScopeValueMap) {
        AppVersion appVersion = appVersionService.getByAppCodeVersion(appCode, version);
        return this.getConfigByVersionId(appVersion.getId(), scopeCodeScopeValueMap, null);
    }


    @Override
    public Map<String, AppConfig> getConfigByAppCodeAndVersionCodeForLanguageAndConfigType(String appCode, String version,
                                                                                           Map<String, String> scopeCodeScopeValueMap,
                                                                                           String configType) {
        AppVersion appVersion = appVersionService.getByAppCodeVersion(appCode, version);
        return this.getConfigByVersionId(appVersion.getId(), scopeCodeScopeValueMap, configType);
    }

    @Override
    public Set<AppConfig> getAppConfigsForAppCodeAndAppVersion(String appCode, String appVersion) {
        return appVersionService.getByAppCodeVersion(appCode, appVersion).getAppConfigList();
    }

    @Override
    public void createDefaultConfig(Map<String, Map<String, Object>>  configKeyValueMap, String tenantScopeValue,
                                    String appCode,String defaultVersion,String existingVersion) {

        PlatformUtil.validateTenantAdmin();
        AppVersion defaultAppVersion = appVersionService.getByAppCodeVersion(appCode,defaultVersion);
        AppVersion existingAppVersion = buildAppVersionFromDefault(defaultAppVersion,configKeyValueMap,tenantScopeValue,existingVersion,appCode);
        appVersionService.save(existingAppVersion);
    }

    @Override
    public List<AppConfig> getConfigsForAppAppCodeAndAppVersionWithScopeValue(String appCode, String appVersion, String scopeValue) {
        List<AppConfig> appConfigList = appconfigRepository.findByAppCodeAndAppVersionWithScopeValue(appCode,appVersion,scopeValue);
        if(appConfigList.isEmpty()) {
            throw new NotFoundException(String.format(
                    "AppConfig not found with AppCode %s AND AppVersion %s with ScopeValue %s", appCode, appVersion, scopeValue));
        }
        return appConfigList;
    }

    @Override
    public Attachment uploadAttachment(MultipartFile multipartFile, String entityType) {
        Attachment attachment= new Attachment();
        attachment.setEntityType(entityType!=null?entityType:ENTITY_TYPE_APP_CONFIG);
        attachment.setPublic(Boolean.TRUE);
        try {
            return attachmentService.uploadAttachment(multipartFile, attachment);
        }catch (IOException ex){
            throw new InvalidInputException(ex.getMessage());
        }
    }

    private AppVersion buildAppVersionFromDefault(AppVersion defaultAppVersion ,Map<String, Map<String, Object>>  configKeyValueMap,String tenantScopeValue,
                                                  String existingVersion,String appCode) {


        AppVersion fetchedAppVersion = appVersionService.getByAppCodeVersion(appCode,existingVersion);

        Set<AppConfig> appConfigSet = buildAppConfigFromDefault(defaultAppVersion.getAppConfigList(),configKeyValueMap,tenantScopeValue);
        appConfigSet.forEach(it->it.setAppVersion(fetchedAppVersion));
        fetchedAppVersion.getAppConfigList().addAll(appConfigSet);
        return fetchedAppVersion;

    }

    private Set<AppConfig> buildAppConfigFromDefault(Set<AppConfig> defaultAppConfigs,Map<String, Map<String, Object>> configKeyValueMap,String tenantScopevalue) {
        Set<AppConfig> appConfigs = new HashSet<>();

        Map<String, Object> configs = new LinkedHashMap<>();
        if(configKeyValueMap!=null) {
            for (Map.Entry<String, Map<String, Object>> configsEntry : configKeyValueMap.entrySet()) {
                String configType = configsEntry.getKey();
                configs = configsEntry.getValue();
                for (Map.Entry<String, Object> entry : configs.entrySet()) {
                    appConfigs.add(AppConfig.builder()
                            .id(null)
                            .configKey(entry.getKey())
                            .configValue((String) entry.getValue())
                            .configValueML(Collections.emptySet())
                            .configType(configType)
                            .build());
                }
            }
        }
        Map<String, Object> finalConfigs = configs;
        defaultAppConfigs.forEach(it-> {
           if(!finalConfigs.containsKey(it.getConfigKey())) {
               appConfigs.add(AppConfig.builder()
                       .configKey(it.getConfigKey())
                       .configType(it.getConfigType())
                       .configValue(it.getConfigValue())
                       .scopeCode(TENANT_SCOPE)
                       .scopeValue(tenantScopevalue)
                       .configValueML(it.getConfigValueML() != null ? buildMLText(it.getConfigValueML()) : null)
                       .notes(it.getNotes())
                       .build());
           }
        });
        return appConfigs;
    }

    private Set<MLText> buildMLText(Set<MLText> defaultMlTexts){
        Set<MLText> mlTexts = new HashSet<>();
        defaultMlTexts.forEach(it->{
            mlTexts.add(MLText.builder()
                            .id(null)
                            .languageCode(it.getLanguageCode())
                            .text(it.getText())
                    .build());
        });
        return mlTexts;
    }

    private Map<String, String> convertRequestInOrderOfPriority(Map<String, String> scopeCodeScopeValueMap, Map<String, Long> scopeCodePriorityMap) {
        SortedMap<String, String> sortedMap = new TreeMap<>((s, t1) -> {
            if (scopeCodePriorityMap.get(s) > scopeCodePriorityMap.get(t1)) {
                return -1;
            } else if (scopeCodePriorityMap.get(s) > scopeCodePriorityMap.get(t1)) {
                return 0;
            } else {
                return 1;
            }
        });
        sortedMap.putAll(scopeCodeScopeValueMap);
        return sortedMap;
    }

}