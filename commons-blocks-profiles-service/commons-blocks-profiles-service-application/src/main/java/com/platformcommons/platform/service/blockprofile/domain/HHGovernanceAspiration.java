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
@Table(name = "h_h_governance_aspiration")
public class HHGovernanceAspiration extends  BaseTransactionalEntity implements DomainEntity<HHGovernanceAspiration> {

    @Column(
             name = "governance_aspiration"
    )
    private String governanceAspiration;



    @ManyToOne
    @JoinColumn(name = "householdAspirationsProfile_id")
    private HouseholdAspirationsProfile householdAspirationsProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public HHGovernanceAspiration(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String governanceAspiration,
        HouseholdAspirationsProfile householdAspirationsProfile,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.governanceAspiration = governanceAspiration;
        this.householdAspirationsProfile = householdAspirationsProfile;
        this.id = id;


    }


    public void update(HHGovernanceAspiration toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HHGovernanceAspiration toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getGovernanceAspiration() != null) {
            this.setGovernanceAspiration(toBeUpdated.getGovernanceAspiration());
        }
        if (toBeUpdated.getHouseholdAspirationsProfile() != null) {
            this.setHouseholdAspirationsProfile(toBeUpdated.getHouseholdAspirationsProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
