package com.platformcommons.platform.service.assessment.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "assessment_actor")
@NoArgsConstructor
public class AssessmentActor extends BaseTransactionalEntity implements DomainEntity<AssessmentActor> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "actor_id",updatable = false)
    private String actorId;

    @Column(name = "actor_type")
    private String actorType;

    @Column(name = "name")
    private String name;

    @Column(name = "group_code")
    private String groupCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_instance_assesse_id", nullable = false)
    private AssessmentInstanceAssesse assessmentInstanceAssesse;


    @Builder
    public AssessmentActor(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String actorId, String actorType, String name, String groupCode, AssessmentInstanceAssesse assessmentInstanceAssesse) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.actorId = actorId;
        this.actorType = actorType;
        this.name = name;
        this.assessmentInstanceAssesse = assessmentInstanceAssesse;
        this.groupCode = groupCode;
    }

    public AssessmentActor init() {
        this.id = null;
        return this;
    }

    public void patch(AssessmentActor toBeUpdated) {
        if (toBeUpdated == null) return;
        if (Objects.nonNull(toBeUpdated.getIsActive()) && !toBeUpdated.getIsActive()) {
            deactivate(toBeUpdated.getInactiveReason());
        }
    }

}

