package com.platformcommons.platform.service.search.domain;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "generic_solution")
public class GenericSolution extends SolrBaseEntity{

    @Id
    @Field
    @Indexed
    private String id;

    @Dynamic
    @Field("TITLE_*")
    private Map<String, String> titles;

    @Field
    private Set<String> tagCodes;

    @Field
    private Set<String> tagLabels;

    @Field
    public Set<String> categoryCodes;
    @Field
    public Set<String> categoryNames;
    @Field
    public Set<String> subCategoryCodes;
    @Field
    public Set<String> subCategoryNames;
    @Field
    public Long genericProductId;
    @Field
    public String genericProductCode;
    @Field
    public String genericProductName;
    @Field
    public Long genericProductVarietyId;
    @Field
    public String genericProductVarietyCode;
    @Field
    public String genericProductVarietyName;

    @Field
    @Indexed
    private Long marketId;

    @Field
    @Indexed
    private String status;

    @Field
    private String solutionType;

    @Field
    @Indexed
    private  Long noOfChildSolutions;


    @Builder
    public GenericSolution(Long createdTimestamp, Long lastModifiedTimestamp, String id, Map<String, String> titles,
                           Set<String> tagCodes, Set<String> tagLabels, Set<String> categoryCodes, Set<String> categoryNames,
                           Set<String> subCategoryCodes, Set<String> subCategoryNames, Long genericProductId, String genericProductCode,
                           String genericProductName, Long genericProductVarietyId, String genericProductVarietyCode, String genericProductVarietyName,
                           Long marketId, String status, String solutionType, Long noOfChildSolutions) {
        super(createdTimestamp, lastModifiedTimestamp);
        this.id = id;
        this.titles = titles;
        this.tagCodes = tagCodes;
        this.tagLabels = tagLabels;
        this.categoryCodes = categoryCodes;
        this.categoryNames = categoryNames;
        this.subCategoryCodes = subCategoryCodes;
        this.subCategoryNames = subCategoryNames;
        this.genericProductId = genericProductId;
        this.genericProductCode = genericProductCode;
        this.genericProductName = genericProductName;
        this.genericProductVarietyId = genericProductVarietyId;
        this.genericProductVarietyCode = genericProductVarietyCode;
        this.genericProductVarietyName = genericProductVarietyName;
        this.marketId = marketId;
        this.status = status;
        this.solutionType = solutionType;
        this.noOfChildSolutions = noOfChildSolutions;
    }

    public void update(GenericSolution toBeUpdated) {
        if(toBeUpdated.getTitles()!=null){
            this.getTitles().clear();
            this.getTitles().putAll(toBeUpdated.getTitles());
        }
        if(toBeUpdated.getTagCodes()!=null){
            this.getTagCodes().clear();
            this.getTagCodes().addAll(toBeUpdated.getTagCodes());
        }
        if(toBeUpdated.getTagLabels()!=null){
            this.getTagLabels().clear();
            this.getTagLabels().addAll(toBeUpdated.getTagLabels());
        }
        if(toBeUpdated.getCategoryNames()!=null){
            this.categoryNames = toBeUpdated.getCategoryNames();
        }
        if(toBeUpdated.getSubCategoryNames()!=null){
            this.subCategoryNames = toBeUpdated.getSubCategoryNames();
        }
        if(toBeUpdated.getGenericProductName()!=null){
            this.genericProductName = toBeUpdated.getGenericProductName();
        }
        if(toBeUpdated.getMarketId()!=null){
            this.marketId = toBeUpdated.getMarketId();
        }
        if(toBeUpdated.getStatus()!=null){
            this.status = toBeUpdated.getStatus();
        }
        if(toBeUpdated.getSolutionType()!=null){
            this.solutionType = toBeUpdated.getSolutionType();
        }
        this.setLastModifiedTimestamp();
    }
}
