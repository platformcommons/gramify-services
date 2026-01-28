package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class TenantMetaMasterDTO {

    @NotNull
    private String metaCode;
    @NotNull
    private String metaName;
    @NotNull
    private String appContext;
    private String metaDescription;
    private String metaDataType;
    private Set<String> metaDefaultValues;
    private Set<String> metaAllowedValue;

    @Builder
    public TenantMetaMasterDTO(String metaCode, String metaName, String appContext, String metaDescription, String metaDataType, Set<String> metaDefaultValues, Set<String> metaAllowedValue) {
        this.metaCode = metaCode;
        this.metaName = metaName;
        this.appContext = appContext;
        this.metaDescription = metaDescription;
        this.metaDataType = metaDataType;
        this.metaDefaultValues = metaDefaultValues;
        this.metaAllowedValue = metaAllowedValue;
    }
}
