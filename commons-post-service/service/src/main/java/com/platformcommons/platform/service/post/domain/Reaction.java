package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import liquibase.pro.packaged.D;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "reaction",
        indexes = {
                @Index(name = "IDX_reacted_on_entity_id",
                        columnList = "reacted_on_entity_id"),
                @Index(name = "IDX_reacted_on_entity_type",
                        columnList = "reacted_on_entity_type")
        })
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Reaction extends BaseTransactionalEntity implements DomainEntity<Reaction> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reacted_by", updatable = false)
    private PostActor reactedBy;

    @Column(name = "reacted_date_time")
    private Date reactedDateTime;

    @Column(name = "reacted_on_entity_id")
    private Long reactedOnEntityId;

    @Column(name = "reacted_on_entity_type")
    private String reactedOnEntityType;

    @Column(name = "reaction_type")
    private String reactionType;
    
    @Column(name="reaction_status")
    private String reactionStatus;

    @Transient
    private boolean isNew;


    @Builder
    public Reaction(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp,
                    Long appLastModifiedTimestamp, Long id, PostActor reactedBy, Date reactedDateTime,
                    Long reactedOnEntityId, String reactedOnEntityType, String reactionType, boolean isNew) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.reactedBy = reactedBy;
        this.reactedDateTime = reactedDateTime;
        this.reactedOnEntityId = reactedOnEntityId;
        this.reactedOnEntityType = reactedOnEntityType;
        this.reactionType = reactionType;
        this.isNew = isNew;
        this.reactionStatus=reactionStatus;
    }

    public void init(Long entityId, String entityType, PostActor postActor) {
        this.id = null;
        this.reactedDateTime = new Date();
        this.reactedOnEntityId = entityId;
        this.reactedOnEntityType = entityType;
        this.reactedBy = postActor;
        if(StringUtils.isEmpty(this.reactionType)){
            this.reactionType = PostConstant.REACTION_TYPE_LIKE;
        }
        if(StringUtils.isEmpty(this.reactionStatus)){
            this.reactionStatus = PostConstant.REACTION_STATUS_ACTIVE;
        }
    }

    public void isAuthorizedForUpdate() {
        boolean isAllowed = this.getCreatedByUser()
                .equals(PlatformSecurityUtil.getCurrentUserId())
                || PlatformSecurityUtil.isTenantAdmin();
        if (!isAllowed)
            throw new UnAuthorizedAccessException("User is not authorized to update this post.");
    }

    public void toggle() {
        if(this.reactionStatus.equals(PostConstant.REACTION_STATUS_ACTIVE)) {
            this.reactionStatus=PostConstant.REACTION_STATUS_INACTIVE;
        }
        else {
            this.reactionStatus=PostConstant.REACTION_STATUS_ACTIVE;
        }
    }
}