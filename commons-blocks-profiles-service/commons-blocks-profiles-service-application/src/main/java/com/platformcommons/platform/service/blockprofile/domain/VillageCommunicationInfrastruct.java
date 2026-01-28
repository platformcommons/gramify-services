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
@Table(name = "village_communication_infrastruct")
public class VillageCommunicationInfrastruct extends  BaseTransactionalEntity implements DomainEntity<VillageCommunicationInfrastruct> {

    @Column(
             name = "mobile_network_quality"
    )
    private String mobileNetworkQuality;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue internetPenetrationRate;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue cscCount;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue postOfficeCount;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
         name = "areas_with_weak_network_description"
    )
    private String areasWithWeakNetworkDescription;




    @Builder
    public VillageCommunicationInfrastruct(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String mobileNetworkQuality,
        UoMValue internetPenetrationRate,
        Long id,
        UoMValue cscCount,
        LinkedCode linkedActor,
        UoMValue postOfficeCount,
        LinkedCode linkedContext,
        String areasWithWeakNetworkDescription
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.mobileNetworkQuality = mobileNetworkQuality;
        this.internetPenetrationRate = internetPenetrationRate;
        this.id = id;
        this.cscCount = cscCount;
        this.linkedActor = linkedActor;
        this.postOfficeCount = postOfficeCount;
        this.linkedContext = linkedContext;
        this.areasWithWeakNetworkDescription = areasWithWeakNetworkDescription;


    }


    public void update(VillageCommunicationInfrastruct toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageCommunicationInfrastruct toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getMobileNetworkQuality() != null) {
            this.setMobileNetworkQuality(toBeUpdated.getMobileNetworkQuality());
        }
        if (toBeUpdated.getInternetPenetrationRate() != null) {
            this.setInternetPenetrationRate(toBeUpdated.getInternetPenetrationRate());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getCscCount() != null) {
            this.setCscCount(toBeUpdated.getCscCount());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getPostOfficeCount() != null) {
            this.setPostOfficeCount(toBeUpdated.getPostOfficeCount());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getAreasWithWeakNetworkDescription() != null) {
            this.setAreasWithWeakNetworkDescription(toBeUpdated.getAreasWithWeakNetworkDescription());
        }
    }

    public void init() {

    }
}
