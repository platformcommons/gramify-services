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
@Table(name = "village_human_capital_profile")
public class VillageHumanCapitalProfile extends  BaseTransactionalEntity implements DomainEntity<VillageHumanCapitalProfile> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
             name = "village_vocational_training_access"
    )
    private String villageVocationalTrainingAccess;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
             name = "village_youth_migration_level"
    )
    private String villageYouthMigrationLevel;



    @Column(
             name = "village_entrepreneurship_level"
    )
    private String villageEntrepreneurshipLevel;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;



    @OneToMany(mappedBy = "villageHumanCapitalProfile", cascade = CascadeType.ALL)
    private Set<VillageYouthAspirations> villageyouthaspirationsList;
    @OneToMany(mappedBy = "villageHumanCapitalProfile", cascade = CascadeType.ALL)
    private Set<VillageSkillShortageType> villageskillshortagetypeList;


    @Builder
    public VillageHumanCapitalProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedContext,
        String villageVocationalTrainingAccess,
        LinkedCode linkedActor,
        String villageYouthMigrationLevel,
        String villageEntrepreneurshipLevel,
        Long id,
        Set<VillageYouthAspirations> villageyouthaspirationsList,
        Set<VillageSkillShortageType> villageskillshortagetypeList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedContext = linkedContext;
        this.villageVocationalTrainingAccess = villageVocationalTrainingAccess;
        this.linkedActor = linkedActor;
        this.villageYouthMigrationLevel = villageYouthMigrationLevel;
        this.villageEntrepreneurshipLevel = villageEntrepreneurshipLevel;
        this.id = id;
        this.villageyouthaspirationsList=villageyouthaspirationsList;
        this.villageyouthaspirationsList.forEach(it->it.setVillageHumanCapitalProfile(this));
        this.villageskillshortagetypeList=villageskillshortagetypeList;
        this.villageskillshortagetypeList.forEach(it->it.setVillageHumanCapitalProfile(this));


    }


    public void update(VillageHumanCapitalProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageHumanCapitalProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getVillageVocationalTrainingAccess() != null) {
            this.setVillageVocationalTrainingAccess(toBeUpdated.getVillageVocationalTrainingAccess());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getVillageYouthMigrationLevel() != null) {
            this.setVillageYouthMigrationLevel(toBeUpdated.getVillageYouthMigrationLevel());
        }
        if (toBeUpdated.getVillageEntrepreneurshipLevel() != null) {
            this.setVillageEntrepreneurshipLevel(toBeUpdated.getVillageEntrepreneurshipLevel());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageyouthaspirationsList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getVillageyouthaspirationsList() != null) {
                this.getVillageyouthaspirationsList().forEach(current -> {
                    toBeUpdated.getVillageyouthaspirationsList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getVillageyouthaspirationsList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageHumanCapitalProfile(this);
                    this.getVillageyouthaspirationsList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getVillageskillshortagetypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getVillageskillshortagetypeList() != null) {
                this.getVillageskillshortagetypeList().forEach(current -> {
                    toBeUpdated.getVillageskillshortagetypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getVillageskillshortagetypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageHumanCapitalProfile(this);
                    this.getVillageskillshortagetypeList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
