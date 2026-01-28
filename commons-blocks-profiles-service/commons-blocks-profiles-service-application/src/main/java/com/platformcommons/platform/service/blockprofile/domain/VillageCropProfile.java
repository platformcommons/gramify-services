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
@Table(name = "village_crop_profile")
public class VillageCropProfile extends  BaseTransactionalEntity implements DomainEntity<VillageCropProfile> {

    @Column(
         name = "has_surplus"
    )
    private Boolean hasSurplus;

    @Column(
             name = "main_buyer_type"
    )
    private String mainBuyerType;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
             name = "sowing_month"
    )
    private String sowingMonth;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
         name = "is_major_crop"
    )
    private Boolean isMajorCrop;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue distanceToMainMarket;


    @Column(
             name = "harvest_month"
    )
    private String harvestMonth;



    @Column(
             name = "irrigation_dependence"
    )
    private String irrigationDependence;



    @Column(
         name = "main_sale_destination"
    )
    private String mainSaleDestination;


    @OneToMany(mappedBy = "villageCropProfile", cascade = CascadeType.ALL)
    private Set<Crop> cropList;
    @OneToMany(mappedBy = "villageCropProfile", cascade = CascadeType.ALL)
    private Set<CroppingSeason> croppingseasonList;


    @Builder
    public VillageCropProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Boolean hasSurplus,
        String mainBuyerType,
        LinkedCode linkedContext,
        LinkedCode linkedActor,
        String sowingMonth,
        Long id,
        Boolean isMajorCrop,
        UoMValue distanceToMainMarket,
        String harvestMonth,
        String irrigationDependence,
        String mainSaleDestination,
        Set<Crop> cropList,
        Set<CroppingSeason> croppingseasonList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.hasSurplus = hasSurplus;
        this.mainBuyerType = mainBuyerType;
        this.linkedContext = linkedContext;
        this.linkedActor = linkedActor;
        this.sowingMonth = sowingMonth;
        this.id = id;
        this.isMajorCrop = isMajorCrop;
        this.distanceToMainMarket = distanceToMainMarket;
        this.harvestMonth = harvestMonth;
        this.irrigationDependence = irrigationDependence;
        this.mainSaleDestination = mainSaleDestination;
        this.cropList=cropList;
        this.cropList.forEach(it->it.setVillageCropProfile(this));
        this.croppingseasonList=croppingseasonList;
        this.croppingseasonList.forEach(it->it.setVillageCropProfile(this));


    }


    public void update(VillageCropProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageCropProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getHasSurplus() != null) {
            this.setHasSurplus(toBeUpdated.getHasSurplus());
        }
        if (toBeUpdated.getMainBuyerType() != null) {
            this.setMainBuyerType(toBeUpdated.getMainBuyerType());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getSowingMonth() != null) {
            this.setSowingMonth(toBeUpdated.getSowingMonth());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getIsMajorCrop() != null) {
            this.setIsMajorCrop(toBeUpdated.getIsMajorCrop());
        }
        if (toBeUpdated.getDistanceToMainMarket() != null) {
            this.setDistanceToMainMarket(toBeUpdated.getDistanceToMainMarket());
        }
        if (toBeUpdated.getHarvestMonth() != null) {
            this.setHarvestMonth(toBeUpdated.getHarvestMonth());
        }
        if (toBeUpdated.getIrrigationDependence() != null) {
            this.setIrrigationDependence(toBeUpdated.getIrrigationDependence());
        }
        if (toBeUpdated.getMainSaleDestination() != null) {
            this.setMainSaleDestination(toBeUpdated.getMainSaleDestination());
        }
        if (toBeUpdated.getCropList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getCropList() != null) {
                this.getCropList().forEach(current -> {
                    toBeUpdated.getCropList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getCropList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageCropProfile(this);
                    this.getCropList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getCroppingseasonList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getCroppingseasonList() != null) {
                this.getCroppingseasonList().forEach(current -> {
                    toBeUpdated.getCroppingseasonList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getCroppingseasonList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageCropProfile(this);
                    this.getCroppingseasonList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
