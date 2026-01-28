package com.platformcommons.platform.service.search.domain;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "tma_channel_product")
public class TMAChannelProduct extends SolrBaseEntity {

    @Id
    @Field
    private String id;
    @Field
    private Long traderId;
    @Field
    private String traderName;
    @Field
    private Long marketId;
    @Field
    private String marketCode;
    @Field
    private Long channelId;
    @Field
    private String channelCode;
    @Field
    private Long productId;//
    @Field
    private String productCode;//
    @Field
    private String productName;//
    @Field
    private Long defaultSKUId;
    @Field
    private String defaultSKUName;

    @Field
    private Double defaultSKUPrice;
    @Field
    private Double defaultSKUAvailableQuantity;

    @Field
    private Long genericProductId;//
    @Field
    private String genericProductCode;
    @Field
    private Long genericProductVarietyId;//
    @Field
    private String genericProductVarietyCode;
    @Field
    private Double totalAvailableQuantity;
    @Field
    private String quantityUOM;
    @Field
    private String traderIconURL;
    @Field
    private Double minPrice;
    @Field
    private Double maxPrice;
    @Field
    private String currencyCode;
    @Field
    private Long tenantId;
    @Field
    private Boolean isActive;
    @Field
    private Set<String> categoryCodes;
    @Field
    private Set<String> categoryNames;
    @Field
    private Set<String> subCategoryCodes;
    @Field
    private Set<String> subCategoryNames;
    @Field
    private Long defaultSKUTmaChannelProductId;

    @Field
    private String tmaChannelProductStatus;

    @Field
    private String productType;

    @Field
    private String productSubType;

    @Field
    private Set<Long> approvedForSolutionIds;

    @Field
    private Set<Long> unApprovedSolutionIds;

    @Field
    private Set<Long> linkedSolutionResourceIds;

    @Builder
    public TMAChannelProduct(Long createdTimestamp, Long lastModifiedTimestamp, String id, Long traderId, String traderName, Long marketId, String marketCode, Long channelId,
                             String channelCode, Long productId, String productCode, String productName, Long defaultSKUId,
                             String defaultSKUName, Double defaultSKUPrice, Double defaultSKUAvailableQuantity, Long genericProductId,
                             String genericProductCode, Long genericProductVarietyId, String genericProductVarietyCode,
                             Double totalAvailableQuantity, String quantityUOM, String traderIconURL, Double minPrice,
                             Double maxPrice, String currencyCode, Long tenantId, Boolean isActive, Set<String> categoryCodes,
                             Set<String> categoryNames, Set<String> subCategoryCodes, Set<String> subCategoryNames,
                             Long defaultSKUTmaChannelProductId,String tmaChannelProductStatus,String productType, String productSubType, Set<Long> approvedForSolutionIds,
                             Set<Long> unApprovedSolutionIds, Set<Long> linkedSolutionResourceIds) {
        super(createdTimestamp, lastModifiedTimestamp);
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
        this.tmaChannelProductStatus = tmaChannelProductStatus;
        this.productType = productType;
        this.productSubType = productSubType;
        this.approvedForSolutionIds = approvedForSolutionIds;
        this.unApprovedSolutionIds = unApprovedSolutionIds;
        this.linkedSolutionResourceIds = linkedSolutionResourceIds;
    }

    public void init() {
        this.setCreatedTimestamp();
        this.setLastModifiedTimestamp();
    }

    public void update(TMAChannelProduct tmaChannelProduct, Boolean updateDefaultSKU) {
        if(updateDefaultSKU){
            this.defaultSKUId = tmaChannelProduct.getDefaultSKUId();
            this.defaultSKUName = tmaChannelProduct.getDefaultSKUName();
            this.defaultSKUPrice = tmaChannelProduct.getDefaultSKUPrice();
            this.defaultSKUAvailableQuantity = tmaChannelProduct.getDefaultSKUAvailableQuantity();
            this.defaultSKUTmaChannelProductId = tmaChannelProduct.defaultSKUTmaChannelProductId;
        }
        this.setLastModifiedTimestamp();
        this.productName = tmaChannelProduct.getProductName()!=null? tmaChannelProduct.getProductName() : this.productName;
        this.productType = tmaChannelProduct.getProductType()!=null? tmaChannelProduct.getProductType() : this.productType;
        this.productSubType = tmaChannelProduct.getProductSubType()!=null? tmaChannelProduct.getProductSubType() : this.productSubType;
        this.maxPrice = tmaChannelProduct.getMaxPrice()!=null? tmaChannelProduct.getMaxPrice() : this.maxPrice;
        this.minPrice = tmaChannelProduct.getMinPrice()!=null? tmaChannelProduct.getMinPrice() : this.minPrice;
        this.totalAvailableQuantity = tmaChannelProduct.getTotalAvailableQuantity()!=null ? tmaChannelProduct.getTotalAvailableQuantity() : this.getTotalAvailableQuantity();
        if(this.getApprovedForSolutionIds()!=null && !this.getApprovedForSolutionIds().isEmpty()  && tmaChannelProduct.getApprovedForSolutionIds()!=null){
            this.getApprovedForSolutionIds().addAll(tmaChannelProduct.getApprovedForSolutionIds());
        }
        if(this.getUnApprovedSolutionIds()!=null && !this.getUnApprovedSolutionIds().isEmpty() && tmaChannelProduct.getUnApprovedSolutionIds()!=null){
            this.getUnApprovedSolutionIds().addAll(tmaChannelProduct.getUnApprovedSolutionIds());
        }
    }

    public void updateInfo(Set<TMAChannelProductSKU> fetchedProductSKUS) {
        if (fetchedProductSKUS != null && !fetchedProductSKUS.isEmpty()) {
            this.maxPrice = fetchedProductSKUS.stream().filter(Objects::nonNull)
                    .mapToDouble(sku -> sku.getSkuMRP() != null ? sku.getSkuMRP() : 0.0).max().orElse(this.maxPrice);
            this.minPrice = fetchedProductSKUS.stream().filter(Objects::nonNull)
                    .mapToDouble(sku -> sku.getSkuMRP() != null ? sku.getSkuMRP() : 0.0).min().orElse(this.minPrice);
            this.totalAvailableQuantity = fetchedProductSKUS.stream().filter(Objects::nonNull)
                    .mapToDouble(sku -> sku.getAvailableQuantity() != null ? sku.getAvailableQuantity() : 0.0).sum();
        }
    }


    public void updateBasicDetails(TMAChannelProduct tmaChannelProduct) {
        if(tmaChannelProduct.getCategoryCodes()!=null){
            if(this.getCategoryCodes() != null){
                this.getCategoryCodes().clear();
                this.getCategoryCodes().addAll(tmaChannelProduct.getCategoryCodes());
            }
            else{
                this.setCategoryCodes(tmaChannelProduct.getCategoryCodes());
            }
        }
        if(tmaChannelProduct.getCategoryNames()!=null){
            if(this.getCategoryNames() != null){
                this.getCategoryNames().clear();
                this.getCategoryNames().addAll(tmaChannelProduct.getCategoryNames());
            }
            else{
                this.setCategoryNames(tmaChannelProduct.getCategoryNames());
            }
        }
        if(tmaChannelProduct.getSubCategoryCodes()!=null){
            if(this.getSubCategoryCodes() != null){
                this.getSubCategoryCodes().clear();
                this.getSubCategoryCodes().addAll(tmaChannelProduct.getSubCategoryCodes());
            }
            else{
                this.setSubCategoryCodes(tmaChannelProduct.getSubCategoryCodes());
            }
        }
        if(tmaChannelProduct.getSubCategoryNames()!=null){
            if(this.getSubCategoryNames() != null){
                this.getSubCategoryNames().clear();
                this.getSubCategoryNames().addAll(tmaChannelProduct.getSubCategoryNames());
            }
            else{
                this.setSubCategoryNames(tmaChannelProduct.getSubCategoryNames());
            }
        }
        if(tmaChannelProduct.getGenericProductId()!=null){
            this.setGenericProductId(tmaChannelProduct.getGenericProductId());
        }
        if(tmaChannelProduct.getGenericProductCode()!=null){
            this.setGenericProductCode(tmaChannelProduct.getGenericProductCode());
        }
        if(tmaChannelProduct.getGenericProductVarietyId()!=null){
            this.setGenericProductVarietyId(tmaChannelProduct.getGenericProductVarietyId());
        }
        if(tmaChannelProduct.getGenericProductVarietyCode()!=null){
            this.setGenericProductVarietyCode(tmaChannelProduct.getGenericProductVarietyCode());
        }
        this.setLastModifiedTimestamp();
    }
}
