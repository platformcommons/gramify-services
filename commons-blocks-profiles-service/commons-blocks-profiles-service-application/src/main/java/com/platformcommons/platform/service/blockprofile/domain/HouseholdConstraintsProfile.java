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
@Table(name = "household_constraints_profile")
public class HouseholdConstraintsProfile extends  BaseTransactionalEntity implements DomainEntity<HouseholdConstraintsProfile> {

    @Column(
         name = "hh_has_incumbent_influence"
    )
    private Boolean hhHasIncumbentInfluence;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "household_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "household_entity_type"))
    })
    private LinkedCode household;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
         name = "hh_incumbent_influence_description"
    )
    private String hhIncumbentInfluenceDescription;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;




    @OneToMany(mappedBy = "householdConstraintsProfile", cascade = CascadeType.ALL)
    private Set<HHSocialConstraints> hhsocialconstraintsList;
    @OneToMany(mappedBy = "householdConstraintsProfile", cascade = CascadeType.ALL)
    private Set<HHEconomicConstraints> hheconomicconstraintsList;


    @Builder
    public HouseholdConstraintsProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Boolean hhHasIncumbentInfluence,
        LinkedCode linkedActor,
        LinkedCode household,
        Long id,
        String hhIncumbentInfluenceDescription,
        LinkedCode linkedContext,
        Set<HHSocialConstraints> hhsocialconstraintsList,
        Set<HHEconomicConstraints> hheconomicconstraintsList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.hhHasIncumbentInfluence = hhHasIncumbentInfluence;
        this.linkedActor = linkedActor;
        this.household = household;
        this.id = id;
        this.hhIncumbentInfluenceDescription = hhIncumbentInfluenceDescription;
        this.linkedContext = linkedContext;
        this.hhsocialconstraintsList=hhsocialconstraintsList;
        this.hhsocialconstraintsList.forEach(it->it.setHouseholdConstraintsProfile(this));
        this.hheconomicconstraintsList=hheconomicconstraintsList;
        this.hheconomicconstraintsList.forEach(it->it.setHouseholdConstraintsProfile(this));


    }


    public void update(HouseholdConstraintsProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HouseholdConstraintsProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getHhHasIncumbentInfluence() != null) {
            this.setHhHasIncumbentInfluence(toBeUpdated.getHhHasIncumbentInfluence());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getHousehold() != null) {
            this.setHousehold(toBeUpdated.getHousehold());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getHhIncumbentInfluenceDescription() != null) {
            this.setHhIncumbentInfluenceDescription(toBeUpdated.getHhIncumbentInfluenceDescription());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getHhsocialconstraintsList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHhsocialconstraintsList() != null) {
                this.getHhsocialconstraintsList().forEach(current -> {
                    toBeUpdated.getHhsocialconstraintsList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHhsocialconstraintsList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setHouseholdConstraintsProfile(this);
                    this.getHhsocialconstraintsList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getHheconomicconstraintsList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHheconomicconstraintsList() != null) {
                this.getHheconomicconstraintsList().forEach(current -> {
                    toBeUpdated.getHheconomicconstraintsList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHheconomicconstraintsList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setHouseholdConstraintsProfile(this);
                    this.getHheconomicconstraintsList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
