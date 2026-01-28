package com.platformcommons.platform.service.search.domain;

import com.platformcommons.platform.service.search.dto.GPVSummaryDTO;
import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@SolrDocument(collection = "generic_product_variety")
public class GenericProductVariety extends SolrBaseEntity {


    @Id
    @Field
    public String id;

    @Field
    private Long genericProductVarietyId;

    @Field
    public String genericProductVarietyCode;
    @Field
    public String genericProductVarietyName;
    @Field
    public Long genericProductId;
    @Field
    public String genericProductCode;
    @Field
    public String genericProductName;
    @Field
    public Set<String> categoryCodes;
    @Field
    public Set<String> categoryNames;
    @Field
    public Set<String> subCategoryCodes;
    @Field
    public Set<String> subCategoryNames;
    @Field
    public Double maxPrice;
    @Field
    public Double minPrice;
    @Field
    public String currencyCode;

    @Field
    public Double totalAvailableQuantity;

    @Field
    public Double minAvailableQuantity;
    @Field
    public Double maxAvailableQuantity;
    @Field
    public String availableQuantityUOM;
    @Field
    public Long traderCount;
    @Field
    public Set<Long> traderIds;
    @Field
    public Set<String> productNames; // if required
    @Field
    public Long marketId;
    @Field
    public String marketCode;
    @Field
    public String marketName;
    @Field
    public Long channelId;
    @Field
    public String channelCode;
    @Field
    public String channelName;

    @Field
    public String iconImageURL; // doubtful

    @Field
    public Long tenantId;
    @Field
    public Boolean isActive;


    @Field
    private Set<String> keywords;

    @Field
    private Set<Long> solutionIds;


    @Field
    private String productType;

    @Field
    private String productSubType;


    @Dynamic
    @Field("SPECIFICATION_*")
    private Map<String, String> specifications;


    @Builder
    public GenericProductVariety(Long createdTimestamp, Long lastModifiedTimestamp, String id,
                                 Long genericProductVarietyId, String genericProductVarietyCode, String genericProductVarietyName,
                                 Long genericProductId, String genericProductCode, String genericProductName, Set<String> categoryCodes,
                                 Set<String> categoryNames, Set<String> subCategoryCodes, Set<String> subCategoryNames, Double maxPrice,
                                 Double minPrice, String currencyCode, Double totalAvailableQuantity, Double minAvailableQuantity,
                                 Double maxAvailableQuantity, String availableQuantityUOM, Long traderCount, Set<Long> traderIds,
                                 Set<String> productNames, Long marketId, String marketCode, String marketName, Long channelId,
                                 String channelCode, String channelName, String iconImageURL, Long tenantId, Boolean isActive,
                                 Set<String> keywords, Map<String, String> specifications,Set<Long> solutionIds,String productType, String productSubType) {
        super(createdTimestamp, lastModifiedTimestamp);
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
        this.totalAvailableQuantity = totalAvailableQuantity;
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
        this.specifications = specifications;
        this.solutionIds = solutionIds;
        this.productType = productType;
        this.productSubType = productSubType;
    }

    public void init() {
        this.setCreatedTimestamp();
        this.setLastModifiedTimestamp();
    }

    public void update(GenericProductVariety genericProductVariety) {
        this.setLastModifiedTimestamp();
        if(!this.traderIds.contains(genericProductVariety.getTraderIds().stream().findFirst().get())){
            this.traderIds.addAll(genericProductVariety.getTraderIds());
            this.traderCount= traderCount+1;    //if updateInfo() deleted uncomment this ,delete below set traderIds,traderCount
        }
//        this.traderIds = genericProductVariety.getTraderIds();
//        this.traderCount = genericProductVariety.getTraderCount();
        if (this.maxPrice == null || (genericProductVariety.getMaxPrice() != null && this.maxPrice < genericProductVariety.getMaxPrice())) {
            this.maxPrice = genericProductVariety.getMaxPrice();
        }
        if (this.minPrice == null || (genericProductVariety.getMinPrice() != null && this.minPrice < genericProductVariety.getMinPrice())) {
            this.minPrice = genericProductVariety.getMinPrice();
        }
        if(this.getCurrencyCode() == null){
            this.currencyCode = genericProductVariety.getCurrencyCode();
        }
        if (this.maxAvailableQuantity == null ||
                (genericProductVariety.getMaxAvailableQuantity() != null && this.maxAvailableQuantity < genericProductVariety.getMaxAvailableQuantity())) {
            this.maxAvailableQuantity = genericProductVariety.getMaxAvailableQuantity();
        }
        if (this.minAvailableQuantity == null ||
                (genericProductVariety.getMinAvailableQuantity() != null && this.minAvailableQuantity > genericProductVariety.getMinAvailableQuantity())) {
            this.minAvailableQuantity = genericProductVariety.getMinAvailableQuantity();
        }
        if (this.totalAvailableQuantity == null ||
                (genericProductVariety.getTotalAvailableQuantity() != null && this.totalAvailableQuantity < genericProductVariety.getTotalAvailableQuantity())) {
            this.totalAvailableQuantity = genericProductVariety.getTotalAvailableQuantity();
        }
        if(this.getAvailableQuantityUOM() == null){
            this.availableQuantityUOM = genericProductVariety.getAvailableQuantityUOM();
        }
        this.productNames = Optional.ofNullable(this.productNames).orElseGet(HashSet::new);
        Optional.ofNullable(genericProductVariety.getProductNames()).ifPresent(this.productNames::addAll);

        this.solutionIds = Optional.ofNullable(this.solutionIds).orElseGet(HashSet::new);
        Optional.ofNullable(genericProductVariety.getSolutionIds()).ifPresent(this.solutionIds::addAll);

    }

    public void updateInfo(Set<TMAChannelProduct> fetchedTMAChannelProducts) {
        if(fetchedTMAChannelProducts!=null && !fetchedTMAChannelProducts.isEmpty()) {
            this.minAvailableQuantity = fetchedTMAChannelProducts.stream().mapToDouble(TMAChannelProduct::getTotalAvailableQuantity).min().orElse(this.minAvailableQuantity);
            this.maxAvailableQuantity = fetchedTMAChannelProducts.stream().mapToDouble(TMAChannelProduct::getTotalAvailableQuantity).max().orElse(this.minAvailableQuantity);
            this.totalAvailableQuantity = fetchedTMAChannelProducts.stream().mapToDouble(TMAChannelProduct::getTotalAvailableQuantity).sum();
            this.maxPrice = fetchedTMAChannelProducts.stream().mapToDouble(TMAChannelProduct::getMaxPrice).max().orElse(this.maxPrice);
            this.minPrice = fetchedTMAChannelProducts.stream().mapToDouble(TMAChannelProduct::getMinPrice).min().orElse(this.minPrice);
            this.traderIds = fetchedTMAChannelProducts.stream().map(TMAChannelProduct::getTraderId).collect(Collectors.toCollection(LinkedHashSet::new));
            this.traderCount = (long) this.traderIds.size();
            this.setLastModifiedTimestamp();
        }
    }

    public void updateBasicDetails(GenericProductVariety toBeUpdated) {
        if(toBeUpdated.getCategoryCodes()!=null){
            if(this.getCategoryCodes() != null){
                this.getCategoryCodes().clear();
                this.getCategoryCodes().addAll(toBeUpdated.getCategoryCodes());
            }
            else{
                this.setCategoryCodes(toBeUpdated.getCategoryCodes());
            }
        }
        if(toBeUpdated.getCategoryNames()!=null){
            if(this.getCategoryNames() != null){
                this.getCategoryNames().clear();
                this.getCategoryNames().addAll(toBeUpdated.getCategoryNames());
            }
            else{
                this.setCategoryNames(toBeUpdated.getCategoryNames());
            }
        }
        if(toBeUpdated.getSubCategoryCodes()!=null){
            if(this.getSubCategoryCodes() != null){
                this.getSubCategoryCodes().clear();
                this.getSubCategoryCodes().addAll(toBeUpdated.getSubCategoryCodes());
            }
            else{
                this.setSubCategoryCodes(toBeUpdated.getSubCategoryCodes());
            }
        }
        if(toBeUpdated.getSubCategoryNames()!=null){
            if(this.getSubCategoryNames() != null){
                this.getSubCategoryNames().clear();
                this.getSubCategoryNames().addAll(toBeUpdated.getSubCategoryNames());
            }
            else{
                this.setSubCategoryNames(toBeUpdated.getSubCategoryNames());
            }
        }
        if(toBeUpdated.getGenericProductId()!=null){
            this.setGenericProductId(toBeUpdated.getGenericProductId());
        }
        if(toBeUpdated.getGenericProductCode()!=null){
            this.setGenericProductCode(toBeUpdated.getGenericProductCode());
        }
        if(toBeUpdated.getGenericProductName()!=null){
            this.setGenericProductName(toBeUpdated.getGenericProductName());
        }
        if(toBeUpdated.getGenericProductVarietyId()!=null){
            this.setGenericProductVarietyId(toBeUpdated.getGenericProductVarietyId());
        }
        if(toBeUpdated.getGenericProductVarietyCode()!=null){
            this.setGenericProductVarietyCode(toBeUpdated.getGenericProductVarietyCode());
        }
        if(toBeUpdated.getGenericProductVarietyName()!=null){
            this.setGenericProductVarietyName(toBeUpdated.getGenericProductVarietyName());
        }
        this.setLastModifiedTimestamp();
    }

    public void updateSummary(GPVSummaryDTO gpvSummaryDTO) {
        if(gpvSummaryDTO.getTraderIds()!=null){
            this.traderIds = Arrays.stream(gpvSummaryDTO.getTraderIds().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Long::valueOf)
                    .collect(Collectors.toSet());
        }

        if(gpvSummaryDTO.getProductName()!=null){
            this.productNames = Arrays.stream(gpvSummaryDTO.getProductName().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());
        }
        this.traderCount = gpvSummaryDTO.getTraderCount();
        this.setLastModifiedTimestamp();
    }

}
