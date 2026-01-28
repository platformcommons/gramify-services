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
@Table(name = "h_h_economic_constraints")
public class HHEconomicConstraints extends  BaseTransactionalEntity implements DomainEntity<HHEconomicConstraints> {

    @ManyToOne
    @JoinColumn(name = "householdConstraintsProfile_id")
    private HouseholdConstraintsProfile householdConstraintsProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "economic_constraint"
    )
    private String economicConstraint;






    @Builder
    public HHEconomicConstraints(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        HouseholdConstraintsProfile householdConstraintsProfile,
        Long id,
        String economicConstraint
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.householdConstraintsProfile = householdConstraintsProfile;
        this.id = id;
        this.economicConstraint = economicConstraint;


    }


    public void update(HHEconomicConstraints toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HHEconomicConstraints toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getHouseholdConstraintsProfile() != null) {
            this.setHouseholdConstraintsProfile(toBeUpdated.getHouseholdConstraintsProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getEconomicConstraint() != null) {
            this.setEconomicConstraint(toBeUpdated.getEconomicConstraint());
        }
    }

    public void init() {

    }
}
