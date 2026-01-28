package com.platformcommons.platform.service.search.domain;


import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "company_master_date_v2")
public class CompanyMasterDataV2 extends SolrBaseEntity{

    @Id
    @Field
    private String code;

    @Field
    private String name;

    @Field
    private String alias;

    @Field
    private String websiteUrl;

    @Field
    private String logoUrl;

    @Builder
    public CompanyMasterDataV2(Long createdTimestamp, Long lastModifiedTimestamp, String id, String name, String alias,
                               String websiteUrl, String logoUrl, String code) {
        super(createdTimestamp, lastModifiedTimestamp);
        this.name = name;
        this.alias = alias;
        this.websiteUrl = websiteUrl;
        this.logoUrl = logoUrl;
        this.code = code;
    }

    public void init() {
        this.setCreatedTimestamp();
        this.setLastModifiedTimestamp();
        code = createCompanyCode(name);
    }

    private String createCompanyCode(String name) {
        String nameModified=name.replace(" ","_");
        int consideredLength=nameModified.length()>=5 ? 5 : name.length();
        return nameModified.substring(0,consideredLength).toUpperCase()+new Date().getTime();
    }

}
