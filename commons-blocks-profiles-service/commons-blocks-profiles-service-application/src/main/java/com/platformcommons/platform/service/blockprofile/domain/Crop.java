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
@Table(name = "crop")
public class Crop extends  BaseTransactionalEntity implements DomainEntity<Crop> {

    @ManyToOne
    @JoinColumn(name = "villageCropProfile_id")
    private VillageCropProfile villageCropProfile;


    @Column(
             name = "crop_code"
    )
    private String cropCode;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public Crop(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageCropProfile villageCropProfile,
        String cropCode,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageCropProfile = villageCropProfile;
        this.cropCode = cropCode;
        this.id = id;


    }


    public void update(Crop toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(Crop toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageCropProfile() != null) {
            this.setVillageCropProfile(toBeUpdated.getVillageCropProfile());
        }
        if (toBeUpdated.getCropCode() != null) {
            this.setCropCode(toBeUpdated.getCropCode());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
