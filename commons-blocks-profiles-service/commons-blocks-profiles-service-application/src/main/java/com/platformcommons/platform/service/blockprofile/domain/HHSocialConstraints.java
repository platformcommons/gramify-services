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
@Table(name = "h_h_social_constraints")
public class HHSocialConstraints extends  BaseTransactionalEntity implements DomainEntity<HHSocialConstraints> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "social_constraint"
    )
    private String socialConstraint;



    @ManyToOne
    @JoinColumn(name = "householdConstraintsProfile_id")
    private HouseholdConstraintsProfile householdConstraintsProfile;





    @Builder
    public HHSocialConstraints(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        String socialConstraint,
        HouseholdConstraintsProfile householdConstraintsProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.socialConstraint = socialConstraint;
        this.householdConstraintsProfile = householdConstraintsProfile;


    }


    public void update(HHSocialConstraints toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HHSocialConstraints toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getSocialConstraint() != null) {
            this.setSocialConstraint(toBeUpdated.getSocialConstraint());
        }
        if (toBeUpdated.getHouseholdConstraintsProfile() != null) {
            this.setHouseholdConstraintsProfile(toBeUpdated.getHouseholdConstraintsProfile());
        }
    }

    public void init() {

    }
}
