package com.platformcommons.platform.service.search.dto;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TMAChannelProductSKUDTO extends BaseDTO {


    private String id;


    private Long genericProductId;


    private String genericProductCode;


    private String genericProductName;


    private Long genericProductVarietyId;


    private String genericProductVarietyCode;


    private String genericProductVarietyName;



    private Long productId;


    private String productCode;


    private String productName;


    private Set<String> categoryCodes;


    private Set<String> categoryNames;


    private Set<String> subCategoryCodes;


    private Set<String> subCategoryNames;



    private Long productSKUId;


    private String productSKUCode;


    private String productSKUName;



    private Long marketId;


    private String marketCode;


    private String marketName;




    private Long channelId;


    private String channelCode;


    private String channelName;


    private Long traderId;


    private String traderName;

    private String traderIconURL;


    private Long tenantId;


    private Boolean isActive;


    private Double skuMRP;


    private String skuMRPCurrency;

    private Double skuDiscountedMRP;
    private String skuDiscountedMRPCurrency;



    private Set<String> servingAreaCodes;


    private Double availableQuantity;



    private String availableQuantityUOM;
    private Long defaultSKUTmaChannelProductId;
    private Long skuTmaChannelProductId;
    private String productType;
    private String productSubType;
    private Set<Long> approvedForSolutionIds;
    private Set<Long> unApprovedSolutionIds;
    private Set<String> tagCodes;
    private Set<String> tagLabels;
    private Map<String, Set<String>> tagCodesByType;
    private Map<String, Set<String>> tagLabelsByType;
    private Map<String, Set<String>> tagLabelsByLang;
    private Set<String> skuFactorCodes;


    @Builder
    public TMAChannelProductSKUDTO(String id, Long genericProductId, String genericProductCode,
                                String genericProductName, Long genericProductVarietyId,
                                String genericProductVarietyCode, String genericProductVarietyName,
                                Long productId, String productCode, String productName,
                                Set<String> categoryCodes, Set<String> categoryNames, Set<String> subCategoryCodes,
                                Set<String> subCategoryNames, Long productSKUId, String productSKUCode,String productSKUName,
                                Long marketId, String marketCode, String marketName, Long traderId,
                                String traderName, String traderIconURL, Long tenantId, Boolean isActive, Double skuMRP,Set<String> servingAreaCodes,
                                Double availableQuantity,String availableQuantityUOM,String skuMRPCurrency,Double skuDiscountedMRP,
                                   String skuDiscountedMRPCurrency,Long channelId,String channelCode,String channelName,
                                   Long defaultSKUTmaChannelProductId, Long skuTmaChannelProductId, String productType,
                                   String productSubType, Set<Long> approvedForSolutionIds, Set<Long> unApprovedSolutionIds,
                                   Set<String> tagCodes, Set<String> tagLabels,
                                   Map<String, Set<String>> tagCodesByType, Map<String, Set<String>> tagLabelsByType, Map<String, Set<String>> tagLabelsByLang, Set<String> skuFactorCodes) {
        this.id = id;
        this.genericProductId = genericProductId;
        this.genericProductCode = genericProductCode;
        this.genericProductName = genericProductName;
        this.genericProductVarietyId = genericProductVarietyId;
        this.genericProductVarietyCode = genericProductVarietyCode;
        this.genericProductVarietyName = genericProductVarietyName;
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.categoryCodes = categoryCodes;
        this.categoryNames = categoryNames;
        this.subCategoryCodes = subCategoryCodes;
        this.subCategoryNames = subCategoryNames;
        this.productSKUId = productSKUId;
        this.productSKUCode = productSKUCode;
        this.productSKUName = productSKUName;
        this.marketId = marketId;
        this.marketCode = marketCode;
        this.marketName = marketName;
        this.traderId = traderId;
        this.traderName = traderName;
        this.tenantId = tenantId;
        this.traderIconURL = traderIconURL;
        this.isActive = isActive;
        this.skuMRP = skuMRP;
        this.servingAreaCodes = servingAreaCodes;
        this.availableQuantity = availableQuantity;
        this.availableQuantityUOM = availableQuantityUOM;
        this.skuMRPCurrency = skuMRPCurrency;
        this.skuDiscountedMRP = skuDiscountedMRP;
        this.skuDiscountedMRPCurrency = skuDiscountedMRPCurrency;
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.channelName = channelName;
        this.defaultSKUTmaChannelProductId = defaultSKUTmaChannelProductId;
        this.skuTmaChannelProductId = skuTmaChannelProductId;
        this.productType = productType;
        this.productSubType = productSubType;
        this.approvedForSolutionIds = approvedForSolutionIds;
        this.unApprovedSolutionIds = unApprovedSolutionIds;
        this.tagCodes = tagCodes;
        this.tagLabels = tagLabels;
        this.tagCodesByType = tagCodesByType;
        this.tagLabelsByType = tagLabelsByType;
        this.tagLabelsByLang = tagLabelsByLang;
        this.skuFactorCodes = skuFactorCodes;
    }
}
