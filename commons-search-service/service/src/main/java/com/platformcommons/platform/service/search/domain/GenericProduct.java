package com.platformcommons.platform.service.search.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SolrDocument(collection = "generic_product")
public class GenericProduct extends SolrBaseEntity{

    @Id
    @Field
    private String id;
    @Field
    private Long genericProductId;
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
    public Long tenantId;
    @Field
    public Long marketId;
    @Field
    public String marketCode;
    @Field
    public String marketName;
    @Field
    private Set<String> keywords;
    @Field
    public Boolean isActive;

    @Builder
    public GenericProduct(String id,Long genericProductId, String genericProductCode, String genericProductName,
                          Set<String> categoryCodes, Set<String> categoryNames, Set<String> subCategoryCodes, Set<String> subCategoryNames,
                          Long tenantId, Long marketId, String marketCode, String marketName, Set<String> keywords, Boolean isActive) {
       this.id = id;
        this.genericProductId = genericProductId;
        this.genericProductCode = genericProductCode;
        this.genericProductName = genericProductName;
        this.categoryCodes = categoryCodes;
        this.categoryNames = categoryNames;
        this.subCategoryCodes = subCategoryCodes;
        this.subCategoryNames = subCategoryNames;
        this.tenantId =tenantId;
        this.marketId = marketId;
        this.marketCode = marketCode;
        this.marketName = marketName;
        this.keywords = keywords;
        this.isActive = isActive;
    }

    public void updateBasicDetails(GenericProduct toBeUpdated) {
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
        if(toBeUpdated.getGenericProductName()!=null){
            this.setGenericProductName(toBeUpdated.getGenericProductName());
        }
        this.setLastModifiedTimestamp();
    }
}
