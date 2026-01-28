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
@Table(name = "items_usually_bought_locally")
public class ItemsUsuallyBoughtLocally extends  BaseTransactionalEntity implements DomainEntity<ItemsUsuallyBoughtLocally> {

    @ManyToOne
    @JoinColumn(name = "villageConsumptionPattern_id")
    private VillageConsumptionPattern villageConsumptionPattern;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "item_usually_bought_locally"
    )
    private String itemUsuallyBoughtLocally;






    @Builder
    public ItemsUsuallyBoughtLocally(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageConsumptionPattern villageConsumptionPattern,
        Long id,
        String itemUsuallyBoughtLocally
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageConsumptionPattern = villageConsumptionPattern;
        this.id = id;
        this.itemUsuallyBoughtLocally = itemUsuallyBoughtLocally;


    }


    public void update(ItemsUsuallyBoughtLocally toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(ItemsUsuallyBoughtLocally toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageConsumptionPattern() != null) {
            this.setVillageConsumptionPattern(toBeUpdated.getVillageConsumptionPattern());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getItemUsuallyBoughtLocally() != null) {
            this.setItemUsuallyBoughtLocally(toBeUpdated.getItemUsuallyBoughtLocally());
        }
    }

    public void init() {

    }
}
