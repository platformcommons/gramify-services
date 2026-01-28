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
@Table(name = "village_skill_shortage_type")
public class VillageSkillShortageType extends  BaseTransactionalEntity implements DomainEntity<VillageSkillShortageType> {

    @Column(
             name = "village_skill_shortage_type"
    )
    private String villageSkillShortageType;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageHumanCapitalProfile_id")
    private VillageHumanCapitalProfile villageHumanCapitalProfile;





    @Builder
    public VillageSkillShortageType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String villageSkillShortageType,
        Long id,
        VillageHumanCapitalProfile villageHumanCapitalProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageSkillShortageType = villageSkillShortageType;
        this.id = id;
        this.villageHumanCapitalProfile = villageHumanCapitalProfile;


    }


    public void update(VillageSkillShortageType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageSkillShortageType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageSkillShortageType() != null) {
            this.setVillageSkillShortageType(toBeUpdated.getVillageSkillShortageType());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageHumanCapitalProfile() != null) {
            this.setVillageHumanCapitalProfile(toBeUpdated.getVillageHumanCapitalProfile());
        }
    }

    public void init() {

    }
}
