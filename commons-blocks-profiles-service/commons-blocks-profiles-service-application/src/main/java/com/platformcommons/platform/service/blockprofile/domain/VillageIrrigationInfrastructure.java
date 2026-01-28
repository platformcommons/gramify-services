package com.platformcommons.platform.service.blockprofile.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;

import com.platformcommons.platform.service.entity.common.*;

import lombok.*;
import java.util.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "village_irrigation_infrastructure")
public class VillageIrrigationInfrastructure extends  BaseTransactionalEntity implements DomainEntity<VillageIrrigationInfrastructure> {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue liftIrrigationProjectCount;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue irrigatedAreaShare;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue borewellCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue checkDamCount;


    @Column(
         name = "major_irrigation_projects"
    )
    private String majorIrrigationProjects;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;






    @Builder
    public VillageIrrigationInfrastructure(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        UoMValue liftIrrigationProjectCount,
        LinkedCode linkedContext,
        UoMValue irrigatedAreaShare,
        Long id,
        UoMValue borewellCount,
        UoMValue checkDamCount,
        String majorIrrigationProjects,
        LinkedCode linkedActor
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.liftIrrigationProjectCount = liftIrrigationProjectCount;
        this.linkedContext = linkedContext;
        this.irrigatedAreaShare = irrigatedAreaShare;
        this.id = id;
        this.borewellCount = borewellCount;
        this.checkDamCount = checkDamCount;
        this.majorIrrigationProjects = majorIrrigationProjects;
        this.linkedActor = linkedActor;


    }


    public void update(VillageIrrigationInfrastructure toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageIrrigationInfrastructure toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLiftIrrigationProjectCount() != null) {
            this.setLiftIrrigationProjectCount(toBeUpdated.getLiftIrrigationProjectCount());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getIrrigatedAreaShare() != null) {
            this.setIrrigatedAreaShare(toBeUpdated.getIrrigatedAreaShare());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getBorewellCount() != null) {
            this.setBorewellCount(toBeUpdated.getBorewellCount());
        }
        if (toBeUpdated.getCheckDamCount() != null) {
            this.setCheckDamCount(toBeUpdated.getCheckDamCount());
        }
        if (toBeUpdated.getMajorIrrigationProjects() != null) {
            this.setMajorIrrigationProjects(toBeUpdated.getMajorIrrigationProjects());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
    }

    public void init() {

    }
}
