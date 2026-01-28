package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class TenantMetaAdditionalPropertyDTO extends BaseTransactionalDTO implements Serializable {
    private Long id;
    private String metaKey;
    private String metaValue;
    private Boolean isMultivalued;
    private String appContext;
    private String multiValueSeparator;
    private Map<String,String> attributes;

    @Builder
    public TenantMetaAdditionalPropertyDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt,Long id, String metaKey,
                                           String metaValue, Boolean isMultivalued, String multiValueSeparator,
                                           Map<String,String> attributes,String appContext) {
        super(uuid, appCreatedAt, appLastModifiedAt);
        this.id = id;
        this.metaKey = metaKey;
        this.metaValue = metaValue;
        this.isMultivalued = isMultivalued;
        this.multiValueSeparator = multiValueSeparator;
        this.attributes = attributes;
        this.appContext = appContext;
    }
}