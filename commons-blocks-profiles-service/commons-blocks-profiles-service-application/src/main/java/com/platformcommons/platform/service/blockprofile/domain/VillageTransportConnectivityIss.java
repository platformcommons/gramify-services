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
@Table(name = "village_transport_connectivity_iss")
public class VillageTransportConnectivityIss extends  BaseTransactionalEntity implements DomainEntity<VillageTransportConnectivityIss> {

    @Column(
             name = "village_transport_connectivity_issues"
    )
    private String villageTransportConnectivityIssues;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageInfrastructureConstraint_id")
    private VillageInfrastructureConstraint villageInfrastructureConstraint;





    @Builder
    public VillageTransportConnectivityIss(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String villageTransportConnectivityIssues,
        Long id,
        VillageInfrastructureConstraint villageInfrastructureConstraint
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageTransportConnectivityIssues = villageTransportConnectivityIssues;
        this.id = id;
        this.villageInfrastructureConstraint = villageInfrastructureConstraint;


    }


    public void update(VillageTransportConnectivityIss toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageTransportConnectivityIss toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageTransportConnectivityIssues() != null) {
            this.setVillageTransportConnectivityIssues(toBeUpdated.getVillageTransportConnectivityIssues());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageInfrastructureConstraint() != null) {
            this.setVillageInfrastructureConstraint(toBeUpdated.getVillageInfrastructureConstraint());
        }
    }

    public void init() {

    }
}
