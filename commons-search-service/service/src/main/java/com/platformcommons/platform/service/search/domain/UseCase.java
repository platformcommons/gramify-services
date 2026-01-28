package com.platformcommons.platform.service.search.domain;

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
@SolrDocument(collection = "use_case")
public class UseCase {


    @Id
    @Field
    private Long id;

    @Field
    private Long authorId;

    @Field
    @Indexed
    private String authorName;

    @Field
    private String name;

    @Field
    private String logo;

    @Field
    private Set<String> domainCodes;

    @Field
    private Set<String> subDomainCodes;


    @Field
    private String description;


    @Field
    private Long sequence;



    @Dynamic
    @Field("FEATURE_*")
    private Map<String, String> features;


    @Builder
    public UseCase(Long id, Long authorId, String authorName, String name, String logo,
                   Set<String> domainCodes, Set<String> subDomainCodes, Map<String, String> features,String description,
                   Long sequence) {
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.name = name;
        this.logo  = logo;
        this.domainCodes = domainCodes;
        this.subDomainCodes = subDomainCodes;
        this.features = features;
        this.description = description;
        this.sequence = sequence;
    }
}
