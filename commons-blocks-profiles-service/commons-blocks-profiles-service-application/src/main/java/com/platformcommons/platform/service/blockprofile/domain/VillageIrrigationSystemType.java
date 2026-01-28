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
@Table(name = "village_irrigation_system_type")
public class VillageIrrigationSystemType extends  BaseTransactionalEntity implements DomainEntity<VillageIrrigationSystemType> {

    @Column(
             name = "village_irrigation_system_type"
    )
    private String villageIrrigationSystemType;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageWaterResourceProfile_id")
    private VillageWaterResourceProfile villageWaterResourceProfile;





    @Builder
    public VillageIrrigationSystemType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String villageIrrigationSystemType,
        Long id,
        VillageWaterResourceProfile villageWaterResourceProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageIrrigationSystemType = villageIrrigationSystemType;
        this.id = id;
        this.villageWaterResourceProfile = villageWaterResourceProfile;


    }


    public void update(VillageIrrigationSystemType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageIrrigationSystemType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageIrrigationSystemType() != null) {
            this.setVillageIrrigationSystemType(toBeUpdated.getVillageIrrigationSystemType());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageWaterResourceProfile() != null) {
            this.setVillageWaterResourceProfile(toBeUpdated.getVillageWaterResourceProfile());
        }
    }

    public void init() {

    }
}
