package com.platformcommons.platform.service.app.facade.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;
import com.platformcommons.platform.service.app.application.AppConfigService;
import com.platformcommons.platform.service.app.domain.AppConfig;
import com.platformcommons.platform.service.app.dto.AppConfigDTO;
import com.platformcommons.platform.service.app.dto.ConfigDTO;
import com.platformcommons.platform.service.app.dto.DefaultConfigRequestDTO;
import com.platformcommons.platform.service.app.facade.AppConfigFacade;
import com.platformcommons.platform.service.app.facade.assembler.AppConfigDTOAssembler;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.entity.common.MLText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class AppConfigFacadeImpl  implements AppConfigFacade {

    @Autowired
    private AppConfigService service;

    @Autowired
    private AppConfigDTOAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Long save(AppConfigDTO AppConfigDTO) {
        return service.save(assembler.fromDTO(AppConfigDTO));
    }

    @Override
    public void addOrUpdateAppConfigToAppVersionIndividually(Long appVersionId, Set<AppConfigDTO> appConfigDTOs) {
        service.addOrUpdateAppConfigToAppVersionIndividually(appVersionId,appConfigDTOs.stream().map(assembler::fromDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    public void addOrUpdateAppConfigToAppVersion(Long appVersionId, String configType, String scopeCode, String scopeValue,
                                                 Set<AppConfigDTO> appConfigDTOs) {
        service.addOrUpdateAppConfigToAppVersion(appVersionId,configType,scopeCode,scopeValue,appConfigDTOs.stream().map(assembler::fromDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    @Override
    public AppConfigDTO update(AppConfigDTO AppConfigDTO) {
        return assembler.toDTO(service.update(assembler.fromDTO(AppConfigDTO)));
    }

    @Override
    public PageDTO<AppConfigDTO> getAllPage(Integer page, Integer size) {
        Page<AppConfig> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id, String reason) {
        service.deleteById(id,reason);
    }

    @Override
    public AppConfigDTO getById(Long id) {
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public Map<String, ConfigDTO> getConfigByAppCodeAndAppVersionForLanguage(String appCode, String version, Map<String, String> scopeMap,
                                                                             String languageCode) {
        //TODO implement languageCode filter
        String scopeKV = scopeMap.get("scope");
        Map<String, String> scopeCodeScopeValueMap = scopeKeyMap(scopeKV);
        Map<String, AppConfig> appConfigMap = service.getConfigByAppCodeAndVersionCodeForLanguage(appCode, version,
                scopeCodeScopeValueMap);
        Map<String, ConfigDTO> appConfigDTOMap = new LinkedHashMap<>();
        appConfigMap.forEach((key, value) -> appConfigDTOMap.put(key, this.fromAppConfig(value)));
        return appConfigDTOMap;
    }

    @Override
    public Map<String, String> getConfigForAppAppCodeAndAppVersionWithConfigType(String appCode, String version,
                                                                                 Map<String, String> scopeMap, String languageCode,
                                                                                 String configType) {
        //TODO implement languageCode filter from DB
        String scopeKV = scopeMap.get("scope");
        Map<String, String> scopeCodeScopeValueMap = scopeKeyMap(scopeKV);
        Map<String, AppConfig> appConfigMap = service.getConfigByAppCodeAndVersionCodeForLanguageAndConfigType(appCode, version,
                scopeCodeScopeValueMap, configType);
        Map<String, String> appConfigDTOMap = new LinkedHashMap<>();
        appConfigMap.forEach((key, value) -> appConfigDTOMap.put(key, this.mapFromAppConfig(value, languageCode)));
        return appConfigDTOMap;
    }


    @Override
    public com.fasterxml.jackson.databind.JsonNode getConfigForAppAppCodeAndAppVersionNestedStructure(String appCode, String version, Map<String, String> scope, String languageCode, String configType) throws JsonProcessingException {
        Map<String, String> fetchedConfig =
                getConfigForAppAppCodeAndAppVersionWithConfigType(appCode,version,scope,languageCode,configType);
        String parsedJson =  JsonUnflattener.unflatten(fetchedConfig);
        return objectMapper.readTree(parsedJson);
    }

    @Override
    public void createDefaultConfig(DefaultConfigRequestDTO defaultConfigRequestDTO) {
        service.createDefaultConfig(defaultConfigRequestDTO.getConfigKeyValueMap(),
                defaultConfigRequestDTO.getTenantScopeValue(), defaultConfigRequestDTO.getAppCode(), defaultConfigRequestDTO.getDefaultVersion(), defaultConfigRequestDTO.getExistingAppVersion());

    }

    @Override
    public List<AppConfigDTO> getAppConfigsForAppAppCodeAndAppVersionWithScopeValue(String appCode, String appVersion, String scopeValue) {
        return service.getConfigsForAppAppCodeAndAppVersionWithScopeValue(appCode,appVersion,scopeValue)
                .stream().map(appConfig -> this.assembler.toDTO(appConfig)).collect(Collectors.toList()) ;
    }

    @Override
    public AttachmentDTO uploadAttachment(MultipartFile multipartFile, String entityType) {
        Attachment attachment = service.uploadAttachment(multipartFile,entityType);
        return AttachmentDTO.builder()
                .id(attachment.getId())
                .mimeType(attachment.getMimeType())
                .completeURL(attachment.getCompleteURL())
                .fileName(attachment.getFileName())
                .createdByUser(attachment.getCreatedByUser())
                .build();
    }

    @Override
    public List<AppConfigDTO> getConfigForAppAppCodeAndAppVersionWithConfigTypeV3(String appCode, String version, Map<String, String> scope, String configType) {
        String scopeKV = scope.get("scope");
        Map<String, String> scopeCodeScopeValueMap = scopeKeyMap(scopeKV);
        Map<String, AppConfig> appConfigMap = service.getConfigByAppCodeAndVersionCodeForLanguageAndConfigType(appCode, version,
                scopeCodeScopeValueMap, configType);
        return appConfigMap.values().stream().map(assembler::toDTO).collect(Collectors.toList());
    }

    @Override
    public void saveOrUpdateAll(Map<String, Object> configKeyValueMap, Long appVersionId, String configType,
                                      String scopeCode, String scopeValue) {

        Set<AppConfigDTO> appConfigDTOS = new LinkedHashSet<>();
        for (Map.Entry<String,Object> entry: configKeyValueMap.entrySet()) {
            appConfigDTOS.add(AppConfigDTO.builder()
                    .id(0L)
                    .configKey(entry.getKey())
                    .configValue(String.valueOf(entry.getValue()))
                    .configType(configType)
                    .scopeCode(scopeCode)
                    .scopeValue(scopeValue)
                    .configValueML(Collections.emptySet())
                    .build());

        }
        this.addOrUpdateAppConfigToAppVersion(appVersionId,configType,scopeCode,scopeValue,appConfigDTOS);
    }


    @Override
    public void saveOrUpdateAllWithNested(String jsonString, Long appVersionId,
                                  String configType, String scopeCode, String scopeValue) {

        Map<String, Object> flattenJson = JsonFlattener.flattenAsMap(jsonString);
        saveOrUpdateAll(flattenJson,appVersionId,configType,scopeCode,scopeValue);
    }

    public Map<String, String> scopeKeyMap(String scope) {
        Map<String, String> results = new HashMap<>();
        String[] values = scope.split(",");
        Arrays.stream(values).forEach(it -> {
            String[] keyValue = it.split("=");
            results.put(keyValue[0], keyValue[1]);
        });
        return results;
    }

    private ConfigDTO fromAppConfig(AppConfig appConfig) {
        return ConfigDTO.builder()
                .configKey(appConfig.getConfigKey())
                .configValue(appConfig.getConfigValue())
                .configType(appConfig.getConfigType())
                .id(appConfig.getId())
                .build();
    }

    private String mapFromAppConfig(AppConfig appConfig, String languageCode) {
        List<MLText> mlLabel = appConfig.getConfigValueML().stream().filter(mlText
                -> mlText.getLanguageCode().equals(languageCode)).collect(Collectors.toList());
        if (mlLabel.isEmpty())
            return appConfig.getConfigValue();
        else
            return mlLabel.get(0).getText();
    }
}
