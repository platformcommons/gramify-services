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
@Table(name = "household_livelihood_profile")
public class HouseholdLivelihoodProfile extends  BaseTransactionalEntity implements DomainEntity<HouseholdLivelihoodProfile> {

    @Column(
         name = "hh_primary_occupation"
    )
    private String hhPrimaryOccupation;

    @Column(
         name = "hh_migration_destination"
    )
    private String hhMigrationDestination;

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



    @Column(
         name = "hh_has_seasonal_migration"
    )
    private Boolean hhHasSeasonalMigration;

    @Column(
         name = "hh_secondary_occupation"
    )
    private String hhSecondaryOccupation;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;



    @OneToMany(mappedBy = "householdLivelihoodProfile", cascade = CascadeType.ALL)
    private Set<HHMigrationMonth> hhmigrationmonthList;


    @Builder
    public HouseholdLivelihoodProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String hhPrimaryOccupation,
        String hhMigrationDestination,
        LinkedCode linkedContext,
        LinkedCode linkedActor,
        Boolean hhHasSeasonalMigration,
        String hhSecondaryOccupation,
        Long id,
        Set<HHMigrationMonth> hhmigrationmonthList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.hhPrimaryOccupation = hhPrimaryOccupation;
        this.hhMigrationDestination = hhMigrationDestination;
        this.linkedContext = linkedContext;
        this.linkedActor = linkedActor;
        this.hhHasSeasonalMigration = hhHasSeasonalMigration;
        this.hhSecondaryOccupation = hhSecondaryOccupation;
        this.id = id;
        this.hhmigrationmonthList=hhmigrationmonthList;
        this.hhmigrationmonthList.forEach(it->it.setHouseholdLivelihoodProfile(this));


    }


    public void update(HouseholdLivelihoodProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HouseholdLivelihoodProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getHhPrimaryOccupation() != null) {
            this.setHhPrimaryOccupation(toBeUpdated.getHhPrimaryOccupation());
        }
        if (toBeUpdated.getHhMigrationDestination() != null) {
            this.setHhMigrationDestination(toBeUpdated.getHhMigrationDestination());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getHhHasSeasonalMigration() != null) {
            this.setHhHasSeasonalMigration(toBeUpdated.getHhHasSeasonalMigration());
        }
        if (toBeUpdated.getHhSecondaryOccupation() != null) {
            this.setHhSecondaryOccupation(toBeUpdated.getHhSecondaryOccupation());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getHhmigrationmonthList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHhmigrationmonthList() != null) {
                this.getHhmigrationmonthList().forEach(current -> {
                    toBeUpdated.getHhmigrationmonthList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHhmigrationmonthList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setHouseholdLivelihoodProfile(this);
                    this.getHhmigrationmonthList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
