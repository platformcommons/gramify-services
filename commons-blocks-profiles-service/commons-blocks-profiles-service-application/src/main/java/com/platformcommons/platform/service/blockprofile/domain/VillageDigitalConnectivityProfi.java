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
@Table(name = "village_digital_connectivity_profi")
public class VillageDigitalConnectivityProfi extends  BaseTransactionalEntity implements DomainEntity<VillageDigitalConnectivityProfi> {

    @Column(
         name = "farmers_use_internet_for_prices"
    )
    private Boolean farmersUseInternetForPrices;

    @Column(
         name = "youth_use_internet_for_education"
    )
    private Boolean youthUseInternetForEducation;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue internetPenetrationRate;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
             name = "mobile_network_quality"
    )
    private String mobileNetworkQuality;



    @Column(
         name = "has_digital_market_awareness"
    )
    private Boolean hasDigitalMarketAwareness;




    @Builder
    public VillageDigitalConnectivityProfi(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Boolean farmersUseInternetForPrices,
        Boolean youthUseInternetForEducation,
        Long id,
        LinkedCode linkedContext,
        UoMValue internetPenetrationRate,
        LinkedCode linkedActor,
        String mobileNetworkQuality,
        Boolean hasDigitalMarketAwareness
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.farmersUseInternetForPrices = farmersUseInternetForPrices;
        this.youthUseInternetForEducation = youthUseInternetForEducation;
        this.id = id;
        this.linkedContext = linkedContext;
        this.internetPenetrationRate = internetPenetrationRate;
        this.linkedActor = linkedActor;
        this.mobileNetworkQuality = mobileNetworkQuality;
        this.hasDigitalMarketAwareness = hasDigitalMarketAwareness;


    }


    public void update(VillageDigitalConnectivityProfi toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageDigitalConnectivityProfi toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getFarmersUseInternetForPrices() != null) {
            this.setFarmersUseInternetForPrices(toBeUpdated.getFarmersUseInternetForPrices());
        }
        if (toBeUpdated.getYouthUseInternetForEducation() != null) {
            this.setYouthUseInternetForEducation(toBeUpdated.getYouthUseInternetForEducation());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getInternetPenetrationRate() != null) {
            this.setInternetPenetrationRate(toBeUpdated.getInternetPenetrationRate());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getMobileNetworkQuality() != null) {
            this.setMobileNetworkQuality(toBeUpdated.getMobileNetworkQuality());
        }
        if (toBeUpdated.getHasDigitalMarketAwareness() != null) {
            this.setHasDigitalMarketAwareness(toBeUpdated.getHasDigitalMarketAwareness());
        }
    }

    public void init() {

    }
}
