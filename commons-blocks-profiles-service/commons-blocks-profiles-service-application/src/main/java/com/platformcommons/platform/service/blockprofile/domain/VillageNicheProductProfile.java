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
@Table(name = "village_niche_product_profile")
public class VillageNicheProductProfile extends  BaseTransactionalEntity implements DomainEntity<VillageNicheProductProfile> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
         name = "are_niche_products_sold_outside"
    )
    private Boolean areNicheProductsSoldOutside;

    @Column(
             name = "niche_product_scale_level"
    )
    private String nicheProductScaleLevel;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
         name = "has_niche_craft_cluster"
    )
    private Boolean hasNicheCraftCluster;


    @OneToMany(mappedBy = "villageNicheProductProfile", cascade = CascadeType.ALL)
    private Set<MainNicheMarket> mainnichemarketList;
    @OneToMany(mappedBy = "villageNicheProductProfile", cascade = CascadeType.ALL)
    private Set<SupportNeededForNicheGrowth> supportneededfornichegrowthList;
    @OneToMany(mappedBy = "villageNicheProductProfile", cascade = CascadeType.ALL)
    private Set<NicheProductsAvailability> nicheproductsavailabilityList;
    @OneToMany(mappedBy = "villageNicheProductProfile", cascade = CascadeType.ALL)
    private Set<NicheProductBuyerType> nicheproductbuyertypeList;


    @Builder
    public VillageNicheProductProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        LinkedCode linkedContext,
        Boolean areNicheProductsSoldOutside,
        String nicheProductScaleLevel,
        LinkedCode linkedActor,
        Boolean hasNicheCraftCluster,
        Set<MainNicheMarket> mainnichemarketList,
        Set<SupportNeededForNicheGrowth> supportneededfornichegrowthList,
        Set<NicheProductsAvailability> nicheproductsavailabilityList,
        Set<NicheProductBuyerType> nicheproductbuyertypeList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.linkedContext = linkedContext;
        this.areNicheProductsSoldOutside = areNicheProductsSoldOutside;
        this.nicheProductScaleLevel = nicheProductScaleLevel;
        this.linkedActor = linkedActor;
        this.hasNicheCraftCluster = hasNicheCraftCluster;
        this.mainnichemarketList=mainnichemarketList;
        this.mainnichemarketList.forEach(it->it.setVillageNicheProductProfile(this));
        this.supportneededfornichegrowthList=supportneededfornichegrowthList;
        this.supportneededfornichegrowthList.forEach(it->it.setVillageNicheProductProfile(this));
        this.nicheproductsavailabilityList=nicheproductsavailabilityList;
        this.nicheproductsavailabilityList.forEach(it->it.setVillageNicheProductProfile(this));
        this.nicheproductbuyertypeList=nicheproductbuyertypeList;
        this.nicheproductbuyertypeList.forEach(it->it.setVillageNicheProductProfile(this));


    }


    public void update(VillageNicheProductProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageNicheProductProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getAreNicheProductsSoldOutside() != null) {
            this.setAreNicheProductsSoldOutside(toBeUpdated.getAreNicheProductsSoldOutside());
        }
        if (toBeUpdated.getNicheProductScaleLevel() != null) {
            this.setNicheProductScaleLevel(toBeUpdated.getNicheProductScaleLevel());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getHasNicheCraftCluster() != null) {
            this.setHasNicheCraftCluster(toBeUpdated.getHasNicheCraftCluster());
        }
        if (toBeUpdated.getMainnichemarketList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getMainnichemarketList() != null) {
                this.getMainnichemarketList().forEach(current -> {
                    toBeUpdated.getMainnichemarketList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getMainnichemarketList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageNicheProductProfile(this);
                    this.getMainnichemarketList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getSupportneededfornichegrowthList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getSupportneededfornichegrowthList() != null) {
                this.getSupportneededfornichegrowthList().forEach(current -> {
                    toBeUpdated.getSupportneededfornichegrowthList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getSupportneededfornichegrowthList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageNicheProductProfile(this);
                    this.getSupportneededfornichegrowthList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getNicheproductsavailabilityList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getNicheproductsavailabilityList() != null) {
                this.getNicheproductsavailabilityList().forEach(current -> {
                    toBeUpdated.getNicheproductsavailabilityList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getNicheproductsavailabilityList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageNicheProductProfile(this);
                    this.getNicheproductsavailabilityList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getNicheproductbuyertypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getNicheproductbuyertypeList() != null) {
                this.getNicheproductbuyertypeList().forEach(current -> {
                    toBeUpdated.getNicheproductbuyertypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getNicheproductbuyertypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageNicheProductProfile(this);
                    this.getNicheproductbuyertypeList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
