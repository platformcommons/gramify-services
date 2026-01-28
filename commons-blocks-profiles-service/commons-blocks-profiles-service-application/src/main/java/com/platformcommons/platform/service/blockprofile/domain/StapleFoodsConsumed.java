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
@Table(name = "staple_foods_consumed")
public class StapleFoodsConsumed extends  BaseTransactionalEntity implements DomainEntity<StapleFoodsConsumed> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "staple_food_consumed"
    )
    private String stapleFoodConsumed;



    @ManyToOne
    @JoinColumn(name = "villageConsumptionPattern_id")
    private VillageConsumptionPattern villageConsumptionPattern;





    @Builder
    public StapleFoodsConsumed(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        String stapleFoodConsumed,
        VillageConsumptionPattern villageConsumptionPattern
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.stapleFoodConsumed = stapleFoodConsumed;
        this.villageConsumptionPattern = villageConsumptionPattern;


    }


    public void update(StapleFoodsConsumed toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(StapleFoodsConsumed toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getStapleFoodConsumed() != null) {
            this.setStapleFoodConsumed(toBeUpdated.getStapleFoodConsumed());
        }
        if (toBeUpdated.getVillageConsumptionPattern() != null) {
            this.setVillageConsumptionPattern(toBeUpdated.getVillageConsumptionPattern());
        }
    }

    public void init() {

    }
}
