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
@Table(name = "village_infrastructure_constraint")
public class VillageInfrastructureConstraint extends  BaseTransactionalEntity implements DomainEntity<VillageInfrastructureConstraint> {

    @Column(
         name = "village_power_supply_reliability"
    )
    private String villagePowerSupplyReliability;

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



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
         name = "village_internal_road_quality"
    )
    private String villageInternalRoadQuality;


    @OneToMany(mappedBy = "villageInfrastructureConstraint", cascade = CascadeType.ALL)
    private Set<VillageTopInfrastructureNeed> villagetopinfrastructureneedList;
    @OneToMany(mappedBy = "villageInfrastructureConstraint", cascade = CascadeType.ALL)
    private Set<VillageTransportConnectivityIss> villagetransportconnectivityissList;
    @OneToMany(mappedBy = "villageInfrastructureConstraint", cascade = CascadeType.ALL)
    private Set<VillageWaterSupplyGap> villagewatersupplygapList;


    @Builder
    public VillageInfrastructureConstraint(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String villagePowerSupplyReliability,
        LinkedCode linkedActor,
        LinkedCode linkedContext,
        Long id,
        String villageInternalRoadQuality,
        Set<VillageTopInfrastructureNeed> villagetopinfrastructureneedList,
        Set<VillageTransportConnectivityIss> villagetransportconnectivityissList,
        Set<VillageWaterSupplyGap> villagewatersupplygapList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villagePowerSupplyReliability = villagePowerSupplyReliability;
        this.linkedActor = linkedActor;
        this.linkedContext = linkedContext;
        this.id = id;
        this.villageInternalRoadQuality = villageInternalRoadQuality;
        this.villagetopinfrastructureneedList=villagetopinfrastructureneedList;
        this.villagetopinfrastructureneedList.forEach(it->it.setVillageInfrastructureConstraint(this));
        this.villagetransportconnectivityissList=villagetransportconnectivityissList;
        this.villagetransportconnectivityissList.forEach(it->it.setVillageInfrastructureConstraint(this));
        this.villagewatersupplygapList=villagewatersupplygapList;
        this.villagewatersupplygapList.forEach(it->it.setVillageInfrastructureConstraint(this));


    }


    public void update(VillageInfrastructureConstraint toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageInfrastructureConstraint toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillagePowerSupplyReliability() != null) {
            this.setVillagePowerSupplyReliability(toBeUpdated.getVillagePowerSupplyReliability());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageInternalRoadQuality() != null) {
            this.setVillageInternalRoadQuality(toBeUpdated.getVillageInternalRoadQuality());
        }
        if (toBeUpdated.getVillagetopinfrastructureneedList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getVillagetopinfrastructureneedList() != null) {
                this.getVillagetopinfrastructureneedList().forEach(current -> {
                    toBeUpdated.getVillagetopinfrastructureneedList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getVillagetopinfrastructureneedList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageInfrastructureConstraint(this);
                    this.getVillagetopinfrastructureneedList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getVillagetransportconnectivityissList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getVillagetransportconnectivityissList() != null) {
                this.getVillagetransportconnectivityissList().forEach(current -> {
                    toBeUpdated.getVillagetransportconnectivityissList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getVillagetransportconnectivityissList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageInfrastructureConstraint(this);
                    this.getVillagetransportconnectivityissList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getVillagewatersupplygapList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getVillagewatersupplygapList() != null) {
                this.getVillagewatersupplygapList().forEach(current -> {
                    toBeUpdated.getVillagewatersupplygapList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getVillagewatersupplygapList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageInfrastructureConstraint(this);
                    this.getVillagewatersupplygapList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
