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
@Table(name = "where_people_go_for_common_illness")
public class WherePeopleGoForCommonIllness extends  BaseTransactionalEntity implements DomainEntity<WherePeopleGoForCommonIllness> {

    @ManyToOne
    @JoinColumn(name = "villageServiceDemandProfile_id")
    private VillageServiceDemandProfile villageServiceDemandProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "where_people_go_for_common_illness"
    )
    private String wherePeopleGoForCommonIllness;






    @Builder
    public WherePeopleGoForCommonIllness(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageServiceDemandProfile villageServiceDemandProfile,
        Long id,
        String wherePeopleGoForCommonIllness
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageServiceDemandProfile = villageServiceDemandProfile;
        this.id = id;
        this.wherePeopleGoForCommonIllness = wherePeopleGoForCommonIllness;


    }


    public void update(WherePeopleGoForCommonIllness toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(WherePeopleGoForCommonIllness toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageServiceDemandProfile() != null) {
            this.setVillageServiceDemandProfile(toBeUpdated.getVillageServiceDemandProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getWherePeopleGoForCommonIllness() != null) {
            this.setWherePeopleGoForCommonIllness(toBeUpdated.getWherePeopleGoForCommonIllness());
        }
    }

    public void init() {

    }
}
