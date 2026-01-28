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
@Table(name = "village_market_profile")
public class VillageMarketProfile extends  BaseTransactionalEntity implements DomainEntity<VillageMarketProfile> {

    @Column(
         name = "has_electronic_weighing_scale"
    )
    private Boolean hasElectronicWeighingScale;

    @Column(
         name = "has_cold_storage"
    )
    private Boolean hasColdStorage;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
         name = "is_primary_market_for_village"
    )
    private Boolean isPrimaryMarketForVillage;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
         name = "nearest_apmc_market"
    )
    private String nearestApmcMarket;

    @Column(
         name = "is_regulated"
    )
    private Boolean isRegulated;

    @Column(
         name = "has_shops_or_stalls"
    )
    private Boolean hasShopsOrStalls;

    @Column(
             name = "market_type"
    )
    private String marketType;



    @Column(
         name = "distance_to_informal_market_km"
    )
    private Double distanceToInformalMarketKm;

    @Column(
         name = "distance_to_apmc_km"
    )
    private Double distanceToApmcKm;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue coldStorageCapacity;


    @Column(
         name = "has_sheds"
    )
    private Boolean hasSheds;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue frequency;


    @Column(
         name = "nearest_informal_market_name"
    )
    private String nearestInformalMarketName;

    @Column(
         name = "has_weighing_scale"
    )
    private Boolean hasWeighingScale;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;




    @OneToMany(mappedBy = "villageMarketProfile", cascade = CascadeType.ALL)
    private Set<OperatingDay> operatingdayList;


    @Builder
    public VillageMarketProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Boolean hasElectronicWeighingScale,
        Boolean hasColdStorage,
        Long id,
        Boolean isPrimaryMarketForVillage,
        LinkedCode linkedContext,
        String nearestApmcMarket,
        Boolean isRegulated,
        Boolean hasShopsOrStalls,
        String marketType,
        Double distanceToInformalMarketKm,
        Double distanceToApmcKm,
        UoMValue coldStorageCapacity,
        Boolean hasSheds,
        UoMValue frequency,
        String nearestInformalMarketName,
        Boolean hasWeighingScale,
        LinkedCode linkedActor,
        Set<OperatingDay> operatingdayList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.hasElectronicWeighingScale = hasElectronicWeighingScale;
        this.hasColdStorage = hasColdStorage;
        this.id = id;
        this.isPrimaryMarketForVillage = isPrimaryMarketForVillage;
        this.linkedContext = linkedContext;
        this.nearestApmcMarket = nearestApmcMarket;
        this.isRegulated = isRegulated;
        this.hasShopsOrStalls = hasShopsOrStalls;
        this.marketType = marketType;
        this.distanceToInformalMarketKm = distanceToInformalMarketKm;
        this.distanceToApmcKm = distanceToApmcKm;
        this.coldStorageCapacity = coldStorageCapacity;
        this.hasSheds = hasSheds;
        this.frequency = frequency;
        this.nearestInformalMarketName = nearestInformalMarketName;
        this.hasWeighingScale = hasWeighingScale;
        this.linkedActor = linkedActor;
        this.operatingdayList=operatingdayList;
        this.operatingdayList.forEach(it->it.setVillageMarketProfile(this));


    }


    public void update(VillageMarketProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageMarketProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getHasElectronicWeighingScale() != null) {
            this.setHasElectronicWeighingScale(toBeUpdated.getHasElectronicWeighingScale());
        }
        if (toBeUpdated.getHasColdStorage() != null) {
            this.setHasColdStorage(toBeUpdated.getHasColdStorage());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getIsPrimaryMarketForVillage() != null) {
            this.setIsPrimaryMarketForVillage(toBeUpdated.getIsPrimaryMarketForVillage());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getNearestApmcMarket() != null) {
            this.setNearestApmcMarket(toBeUpdated.getNearestApmcMarket());
        }
        if (toBeUpdated.getIsRegulated() != null) {
            this.setIsRegulated(toBeUpdated.getIsRegulated());
        }
        if (toBeUpdated.getHasShopsOrStalls() != null) {
            this.setHasShopsOrStalls(toBeUpdated.getHasShopsOrStalls());
        }
        if (toBeUpdated.getMarketType() != null) {
            this.setMarketType(toBeUpdated.getMarketType());
        }
        if (toBeUpdated.getDistanceToInformalMarketKm() != null) {
            this.setDistanceToInformalMarketKm(toBeUpdated.getDistanceToInformalMarketKm());
        }
        if (toBeUpdated.getDistanceToApmcKm() != null) {
            this.setDistanceToApmcKm(toBeUpdated.getDistanceToApmcKm());
        }
        if (toBeUpdated.getColdStorageCapacity() != null) {
            this.setColdStorageCapacity(toBeUpdated.getColdStorageCapacity());
        }
        if (toBeUpdated.getHasSheds() != null) {
            this.setHasSheds(toBeUpdated.getHasSheds());
        }
        if (toBeUpdated.getFrequency() != null) {
            this.setFrequency(toBeUpdated.getFrequency());
        }
        if (toBeUpdated.getNearestInformalMarketName() != null) {
            this.setNearestInformalMarketName(toBeUpdated.getNearestInformalMarketName());
        }
        if (toBeUpdated.getHasWeighingScale() != null) {
            this.setHasWeighingScale(toBeUpdated.getHasWeighingScale());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getOperatingdayList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getOperatingdayList() != null) {
                this.getOperatingdayList().forEach(current -> {
                    toBeUpdated.getOperatingdayList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getOperatingdayList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageMarketProfile(this);
                    this.getOperatingdayList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
