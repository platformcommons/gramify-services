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
@Table(name = "village_top_infrastructure_need")
public class VillageTopInfrastructureNeed extends  BaseTransactionalEntity implements DomainEntity<VillageTopInfrastructureNeed> {

    @ManyToOne
    @JoinColumn(name = "villageInfrastructureConstraint_id")
    private VillageInfrastructureConstraint villageInfrastructureConstraint;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "village_top_infrastructure_need"
    )
    private String villageTopInfrastructureNeed;






    @Builder
    public VillageTopInfrastructureNeed(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageInfrastructureConstraint villageInfrastructureConstraint,
        Long id,
        String villageTopInfrastructureNeed
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageInfrastructureConstraint = villageInfrastructureConstraint;
        this.id = id;
        this.villageTopInfrastructureNeed = villageTopInfrastructureNeed;


    }


    public void update(VillageTopInfrastructureNeed toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageTopInfrastructureNeed toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageInfrastructureConstraint() != null) {
            this.setVillageInfrastructureConstraint(toBeUpdated.getVillageInfrastructureConstraint());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageTopInfrastructureNeed() != null) {
            this.setVillageTopInfrastructureNeed(toBeUpdated.getVillageTopInfrastructureNeed());
        }
    }

    public void init() {

    }
}
