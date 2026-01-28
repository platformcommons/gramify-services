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
@SolrDocument(collection = "post")
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @Field
    private Long id;

    @Field
    private String tenantLogin;

    @Field
    private Long tenantId;

    @Field
    private String postedByActorType;

    @Field
    private Long postedByActorId;

    @Field
    private String postedByIconPic;

    @Field
    private String postedByName;

    @Field
    private String title;

    @Field
    private String payload;

    @Field
    private Date postTime;

    @Field
    private String postType;

    @Field
    private String postSubType;

    @Field
    private String appContext;

    @Field
    private String content;

    @Field
    private String status;

    @Field
    private Boolean isPublic;

    @Field
    private Boolean isActive;

}
