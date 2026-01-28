package com.platformcommons.platform.service.assessment.domain;

import javax.validation.constraints.NotNull;
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
@Table(name = "aia_level")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AiaLevel extends BaseTransactionalEntity implements DomainEntity<AiaLevel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "assessmentInstanceAssesseId must not be null")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_instance_assesse_id", nullable = false, updatable = false)
    private AssessmentInstanceAssesse assessmentInstanceAssesseId;

    @Column(name = "level_marks")
    private Long levelMarks;

    @Column(name = "level_number")
    private Long levelNumber;

    @Column(name = "tenant")
    private Long tenant;
    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;
    @Transient
    private boolean isNew;

    @Builder
    public AiaLevel(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, AssessmentInstanceAssesse assessmentInstanceAssesseId, Long levelMarks, Long levelNumber, Long tenant,String linkedSystemType,String linkedSystemId) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.assessmentInstanceAssesseId = assessmentInstanceAssesseId;
        this.levelMarks = levelMarks;
        this.levelNumber = levelNumber;
        this.tenant = tenant;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType = linkedSystemType;
    }

    public void init() {
        this.id = null;
    }

    public void update(AiaLevel toBeUpdated) {
    }
}