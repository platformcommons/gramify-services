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
@Table(name = "village_water_supply_gap")
public class VillageWaterSupplyGap extends  BaseTransactionalEntity implements DomainEntity<VillageWaterSupplyGap> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageInfrastructureConstraint_id")
    private VillageInfrastructureConstraint villageInfrastructureConstraint;


    @Column(
             name = "village_water_supply_gap"
    )
    private String villageWaterSupplyGap;






    @Builder
    public VillageWaterSupplyGap(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        VillageInfrastructureConstraint villageInfrastructureConstraint,
        String villageWaterSupplyGap
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.villageInfrastructureConstraint = villageInfrastructureConstraint;
        this.villageWaterSupplyGap = villageWaterSupplyGap;


    }


    public void update(VillageWaterSupplyGap toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageWaterSupplyGap toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageInfrastructureConstraint() != null) {
            this.setVillageInfrastructureConstraint(toBeUpdated.getVillageInfrastructureConstraint());
        }
        if (toBeUpdated.getVillageWaterSupplyGap() != null) {
            this.setVillageWaterSupplyGap(toBeUpdated.getVillageWaterSupplyGap());
        }
    }

    public void init() {

    }
}
