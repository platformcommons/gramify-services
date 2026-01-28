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
@Table(name = "cropping_season")
public class CroppingSeason extends  BaseTransactionalEntity implements DomainEntity<CroppingSeason> {

    @ManyToOne
    @JoinColumn(name = "villageCropProfile_id")
    private VillageCropProfile villageCropProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "cropping_season"
    )
    private String croppingSeason;






    @Builder
    public CroppingSeason(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageCropProfile villageCropProfile,
        Long id,
        String croppingSeason
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageCropProfile = villageCropProfile;
        this.id = id;
        this.croppingSeason = croppingSeason;


    }


    public void update(CroppingSeason toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(CroppingSeason toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageCropProfile() != null) {
            this.setVillageCropProfile(toBeUpdated.getVillageCropProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getCroppingSeason() != null) {
            this.setCroppingSeason(toBeUpdated.getCroppingSeason());
        }
    }

    public void init() {

    }
}
