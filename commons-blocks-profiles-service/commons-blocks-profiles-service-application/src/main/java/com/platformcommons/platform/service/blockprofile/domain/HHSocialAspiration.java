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
@Table(name = "h_h_social_aspiration")
public class HHSocialAspiration extends  BaseTransactionalEntity implements DomainEntity<HHSocialAspiration> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "householdAspirationsProfile_id")
    private HouseholdAspirationsProfile householdAspirationsProfile;


    @Column(
             name = "social_aspiration"
    )
    private String socialAspiration;






    @Builder
    public HHSocialAspiration(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        HouseholdAspirationsProfile householdAspirationsProfile,
        String socialAspiration
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.householdAspirationsProfile = householdAspirationsProfile;
        this.socialAspiration = socialAspiration;


    }


    public void update(HHSocialAspiration toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HHSocialAspiration toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getHouseholdAspirationsProfile() != null) {
            this.setHouseholdAspirationsProfile(toBeUpdated.getHouseholdAspirationsProfile());
        }
        if (toBeUpdated.getSocialAspiration() != null) {
            this.setSocialAspiration(toBeUpdated.getSocialAspiration());
        }
    }

    public void init() {

    }
}
