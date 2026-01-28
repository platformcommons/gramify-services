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
@Table(name = "h_h_migration_month")
public class HHMigrationMonth extends  BaseTransactionalEntity implements DomainEntity<HHMigrationMonth> {

    @Column(
             name = "migration_month"
    )
    private String migrationMonth;



    @ManyToOne
    @JoinColumn(name = "householdLivelihoodProfile_id")
    private HouseholdLivelihoodProfile householdLivelihoodProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public HHMigrationMonth(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String migrationMonth,
        HouseholdLivelihoodProfile householdLivelihoodProfile,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.migrationMonth = migrationMonth;
        this.householdLivelihoodProfile = householdLivelihoodProfile;
        this.id = id;


    }


    public void update(HHMigrationMonth toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HHMigrationMonth toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getMigrationMonth() != null) {
            this.setMigrationMonth(toBeUpdated.getMigrationMonth());
        }
        if (toBeUpdated.getHouseholdLivelihoodProfile() != null) {
            this.setHouseholdLivelihoodProfile(toBeUpdated.getHouseholdLivelihoodProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
