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
@Table(name = "production_season")
public class ProductionSeason extends  BaseTransactionalEntity implements DomainEntity<ProductionSeason> {

    @ManyToOne
    @JoinColumn(name = "villageFisheriesProfile_id")
    private VillageFisheriesProfile villageFisheriesProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageHorticultureProfile_id")
    private VillageHorticultureProfile villageHorticultureProfile;


    @Column(
             name = "production_season"
    )
    private String productionSeason;






    @Builder
    public ProductionSeason(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageFisheriesProfile villageFisheriesProfile,
        Long id,
        VillageHorticultureProfile villageHorticultureProfile,
        String productionSeason
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageFisheriesProfile = villageFisheriesProfile;
        this.id = id;
        this.villageHorticultureProfile = villageHorticultureProfile;
        this.productionSeason = productionSeason;


    }


    public void update(ProductionSeason toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(ProductionSeason toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageFisheriesProfile() != null) {
            this.setVillageFisheriesProfile(toBeUpdated.getVillageFisheriesProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageHorticultureProfile() != null) {
            this.setVillageHorticultureProfile(toBeUpdated.getVillageHorticultureProfile());
        }
        if (toBeUpdated.getProductionSeason() != null) {
            this.setProductionSeason(toBeUpdated.getProductionSeason());
        }
    }

    public void init() {

    }
}
