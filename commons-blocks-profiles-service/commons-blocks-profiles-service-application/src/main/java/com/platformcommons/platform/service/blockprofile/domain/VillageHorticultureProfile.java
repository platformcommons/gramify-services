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
@Table(name = "village_horticulture_profile")
public class VillageHorticultureProfile extends  BaseTransactionalEntity implements DomainEntity<VillageHorticultureProfile> {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue estimatedSurplusShare;


    @Column(
         name = "has_surplus"
    )
    private Boolean hasSurplus;

    @Column(
         name = "requires_cold_chain"
    )
    private Boolean requiresColdChain;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
         name = "is_major_horticulture_product"
    )
    private Boolean isMajorHorticultureProduct;

    @Column(
             name = "main_buyer_type"
    )
    private String mainBuyerType;



    @Column(
         name = "is_perishable"
    )
    private Boolean isPerishable;

    @Column(
         name = "main_sale_destination"
    )
    private String mainSaleDestination;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;




    @OneToMany(mappedBy = "villageHorticultureProfile", cascade = CascadeType.ALL)
    private Set<HorticultureProduct> horticultureproductList;
    @OneToMany(mappedBy = "villageHorticultureProfile", cascade = CascadeType.ALL)
    private Set<ProductionSeason> productionseasonList;


    @Builder
    public VillageHorticultureProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        UoMValue estimatedSurplusShare,
        Boolean hasSurplus,
        Boolean requiresColdChain,
        LinkedCode linkedActor,
        Long id,
        Boolean isMajorHorticultureProduct,
        String mainBuyerType,
        Boolean isPerishable,
        String mainSaleDestination,
        LinkedCode linkedContext,
        Set<HorticultureProduct> horticultureproductList,
        Set<ProductionSeason> productionseasonList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.estimatedSurplusShare = estimatedSurplusShare;
        this.hasSurplus = hasSurplus;
        this.requiresColdChain = requiresColdChain;
        this.linkedActor = linkedActor;
        this.id = id;
        this.isMajorHorticultureProduct = isMajorHorticultureProduct;
        this.mainBuyerType = mainBuyerType;
        this.isPerishable = isPerishable;
        this.mainSaleDestination = mainSaleDestination;
        this.linkedContext = linkedContext;
        this.horticultureproductList=horticultureproductList;
        this.horticultureproductList.forEach(it->it.setVillageHorticultureProfile(this));
        this.productionseasonList=productionseasonList;
        this.productionseasonList.forEach(it->it.setVillageHorticultureProfile(this));


    }


    public void update(VillageHorticultureProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageHorticultureProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getEstimatedSurplusShare() != null) {
            this.setEstimatedSurplusShare(toBeUpdated.getEstimatedSurplusShare());
        }
        if (toBeUpdated.getHasSurplus() != null) {
            this.setHasSurplus(toBeUpdated.getHasSurplus());
        }
        if (toBeUpdated.getRequiresColdChain() != null) {
            this.setRequiresColdChain(toBeUpdated.getRequiresColdChain());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getIsMajorHorticultureProduct() != null) {
            this.setIsMajorHorticultureProduct(toBeUpdated.getIsMajorHorticultureProduct());
        }
        if (toBeUpdated.getMainBuyerType() != null) {
            this.setMainBuyerType(toBeUpdated.getMainBuyerType());
        }
        if (toBeUpdated.getIsPerishable() != null) {
            this.setIsPerishable(toBeUpdated.getIsPerishable());
        }
        if (toBeUpdated.getMainSaleDestination() != null) {
            this.setMainSaleDestination(toBeUpdated.getMainSaleDestination());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getHorticultureproductList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHorticultureproductList() != null) {
                this.getHorticultureproductList().forEach(current -> {
                    toBeUpdated.getHorticultureproductList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHorticultureproductList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageHorticultureProfile(this);
                    this.getHorticultureproductList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getProductionseasonList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getProductionseasonList() != null) {
                this.getProductionseasonList().forEach(current -> {
                    toBeUpdated.getProductionseasonList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getProductionseasonList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageHorticultureProfile(this);
                    this.getProductionseasonList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
