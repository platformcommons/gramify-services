package com.platformcommons.platform.service.app.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.platformcommons.platform.service.app.dto.AppConfigDTO;
import com.platformcommons.platform.service.app.dto.ConfigDTO;
import com.platformcommons.platform.service.app.dto.DefaultConfigRequestDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AppConfigFacade {

    Long save(AppConfigDTO AppConfigDTO);

    void addOrUpdateAppConfigToAppVersionIndividually(Long appVersionId, Set<AppConfigDTO> appConfigDTOs);

    AppConfigDTO update(AppConfigDTO AppConfigDTO);

    PageDTO<AppConfigDTO> getAllPage(Integer page, Integer size);

    void delete(Long id, String reason);

    AppConfigDTO getById(Long id);


    Map<String, ConfigDTO> getConfigByAppCodeAndAppVersionForLanguage(String appCode, String version,
                                                                      Map<String, String> scopeCodeScopeValueMap,
                                                                      String languageCode);

    Map<String,String> getConfigForAppAppCodeAndAppVersionWithConfigType(String appCode, String version,
                                                             Map<String, String> scope, String languageCode, String configType);


    void saveOrUpdateAll(Map<String, Object> configKeyValueMap, Long appVersionId, String configType, String scopeCode, String scopeValue);

    void saveOrUpdateAllWithNested(String jsonString, Long appVersionId, String configType, String scopeCode, String scopeValue);

    com.fasterxml.jackson.databind.JsonNode getConfigForAppAppCodeAndAppVersionNestedStructure(String appCode, String version, Map<String, String> scope, String languageCode, String configType) throws JsonProcessingException;

    void createDefaultConfig(DefaultConfigRequestDTO defaultConfigRequestDTO);

    List<AppConfigDTO> getAppConfigsForAppAppCodeAndAppVersionWithScopeValue(String appCode,String appVersion,String scopeValue);

    AttachmentDTO uploadAttachment(MultipartFile file, String entityType);

    List<AppConfigDTO> getConfigForAppAppCodeAndAppVersionWithConfigTypeV3(String appCode, String version,
                                                                           Map<String, String> scope,
                                                                           String configType);
}
