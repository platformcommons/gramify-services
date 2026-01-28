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
@Table(name = "village_environmental_risk_profile")
public class VillageEnvironmentalRiskProfile extends  BaseTransactionalEntity implements DomainEntity<VillageEnvironmentalRiskProfile> {

    @Column(
         name = "village_faces_drought"
    )
    private Boolean villageFacesDrought;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



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
             name = "village_soil_erosion_severity"
    )
    private String villageSoilErosionSeverity;



    @Column(
             name = "village_groundwater_decline_level"
    )
    private String villageGroundwaterDeclineLevel;



    @Column(
             name = "village_rainfall_irregularity_level"
    )
    private String villageRainfallIrregularityLevel;






    @Builder
    public VillageEnvironmentalRiskProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Boolean villageFacesDrought,
        LinkedCode linkedContext,
        Long id,
        LinkedCode linkedActor,
        String villageSoilErosionSeverity,
        String villageGroundwaterDeclineLevel,
        String villageRainfallIrregularityLevel
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageFacesDrought = villageFacesDrought;
        this.linkedContext = linkedContext;
        this.id = id;
        this.linkedActor = linkedActor;
        this.villageSoilErosionSeverity = villageSoilErosionSeverity;
        this.villageGroundwaterDeclineLevel = villageGroundwaterDeclineLevel;
        this.villageRainfallIrregularityLevel = villageRainfallIrregularityLevel;


    }


    public void update(VillageEnvironmentalRiskProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageEnvironmentalRiskProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageFacesDrought() != null) {
            this.setVillageFacesDrought(toBeUpdated.getVillageFacesDrought());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getVillageSoilErosionSeverity() != null) {
            this.setVillageSoilErosionSeverity(toBeUpdated.getVillageSoilErosionSeverity());
        }
        if (toBeUpdated.getVillageGroundwaterDeclineLevel() != null) {
            this.setVillageGroundwaterDeclineLevel(toBeUpdated.getVillageGroundwaterDeclineLevel());
        }
        if (toBeUpdated.getVillageRainfallIrregularityLevel() != null) {
            this.setVillageRainfallIrregularityLevel(toBeUpdated.getVillageRainfallIrregularityLevel());
        }
    }

    public void init() {

    }
}
