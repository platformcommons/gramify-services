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
@Table(name = "village_road_infrastructure")
public class VillageRoadInfrastructure extends  BaseTransactionalEntity implements DomainEntity<VillageRoadInfrastructure> {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue ruralRoadUnpavedShare;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue roadLengthStateHighwayKm;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
         name = "road_usability_in_monsoon"
    )
    private String roadUsabilityInMonsoon;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue roadLengthRuralRoadKm;


    @Column(
         name = "has_all_weather_connectivity"
    )
    private Boolean hasAllWeatherConnectivity;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue roadLengthMdrKm;





    @Builder
    public VillageRoadInfrastructure(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        UoMValue ruralRoadUnpavedShare,
        Long id,
        UoMValue roadLengthStateHighwayKm,
        LinkedCode linkedActor,
        LinkedCode linkedContext,
        String roadUsabilityInMonsoon,
        UoMValue roadLengthRuralRoadKm,
        Boolean hasAllWeatherConnectivity,
        UoMValue roadLengthMdrKm
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.ruralRoadUnpavedShare = ruralRoadUnpavedShare;
        this.id = id;
        this.roadLengthStateHighwayKm = roadLengthStateHighwayKm;
        this.linkedActor = linkedActor;
        this.linkedContext = linkedContext;
        this.roadUsabilityInMonsoon = roadUsabilityInMonsoon;
        this.roadLengthRuralRoadKm = roadLengthRuralRoadKm;
        this.hasAllWeatherConnectivity = hasAllWeatherConnectivity;
        this.roadLengthMdrKm = roadLengthMdrKm;


    }


    public void update(VillageRoadInfrastructure toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageRoadInfrastructure toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getRuralRoadUnpavedShare() != null) {
            this.setRuralRoadUnpavedShare(toBeUpdated.getRuralRoadUnpavedShare());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getRoadLengthStateHighwayKm() != null) {
            this.setRoadLengthStateHighwayKm(toBeUpdated.getRoadLengthStateHighwayKm());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getRoadUsabilityInMonsoon() != null) {
            this.setRoadUsabilityInMonsoon(toBeUpdated.getRoadUsabilityInMonsoon());
        }
        if (toBeUpdated.getRoadLengthRuralRoadKm() != null) {
            this.setRoadLengthRuralRoadKm(toBeUpdated.getRoadLengthRuralRoadKm());
        }
        if (toBeUpdated.getHasAllWeatherConnectivity() != null) {
            this.setHasAllWeatherConnectivity(toBeUpdated.getHasAllWeatherConnectivity());
        }
        if (toBeUpdated.getRoadLengthMdrKm() != null) {
            this.setRoadLengthMdrKm(toBeUpdated.getRoadLengthMdrKm());
        }
    }

    public void init() {

    }
}
