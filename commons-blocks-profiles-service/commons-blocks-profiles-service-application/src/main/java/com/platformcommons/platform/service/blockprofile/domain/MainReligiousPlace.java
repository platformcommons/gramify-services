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
@Table(name = "main_religious_place")
public class MainReligiousPlace extends  BaseTransactionalEntity implements DomainEntity<MainReligiousPlace> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageCulturalResourceProfile_id")
    private VillageCulturalResourceProfile villageCulturalResourceProfile;


    @Column(
             name = "main_religious_place"
    )
    private String mainReligiousPlace;






    @Builder
    public MainReligiousPlace(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        VillageCulturalResourceProfile villageCulturalResourceProfile,
        String mainReligiousPlace
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.villageCulturalResourceProfile = villageCulturalResourceProfile;
        this.mainReligiousPlace = mainReligiousPlace;


    }


    public void update(MainReligiousPlace toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(MainReligiousPlace toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageCulturalResourceProfile() != null) {
            this.setVillageCulturalResourceProfile(toBeUpdated.getVillageCulturalResourceProfile());
        }
        if (toBeUpdated.getMainReligiousPlace() != null) {
            this.setMainReligiousPlace(toBeUpdated.getMainReligiousPlace());
        }
    }

    public void init() {

    }
}
