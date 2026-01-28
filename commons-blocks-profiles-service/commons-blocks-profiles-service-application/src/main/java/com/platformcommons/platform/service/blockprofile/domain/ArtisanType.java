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
@Table(name = "artisan_type")
public class ArtisanType extends  BaseTransactionalEntity implements DomainEntity<ArtisanType> {

    @Column(
             name = "artisan_type"
    )
    private String artisanType;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageHumanResourceProfile_id")
    private VillageHumanResourceProfile villageHumanResourceProfile;





    @Builder
    public ArtisanType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String artisanType,
        Long id,
        VillageHumanResourceProfile villageHumanResourceProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.artisanType = artisanType;
        this.id = id;
        this.villageHumanResourceProfile = villageHumanResourceProfile;


    }


    public void update(ArtisanType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(ArtisanType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getArtisanType() != null) {
            this.setArtisanType(toBeUpdated.getArtisanType());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageHumanResourceProfile() != null) {
            this.setVillageHumanResourceProfile(toBeUpdated.getVillageHumanResourceProfile());
        }
    }

    public void init() {

    }
}
