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
@Table(name = "support_needed_for_niche_growth")
public class SupportNeededForNicheGrowth extends  BaseTransactionalEntity implements DomainEntity<SupportNeededForNicheGrowth> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "support_needed_for_niche_growth"
    )
    private String supportNeededForNicheGrowth;



    @ManyToOne
    @JoinColumn(name = "villageNicheProductProfile_id")
    private VillageNicheProductProfile villageNicheProductProfile;





    @Builder
    public SupportNeededForNicheGrowth(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        String supportNeededForNicheGrowth,
        VillageNicheProductProfile villageNicheProductProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.supportNeededForNicheGrowth = supportNeededForNicheGrowth;
        this.villageNicheProductProfile = villageNicheProductProfile;


    }


    public void update(SupportNeededForNicheGrowth toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(SupportNeededForNicheGrowth toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getSupportNeededForNicheGrowth() != null) {
            this.setSupportNeededForNicheGrowth(toBeUpdated.getSupportNeededForNicheGrowth());
        }
        if (toBeUpdated.getVillageNicheProductProfile() != null) {
            this.setVillageNicheProductProfile(toBeUpdated.getVillageNicheProductProfile());
        }
    }

    public void init() {

    }
}
