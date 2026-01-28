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
@Table(name = "common_consumer_goods_purchased")
public class CommonConsumerGoodsPurchased extends  BaseTransactionalEntity implements DomainEntity<CommonConsumerGoodsPurchased> {

    @ManyToOne
    @JoinColumn(name = "villageConsumptionPattern_id")
    private VillageConsumptionPattern villageConsumptionPattern;


    @Column(
             name = "common_consumer_goods_purchased"
    )
    private String commonConsumerGoodsPurchased;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public CommonConsumerGoodsPurchased(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageConsumptionPattern villageConsumptionPattern,
        String commonConsumerGoodsPurchased,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageConsumptionPattern = villageConsumptionPattern;
        this.commonConsumerGoodsPurchased = commonConsumerGoodsPurchased;
        this.id = id;


    }


    public void update(CommonConsumerGoodsPurchased toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(CommonConsumerGoodsPurchased toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageConsumptionPattern() != null) {
            this.setVillageConsumptionPattern(toBeUpdated.getVillageConsumptionPattern());
        }
        if (toBeUpdated.getCommonConsumerGoodsPurchased() != null) {
            this.setCommonConsumerGoodsPurchased(toBeUpdated.getCommonConsumerGoodsPurchased());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
