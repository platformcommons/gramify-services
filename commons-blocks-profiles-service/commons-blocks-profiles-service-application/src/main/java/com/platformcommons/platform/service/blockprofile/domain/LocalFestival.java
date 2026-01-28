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
@Table(name = "local_festival")
public class LocalFestival extends  BaseTransactionalEntity implements DomainEntity<LocalFestival> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageCulturalResourceProfile_id")
    private VillageCulturalResourceProfile villageCulturalResourceProfile;


    @Column(
             name = "local_festival"
    )
    private String localFestival;






    @Builder
    public LocalFestival(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        VillageCulturalResourceProfile villageCulturalResourceProfile,
        String localFestival
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.villageCulturalResourceProfile = villageCulturalResourceProfile;
        this.localFestival = localFestival;


    }


    public void update(LocalFestival toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(LocalFestival toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageCulturalResourceProfile() != null) {
            this.setVillageCulturalResourceProfile(toBeUpdated.getVillageCulturalResourceProfile());
        }
        if (toBeUpdated.getLocalFestival() != null) {
            this.setLocalFestival(toBeUpdated.getLocalFestival());
        }
    }

    public void init() {

    }
}
