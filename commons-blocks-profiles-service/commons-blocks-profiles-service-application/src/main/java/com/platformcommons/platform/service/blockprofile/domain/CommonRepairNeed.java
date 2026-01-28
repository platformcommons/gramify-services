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
@Table(name = "common_repair_need")
public class CommonRepairNeed extends  BaseTransactionalEntity implements DomainEntity<CommonRepairNeed> {

    @Column(
             name = "common_repair_need"
    )
    private String commonRepairNeed;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageServiceDemandProfile_id")
    private VillageServiceDemandProfile villageServiceDemandProfile;





    @Builder
    public CommonRepairNeed(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String commonRepairNeed,
        Long id,
        VillageServiceDemandProfile villageServiceDemandProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.commonRepairNeed = commonRepairNeed;
        this.id = id;
        this.villageServiceDemandProfile = villageServiceDemandProfile;


    }


    public void update(CommonRepairNeed toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(CommonRepairNeed toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getCommonRepairNeed() != null) {
            this.setCommonRepairNeed(toBeUpdated.getCommonRepairNeed());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageServiceDemandProfile() != null) {
            this.setVillageServiceDemandProfile(toBeUpdated.getVillageServiceDemandProfile());
        }
    }

    public void init() {

    }
}
