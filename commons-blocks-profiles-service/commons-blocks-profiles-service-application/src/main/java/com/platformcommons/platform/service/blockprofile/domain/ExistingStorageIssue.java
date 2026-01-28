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
@Table(name = "existing_storage_issue")
public class ExistingStorageIssue extends  BaseTransactionalEntity implements DomainEntity<ExistingStorageIssue> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageAgriInputDemandProfile_id")
    private VillageAgriInputDemandProfile villageAgriInputDemandProfile;


    @Column(
             name = "existing_storage_issue"
    )
    private String existingStorageIssue;






    @Builder
    public ExistingStorageIssue(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        VillageAgriInputDemandProfile villageAgriInputDemandProfile,
        String existingStorageIssue
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.villageAgriInputDemandProfile = villageAgriInputDemandProfile;
        this.existingStorageIssue = existingStorageIssue;


    }


    public void update(ExistingStorageIssue toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(ExistingStorageIssue toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageAgriInputDemandProfile() != null) {
            this.setVillageAgriInputDemandProfile(toBeUpdated.getVillageAgriInputDemandProfile());
        }
        if (toBeUpdated.getExistingStorageIssue() != null) {
            this.setExistingStorageIssue(toBeUpdated.getExistingStorageIssue());
        }
    }

    public void init() {

    }
}
