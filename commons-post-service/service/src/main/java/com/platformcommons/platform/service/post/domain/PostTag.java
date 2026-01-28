package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "post_tag")
@NoArgsConstructor
public class PostTag extends BaseTransactionalEntity implements DomainEntity<PostTag> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "post_id",updatable = false,nullable = false)
    private Post post;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "tag_purpose")
    private String tagPurpose;

    @Column(name = "tag_code", nullable = false)
    private String tagCode;

    @Builder
    public PostTag(String uuid, Long tenantId, Boolean isActive, String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                   Long id, Post post, String type, String tagCode, String tagPurpose) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.post = post;
        this.type = type;
        this.tagCode = tagCode;
        this.tagPurpose = tagPurpose;
    }

    public void init() {
        this.id = null;
    }

    public void patchUpdate(PostTag postTag) {
        if(postTag.getType() != null) {
            this.type = postTag.getType();
        }
        if(postTag.getTagCode() != null) {
            this.tagCode = postTag.getTagCode();
        }
    }
}
