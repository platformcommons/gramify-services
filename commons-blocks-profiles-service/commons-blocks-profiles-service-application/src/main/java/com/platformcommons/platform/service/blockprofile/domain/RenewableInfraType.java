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
@Table(name = "renewable_infra_type")
public class RenewableInfraType extends  BaseTransactionalEntity implements DomainEntity<RenewableInfraType> {

    @ManyToOne
    @JoinColumn(name = "villageElectricityInfrastructur_id")
    private VillageElectricityInfrastructur villageElectricityInfrastructur;


    @Column(
             name = "renewable_infra_type"
    )
    private String renewableInfraType;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public RenewableInfraType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageElectricityInfrastructur villageElectricityInfrastructur,
        String renewableInfraType,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageElectricityInfrastructur = villageElectricityInfrastructur;
        this.renewableInfraType = renewableInfraType;
        this.id = id;


    }


    public void update(RenewableInfraType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(RenewableInfraType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageElectricityInfrastructur() != null) {
            this.setVillageElectricityInfrastructur(toBeUpdated.getVillageElectricityInfrastructur());
        }
        if (toBeUpdated.getRenewableInfraType() != null) {
            this.setRenewableInfraType(toBeUpdated.getRenewableInfraType());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
