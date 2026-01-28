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
@Table(name = "emerging_enterprise_type")
public class EmergingEnterpriseType extends  BaseTransactionalEntity implements DomainEntity<EmergingEnterpriseType> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "emerging_enterprise_type"
    )
    private String emergingEnterpriseType;



    @ManyToOne
    @JoinColumn(name = "villageHumanResourceProfile_id")
    private VillageHumanResourceProfile villageHumanResourceProfile;





    @Builder
    public EmergingEnterpriseType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        String emergingEnterpriseType,
        VillageHumanResourceProfile villageHumanResourceProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.emergingEnterpriseType = emergingEnterpriseType;
        this.villageHumanResourceProfile = villageHumanResourceProfile;


    }


    public void update(EmergingEnterpriseType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(EmergingEnterpriseType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getEmergingEnterpriseType() != null) {
            this.setEmergingEnterpriseType(toBeUpdated.getEmergingEnterpriseType());
        }
        if (toBeUpdated.getVillageHumanResourceProfile() != null) {
            this.setVillageHumanResourceProfile(toBeUpdated.getVillageHumanResourceProfile());
        }
    }

    public void init() {

    }
}
