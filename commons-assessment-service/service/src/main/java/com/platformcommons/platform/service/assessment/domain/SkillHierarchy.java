package com.platformcommons.platform.service.assessment.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Transient;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "skill_hierarchy")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class SkillHierarchy extends BaseTransactionalEntity implements DomainEntity<SkillHierarchy> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_skill_id", nullable = true, updatable = false)
    private Skill parentSkillId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "child_skill_id", nullable = true, updatable = false)
    private Skill childSkill;

    @Column(name = "contributed_weight")
    private Long contributedWeight;

    @Transient
    private boolean isNew;

    @Builder
    public SkillHierarchy(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Skill parentSkillId, Skill childSkill, Long contributedWeight) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.parentSkillId = parentSkillId;
        this.childSkill = childSkill;
        this.contributedWeight = contributedWeight;
    }

    public void init() {
        this.id = null;
    }

    public void update(SkillHierarchy toBeUpdated) {
    }
}