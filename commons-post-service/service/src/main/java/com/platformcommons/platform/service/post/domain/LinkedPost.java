package com.platformcommons.platform.service.post.domain;


import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "linked_post")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LinkedPost extends BaseTransactionalEntity implements DomainEntity<LinkedPost> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "linking_type")
    @NotNull(message = "linkingType must not be null")
    private String linkingType;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "targetPost must not be null")
    @JoinColumn(name = "source_post", referencedColumnName = "id")
    private Post sourcePost ;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "targetPost must not be null")
    @JoinColumn(name = "target_post", referencedColumnName = "id")
    private Post targetPost;

    @Builder
    public LinkedPost(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp,
                      Long appLastModifiedTimestamp, Long id, String linkingType, Post sourcePost, Post targetPost) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.linkingType = linkingType;
        this.sourcePost = sourcePost;
        this.targetPost = targetPost;
    }

    public void patch(LinkedPost linkedPost) {
        if(linkedPost.linkingType!=null) this.linkingType = linkedPost.getLinkingType();
        if(linkedPost.getSourcePost()!=null) this.sourcePost = linkedPost.getSourcePost();
        if(linkedPost.getTargetPost()!=null) this.targetPost = linkedPost.getTargetPost();
    }

    public void update(LinkedPost linkedPost) {
        this.linkingType = linkedPost.getLinkingType();
        this.sourcePost = linkedPost.getSourcePost();
        this.targetPost = linkedPost.getTargetPost();
    }
}
