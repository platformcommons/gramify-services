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
@Table(name = "village_fisheries_profile")
public class VillageFisheriesProfile extends  BaseTransactionalEntity implements DomainEntity<VillageFisheriesProfile> {

    @Column(
         name = "main_sale_destination"
    )
    private String mainSaleDestination;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
             name = "main_buyer_type"
    )
    private String mainBuyerType;



    @Column(
         name = "is_seasonal"
    )
    private Boolean isSeasonal;

    @Column(
         name = "estimated_surplus_kg_per_month"
    )
    private Double estimatedSurplusKgPerMonth;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
         name = "is_major_fish_type"
    )
    private Boolean isMajorFishType;

    @Column(
         name = "depends_on_monsoon"
    )
    private Boolean dependsOnMonsoon;

    @Column(
         name = "pond_or_tank_count"
    )
    private Integer pondOrTankCount;

    @Column(
             name = "fish_type"
    )
    private String fishType;



    @Column(
         name = "has_surplus"
    )
    private Boolean hasSurplus;


    @OneToMany(mappedBy = "villageFisheriesProfile", cascade = CascadeType.ALL)
    private Set<ProductionSeason> productionseasonList;


    @Builder
    public VillageFisheriesProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String mainSaleDestination,
        LinkedCode linkedContext,
        String mainBuyerType,
        Boolean isSeasonal,
        Double estimatedSurplusKgPerMonth,
        LinkedCode linkedActor,
        Long id,
        Boolean isMajorFishType,
        Boolean dependsOnMonsoon,
        Integer pondOrTankCount,
        String fishType,
        Boolean hasSurplus,
        Set<ProductionSeason> productionseasonList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.mainSaleDestination = mainSaleDestination;
        this.linkedContext = linkedContext;
        this.mainBuyerType = mainBuyerType;
        this.isSeasonal = isSeasonal;
        this.estimatedSurplusKgPerMonth = estimatedSurplusKgPerMonth;
        this.linkedActor = linkedActor;
        this.id = id;
        this.isMajorFishType = isMajorFishType;
        this.dependsOnMonsoon = dependsOnMonsoon;
        this.pondOrTankCount = pondOrTankCount;
        this.fishType = fishType;
        this.hasSurplus = hasSurplus;
        this.productionseasonList=productionseasonList;
        this.productionseasonList.forEach(it->it.setVillageFisheriesProfile(this));


    }


    public void update(VillageFisheriesProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageFisheriesProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getMainSaleDestination() != null) {
            this.setMainSaleDestination(toBeUpdated.getMainSaleDestination());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getMainBuyerType() != null) {
            this.setMainBuyerType(toBeUpdated.getMainBuyerType());
        }
        if (toBeUpdated.getIsSeasonal() != null) {
            this.setIsSeasonal(toBeUpdated.getIsSeasonal());
        }
        if (toBeUpdated.getEstimatedSurplusKgPerMonth() != null) {
            this.setEstimatedSurplusKgPerMonth(toBeUpdated.getEstimatedSurplusKgPerMonth());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getIsMajorFishType() != null) {
            this.setIsMajorFishType(toBeUpdated.getIsMajorFishType());
        }
        if (toBeUpdated.getDependsOnMonsoon() != null) {
            this.setDependsOnMonsoon(toBeUpdated.getDependsOnMonsoon());
        }
        if (toBeUpdated.getPondOrTankCount() != null) {
            this.setPondOrTankCount(toBeUpdated.getPondOrTankCount());
        }
        if (toBeUpdated.getFishType() != null) {
            this.setFishType(toBeUpdated.getFishType());
        }
        if (toBeUpdated.getHasSurplus() != null) {
            this.setHasSurplus(toBeUpdated.getHasSurplus());
        }
        if (toBeUpdated.getProductionseasonList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getProductionseasonList() != null) {
                this.getProductionseasonList().forEach(current -> {
                    toBeUpdated.getProductionseasonList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getProductionseasonList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageFisheriesProfile(this);
                    this.getProductionseasonList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
