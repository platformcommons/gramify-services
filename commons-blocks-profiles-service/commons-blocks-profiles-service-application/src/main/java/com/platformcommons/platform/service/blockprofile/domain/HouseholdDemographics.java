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
@Table(name = "household_demographics")
public class HouseholdDemographics extends  BaseTransactionalEntity implements DomainEntity<HouseholdDemographics> {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue hhAgeGroup15to59;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue hhLiteracyMembersShare;


    @Column(
         name = "hh_caste_category"
    )
    private String hhCasteCategory;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue hhAgeGroup6to14;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue hhFemaleCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue hhMaleCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue hhAgeGroup0to5;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue hhSize;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue hhAgeGroup60plus;





    @Builder
    public HouseholdDemographics(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        UoMValue hhAgeGroup15to59,
        UoMValue hhLiteracyMembersShare,
        String hhCasteCategory,
        UoMValue hhAgeGroup6to14,
        UoMValue hhFemaleCount,
        UoMValue hhMaleCount,
        UoMValue hhAgeGroup0to5,
        LinkedCode linkedContext,
        LinkedCode linkedActor,
        UoMValue hhSize,
        Long id,
        UoMValue hhAgeGroup60plus
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.hhAgeGroup15to59 = hhAgeGroup15to59;
        this.hhLiteracyMembersShare = hhLiteracyMembersShare;
        this.hhCasteCategory = hhCasteCategory;
        this.hhAgeGroup6to14 = hhAgeGroup6to14;
        this.hhFemaleCount = hhFemaleCount;
        this.hhMaleCount = hhMaleCount;
        this.hhAgeGroup0to5 = hhAgeGroup0to5;
        this.linkedContext = linkedContext;
        this.linkedActor = linkedActor;
        this.hhSize = hhSize;
        this.id = id;
        this.hhAgeGroup60plus = hhAgeGroup60plus;


    }


    public void update(HouseholdDemographics toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HouseholdDemographics toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getHhAgeGroup15to59() != null) {
            this.setHhAgeGroup15to59(toBeUpdated.getHhAgeGroup15to59());
        }
        if (toBeUpdated.getHhLiteracyMembersShare() != null) {
            this.setHhLiteracyMembersShare(toBeUpdated.getHhLiteracyMembersShare());
        }
        if (toBeUpdated.getHhCasteCategory() != null) {
            this.setHhCasteCategory(toBeUpdated.getHhCasteCategory());
        }
        if (toBeUpdated.getHhAgeGroup6to14() != null) {
            this.setHhAgeGroup6to14(toBeUpdated.getHhAgeGroup6to14());
        }
        if (toBeUpdated.getHhFemaleCount() != null) {
            this.setHhFemaleCount(toBeUpdated.getHhFemaleCount());
        }
        if (toBeUpdated.getHhMaleCount() != null) {
            this.setHhMaleCount(toBeUpdated.getHhMaleCount());
        }
        if (toBeUpdated.getHhAgeGroup0to5() != null) {
            this.setHhAgeGroup0to5(toBeUpdated.getHhAgeGroup0to5());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getHhSize() != null) {
            this.setHhSize(toBeUpdated.getHhSize());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getHhAgeGroup60plus() != null) {
            this.setHhAgeGroup60plus(toBeUpdated.getHhAgeGroup60plus());
        }
    }

    public void init() {

    }
}
