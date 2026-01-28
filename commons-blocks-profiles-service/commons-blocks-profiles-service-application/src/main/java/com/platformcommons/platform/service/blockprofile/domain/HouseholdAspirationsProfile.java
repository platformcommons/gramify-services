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
@Table(name = "household_aspirations_profile")
public class HouseholdAspirationsProfile extends  BaseTransactionalEntity implements DomainEntity<HouseholdAspirationsProfile> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "household_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "household_entity_type"))
    })
    private LinkedCode household;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;



    @OneToMany(mappedBy = "householdAspirationsProfile", cascade = CascadeType.ALL)
    private Set<HHSocialAspiration> hhsocialaspirationList;
    @OneToMany(mappedBy = "householdAspirationsProfile", cascade = CascadeType.ALL)
    private Set<HHEconomicAspiration> hheconomicaspirationList;
    @OneToMany(mappedBy = "householdAspirationsProfile", cascade = CascadeType.ALL)
    private Set<HHGovernanceAspiration> hhgovernanceaspirationList;


    @Builder
    public HouseholdAspirationsProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedContext,
        LinkedCode household,
        LinkedCode linkedActor,
        Long id,
        Set<HHSocialAspiration> hhsocialaspirationList,
        Set<HHEconomicAspiration> hheconomicaspirationList,
        Set<HHGovernanceAspiration> hhgovernanceaspirationList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedContext = linkedContext;
        this.household = household;
        this.linkedActor = linkedActor;
        this.id = id;
        this.hhsocialaspirationList=hhsocialaspirationList;
        this.hhsocialaspirationList.forEach(it->it.setHouseholdAspirationsProfile(this));
        this.hheconomicaspirationList=hheconomicaspirationList;
        this.hheconomicaspirationList.forEach(it->it.setHouseholdAspirationsProfile(this));
        this.hhgovernanceaspirationList=hhgovernanceaspirationList;
        this.hhgovernanceaspirationList.forEach(it->it.setHouseholdAspirationsProfile(this));


    }


    public void update(HouseholdAspirationsProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HouseholdAspirationsProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getHousehold() != null) {
            this.setHousehold(toBeUpdated.getHousehold());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getHhsocialaspirationList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHhsocialaspirationList() != null) {
                this.getHhsocialaspirationList().forEach(current -> {
                    toBeUpdated.getHhsocialaspirationList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHhsocialaspirationList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setHouseholdAspirationsProfile(this);
                    this.getHhsocialaspirationList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getHheconomicaspirationList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHheconomicaspirationList() != null) {
                this.getHheconomicaspirationList().forEach(current -> {
                    toBeUpdated.getHheconomicaspirationList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHheconomicaspirationList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setHouseholdAspirationsProfile(this);
                    this.getHheconomicaspirationList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getHhgovernanceaspirationList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHhgovernanceaspirationList() != null) {
                this.getHhgovernanceaspirationList().forEach(current -> {
                    toBeUpdated.getHhgovernanceaspirationList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHhgovernanceaspirationList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setHouseholdAspirationsProfile(this);
                    this.getHhgovernanceaspirationList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
