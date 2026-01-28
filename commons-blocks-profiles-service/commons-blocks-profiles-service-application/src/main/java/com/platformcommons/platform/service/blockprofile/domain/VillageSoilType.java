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
@Table(name = "village_soil_type")
public class VillageSoilType extends  BaseTransactionalEntity implements DomainEntity<VillageSoilType> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "village_soil_type"
    )
    private String villageSoilType;



    @ManyToOne
    @JoinColumn(name = "villageLandResourceProfile_id")
    private VillageLandResourceProfile villageLandResourceProfile;





    @Builder
    public VillageSoilType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        String villageSoilType,
        VillageLandResourceProfile villageLandResourceProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.villageSoilType = villageSoilType;
        this.villageLandResourceProfile = villageLandResourceProfile;


    }


    public void update(VillageSoilType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageSoilType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageSoilType() != null) {
            this.setVillageSoilType(toBeUpdated.getVillageSoilType());
        }
        if (toBeUpdated.getVillageLandResourceProfile() != null) {
            this.setVillageLandResourceProfile(toBeUpdated.getVillageLandResourceProfile());
        }
    }

    public void init() {

    }
}
