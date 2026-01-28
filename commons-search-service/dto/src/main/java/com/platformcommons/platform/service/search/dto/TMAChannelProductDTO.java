package com.platformcommons.platform.service.search.dto;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TMAChannelProductDTO extends BaseDTO {

    private String id;

    private Long traderId;

    private String traderName;

    private Long marketId;

    private String marketCode;

    private Long channelId;

    private String channelCode;

    private Long productId;

    private String productCode;

    private String productName;

    private Long defaultSKUId;

    private String defaultSKUName;

    private Double defaultSKUPrice;

    private Double defaultSKUAvailableQuantity;

    private Long genericProductId;

    private String genericProductCode;

    private Long genericProductVarietyId;

    private String genericProductVarietyCode;

    private Double totalAvailableQuantity;

    private String quantityUOM;

    private String traderIconURL;

    private Double minPrice;

    private Double maxPrice;

    private String currencyCode;

    private Long tenantId;

    private Boolean isActive;

    private Set<String> categoryCodes;

    private Set<String> categoryNames;

    private Set<String> subCategoryCodes;

    private Set<String> subCategoryNames;

    private Long defaultSKUTmaChannelProductId;

    private String productType;

    private String productSubType;

    private Set<Long> approvedForSolutionIds;

    private Set<Long> unApprovedSolutionIds;

    private Set<Long> linkedSolutionResourceIds;

    @Builder

    public TMAChannelProductDTO(String id, Long traderId, String traderName, Long marketId, String marketCode,
                                Long channelId, String channelCode, Long productId, String productCode, String productName,
                                Long defaultSKUId, String defaultSKUName, Double defaultSKUPrice, Double defaultSKUAvailableQuantity,
                                Long genericProductId, String genericProductCode, Long genericProductVarietyId, String genericProductVarietyCode,
                                Double totalAvailableQuantity, String quantityUOM, String traderIconURL, Double minPrice, Double maxPrice,
                                String currencyCode, Long tenantId, Boolean isActive, Set<String> categoryCodes, Set<String> categoryNames,
                                Set<String> subCategoryCodes, Set<String> subCategoryNames, Long defaultSKUTmaChannelProductId,
                                String productType, String productSubType,Set<Long> approvedForSolutionIds, Set<Long> unApprovedSolutionIds,
                                Set<Long> linkedSolutionResourceIds) {
        this.id = id;
        this.traderId = traderId;
        this.traderName = traderName;
        this.marketId = marketId;
        this.marketCode = marketCode;
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.defaultSKUId = defaultSKUId;
        this.defaultSKUName = defaultSKUName;
        this.defaultSKUPrice = defaultSKUPrice;
        this.defaultSKUAvailableQuantity = defaultSKUAvailableQuantity;
        this.genericProductId = genericProductId;
        this.genericProductCode = genericProductCode;
        this.genericProductVarietyId = genericProductVarietyId;
        this.genericProductVarietyCode = genericProductVarietyCode;
        this.totalAvailableQuantity = totalAvailableQuantity;
        this.quantityUOM = quantityUOM;
        this.traderIconURL = traderIconURL;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.currencyCode = currencyCode;
        this.tenantId = tenantId;
        this.isActive = isActive;
        this.categoryCodes = categoryCodes;
        this.categoryNames = categoryNames;
        this.subCategoryCodes = subCategoryCodes;
        this.subCategoryNames = subCategoryNames;
        this.defaultSKUTmaChannelProductId = defaultSKUTmaChannelProductId;
        this.productType = productType;
        this.productSubType = productSubType;
        this.approvedForSolutionIds = approvedForSolutionIds;
        this.unApprovedSolutionIds = unApprovedSolutionIds;
        this.linkedSolutionResourceIds = linkedSolutionResourceIds;

    }
}
