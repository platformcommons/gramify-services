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
@Table(name = "surplus_produce_type")
public class SurplusProduceType extends  BaseTransactionalEntity implements DomainEntity<SurplusProduceType> {

    @ManyToOne
    @JoinColumn(name = "villageExportPotentialProfile_id")
    private VillageExportPotentialProfile villageExportPotentialProfile;


    @Column(
             name = "surplus_produce_type"
    )
    private String surplusProduceType;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageSurplusProduceProfile_id")
    private VillageSurplusProduceProfile villageSurplusProduceProfile;





    @Builder
    public SurplusProduceType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageExportPotentialProfile villageExportPotentialProfile,
        String surplusProduceType,
        Long id,
        VillageSurplusProduceProfile villageSurplusProduceProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageExportPotentialProfile = villageExportPotentialProfile;
        this.surplusProduceType = surplusProduceType;
        this.id = id;
        this.villageSurplusProduceProfile = villageSurplusProduceProfile;


    }


    public void update(SurplusProduceType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(SurplusProduceType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageExportPotentialProfile() != null) {
            this.setVillageExportPotentialProfile(toBeUpdated.getVillageExportPotentialProfile());
        }
        if (toBeUpdated.getSurplusProduceType() != null) {
            this.setSurplusProduceType(toBeUpdated.getSurplusProduceType());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageSurplusProduceProfile() != null) {
            this.setVillageSurplusProduceProfile(toBeUpdated.getVillageSurplusProduceProfile());
        }
    }

    public void init() {

    }
}
