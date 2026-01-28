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
@Table(name = "major_festival")
public class MajorFestival extends  BaseTransactionalEntity implements DomainEntity<MajorFestival> {

    @ManyToOne
    @JoinColumn(name = "villageCulturalResourceProfile_id")
    private VillageCulturalResourceProfile villageCulturalResourceProfile;


    @Column(
             name = "major_festival"
    )
    private String majorFestival;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public MajorFestival(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageCulturalResourceProfile villageCulturalResourceProfile,
        String majorFestival,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageCulturalResourceProfile = villageCulturalResourceProfile;
        this.majorFestival = majorFestival;
        this.id = id;


    }


    public void update(MajorFestival toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(MajorFestival toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageCulturalResourceProfile() != null) {
            this.setVillageCulturalResourceProfile(toBeUpdated.getVillageCulturalResourceProfile());
        }
        if (toBeUpdated.getMajorFestival() != null) {
            this.setMajorFestival(toBeUpdated.getMajorFestival());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
