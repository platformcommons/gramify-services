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
@Table(name = "h_h_skilled_worker_type")
public class HHSkilledWorkerType extends  BaseTransactionalEntity implements DomainEntity<HHSkilledWorkerType> {

    @Column(
             name = "skilled_worker_type"
    )
    private String skilledWorkerType;



    @ManyToOne
    @JoinColumn(name = "villageHumanResourceProfile_id")
    private VillageHumanResourceProfile villageHumanResourceProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public HHSkilledWorkerType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String skilledWorkerType,
        VillageHumanResourceProfile villageHumanResourceProfile,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.skilledWorkerType = skilledWorkerType;
        this.villageHumanResourceProfile = villageHumanResourceProfile;
        this.id = id;


    }


    public void update(HHSkilledWorkerType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HHSkilledWorkerType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getSkilledWorkerType() != null) {
            this.setSkilledWorkerType(toBeUpdated.getSkilledWorkerType());
        }
        if (toBeUpdated.getVillageHumanResourceProfile() != null) {
            this.setVillageHumanResourceProfile(toBeUpdated.getVillageHumanResourceProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
