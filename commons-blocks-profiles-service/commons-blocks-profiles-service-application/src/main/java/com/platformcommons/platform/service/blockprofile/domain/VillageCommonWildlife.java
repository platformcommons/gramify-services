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
@Table(name = "village_common_wildlife")
public class VillageCommonWildlife extends  BaseTransactionalEntity implements DomainEntity<VillageCommonWildlife> {

    @ManyToOne
    @JoinColumn(name = "villageForestResourceProfile_id")
    private VillageForestResourceProfile villageForestResourceProfile;


    @Column(
             name = "village_common_wildlife"
    )
    private String villageCommonWildlife;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public VillageCommonWildlife(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageForestResourceProfile villageForestResourceProfile,
        String villageCommonWildlife,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageForestResourceProfile = villageForestResourceProfile;
        this.villageCommonWildlife = villageCommonWildlife;
        this.id = id;


    }


    public void update(VillageCommonWildlife toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageCommonWildlife toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageForestResourceProfile() != null) {
            this.setVillageForestResourceProfile(toBeUpdated.getVillageForestResourceProfile());
        }
        if (toBeUpdated.getVillageCommonWildlife() != null) {
            this.setVillageCommonWildlife(toBeUpdated.getVillageCommonWildlife());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
