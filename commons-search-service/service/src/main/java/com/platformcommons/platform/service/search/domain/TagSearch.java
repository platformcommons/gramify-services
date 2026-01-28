package com.platformcommons.platform.service.search.domain;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "tag_search")
public class TagSearch extends SolrBaseEntity{

    @Id
    @Field
    @Indexed
    private String id;

    @Field
    @Indexed
    private String name;

    @Field
    private String tagCode;

    @Field
    @Indexed
    private String categoryCode;

    @Field
    @Indexed
    private String subCategoryCode;

    @Field
    @Indexed
    private String context;

    @Field
    @Indexed
    private String type;


    @Dynamic
    @Field("LABEL_*")
    private Map<String, String> labels;


    @Builder
    public TagSearch(Long createdTimestamp, Long lastModifiedTimestamp, String id, String name,String tagCode ,String categoryCode,
                     String subCategoryCode, String context,String type, Map<String, String> labels) {
        super(createdTimestamp, lastModifiedTimestamp);
        this.id = id;
        this.name = name;
        this.tagCode= tagCode;
        this.categoryCode = categoryCode;
        this.subCategoryCode = subCategoryCode;
        this.context = context;
        this.type = type;
        this.labels = labels;
    }

    public void update(TagSearch toBeUpdated) {
        if(toBeUpdated.getName()!=null){
            this.name = toBeUpdated.getName();
        }
        if(toBeUpdated.getTagCode()!=null){
            this.tagCode = toBeUpdated.getTagCode();
        }
        if(toBeUpdated.getCategoryCode()!=null){
            this.categoryCode = toBeUpdated.getCategoryCode();
        }
        if(toBeUpdated.getSubCategoryCode()!=null){
            this.subCategoryCode = toBeUpdated.getSubCategoryCode();
        }
        if(toBeUpdated.getContext()!=null){
            this.context = toBeUpdated.getContext();
        }
        if(toBeUpdated.getLabels()!=null){
            this.labels = toBeUpdated.getLabels();
        }
        if(toBeUpdated.getLastModifiedTimestamp()!=null){
            this.setLastModifiedTimestamp();
        }
        if(toBeUpdated.getType()!=null){
            this.type = toBeUpdated.getType();
        }
    }
}
