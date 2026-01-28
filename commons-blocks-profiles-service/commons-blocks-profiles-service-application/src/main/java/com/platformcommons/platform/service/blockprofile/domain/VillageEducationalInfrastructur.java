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
@Table(name = "village_educational_infrastructur")
public class VillageEducationalInfrastructur extends  BaseTransactionalEntity implements DomainEntity<VillageEducationalInfrastructur> {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue primarySchoolCount;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue middleSchoolCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue schoolsNeedingRepairCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue highSchoolCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue schoolsWithLibraryCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue schoolsWithLabCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue schoolsWithDigitalAidsCount;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;






    @Builder
    public VillageEducationalInfrastructur(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        UoMValue primarySchoolCount,
        LinkedCode linkedActor,
        UoMValue middleSchoolCount,
        UoMValue schoolsNeedingRepairCount,
        UoMValue highSchoolCount,
        UoMValue schoolsWithLibraryCount,
        UoMValue schoolsWithLabCount,
        UoMValue schoolsWithDigitalAidsCount,
        Long id,
        LinkedCode linkedContext
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.primarySchoolCount = primarySchoolCount;
        this.linkedActor = linkedActor;
        this.middleSchoolCount = middleSchoolCount;
        this.schoolsNeedingRepairCount = schoolsNeedingRepairCount;
        this.highSchoolCount = highSchoolCount;
        this.schoolsWithLibraryCount = schoolsWithLibraryCount;
        this.schoolsWithLabCount = schoolsWithLabCount;
        this.schoolsWithDigitalAidsCount = schoolsWithDigitalAidsCount;
        this.id = id;
        this.linkedContext = linkedContext;


    }


    public void update(VillageEducationalInfrastructur toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageEducationalInfrastructur toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getPrimarySchoolCount() != null) {
            this.setPrimarySchoolCount(toBeUpdated.getPrimarySchoolCount());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getMiddleSchoolCount() != null) {
            this.setMiddleSchoolCount(toBeUpdated.getMiddleSchoolCount());
        }
        if (toBeUpdated.getSchoolsNeedingRepairCount() != null) {
            this.setSchoolsNeedingRepairCount(toBeUpdated.getSchoolsNeedingRepairCount());
        }
        if (toBeUpdated.getHighSchoolCount() != null) {
            this.setHighSchoolCount(toBeUpdated.getHighSchoolCount());
        }
        if (toBeUpdated.getSchoolsWithLibraryCount() != null) {
            this.setSchoolsWithLibraryCount(toBeUpdated.getSchoolsWithLibraryCount());
        }
        if (toBeUpdated.getSchoolsWithLabCount() != null) {
            this.setSchoolsWithLabCount(toBeUpdated.getSchoolsWithLabCount());
        }
        if (toBeUpdated.getSchoolsWithDigitalAidsCount() != null) {
            this.setSchoolsWithDigitalAidsCount(toBeUpdated.getSchoolsWithDigitalAidsCount());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
    }

    public void init() {

    }
}
