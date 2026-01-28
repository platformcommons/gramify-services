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
@Table(name = "local_art_form_type")
public class LocalArtFormType extends  BaseTransactionalEntity implements DomainEntity<LocalArtFormType> {

    @Column(
             name = "local_art_form_type"
    )
    private String localArtFormType;



    @ManyToOne
    @JoinColumn(name = "villageCulturalResourceProfile_id")
    private VillageCulturalResourceProfile villageCulturalResourceProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public LocalArtFormType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String localArtFormType,
        VillageCulturalResourceProfile villageCulturalResourceProfile,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.localArtFormType = localArtFormType;
        this.villageCulturalResourceProfile = villageCulturalResourceProfile;
        this.id = id;


    }


    public void update(LocalArtFormType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(LocalArtFormType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLocalArtFormType() != null) {
            this.setLocalArtFormType(toBeUpdated.getLocalArtFormType());
        }
        if (toBeUpdated.getVillageCulturalResourceProfile() != null) {
            this.setVillageCulturalResourceProfile(toBeUpdated.getVillageCulturalResourceProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
