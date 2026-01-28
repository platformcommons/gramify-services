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
@Table(name = "raw_materials_needed_for_industry")
public class RawMaterialsNeededForIndustry extends  BaseTransactionalEntity implements DomainEntity<RawMaterialsNeededForIndustry> {

    @Column(
             name = "raw_material_needed_for_industry"
    )
    private String rawMaterialNeededForIndustry;



    @ManyToOne
    @JoinColumn(name = "villageAgriInputDemandProfile_id")
    private VillageAgriInputDemandProfile villageAgriInputDemandProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public RawMaterialsNeededForIndustry(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String rawMaterialNeededForIndustry,
        VillageAgriInputDemandProfile villageAgriInputDemandProfile,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.rawMaterialNeededForIndustry = rawMaterialNeededForIndustry;
        this.villageAgriInputDemandProfile = villageAgriInputDemandProfile;
        this.id = id;


    }


    public void update(RawMaterialsNeededForIndustry toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(RawMaterialsNeededForIndustry toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getRawMaterialNeededForIndustry() != null) {
            this.setRawMaterialNeededForIndustry(toBeUpdated.getRawMaterialNeededForIndustry());
        }
        if (toBeUpdated.getVillageAgriInputDemandProfile() != null) {
            this.setVillageAgriInputDemandProfile(toBeUpdated.getVillageAgriInputDemandProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
