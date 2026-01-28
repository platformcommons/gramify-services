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
@Table(name = "where_people_go_for_repairs")
public class WherePeopleGoForRepairs extends  BaseTransactionalEntity implements DomainEntity<WherePeopleGoForRepairs> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageServiceDemandProfile_id")
    private VillageServiceDemandProfile villageServiceDemandProfile;


    @Column(
             name = "where_people_go_for_repairs"
    )
    private String wherePeopleGoForRepairs;






    @Builder
    public WherePeopleGoForRepairs(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        VillageServiceDemandProfile villageServiceDemandProfile,
        String wherePeopleGoForRepairs
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.villageServiceDemandProfile = villageServiceDemandProfile;
        this.wherePeopleGoForRepairs = wherePeopleGoForRepairs;


    }


    public void update(WherePeopleGoForRepairs toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(WherePeopleGoForRepairs toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageServiceDemandProfile() != null) {
            this.setVillageServiceDemandProfile(toBeUpdated.getVillageServiceDemandProfile());
        }
        if (toBeUpdated.getWherePeopleGoForRepairs() != null) {
            this.setWherePeopleGoForRepairs(toBeUpdated.getWherePeopleGoForRepairs());
        }
    }

    public void init() {

    }
}
