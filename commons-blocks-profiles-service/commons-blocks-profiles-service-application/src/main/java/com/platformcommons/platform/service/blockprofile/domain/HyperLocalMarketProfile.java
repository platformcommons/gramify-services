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
@Table(name = "hyper_local_market_profile")
public class HyperLocalMarketProfile extends  BaseTransactionalEntity implements DomainEntity<HyperLocalMarketProfile> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
         name = "has_grocery_shops"
    )
    private Boolean hasGroceryShops;

    @Column(
         name = "has_daily_needs_shops"
    )
    private Boolean hasDailyNeedsShops;

    @Column(
         name = "has_veg_fruit_vendors"
    )
    private Boolean hasVegFruitVendors;

    @Column(
         name = "has_agri_input_shop"
    )
    private Boolean hasAgriInputShop;

    @Column(
         name = "has_pharmacy"
    )
    private Boolean hasPharmacy;




    @Builder
    public HyperLocalMarketProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedContext,
        Long id,
        LinkedCode linkedActor,
        Boolean hasGroceryShops,
        Boolean hasDailyNeedsShops,
        Boolean hasVegFruitVendors,
        Boolean hasAgriInputShop,
        Boolean hasPharmacy
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedContext = linkedContext;
        this.id = id;
        this.linkedActor = linkedActor;
        this.hasGroceryShops = hasGroceryShops;
        this.hasDailyNeedsShops = hasDailyNeedsShops;
        this.hasVegFruitVendors = hasVegFruitVendors;
        this.hasAgriInputShop = hasAgriInputShop;
        this.hasPharmacy = hasPharmacy;


    }


    public void update(HyperLocalMarketProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HyperLocalMarketProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getHasGroceryShops() != null) {
            this.setHasGroceryShops(toBeUpdated.getHasGroceryShops());
        }
        if (toBeUpdated.getHasDailyNeedsShops() != null) {
            this.setHasDailyNeedsShops(toBeUpdated.getHasDailyNeedsShops());
        }
        if (toBeUpdated.getHasVegFruitVendors() != null) {
            this.setHasVegFruitVendors(toBeUpdated.getHasVegFruitVendors());
        }
        if (toBeUpdated.getHasAgriInputShop() != null) {
            this.setHasAgriInputShop(toBeUpdated.getHasAgriInputShop());
        }
        if (toBeUpdated.getHasPharmacy() != null) {
            this.setHasPharmacy(toBeUpdated.getHasPharmacy());
        }
    }

    public void init() {

    }
}
