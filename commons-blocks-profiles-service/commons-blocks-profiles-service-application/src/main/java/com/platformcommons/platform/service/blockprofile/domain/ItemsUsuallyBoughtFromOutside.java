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
@Table(name = "items_usually_bought_from_outside")
public class ItemsUsuallyBoughtFromOutside extends  BaseTransactionalEntity implements DomainEntity<ItemsUsuallyBoughtFromOutside> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageConsumptionPattern_id")
    private VillageConsumptionPattern villageConsumptionPattern;


    @Column(
             name = "item_usually_bought_from_outside"
    )
    private String itemUsuallyBoughtFromOutside;






    @Builder
    public ItemsUsuallyBoughtFromOutside(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        VillageConsumptionPattern villageConsumptionPattern,
        String itemUsuallyBoughtFromOutside
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.villageConsumptionPattern = villageConsumptionPattern;
        this.itemUsuallyBoughtFromOutside = itemUsuallyBoughtFromOutside;


    }


    public void update(ItemsUsuallyBoughtFromOutside toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(ItemsUsuallyBoughtFromOutside toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageConsumptionPattern() != null) {
            this.setVillageConsumptionPattern(toBeUpdated.getVillageConsumptionPattern());
        }
        if (toBeUpdated.getItemUsuallyBoughtFromOutside() != null) {
            this.setItemUsuallyBoughtFromOutside(toBeUpdated.getItemUsuallyBoughtFromOutside());
        }
    }

    public void init() {

    }
}
