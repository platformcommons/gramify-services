package com.platformcommons.platform.service.app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class DefaultConfigRequestDTO {

    private Map<String, Map<String, Object>>  configKeyValueMap;

    @NotNull
    private String tenantScopeValue;

    @NotNull
    private String appCode;

    @NotNull
    private String defaultVersion;

    @NotNull
    private String existingAppVersion;

    @Builder
    public DefaultConfigRequestDTO(Map<String, Map<String, Object>> configKeyValueMap, String tenantScopeValue, String appCode, String defaultVersion, String existingAppVersion) {
        this.configKeyValueMap = configKeyValueMap;
        this.tenantScopeValue = tenantScopeValue;
        this.appCode = appCode;
        this.defaultVersion = defaultVersion;
        this.existingAppVersion = existingAppVersion;
    }
}
