package com.platformcommons.platform.service.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericProductVarietySearchDTO {

    public String id;

    private Long genericProductVarietyId;


    public String genericProductVarietyCode;

    public String genericProductVarietyName;

    public Long genericProductId;

    public String genericProductCode;

    public String genericProductName;

    public Set<String> categoryCodes;

    public Set<String> categoryNames;

    public Set<String> subCategoryCodes;

    public Set<String> subCategoryNames;

    public Double maxPrice;

    public Double minPrice;

    public String currencyCode;

    public Double minAvailableQuantity;

    public Double maxAvailableQuantity;

    public String availableQuantityUOM;

    public Long traderCount;

    public Set<Long> traderIds;

    public Set<String> productNames;

    public Long marketId;

    public String marketCode;

    public String marketName;

    public Long channelId;

    public String channelCode;

    public String channelName;


    public String iconImageURL;


    public Long tenantId;

    public Boolean isActive;


    private Set<String> keywords;


    private Map<String,String> specification;


    @Builder
    public GenericProductVarietySearchDTO(String id, Long genericProductVarietyId,
                                          String genericProductVarietyCode, String genericProductVarietyName,
                                          Long genericProductId, String genericProductCode, String genericProductName,
                                          Set<String> categoryCodes, Set<String> categoryNames, Set<String> subCategoryCodes,
                                          Set<String> subCategoryNames, Double maxPrice, Double minPrice, String currencyCode,
                                          Double minAvailableQuantity, Double maxAvailableQuantity, String availableQuantityUOM,
                                          Long traderCount, Set<Long> traderIds, Set<String> productNames, Long marketId,
                                          String marketCode, String marketName, Long channelId, String channelCode,
                                          String channelName, String iconImageURL, Long tenantId, Boolean isActive,
                                          Set<String> keywords) {
        this.id = id;
        this.genericProductVarietyId = genericProductVarietyId;
        this.genericProductVarietyCode = genericProductVarietyCode;
        this.genericProductVarietyName = genericProductVarietyName;
        this.genericProductId = genericProductId;
        this.genericProductCode = genericProductCode;
        this.genericProductName = genericProductName;
        this.categoryCodes = categoryCodes;
        this.categoryNames = categoryNames;
        this.subCategoryCodes = subCategoryCodes;
        this.subCategoryNames = subCategoryNames;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.currencyCode = currencyCode;
        this.minAvailableQuantity = minAvailableQuantity;
        this.maxAvailableQuantity = maxAvailableQuantity;
        this.availableQuantityUOM = availableQuantityUOM;
        this.traderCount = traderCount;
        this.traderIds = traderIds;
        this.productNames = productNames;
        this.marketId = marketId;
        this.marketCode = marketCode;
        this.marketName = marketName;
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.channelName = channelName;
        this.iconImageURL = iconImageURL;
        this.tenantId = tenantId;
        this.isActive = isActive;
        this.keywords = keywords;
    }
}
