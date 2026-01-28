package com.platformcommons.platform.service.search.domain;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "tma_channel_solution")
public class TMAChannelSolution extends SolrBaseEntity {

    @Id
    @Field
    private String id;

    @Field
    private Long traderId;
    @Field
    private String traderName;

    @Field
    private String traderIconURL;

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
    public Long tenantId;

    @Field
    private Long baseSolutionId;

    @Field
    public Boolean isActive;

    @Dynamic
    @Field("TITLE_*")
    private Map<String, String> titles;

    @Field
    private Set<String> tagCodes;
    @Field
    private Set<String> tagLabels;

    // Tag codes grouped by type (e.g., CATEGORY -> [CAT01, CAT02])
    @Dynamic
    @Field("TAG_CODE_BY_TYPE_*")
    private Map<String, List<String>> tagCodesByType;

    // Tag labels grouped by type (e.g., CATEGORY -> ["Agriculture", "Farming"])
    @Dynamic
    @Field("TAG_LABEL_BY_TYPE_*")
    private Map<String, List<String>> tagLabelsByType;

    // Tag labels grouped by language (e.g., ENG -> ["Agriculture", "Farming"])
    @Dynamic
    @Field("TAG_LABEL_*")
    private Map<String, List<String>> tagLabelsByLang;

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
    private String traderCenterSolutionStatus;

    @Field
    private String tmaChannelSolutionStatus;

    @Field
    private String solutionType;

    @Field
    private String solutionClass;

    @Field
    private Long solutionId;


    @Builder
    public TMAChannelSolution(Long createdTimestamp, Long lastModifiedTimestamp,
                              String id, Long traderId, String traderName,String traderIconURL,Long marketId, String marketCode,String marketName,
                              Long channelId, String channelCode, String channelName,Long tenantId, Boolean isActive, Map<String, String> titles, Set<String> categoryCodes, Set<String> categoryNames,
                              Set<String> subCategoryCodes, Set<String> subCategoryNames, Long genericProductId, String genericProductCode,
                              String genericProductName, Long genericProductVarietyId, String genericProductVarietyCode, String genericProductVarietyName, Long baseSolutionId,String traderCenterSolutionStatus,
                              String tmaChannelSolutionStatus,Long solutionId, Set<String> tagCodes, Set<String> tagLabels, String solutionType, String solutionClass,
                              Map<String, List<String>> tagCodesByType, Map<String, List<String>> tagLabelsByType, Map<String, List<String>> tagLabelsByLang) {
        super(createdTimestamp, lastModifiedTimestamp);
        this.id = id;
        this.traderId = traderId;
        this.traderName = traderName;
        this.traderIconURL = traderIconURL;
        this.marketId = marketId;
        this.marketCode = marketCode;
        this.marketName = marketName;
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.channelName = channelName;
        this.tenantId = tenantId;
        this.isActive = isActive;
        this.titles = titles;
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
        this.baseSolutionId = baseSolutionId;
        this.traderCenterSolutionStatus = traderCenterSolutionStatus;
        this.tmaChannelSolutionStatus= tmaChannelSolutionStatus;
        this.solutionId = solutionId;
        this.tagCodes  = tagCodes;
        this.tagLabels = tagLabels;
        this.solutionType = solutionType;
        this.solutionClass = solutionClass;
        this.tagCodesByType = tagCodesByType;
        this.tagLabelsByType = tagLabelsByType;
        this.tagLabelsByLang = tagLabelsByLang;
    }

    public void init() {
        this.setCreatedTimestamp();
        this.setLastModifiedTimestamp();
    }

    public void update(TMAChannelSolution tmaChannelSolution) {
        if(tmaChannelSolution.getTitles()!=null){
            this.getTitles().clear();
            this.getTitles().putAll(tmaChannelSolution.getTitles());
        }
        if(tmaChannelSolution.getTraderCenterSolutionStatus()!=null){
            this.setTraderCenterSolutionStatus(tmaChannelSolution.getTraderCenterSolutionStatus());
        }
        if(tmaChannelSolution.getTmaChannelSolutionStatus()!=null){
            this.setTmaChannelSolutionStatus(tmaChannelSolution.getTmaChannelSolutionStatus());
        }
        if(tmaChannelSolution.getSolutionId()!=null){
            this.setSolutionId(tmaChannelSolution.getSolutionId());
        }
        if(tmaChannelSolution.getTagCodes()!=null){
            if(this.getTagCodes() != null){
                this.getTagCodes().clear();
                this.getTagCodes().addAll(tmaChannelSolution.getTagCodes());
            }
            else{
                this.setTagCodes(tmaChannelSolution.getTagCodes());
            }
        }
        if(tmaChannelSolution.getTagLabels()!=null){
            if(this.getTagLabels() != null){
                this.getTagLabels().clear();
                this.getTagLabels().addAll(tmaChannelSolution.getTagLabels());
            }
            else{
                this.setTagLabels(tmaChannelSolution.getTagLabels());
            }
        }
        if(tmaChannelSolution.getSolutionType()!=null){
            this.setSolutionType(tmaChannelSolution.getSolutionType());
        }
        if(tmaChannelSolution.getSolutionClass()!=null){
            this.setSolutionClass(tmaChannelSolution.getSolutionClass());
        }
        if(tmaChannelSolution.getCategoryCodes()!=null){
            if(this.getCategoryCodes() != null){
                this.getCategoryCodes().clear();
                this.getCategoryCodes().addAll(tmaChannelSolution.getCategoryCodes());
            }
            else{
                this.setCategoryCodes(tmaChannelSolution.getCategoryCodes());
            }
        }
        if(tmaChannelSolution.getCategoryNames()!=null){
            if(this.getCategoryNames() != null){
                this.getCategoryNames().clear();
                this.getCategoryNames().addAll(tmaChannelSolution.getCategoryNames());
            }
            else{
                this.setCategoryNames(tmaChannelSolution.getCategoryNames());
            }
        }
        if(tmaChannelSolution.getSubCategoryCodes()!=null){
            if(this.getSubCategoryCodes() != null){
                this.getSubCategoryCodes().clear();
                this.getSubCategoryCodes().addAll(tmaChannelSolution.getSubCategoryCodes());
            }
            else{
                this.setSubCategoryCodes(tmaChannelSolution.getSubCategoryCodes());
            }
        }
        if(tmaChannelSolution.getSubCategoryNames()!=null){
            if(this.getSubCategoryNames() != null){
                this.getSubCategoryNames().clear();
                this.getSubCategoryNames().addAll(tmaChannelSolution.getSubCategoryNames());
            }
            else{
                this.setSubCategoryNames(tmaChannelSolution.getSubCategoryNames());
            }
        }
        if(tmaChannelSolution.getGenericProductId()!=null){
            this.setGenericProductId(tmaChannelSolution.getGenericProductId());
        }
        if(tmaChannelSolution.getGenericProductCode()!=null){
            this.setGenericProductCode(tmaChannelSolution.getGenericProductCode());
        }
        if(tmaChannelSolution.getGenericProductName()!=null){
            this.setGenericProductName(tmaChannelSolution.getGenericProductName());
        }
        if(tmaChannelSolution.getGenericProductVarietyId()!=null){
            this.setGenericProductVarietyId(tmaChannelSolution.getGenericProductVarietyId());
        }
        if(tmaChannelSolution.getGenericProductVarietyCode()!=null){
            this.setGenericProductVarietyCode(tmaChannelSolution.getGenericProductVarietyCode());
        }
        if(tmaChannelSolution.getGenericProductVarietyName()!=null){
            this.setGenericProductVarietyName(tmaChannelSolution.getGenericProductVarietyName());
        }
        if (tmaChannelSolution.getTagCodesByType() != null) {
            if (this.getTagCodesByType() != null) {
                this.getTagCodesByType().clear();
                this.getTagCodesByType().putAll(tmaChannelSolution.getTagCodesByType());
            } else {
                this.setTagCodesByType(new HashMap<>(tmaChannelSolution.getTagCodesByType()));
            }
        }

        if (tmaChannelSolution.getTagLabelsByType() != null) {
            if (this.getTagLabelsByType() != null) {
                this.getTagLabelsByType().clear();
                this.getTagLabelsByType().putAll(tmaChannelSolution.getTagLabelsByType());
            } else {
                this.setTagLabelsByType(new HashMap<>(tmaChannelSolution.getTagLabelsByType()));
            }
        }

        if (tmaChannelSolution.getTagLabelsByLang() != null) {
            if (this.getTagLabelsByLang() != null) {
                this.getTagLabelsByLang().clear();
                this.getTagLabelsByLang().putAll(tmaChannelSolution.getTagLabelsByLang());
            } else {
                this.setTagLabelsByLang(new HashMap<>(tmaChannelSolution.getTagLabelsByLang()));
            }
        }
        this.setLastModifiedTimestamp();
    }

    public void updateTagDetails(TMAChannelSolution solutionToBeUpdated) {
        if(solutionToBeUpdated.getTagCodes()!=null){
            if(this.getTagCodes() != null){
                this.getTagCodes().clear();
                this.getTagCodes().addAll(solutionToBeUpdated.getTagCodes());
            }
            else{
                this.setTagCodes(solutionToBeUpdated.getTagCodes());
            }
        }
        if(solutionToBeUpdated.getTagLabels()!=null){
            if(this.getTagLabels() != null){
                this.getTagLabels().clear();
                this.getTagLabels().addAll(solutionToBeUpdated.getTagLabels());
            }
            else{
                this.setTagLabels(solutionToBeUpdated.getTagLabels());
            }
        }
        if (solutionToBeUpdated.getTagCodesByType() != null) {
            if (this.getTagCodesByType() != null) {
                this.getTagCodesByType().clear();
                this.getTagCodesByType().putAll(solutionToBeUpdated.getTagCodesByType());
            } else {
                this.setTagCodesByType(new HashMap<>(solutionToBeUpdated.getTagCodesByType()));
            }
        }

        if (solutionToBeUpdated.getTagLabelsByType() != null) {
            if (this.getTagLabelsByType() != null) {
                this.getTagLabelsByType().clear();
                this.getTagLabelsByType().putAll(solutionToBeUpdated.getTagLabelsByType());
            } else {
                this.setTagLabelsByType(new HashMap<>(solutionToBeUpdated.getTagLabelsByType()));
            }
        }

        if (solutionToBeUpdated.getTagLabelsByLang() != null) {
            if (this.getTagLabelsByLang() != null) {
                this.getTagLabelsByLang().clear();
                this.getTagLabelsByLang().putAll(solutionToBeUpdated.getTagLabelsByLang());
            } else {
                this.setTagLabelsByLang(new HashMap<>(solutionToBeUpdated.getTagLabelsByLang()));
            }
        }
        this.setLastModifiedTimestamp();
    }
}
