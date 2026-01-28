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
@Table(name = "village_youth_aspirations")
public class VillageYouthAspirations extends  BaseTransactionalEntity implements DomainEntity<VillageYouthAspirations> {

    @Column(
             name = "village_youth_aspirations"
    )
    private String villageYouthAspirations;



    @ManyToOne
    @JoinColumn(name = "villageHumanCapitalProfile_id")
    private VillageHumanCapitalProfile villageHumanCapitalProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public VillageYouthAspirations(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String villageYouthAspirations,
        VillageHumanCapitalProfile villageHumanCapitalProfile,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageYouthAspirations = villageYouthAspirations;
        this.villageHumanCapitalProfile = villageHumanCapitalProfile;
        this.id = id;


    }


    public void update(VillageYouthAspirations toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageYouthAspirations toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageYouthAspirations() != null) {
            this.setVillageYouthAspirations(toBeUpdated.getVillageYouthAspirations());
        }
        if (toBeUpdated.getVillageHumanCapitalProfile() != null) {
            this.setVillageHumanCapitalProfile(toBeUpdated.getVillageHumanCapitalProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
