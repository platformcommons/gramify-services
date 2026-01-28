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
@Table(name = "storage_needed_for_crop")
public class StorageNeededForCrop extends  BaseTransactionalEntity implements DomainEntity<StorageNeededForCrop> {

    @Column(
             name = "storage_needed_for_crops"
    )
    private String storageNeededForCrops;



    @ManyToOne
    @JoinColumn(name = "villageAgriInputDemandProfile_id")
    private VillageAgriInputDemandProfile villageAgriInputDemandProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public StorageNeededForCrop(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String storageNeededForCrops,
        VillageAgriInputDemandProfile villageAgriInputDemandProfile,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.storageNeededForCrops = storageNeededForCrops;
        this.villageAgriInputDemandProfile = villageAgriInputDemandProfile;
        this.id = id;


    }


    public void update(StorageNeededForCrop toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(StorageNeededForCrop toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getStorageNeededForCrops() != null) {
            this.setStorageNeededForCrops(toBeUpdated.getStorageNeededForCrops());
        }
        if (toBeUpdated.getVillageAgriInputDemandProfile() != null) {
            this.setVillageAgriInputDemandProfile(toBeUpdated.getVillageAgriInputDemandProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
