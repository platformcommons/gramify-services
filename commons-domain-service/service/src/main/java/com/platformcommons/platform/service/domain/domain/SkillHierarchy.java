package com.platformcommons.platform.service.domain.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Indexed
public class SkillHierarchy extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<SkillHierarchy> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    @GenericField
    private Long id;

    @NotNull(message = "skill must not be null")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "skill", nullable = false, updatable = false)
    @IndexedEmbedded
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    private Skill skill;

    @NotNull(message = "parentSkill must not be null")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_skill", nullable = false, updatable = false)
    @IndexedEmbedded
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    private Skill parentSkill;

    @NotNull(message = "depth must not be null")
    @Column(name = "depth")
    private Long depth;

    @Transient
    private boolean isNew;
    @Builder
    public SkillHierarchy(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Skill skill, Skill parentSkill, Long depth, boolean isNew) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.skill = skill;
        this.parentSkill = parentSkill;
        this.depth = depth;
        this.isNew = isNew;
    }

    public void init() {
    }

}
