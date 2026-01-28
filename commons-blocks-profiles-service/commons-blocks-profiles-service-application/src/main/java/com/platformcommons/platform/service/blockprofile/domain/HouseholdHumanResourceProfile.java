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
@Table(name = "household_human_resource_profile")
public class HouseholdHumanResourceProfile extends  BaseTransactionalEntity implements DomainEntity<HouseholdHumanResourceProfile> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
         name = "hh_has_artisan_occupation"
    )
    private Boolean hhHasArtisanOccupation;

    @Column(
         name = "hh_has_skilled_worker"
    )
    private Boolean hhHasSkilledWorker;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
         name = "hh_has_entrepreneur"
    )
    private Boolean hhHasEntrepreneur;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;




    @OneToMany(mappedBy = "householdHumanResourceProfile", cascade = CascadeType.ALL)
    private Set<HHEmploymentType> hhemploymenttypeList;
    @OneToMany(mappedBy = "householdHumanResourceProfile", cascade = CascadeType.ALL)
    private Set<HHEnterpriseType> hhenterprisetypeList;
    @OneToMany(mappedBy = "householdHumanResourceProfile", cascade = CascadeType.ALL)
    private Set<HHArtisanType> hhartisantypeList;


    @Builder
    public HouseholdHumanResourceProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedContext,
        Boolean hhHasArtisanOccupation,
        Boolean hhHasSkilledWorker,
        Long id,
        Boolean hhHasEntrepreneur,
        LinkedCode linkedActor,
        Set<HHEmploymentType> hhemploymenttypeList,
        Set<HHEnterpriseType> hhenterprisetypeList,
        Set<HHArtisanType> hhartisantypeList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedContext = linkedContext;
        this.hhHasArtisanOccupation = hhHasArtisanOccupation;
        this.hhHasSkilledWorker = hhHasSkilledWorker;
        this.id = id;
        this.hhHasEntrepreneur = hhHasEntrepreneur;
        this.linkedActor = linkedActor;
        this.hhemploymenttypeList=hhemploymenttypeList;
        this.hhemploymenttypeList.forEach(it->it.setHouseholdHumanResourceProfile(this));
        this.hhenterprisetypeList=hhenterprisetypeList;
        this.hhenterprisetypeList.forEach(it->it.setHouseholdHumanResourceProfile(this));
        this.hhartisantypeList=hhartisantypeList;
        this.hhartisantypeList.forEach(it->it.setHouseholdHumanResourceProfile(this));


    }


    public void update(HouseholdHumanResourceProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HouseholdHumanResourceProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getHhHasArtisanOccupation() != null) {
            this.setHhHasArtisanOccupation(toBeUpdated.getHhHasArtisanOccupation());
        }
        if (toBeUpdated.getHhHasSkilledWorker() != null) {
            this.setHhHasSkilledWorker(toBeUpdated.getHhHasSkilledWorker());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getHhHasEntrepreneur() != null) {
            this.setHhHasEntrepreneur(toBeUpdated.getHhHasEntrepreneur());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getHhemploymenttypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHhemploymenttypeList() != null) {
                this.getHhemploymenttypeList().forEach(current -> {
                    toBeUpdated.getHhemploymenttypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHhemploymenttypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setHouseholdHumanResourceProfile(this);
                    this.getHhemploymenttypeList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getHhenterprisetypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHhenterprisetypeList() != null) {
                this.getHhenterprisetypeList().forEach(current -> {
                    toBeUpdated.getHhenterprisetypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHhenterprisetypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setHouseholdHumanResourceProfile(this);
                    this.getHhenterprisetypeList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getHhartisantypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHhartisantypeList() != null) {
                this.getHhartisantypeList().forEach(current -> {
                    toBeUpdated.getHhartisantypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHhartisantypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setHouseholdHumanResourceProfile(this);
                    this.getHhartisantypeList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
