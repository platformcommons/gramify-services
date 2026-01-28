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
@Table(name = "village_livestock_profile")
public class VillageLivestockProfile extends  BaseTransactionalEntity implements DomainEntity<VillageLivestockProfile> {

    @Column(
         name = "has_surplus"
    )
    private Boolean hasSurplus;

    @Column(
         name = "main_sale_destination"
    )
    private String mainSaleDestination;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "livestock_type"
    )
    private String livestockType;



    @Column(
         name = "is_major_livestock_type"
    )
    private Boolean isMajorLivestockType;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
         name = "estimated_surplus_per_day_or_month"
    )
    private Double estimatedSurplusPerDayOrMonth;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue householdsKeepingLivestockShare;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue livestockPopulationEstimate;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
             name = "main_buyer_type"
    )
    private String mainBuyerType;




    @OneToMany(mappedBy = "villageLivestockProfile", cascade = CascadeType.ALL)
    private Set<ProductionSeasonality> productionseasonalityList;


    @Builder
    public VillageLivestockProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Boolean hasSurplus,
        String mainSaleDestination,
        Long id,
        String livestockType,
        Boolean isMajorLivestockType,
        LinkedCode linkedContext,
        Double estimatedSurplusPerDayOrMonth,
        UoMValue householdsKeepingLivestockShare,
        UoMValue livestockPopulationEstimate,
        LinkedCode linkedActor,
        String mainBuyerType,
        Set<ProductionSeasonality> productionseasonalityList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.hasSurplus = hasSurplus;
        this.mainSaleDestination = mainSaleDestination;
        this.id = id;
        this.livestockType = livestockType;
        this.isMajorLivestockType = isMajorLivestockType;
        this.linkedContext = linkedContext;
        this.estimatedSurplusPerDayOrMonth = estimatedSurplusPerDayOrMonth;
        this.householdsKeepingLivestockShare = householdsKeepingLivestockShare;
        this.livestockPopulationEstimate = livestockPopulationEstimate;
        this.linkedActor = linkedActor;
        this.mainBuyerType = mainBuyerType;
        this.productionseasonalityList=productionseasonalityList;
        this.productionseasonalityList.forEach(it->it.setVillageLivestockProfile(this));


    }


    public void update(VillageLivestockProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageLivestockProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getHasSurplus() != null) {
            this.setHasSurplus(toBeUpdated.getHasSurplus());
        }
        if (toBeUpdated.getMainSaleDestination() != null) {
            this.setMainSaleDestination(toBeUpdated.getMainSaleDestination());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLivestockType() != null) {
            this.setLivestockType(toBeUpdated.getLivestockType());
        }
        if (toBeUpdated.getIsMajorLivestockType() != null) {
            this.setIsMajorLivestockType(toBeUpdated.getIsMajorLivestockType());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getEstimatedSurplusPerDayOrMonth() != null) {
            this.setEstimatedSurplusPerDayOrMonth(toBeUpdated.getEstimatedSurplusPerDayOrMonth());
        }
        if (toBeUpdated.getHouseholdsKeepingLivestockShare() != null) {
            this.setHouseholdsKeepingLivestockShare(toBeUpdated.getHouseholdsKeepingLivestockShare());
        }
        if (toBeUpdated.getLivestockPopulationEstimate() != null) {
            this.setLivestockPopulationEstimate(toBeUpdated.getLivestockPopulationEstimate());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getMainBuyerType() != null) {
            this.setMainBuyerType(toBeUpdated.getMainBuyerType());
        }
        if (toBeUpdated.getProductionseasonalityList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getProductionseasonalityList() != null) {
                this.getProductionseasonalityList().forEach(current -> {
                    toBeUpdated.getProductionseasonalityList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getProductionseasonalityList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageLivestockProfile(this);
                    this.getProductionseasonalityList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
