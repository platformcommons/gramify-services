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
@Table(name = "village_road_connectivity_profile")
public class VillageRoadConnectivityProfile extends  BaseTransactionalEntity implements DomainEntity<VillageRoadConnectivityProfile> {

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



    @Column(
             name = "internal_road_condition"
    )
    private String internalRoadCondition;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue busFrequency;


    @Column(
         name = "has_all_weather_road"
    )
    private Boolean hasAllWeatherRoad;

    @Column(
         name = "distance_to_district_h_q_km"
    )
    private Double distanceToDistrictHQKm;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
         name = "has_private_vans"
    )
    private Boolean hasPrivateVans;

    @Column(
             name = "main_road_type_connection"
    )
    private String mainRoadTypeConnection;



    @Column(
             name = "road_usability_in_monsoon"
    )
    private String roadUsabilityInMonsoon;



    @Column(
         name = "has_public_bus_service"
    )
    private Boolean hasPublicBusService;




    @Builder
    public VillageRoadConnectivityProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        LinkedCode linkedContext,
        String internalRoadCondition,
        UoMValue busFrequency,
        Boolean hasAllWeatherRoad,
        Double distanceToDistrictHQKm,
        LinkedCode linkedActor,
        Boolean hasPrivateVans,
        String mainRoadTypeConnection,
        String roadUsabilityInMonsoon,
        Boolean hasPublicBusService
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.linkedContext = linkedContext;
        this.internalRoadCondition = internalRoadCondition;
        this.busFrequency = busFrequency;
        this.hasAllWeatherRoad = hasAllWeatherRoad;
        this.distanceToDistrictHQKm = distanceToDistrictHQKm;
        this.linkedActor = linkedActor;
        this.hasPrivateVans = hasPrivateVans;
        this.mainRoadTypeConnection = mainRoadTypeConnection;
        this.roadUsabilityInMonsoon = roadUsabilityInMonsoon;
        this.hasPublicBusService = hasPublicBusService;


    }


    public void update(VillageRoadConnectivityProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageRoadConnectivityProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getInternalRoadCondition() != null) {
            this.setInternalRoadCondition(toBeUpdated.getInternalRoadCondition());
        }
        if (toBeUpdated.getBusFrequency() != null) {
            this.setBusFrequency(toBeUpdated.getBusFrequency());
        }
        if (toBeUpdated.getHasAllWeatherRoad() != null) {
            this.setHasAllWeatherRoad(toBeUpdated.getHasAllWeatherRoad());
        }
        if (toBeUpdated.getDistanceToDistrictHQKm() != null) {
            this.setDistanceToDistrictHQKm(toBeUpdated.getDistanceToDistrictHQKm());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getHasPrivateVans() != null) {
            this.setHasPrivateVans(toBeUpdated.getHasPrivateVans());
        }
        if (toBeUpdated.getMainRoadTypeConnection() != null) {
            this.setMainRoadTypeConnection(toBeUpdated.getMainRoadTypeConnection());
        }
        if (toBeUpdated.getRoadUsabilityInMonsoon() != null) {
            this.setRoadUsabilityInMonsoon(toBeUpdated.getRoadUsabilityInMonsoon());
        }
        if (toBeUpdated.getHasPublicBusService() != null) {
            this.setHasPublicBusService(toBeUpdated.getHasPublicBusService());
        }
    }

    public void init() {

    }
}
