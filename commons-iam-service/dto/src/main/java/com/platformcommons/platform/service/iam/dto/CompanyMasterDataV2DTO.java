package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@Validated
@Data
@NoArgsConstructor
public class CompanyMasterDataV2DTO extends BaseTransactionalDTO implements Serializable {
    private String code;
    private String name;
    private String alias;
    private String websiteUrl;
    private String logoUrl;

    @Builder
    public CompanyMasterDataV2DTO(String uuid, Long appCreatedAt, Long appLastModifiedAt, Boolean isActive, String code, String name, String alias, String websiteUrl, String logoUrl) {
        super(uuid, appCreatedAt, appLastModifiedAt, isActive);
        this.code = code;
        this.name = name;
        this.alias = alias;
        this.websiteUrl = websiteUrl;
        this.logoUrl = logoUrl;
    }
}