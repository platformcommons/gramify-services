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
@Table(name = "power_cut_season")
public class PowerCutSeason extends  BaseTransactionalEntity implements DomainEntity<PowerCutSeason> {

    @Column(
             name = "power_cut_seasons"
    )
    private String powerCutSeasons;



    @ManyToOne
    @JoinColumn(name = "villageElectricityInfrastructur_id")
    private VillageElectricityInfrastructur villageElectricityInfrastructur;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public PowerCutSeason(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String powerCutSeasons,
        VillageElectricityInfrastructur villageElectricityInfrastructur,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.powerCutSeasons = powerCutSeasons;
        this.villageElectricityInfrastructur = villageElectricityInfrastructur;
        this.id = id;


    }


    public void update(PowerCutSeason toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(PowerCutSeason toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getPowerCutSeasons() != null) {
            this.setPowerCutSeasons(toBeUpdated.getPowerCutSeasons());
        }
        if (toBeUpdated.getVillageElectricityInfrastructur() != null) {
            this.setVillageElectricityInfrastructur(toBeUpdated.getVillageElectricityInfrastructur());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
