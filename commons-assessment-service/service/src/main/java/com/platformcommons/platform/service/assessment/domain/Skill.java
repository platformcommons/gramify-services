package com.platformcommons.platform.service.assessment.domain;

import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Transient;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "skill")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Skill extends BaseTransactionalEntity implements DomainEntity<Skill> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "major_sub_type")
    private String majorSubType;

    @Column(name = "major_type")
    private String majorType;

    @Column(name = "skill_type", nullable = true, updatable = false)
    private String skillType;

    @Column(name = "difficulty_level")
    private Long difficultyLevel;

    @Column(name = "domain")
    private String domain;

    @Column(name = "skill_code")
    private String skillCode;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "skill_description", joinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "skill_desc_id", referencedColumnName = "id", unique = true))
    private Set<MLText> skillDesc;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "skill_name", joinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "skill_name_id", referencedColumnName = "id", unique = true))
    private Set<MLText> skillName;

    @Transient
    private boolean isNew;

    @Builder
    public Skill(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String majorSubType, String majorType, String skillType, Long difficultyLevel, String domain, String skillCode, Set<MLText> skillDesc, Set<MLText>  skillName) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.majorSubType = majorSubType;
        this.majorType = majorType;
        this.skillType = skillType;
        this.difficultyLevel = difficultyLevel;
        this.domain = domain;
        this.skillCode = skillCode;
        this.skillName = skillName;
        this.skillDesc = skillDesc;
    }

    public void init() {
        this.id = null;
    }

    public void update(Skill toBeUpdated) {
    }
}