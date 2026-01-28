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
@Table(name = "current_storage_method")
public class CurrentStorageMethod extends  BaseTransactionalEntity implements DomainEntity<CurrentStorageMethod> {

    @ManyToOne
    @JoinColumn(name = "villageAgriInputDemandProfile_id")
    private VillageAgriInputDemandProfile villageAgriInputDemandProfile;


    @Column(
             name = "current_storage_method"
    )
    private String currentStorageMethod;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public CurrentStorageMethod(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageAgriInputDemandProfile villageAgriInputDemandProfile,
        String currentStorageMethod,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageAgriInputDemandProfile = villageAgriInputDemandProfile;
        this.currentStorageMethod = currentStorageMethod;
        this.id = id;


    }


    public void update(CurrentStorageMethod toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(CurrentStorageMethod toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageAgriInputDemandProfile() != null) {
            this.setVillageAgriInputDemandProfile(toBeUpdated.getVillageAgriInputDemandProfile());
        }
        if (toBeUpdated.getCurrentStorageMethod() != null) {
            this.setCurrentStorageMethod(toBeUpdated.getCurrentStorageMethod());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
