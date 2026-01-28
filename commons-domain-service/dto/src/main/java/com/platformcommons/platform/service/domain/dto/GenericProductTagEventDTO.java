package com.platformcommons.platform.service.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class GenericProductTagEventDTO {

    private Long genericProductId;

    private String genericProductCode;

    private Set<MLTextDTO> genericProductName;

    private Set<ClassificationTagEventDTO> categories;

    private Set<ClassificationTagEventDTO> subCategories;

    private Long tenantId;

    private Long marketId;

    private String marketCode;

    private String marketName;

    private String tagIdentifier;

    private Boolean isActive;

    @Builder
    public GenericProductTagEventDTO( Long genericProductId,
                                  String genericProductCode, Set<MLTextDTO> genericProductName,
                                  Set<ClassificationTagEventDTO> categories, Set<ClassificationTagEventDTO> subCategories,
                                  Long tenantId, Long marketId, String marketCode, String marketName, String tagIdentifier, Boolean isActive ) {
        this.genericProductId = genericProductId;
        this.genericProductCode = genericProductCode;
        this.genericProductName = genericProductName;
        this.categories = categories;
        this.subCategories = subCategories;
        this.tenantId = tenantId;
        this.marketId = marketId;
        this.marketCode = marketCode;
        this.marketName = marketName;
        this.tagIdentifier = tagIdentifier;
        this.isActive = isActive;
    }
}
