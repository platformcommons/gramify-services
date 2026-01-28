package com.platformcommons.platform.service.domain.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "tag_hierarchy")
public class TagHierarchy extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<TagHierarchy> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "tag must not be null")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tag", nullable = false, updatable = false)
    private Tag tag;

    @NotNull(message = "parentTag must not be null")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_tag", nullable = false, updatable = false)
    private Tag parentTag;

    @NotNull(message = "depth must not be null")
    @Column(name = "depth")
    private Long depth;

    @Transient
    private boolean isNew;
    @Builder
    public TagHierarchy(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Tag tag, Tag parentTag, Long depth, boolean isNew) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.tag = tag;
        this.parentTag = parentTag;
        this.depth = depth;
        this.isNew = isNew;
    }

    public void init() {
    }

}
