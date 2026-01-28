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
@Table(name = "surplus_moved_through")
public class SurplusMovedThrough extends  BaseTransactionalEntity implements DomainEntity<SurplusMovedThrough> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "surplus_moved_through"
    )
    private String surplusMovedThrough;



    @ManyToOne
    @JoinColumn(name = "villageSurplusProduceProfile_id")
    private VillageSurplusProduceProfile villageSurplusProduceProfile;





    @Builder
    public SurplusMovedThrough(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        String surplusMovedThrough,
        VillageSurplusProduceProfile villageSurplusProduceProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.surplusMovedThrough = surplusMovedThrough;
        this.villageSurplusProduceProfile = villageSurplusProduceProfile;


    }


    public void update(SurplusMovedThrough toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(SurplusMovedThrough toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getSurplusMovedThrough() != null) {
            this.setSurplusMovedThrough(toBeUpdated.getSurplusMovedThrough());
        }
        if (toBeUpdated.getVillageSurplusProduceProfile() != null) {
            this.setVillageSurplusProduceProfile(toBeUpdated.getVillageSurplusProduceProfile());
        }
    }

    public void init() {

    }
}
