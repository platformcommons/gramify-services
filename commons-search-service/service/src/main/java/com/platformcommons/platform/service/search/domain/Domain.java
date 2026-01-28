package com.platformcommons.platform.service.search.domain;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "domain")
public class Domain {

    @Id
    @Field
    private Long id;

    @Field
    @Indexed(name = "name", type = "string")
    private String name;

    @Field
    @Indexed(name = "code", type = "string")
    private String code;

    @Field
    @Indexed(name = "context", type = "string")
    private String context;


    @Field
    private Boolean isRoot;

    @Field
    private Set<String> subDomainCodes;
    @Field
    @Indexed
    private Long tenantId;

    @Builder
    public Domain(Long id, String name, String code, Boolean isRoot, String context, Set<String> subDomainCodes, Long tenantId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.isRoot = isRoot;
        this.context = context;
        this.subDomainCodes = subDomainCodes;
        this.tenantId = tenantId;
    }

    public void update(Domain domain) {
        if(domain.getName()!=null){
            this.name = domain.getName();
        }
    }
}
