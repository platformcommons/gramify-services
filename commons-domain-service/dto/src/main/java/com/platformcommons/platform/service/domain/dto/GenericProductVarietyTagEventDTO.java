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
public class GenericProductVarietyTagEventDTO {
    private Long genericProductVarietyId;

    private String genericProductVarietyCode;

    private Set<MLTextDTO> genericProductVarietyName;

    private Long genericProductId;

    private String genericProductCode;

    private Set<MLTextDTO> genericProductName;

    private Set<ClassificationTagEventDTO> categories;

    private Set<ClassificationTagEventDTO> subCategories;

    private Long tenantId;

    public Long marketId;

    public String marketCode;

    public String marketName;

    public Long channelId;

    public String channelCode;

    public String channelName;

    private Boolean isDefaultChannel;

    private String tagIdentifier;

    private Boolean isActive;

    private Long oldGenericProductId;

    private String oldGenericProductCode;

    private Set<MLTextDTO> oldGenericProductName;


    @Builder
    public GenericProductVarietyTagEventDTO(Long genericProductVarietyId, String genericProductVarietyCode,
                                         Set<MLTextDTO> genericProductVarietyName, Long genericProductId,
                                         String genericProductCode, Set<MLTextDTO> genericProductName,
                                         Set<ClassificationTagEventDTO> categories, Set<ClassificationTagEventDTO> subCategories,
                                         Long tenantId, Long marketId, String marketCode, String marketName,
                                         Long channelId, String channelCode, String channelName, Boolean isDefaultChannel,
                                         String tagIdentifier, Boolean isActive, Long oldGenericProductId, String oldGenericProductCode, Set<MLTextDTO> oldGenericProductName) {
        this.genericProductVarietyId = genericProductVarietyId;
        this.genericProductVarietyCode = genericProductVarietyCode;
        this.genericProductVarietyName = genericProductVarietyName;
        this.genericProductId = genericProductId;
        this.genericProductCode = genericProductCode;
        this.genericProductName = genericProductName;
        this.categories = categories;
        this.subCategories = subCategories;
        this.tenantId = tenantId;
        this.marketId = marketId;
        this.marketCode = marketCode;
        this.marketName = marketName;
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.channelName = channelName;
        this.isDefaultChannel = isDefaultChannel;
        this.tagIdentifier = tagIdentifier;
        this.isActive = isActive;
        this.oldGenericProductId = oldGenericProductId;
        this.oldGenericProductCode = oldGenericProductCode;
        this.oldGenericProductName = oldGenericProductName;
    }
}
