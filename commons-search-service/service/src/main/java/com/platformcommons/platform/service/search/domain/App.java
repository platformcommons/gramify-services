package com.platformcommons.platform.service.search.domain;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "app")
public class App {

    @Id
    @Field
    private Long id;

    @Field
    @Indexed
    private String slug;

    @Field
    private Long authorId;

    @Field
    @Indexed
    private String authorName;

    @Field
    @Indexed
    private String name;

    @Field
    private String logo;

    @Field
    private Set<String>  domainCodes;

    @Field
    private Set<String> subDomainCodes;


    @Field
    private Set<Long> useCaseIds;

    @Field
    private Double rating;


    @Field
    private String description;


    @Field
    private Set<String> appKeywords;

    @Field
    private Set<String> useCaseKeywords;


    @Field
    private Set<String> useCaseNames;

    @Dynamic
    @Field("FEATURE_*")
    private Map<String, String> features;


    @Builder
    public App(Long id, String slug,Long authorId, String authorName, String name, String logo, Set<String> domainCodes,
               Set<String> subDomainCodes, Set<Long> useCaseIds, Map<String, String> features, Double rating,String description,
               Set<String> appKeywords,Set<String> useCaseKeywords,Set<String> useCaseNames) {
        this.id = id;
        this.slug = slug;
        this.authorId = authorId;
        this.authorName = authorName;
        this.name = name;
        this.logo = logo;
        this.domainCodes = domainCodes;
        this.subDomainCodes = subDomainCodes;
        this.useCaseIds = useCaseIds;
        this.features = features;
        this.rating = rating;
        this.description = description;
        this.appKeywords = appKeywords;
        this.useCaseKeywords = useCaseKeywords;
        this.useCaseNames = useCaseNames;
    }
}
