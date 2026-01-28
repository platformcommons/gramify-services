package com.platformcommons.platform.service.search.domain;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "tma_channel_product_sku")
public class TMAChannelProductSKU extends SolrBaseEntity {

    @Id
    @Field
    private String id;

    @Field
    private Long genericProductId;

    @Field
    private String genericProductCode;

    @Field
    private String genericProductName;

    @Field
    private Long genericProductVarietyId;

    @Field
    private String genericProductVarietyCode;

    @Field
    private String genericProductVarietyName;


    @Field
    private Long productId;

    @Field
    private String productCode;

    @Field
    private String productName;

    @Field
    private Set<String> categoryCodes;

    @Field
    private Set<String> categoryNames;

    @Field
    private Set<String> subCategoryCodes;

    @Field
    private Set<String> subCategoryNames;


    @Field
    private Long productSKUId;

    @Field
    private String productSKUCode;

    @Field
    private String productSKUName;


    @Field
    private Long marketId;

    @Field
    private String marketCode;

    @Field
    private String marketName;



    @Field
    private Long channelId;

    @Field
    private String channelCode;

    @Field
    private String channelName;

    @Field
    private Long traderId;

    @Field
    private String traderName;

    @Field
    private String traderIconURL;

    @Field
    private Long tenantId;

    @Field
    private Boolean isActive;

    @Field
    private Double skuMRP;

    @Field
    private String skuMRPCurrency;

    @Field
    private Double skuDiscountedMRP;

    @Field
    private String skuDiscountedMRPCurrency;


    @Field
    private Set<String> servingAreaCodes;

    @Field
    private Double availableQuantity;


    @Field
    private String availableQuantityUOM;

    @Field
    private Long defaultSKUTmaChannelProductId;

    @Field
    private Long skuTmaChannelProductId;

    @Field
    private String skuTmaChannelProductStatus;

    @Field
    private String productType;

    @Field
    private String productSubType;

    @Field
    private Set<Long> approvedForSolutionIds;

    @Field
    private Set<Long> unApprovedSolutionIds;

    @Field
    private Set<String> tagCodes;

    @Field
    private Set<String> tagLabels;

    @Dynamic
    @Field("TAG_CODE_BY_TYPE_*")
    private Map<String, List<String>> tagCodesByType;

    @Dynamic
    @Field("TAG_LABEL_BY_TYPE_*")
    private Map<String, List<String>> tagLabelsByType;

    @Dynamic
    @Field("TAG_LABEL_*")
    private Map<String, List<String>> tagLabelsByLang;

    @Field
    private Set<String> skuFactorCodes;

    @Builder
    public TMAChannelProductSKU(Long createdTimestamp, Long lastModifiedTimestamp,
                                String id, Long genericProductId, String genericProductCode,
                                String genericProductName, Long genericProductVarietyId,
                                String genericProductVarietyCode, String genericProductVarietyName,
                                Long productId, String productCode, String productName,
                                Set<String> categoryCodes, Set<String> categoryNames, Set<String> subCategoryCodes,
                                Set<String> subCategoryNames, Long productSKUId, String productSKUCode,String productSKUName,
                                Long marketId, String marketCode, String marketName, Long traderId, String traderName,
                                String traderIconURL, Long tenantId, Boolean isActive, Double skuMRP,Set<String> servingAreaCodes,
                                Double availableQuantity,String availableQuantityUOM,String skuMRPCurrency,Double skuDiscountedMRP,String skuDiscountedMRPCurrency,
                                Long channelId,String channelCode,String channelName,Long defaultSKUTmaChannelProductId,Long skuTmaChannelProductId,String skuTmaChannelProductStatus,
                                String productType, String productSubType, Set<Long> approvedSolutionIds,Set<Long> unApprovedSolutionIds,
                                Set<String> tagCodes, Set<String> tagLabels, Set<String> skuFactorCodes,
                                Map<String, List<String>> tagCodesByType, Map<String, List<String>> tagLabelsByType, Map<String, List<String>> tagLabelsByLang) {
        super(createdTimestamp, lastModifiedTimestamp);
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
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.channelName = channelName;
        this.traderId = traderId;
        this.traderName = traderName;
        this.traderIconURL = traderIconURL;
        this.tenantId = tenantId;
        this.isActive = isActive;
        this.skuMRP = skuMRP;
        this.skuMRPCurrency = skuMRPCurrency;
        this.skuDiscountedMRP = skuDiscountedMRP;
        this.skuDiscountedMRPCurrency = skuDiscountedMRPCurrency;
        this.servingAreaCodes = servingAreaCodes;
        this.availableQuantity = availableQuantity;
        this.availableQuantityUOM = availableQuantityUOM;
        this.defaultSKUTmaChannelProductId = defaultSKUTmaChannelProductId;
        this.skuTmaChannelProductId = skuTmaChannelProductId;
        this.skuTmaChannelProductStatus = skuTmaChannelProductStatus;
        this.productType = productType;
        this.productSubType = productSubType;
        this.approvedForSolutionIds = approvedSolutionIds;
        this.unApprovedSolutionIds = unApprovedSolutionIds;
        this.tagCodes = tagCodes;
        this.tagLabels = tagLabels;
        this.skuFactorCodes = skuFactorCodes;
        this.tagCodesByType = tagCodesByType;
        this.tagLabelsByType = tagLabelsByType;
        this.tagLabelsByLang = tagLabelsByLang;
    }

    public void init() {
        this.setCreatedTimestamp();
        this.setLastModifiedTimestamp();
    }

    public void updateBasicDetails(TMAChannelProductSKU tmaChannelProductSKU) {
        if(tmaChannelProductSKU.getCategoryCodes()!=null){
            if(this.getCategoryCodes() != null){
                this.getCategoryCodes().clear();
                this.getCategoryCodes().addAll(tmaChannelProductSKU.getCategoryCodes());
            }
            else{
                this.setCategoryCodes(tmaChannelProductSKU.getCategoryCodes());
            }
        }
        if(tmaChannelProductSKU.getCategoryNames()!=null){
            if(this.getCategoryNames() != null){
                this.getCategoryNames().clear();
                this.getCategoryNames().addAll(tmaChannelProductSKU.getCategoryNames());
            }
            else{
                this.setCategoryNames(tmaChannelProductSKU.getCategoryNames());
            }
        }
        if(tmaChannelProductSKU.getSubCategoryCodes()!=null){
            if(this.getSubCategoryCodes() != null){
                this.getSubCategoryCodes().clear();
                this.getSubCategoryCodes().addAll(tmaChannelProductSKU.getSubCategoryCodes());
            }
            else{
                this.setSubCategoryCodes(tmaChannelProductSKU.getSubCategoryCodes());
            }
        }
        if(tmaChannelProductSKU.getSubCategoryNames()!=null){
            if(this.getSubCategoryNames() != null){
                this.getSubCategoryNames().clear();
                this.getSubCategoryNames().addAll(tmaChannelProductSKU.getSubCategoryNames());
            }
            else{
                this.setSubCategoryNames(tmaChannelProductSKU.getSubCategoryNames());
            }
        }
        if(tmaChannelProductSKU.getGenericProductId()!=null){
            this.setGenericProductId(tmaChannelProductSKU.getGenericProductId());
        }
        if(tmaChannelProductSKU.getGenericProductCode()!=null){
            this.setGenericProductCode(tmaChannelProductSKU.getGenericProductCode());
        }
        if(tmaChannelProductSKU.getGenericProductName()!=null){
            this.setGenericProductName(tmaChannelProductSKU.getGenericProductName());
        }
        if(tmaChannelProductSKU.getGenericProductVarietyId()!=null){
            this.setGenericProductVarietyId(tmaChannelProductSKU.getGenericProductVarietyId());
        }
        if(tmaChannelProductSKU.getGenericProductVarietyCode()!=null){
            this.setGenericProductVarietyCode(tmaChannelProductSKU.getGenericProductVarietyCode());
        }
        if(tmaChannelProductSKU.getGenericProductVarietyName()!=null){
            this.setGenericProductVarietyName(tmaChannelProductSKU.getGenericProductVarietyName());
        }
        this.setLastModifiedTimestamp();
    }

    public void updateTagDetails(TMAChannelProductSKU skuToBeUpdated) {
        if(skuToBeUpdated.getTagCodes()!=null){
            if(this.getTagCodes() != null){
                this.getTagCodes().clear();
                this.getTagCodes().addAll(skuToBeUpdated.getTagCodes());
            }
            else{
                this.setTagCodes(skuToBeUpdated.getTagCodes());
            }
        }
        if(skuToBeUpdated.getTagLabels()!=null){
            if(this.getTagLabels() != null){
                this.getTagLabels().clear();
                this.getTagLabels().addAll(skuToBeUpdated.getTagLabels());
            }
            else{
                this.setTagLabels(skuToBeUpdated.getTagLabels());
            }
        }
        if (skuToBeUpdated.getTagCodesByType() != null) {
            if (this.getTagCodesByType() != null) {
                this.getTagCodesByType().clear();
                this.getTagCodesByType().putAll(skuToBeUpdated.getTagCodesByType());
            } else {
                this.setTagCodesByType(new HashMap<>(skuToBeUpdated.getTagCodesByType()));
            }
        }

        if (skuToBeUpdated.getTagLabelsByType() != null) {
            if (this.getTagLabelsByType() != null) {
                this.getTagLabelsByType().clear();
                this.getTagLabelsByType().putAll(skuToBeUpdated.getTagLabelsByType());
            } else {
                this.setTagLabelsByType(new HashMap<>(skuToBeUpdated.getTagLabelsByType()));
            }
        }

        if (skuToBeUpdated.getTagLabelsByLang() != null) {
            if (this.getTagLabelsByLang() != null) {
                this.getTagLabelsByLang().clear();
                this.getTagLabelsByLang().putAll(skuToBeUpdated.getTagLabelsByLang());
            } else {
                this.setTagLabelsByLang(new HashMap<>(skuToBeUpdated.getTagLabelsByLang()));
            }
        }
        this.setLastModifiedTimestamp();
    }
}
