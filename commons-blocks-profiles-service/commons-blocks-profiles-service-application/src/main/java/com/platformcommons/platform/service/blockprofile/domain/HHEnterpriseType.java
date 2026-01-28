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
@Table(name = "h_h_enterprise_type")
public class HHEnterpriseType extends  BaseTransactionalEntity implements DomainEntity<HHEnterpriseType> {

    @ManyToOne
    @JoinColumn(name = "householdHumanResourceProfile_id")
    private HouseholdHumanResourceProfile householdHumanResourceProfile;


    @Column(
             name = "enterprise_type"
    )
    private String enterpriseType;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public HHEnterpriseType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        HouseholdHumanResourceProfile householdHumanResourceProfile,
        String enterpriseType,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.householdHumanResourceProfile = householdHumanResourceProfile;
        this.enterpriseType = enterpriseType;
        this.id = id;


    }


    public void update(HHEnterpriseType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HHEnterpriseType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getHouseholdHumanResourceProfile() != null) {
            this.setHouseholdHumanResourceProfile(toBeUpdated.getHouseholdHumanResourceProfile());
        }
        if (toBeUpdated.getEnterpriseType() != null) {
            this.setEnterpriseType(toBeUpdated.getEnterpriseType());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
