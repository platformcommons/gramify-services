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
@Table(name = "village_electricity_infrastructur")
public class VillageElectricityInfrastructur extends  BaseTransactionalEntity implements DomainEntity<VillageElectricityInfrastructur> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue householdElectrificationRate;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue agriPumpElectrificationRate;


    @Column(
         name = "has_biogas_plant"
    )
    private Boolean hasBiogasPlant;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue solarStreetLightCount;


    @Column(
             name = "avg_daily_power_supply_hours"
    )
    private String avgDailyPowerSupplyHours;




    @OneToMany(mappedBy = "villageElectricityInfrastructur", cascade = CascadeType.ALL)
    private Set<PowerCutSeason> powercutseasonList;
    @OneToMany(mappedBy = "villageElectricityInfrastructur", cascade = CascadeType.ALL)
    private Set<RenewableInfraType> renewableinfratypeList;


    @Builder
    public VillageElectricityInfrastructur(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedActor,
        LinkedCode linkedContext,
        UoMValue householdElectrificationRate,
        Long id,
        UoMValue agriPumpElectrificationRate,
        Boolean hasBiogasPlant,
        UoMValue solarStreetLightCount,
        String avgDailyPowerSupplyHours,
        Set<PowerCutSeason> powercutseasonList,
        Set<RenewableInfraType> renewableinfratypeList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedActor = linkedActor;
        this.linkedContext = linkedContext;
        this.householdElectrificationRate = householdElectrificationRate;
        this.id = id;
        this.agriPumpElectrificationRate = agriPumpElectrificationRate;
        this.hasBiogasPlant = hasBiogasPlant;
        this.solarStreetLightCount = solarStreetLightCount;
        this.avgDailyPowerSupplyHours = avgDailyPowerSupplyHours;
        this.powercutseasonList=powercutseasonList;
        this.powercutseasonList.forEach(it->it.setVillageElectricityInfrastructur(this));
        this.renewableinfratypeList=renewableinfratypeList;
        this.renewableinfratypeList.forEach(it->it.setVillageElectricityInfrastructur(this));


    }


    public void update(VillageElectricityInfrastructur toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageElectricityInfrastructur toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getHouseholdElectrificationRate() != null) {
            this.setHouseholdElectrificationRate(toBeUpdated.getHouseholdElectrificationRate());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getAgriPumpElectrificationRate() != null) {
            this.setAgriPumpElectrificationRate(toBeUpdated.getAgriPumpElectrificationRate());
        }
        if (toBeUpdated.getHasBiogasPlant() != null) {
            this.setHasBiogasPlant(toBeUpdated.getHasBiogasPlant());
        }
        if (toBeUpdated.getSolarStreetLightCount() != null) {
            this.setSolarStreetLightCount(toBeUpdated.getSolarStreetLightCount());
        }
        if (toBeUpdated.getAvgDailyPowerSupplyHours() != null) {
            this.setAvgDailyPowerSupplyHours(toBeUpdated.getAvgDailyPowerSupplyHours());
        }
        if (toBeUpdated.getPowercutseasonList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getPowercutseasonList() != null) {
                this.getPowercutseasonList().forEach(current -> {
                    toBeUpdated.getPowercutseasonList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getPowercutseasonList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageElectricityInfrastructur(this);
                    this.getPowercutseasonList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getRenewableinfratypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getRenewableinfratypeList() != null) {
                this.getRenewableinfratypeList().forEach(current -> {
                    toBeUpdated.getRenewableinfratypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getRenewableinfratypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageElectricityInfrastructur(this);
                    this.getRenewableinfratypeList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
