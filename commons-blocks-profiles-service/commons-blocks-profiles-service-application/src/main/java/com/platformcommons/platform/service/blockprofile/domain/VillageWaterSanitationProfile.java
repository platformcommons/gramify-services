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
@Table(name = "village_water_sanitation_profile")
public class VillageWaterSanitationProfile extends  BaseTransactionalEntity implements DomainEntity<VillageWaterSanitationProfile> {

    @Column(
         name = "has100pct_toilet_coverage_official"
    )
    private Boolean has100pctToiletCoverageOfficial;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue shareHhPipedWater;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
         name = "has_solid_waste_system"
    )
    private Boolean hasSolidWasteSystem;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue estimatedFunctionalToiletRate;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue shareHhBorewellHandpump;


    @Column(
             name = "has_liquid_waste_system"
    )
    private String hasLiquidWasteSystem;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue shareHhOpenWell;





    @Builder
    public VillageWaterSanitationProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Boolean has100pctToiletCoverageOfficial,
        UoMValue shareHhPipedWater,
        Long id,
        LinkedCode linkedActor,
        Boolean hasSolidWasteSystem,
        LinkedCode linkedContext,
        UoMValue estimatedFunctionalToiletRate,
        UoMValue shareHhBorewellHandpump,
        String hasLiquidWasteSystem,
        UoMValue shareHhOpenWell
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.has100pctToiletCoverageOfficial = has100pctToiletCoverageOfficial;
        this.shareHhPipedWater = shareHhPipedWater;
        this.id = id;
        this.linkedActor = linkedActor;
        this.hasSolidWasteSystem = hasSolidWasteSystem;
        this.linkedContext = linkedContext;
        this.estimatedFunctionalToiletRate = estimatedFunctionalToiletRate;
        this.shareHhBorewellHandpump = shareHhBorewellHandpump;
        this.hasLiquidWasteSystem = hasLiquidWasteSystem;
        this.shareHhOpenWell = shareHhOpenWell;


    }


    public void update(VillageWaterSanitationProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageWaterSanitationProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getHas100pctToiletCoverageOfficial() != null) {
            this.setHas100pctToiletCoverageOfficial(toBeUpdated.getHas100pctToiletCoverageOfficial());
        }
        if (toBeUpdated.getShareHhPipedWater() != null) {
            this.setShareHhPipedWater(toBeUpdated.getShareHhPipedWater());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getHasSolidWasteSystem() != null) {
            this.setHasSolidWasteSystem(toBeUpdated.getHasSolidWasteSystem());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getEstimatedFunctionalToiletRate() != null) {
            this.setEstimatedFunctionalToiletRate(toBeUpdated.getEstimatedFunctionalToiletRate());
        }
        if (toBeUpdated.getShareHhBorewellHandpump() != null) {
            this.setShareHhBorewellHandpump(toBeUpdated.getShareHhBorewellHandpump());
        }
        if (toBeUpdated.getHasLiquidWasteSystem() != null) {
            this.setHasLiquidWasteSystem(toBeUpdated.getHasLiquidWasteSystem());
        }
        if (toBeUpdated.getShareHhOpenWell() != null) {
            this.setShareHhOpenWell(toBeUpdated.getShareHhOpenWell());
        }
    }

    public void init() {

    }
}
