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
@Table(name = "village_other_infrastructure")
public class VillageOtherInfrastructure extends  BaseTransactionalEntity implements DomainEntity<VillageOtherInfrastructure> {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue atmCount;


    @Column(
             name = "public_bus_service_frequency"
    )
    private String publicBusServiceFrequency;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
         name = "public_cold_storage_available"
    )
    private Boolean publicColdStorageAvailable;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue warehouseCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue bankBranchCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue fpoGodownCapacityMt;


    @Column(
             name = "atm_functionality_reliability"
    )
    private String atmFunctionalityReliability;



    @Column(
         name = "has_bus_stand"
    )
    private Boolean hasBusStand;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;






    @Builder
    public VillageOtherInfrastructure(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        UoMValue atmCount,
        String publicBusServiceFrequency,
        LinkedCode linkedActor,
        Boolean publicColdStorageAvailable,
        Long id,
        UoMValue warehouseCount,
        UoMValue bankBranchCount,
        UoMValue fpoGodownCapacityMt,
        String atmFunctionalityReliability,
        Boolean hasBusStand,
        LinkedCode linkedContext
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.atmCount = atmCount;
        this.publicBusServiceFrequency = publicBusServiceFrequency;
        this.linkedActor = linkedActor;
        this.publicColdStorageAvailable = publicColdStorageAvailable;
        this.id = id;
        this.warehouseCount = warehouseCount;
        this.bankBranchCount = bankBranchCount;
        this.fpoGodownCapacityMt = fpoGodownCapacityMt;
        this.atmFunctionalityReliability = atmFunctionalityReliability;
        this.hasBusStand = hasBusStand;
        this.linkedContext = linkedContext;


    }


    public void update(VillageOtherInfrastructure toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageOtherInfrastructure toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getAtmCount() != null) {
            this.setAtmCount(toBeUpdated.getAtmCount());
        }
        if (toBeUpdated.getPublicBusServiceFrequency() != null) {
            this.setPublicBusServiceFrequency(toBeUpdated.getPublicBusServiceFrequency());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getPublicColdStorageAvailable() != null) {
            this.setPublicColdStorageAvailable(toBeUpdated.getPublicColdStorageAvailable());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getWarehouseCount() != null) {
            this.setWarehouseCount(toBeUpdated.getWarehouseCount());
        }
        if (toBeUpdated.getBankBranchCount() != null) {
            this.setBankBranchCount(toBeUpdated.getBankBranchCount());
        }
        if (toBeUpdated.getFpoGodownCapacityMt() != null) {
            this.setFpoGodownCapacityMt(toBeUpdated.getFpoGodownCapacityMt());
        }
        if (toBeUpdated.getAtmFunctionalityReliability() != null) {
            this.setAtmFunctionalityReliability(toBeUpdated.getAtmFunctionalityReliability());
        }
        if (toBeUpdated.getHasBusStand() != null) {
            this.setHasBusStand(toBeUpdated.getHasBusStand());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
    }

    public void init() {

    }
}
