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
@Table(name = "village_land_resource_profile")
public class VillageLandResourceProfile extends  BaseTransactionalEntity implements DomainEntity<VillageLandResourceProfile> {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageForestLandShare;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
         name = "village_total_area"
    )
    private String villageTotalArea;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageWastelandShare;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageFallowLandShare;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageCroppingIntensity;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageIrrigatedLandShare;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageArableLandShare;


    @Column(
         name = "village_soil_fertility_level"
    )
    private String villageSoilFertilityLevel;


    @OneToMany(mappedBy = "villageLandResourceProfile", cascade = CascadeType.ALL)
    private Set<VillageSoilType> villagesoiltypeList;


    @Builder
    public VillageLandResourceProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        UoMValue villageForestLandShare,
        LinkedCode linkedActor,
        String villageTotalArea,
        UoMValue villageWastelandShare,
        LinkedCode linkedContext,
        UoMValue villageFallowLandShare,
        UoMValue villageCroppingIntensity,
        Long id,
        UoMValue villageIrrigatedLandShare,
        UoMValue villageArableLandShare,
        String villageSoilFertilityLevel,
        Set<VillageSoilType> villagesoiltypeList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageForestLandShare = villageForestLandShare;
        this.linkedActor = linkedActor;
        this.villageTotalArea = villageTotalArea;
        this.villageWastelandShare = villageWastelandShare;
        this.linkedContext = linkedContext;
        this.villageFallowLandShare = villageFallowLandShare;
        this.villageCroppingIntensity = villageCroppingIntensity;
        this.id = id;
        this.villageIrrigatedLandShare = villageIrrigatedLandShare;
        this.villageArableLandShare = villageArableLandShare;
        this.villageSoilFertilityLevel = villageSoilFertilityLevel;
        this.villagesoiltypeList=villagesoiltypeList;
        this.villagesoiltypeList.forEach(it->it.setVillageLandResourceProfile(this));


    }


    public void update(VillageLandResourceProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageLandResourceProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageForestLandShare() != null) {
            this.setVillageForestLandShare(toBeUpdated.getVillageForestLandShare());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getVillageTotalArea() != null) {
            this.setVillageTotalArea(toBeUpdated.getVillageTotalArea());
        }
        if (toBeUpdated.getVillageWastelandShare() != null) {
            this.setVillageWastelandShare(toBeUpdated.getVillageWastelandShare());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getVillageFallowLandShare() != null) {
            this.setVillageFallowLandShare(toBeUpdated.getVillageFallowLandShare());
        }
        if (toBeUpdated.getVillageCroppingIntensity() != null) {
            this.setVillageCroppingIntensity(toBeUpdated.getVillageCroppingIntensity());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageIrrigatedLandShare() != null) {
            this.setVillageIrrigatedLandShare(toBeUpdated.getVillageIrrigatedLandShare());
        }
        if (toBeUpdated.getVillageArableLandShare() != null) {
            this.setVillageArableLandShare(toBeUpdated.getVillageArableLandShare());
        }
        if (toBeUpdated.getVillageSoilFertilityLevel() != null) {
            this.setVillageSoilFertilityLevel(toBeUpdated.getVillageSoilFertilityLevel());
        }
        if (toBeUpdated.getVillagesoiltypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getVillagesoiltypeList() != null) {
                this.getVillagesoiltypeList().forEach(current -> {
                    toBeUpdated.getVillagesoiltypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getVillagesoiltypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageLandResourceProfile(this);
                    this.getVillagesoiltypeList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
