package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.Getter;
import lombok.Setter;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "post_tag_actor")
@Getter
@Setter
@NoArgsConstructor
public class PostTagActor extends BaseTransactionalEntity implements DomainEntity<PostTagActor>, Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actor_id")
    private Long actorId;

    @Column(name = "tag_id", nullable = false)
    private String actorIdentifier;

    @Column(name = "tag_code", nullable = false)
    private String actorName;

    @Column(name = "sequence")
    private Long sequence;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.DETACH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    @Transient
    private boolean isNew;

    @Builder
    public PostTagActor(String uuid, Long tenantId, Boolean isActive, String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                        Long id, Long actorId, String actorIdentifier, String actorName, Long sequence, Post post) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.post = post;
        this.actorId = actorId;
        this.actorIdentifier = actorIdentifier;
        this.actorName = actorName;
        this.sequence = sequence;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew || id == null;
    }
}