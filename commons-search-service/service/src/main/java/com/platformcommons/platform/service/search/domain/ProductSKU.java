package com.platformcommons.platform.service.search.domain;


import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "ProductSKU")
public class ProductSKU {

    @Id
    @Field
    private Long id;

    @Field
    private Long productId;

    @Field
    private String productCode;

    @Indexed
    @Field
    private Set<String> productNames;

    @Field
    private String productName_en;

    @Field
    private String productName_hin;

    @Field
    private Long genericProductId;

    @Indexed
    @Field
    private Set<String> genericProductNames;

    @Field
    private String genericProductName_en;

    @Field
    private String genericProductName_hin;

    @Field
    private Long genericProductVarietyId;

    @Indexed
    @Field
    private Set<String> genericProductVarietyNames;

    @Field
    private String genericProductVarietyName_hin;

    @Field
    private String genericProductVarietyName_en;

    @Field
    private Long productSKUId;

    @Indexed
    @Field
    private Set<String> productSKUNames;

    @Field
    private String productSKUName_hin;

    @Field
    private String productSKUName_en;

    @Field
    private Long traderCenterProductId;

    @Field
    private Long tmaChannelProductId;

    @Field
    private Set<String> categoryCodes;

    @Field
    private Set<String> categoryNames;

    @Field
    private Set<String> subCategoryCodes;

    @Field
    private Set<String> subCategoryNames;

    @Field
    private Long traderId;

    @Field
    private String traderName;

    @Field
    private Long channelId;

    @Field
    private String channelName;

    @Field
    private String channelCode;

    @Field
    private Long marketId;

    @Field
    private String marketName;

    @Field
    private String marketCode;

    @Field
    private Long availableQuantity;

    @Field
    private Set<String> serviceableArea;

    @Field
    private  Long tenantId;

    @Field
    private Boolean isActive;

    @Builder
    public ProductSKU(Long id, Long productId, String productCode, Set<String> productNames, String productName_en,
                      String productName_hin, Long genericProductId, Set<String> genericProductNames, String genericProductName_en,
                      String genericProductName_hin, Long genericProductVarietyId, Set<String> genericProductVarietyNames,
                      String genericProductVarietyName_hin, String genericProductVarietyName_en, Long productSKUId,
                      Set<String> productSKUNames, String productSKUName_hin, String productSKUName_en, Long traderCenterProductId,
                      Long tmaChannelProductId, Set<String> categoryCodes, Set<String> categoryNames, Set<String> subCategoryCodes,
                      Set<String> subCategoryNames, Long traderId, String traderName, Long channelId, String channelName,
                      String channelCode, Long marketId, String marketName, String marketCode, Long availableQuantity,
                      Set<String> serviceableArea, Long tenantId, Boolean isActive) {
        this.id = id;
        this.productId = productId;
        this.productCode = productCode;
        this.productNames = productNames;
        this.productName_en = productName_en;
        this.productName_hin = productName_hin;
        this.genericProductId = genericProductId;
        this.genericProductNames = genericProductNames;
        this.genericProductName_en = genericProductName_en;
        this.genericProductName_hin = genericProductName_hin;
        this.genericProductVarietyId = genericProductVarietyId;
        this.genericProductVarietyNames = genericProductVarietyNames;
        this.genericProductVarietyName_hin = genericProductVarietyName_hin;
        this.genericProductVarietyName_en = genericProductVarietyName_en;
        this.productSKUId = productSKUId;
        this.productSKUNames = productSKUNames;
        this.productSKUName_hin = productSKUName_hin;
        this.productSKUName_en = productSKUName_en;
        this.traderCenterProductId = traderCenterProductId;
        this.tmaChannelProductId = tmaChannelProductId;
        this.categoryCodes = categoryCodes;
        this.categoryNames = categoryNames;
        this.subCategoryCodes = subCategoryCodes;
        this.subCategoryNames = subCategoryNames;
        this.traderId = traderId;
        this.traderName = traderName;
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelCode = channelCode;
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketCode = marketCode;
        this.availableQuantity = availableQuantity;
        this.serviceableArea = serviceableArea;
        this.tenantId = tenantId;
        this.isActive = isActive;
    }

    public void  init(){
        this.setId(this.getTmaChannelProductId());
    }
}
