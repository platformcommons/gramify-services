package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "post_content")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PostContent extends BaseTransactionalEntity implements DomainEntity<PostContent> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_data", columnDefinition = "LONGTEXT")
    private String contentData;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "content_mime_type")
    private String contentMimeType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, updatable = false)
    private Post post;


    @Builder
    public PostContent(String uuid, Long tenantId, Boolean isActive, String inactiveReason, Long appCreatedTimestamp,
                       Long appLastModifiedTimestamp,
                       Long id, String contentData, String contentType, String contentMimeType, Post post) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.contentData = contentData;
        this.contentType = contentType;
        this.contentMimeType = contentMimeType;
        this.post = post;
    }
}
