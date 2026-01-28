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
@Table(name = "village_water_resource_profile")
public class VillageWaterResourceProfile extends  BaseTransactionalEntity implements DomainEntity<VillageWaterResourceProfile> {

    @Column(
         name = "village_has_fluoride_issue"
    )
    private Boolean villageHasFluorideIssue;

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



    @Column(
         name = "village_river_names"
    )
    private String villageRiverNames;

    @Column(
             name = "village_groundwater_stress_level"
    )
    private String villageGroundwaterStressLevel;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
             name = "village_borewell_dependence_level"
    )
    private String villageBorewellDependenceLevel;



    @Column(
         name = "village_groundwater_depth_range"
    )
    private String villageGroundwaterDepthRange;

    @Column(
         name = "village_avg_annual_rainfall"
    )
    private String villageAvgAnnualRainfall;

    @Column(
         name = "village_water_quality_status"
    )
    private String villageWaterQualityStatus;

    @Column(
         name = "village_tank_count"
    )
    private Long villageTankCount;

    @Column(
         name = "village_has_river"
    )
    private Boolean villageHasRiver;

    @Column(
             name = "village_rainfall_variability"
    )
    private String villageRainfallVariability;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageLiftIrrigationCount;



    @OneToMany(mappedBy = "villageWaterResourceProfile", cascade = CascadeType.ALL)
    private Set<VillageIrrigationSystemType> villageirrigationsystemtypeList;


    @Builder
    public VillageWaterResourceProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Boolean villageHasFluorideIssue,
        Long id,
        LinkedCode linkedContext,
        String villageRiverNames,
        String villageGroundwaterStressLevel,
        LinkedCode linkedActor,
        String villageBorewellDependenceLevel,
        String villageGroundwaterDepthRange,
        String villageAvgAnnualRainfall,
        String villageWaterQualityStatus,
        Long villageTankCount,
        Boolean villageHasRiver,
        String villageRainfallVariability,
        UoMValue villageLiftIrrigationCount,
        Set<VillageIrrigationSystemType> villageirrigationsystemtypeList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageHasFluorideIssue = villageHasFluorideIssue;
        this.id = id;
        this.linkedContext = linkedContext;
        this.villageRiverNames = villageRiverNames;
        this.villageGroundwaterStressLevel = villageGroundwaterStressLevel;
        this.linkedActor = linkedActor;
        this.villageBorewellDependenceLevel = villageBorewellDependenceLevel;
        this.villageGroundwaterDepthRange = villageGroundwaterDepthRange;
        this.villageAvgAnnualRainfall = villageAvgAnnualRainfall;
        this.villageWaterQualityStatus = villageWaterQualityStatus;
        this.villageTankCount = villageTankCount;
        this.villageHasRiver = villageHasRiver;
        this.villageRainfallVariability = villageRainfallVariability;
        this.villageLiftIrrigationCount = villageLiftIrrigationCount;
        this.villageirrigationsystemtypeList=villageirrigationsystemtypeList;
        this.villageirrigationsystemtypeList.forEach(it->it.setVillageWaterResourceProfile(this));


    }


    public void update(VillageWaterResourceProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageWaterResourceProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageHasFluorideIssue() != null) {
            this.setVillageHasFluorideIssue(toBeUpdated.getVillageHasFluorideIssue());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getVillageRiverNames() != null) {
            this.setVillageRiverNames(toBeUpdated.getVillageRiverNames());
        }
        if (toBeUpdated.getVillageGroundwaterStressLevel() != null) {
            this.setVillageGroundwaterStressLevel(toBeUpdated.getVillageGroundwaterStressLevel());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getVillageBorewellDependenceLevel() != null) {
            this.setVillageBorewellDependenceLevel(toBeUpdated.getVillageBorewellDependenceLevel());
        }
        if (toBeUpdated.getVillageGroundwaterDepthRange() != null) {
            this.setVillageGroundwaterDepthRange(toBeUpdated.getVillageGroundwaterDepthRange());
        }
        if (toBeUpdated.getVillageAvgAnnualRainfall() != null) {
            this.setVillageAvgAnnualRainfall(toBeUpdated.getVillageAvgAnnualRainfall());
        }
        if (toBeUpdated.getVillageWaterQualityStatus() != null) {
            this.setVillageWaterQualityStatus(toBeUpdated.getVillageWaterQualityStatus());
        }
        if (toBeUpdated.getVillageTankCount() != null) {
            this.setVillageTankCount(toBeUpdated.getVillageTankCount());
        }
        if (toBeUpdated.getVillageHasRiver() != null) {
            this.setVillageHasRiver(toBeUpdated.getVillageHasRiver());
        }
        if (toBeUpdated.getVillageRainfallVariability() != null) {
            this.setVillageRainfallVariability(toBeUpdated.getVillageRainfallVariability());
        }
        if (toBeUpdated.getVillageLiftIrrigationCount() != null) {
            this.setVillageLiftIrrigationCount(toBeUpdated.getVillageLiftIrrigationCount());
        }
        if (toBeUpdated.getVillageirrigationsystemtypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getVillageirrigationsystemtypeList() != null) {
                this.getVillageirrigationsystemtypeList().forEach(current -> {
                    toBeUpdated.getVillageirrigationsystemtypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getVillageirrigationsystemtypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageWaterResourceProfile(this);
                    this.getVillageirrigationsystemtypeList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
