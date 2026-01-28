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
@Table(name = "village_export_potential_profile")
public class VillageExportPotentialProfile extends  BaseTransactionalEntity implements DomainEntity<VillageExportPotentialProfile> {

    @Column(
         name = "are_niche_products_sold_outside"
    )
    private Boolean areNicheProductsSoldOutside;

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



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "village_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "village_entity_type"))
    })
    private LinkedCode village;



    @Column(
             name = "surplus_export_level"
    )
    private String surplusExportLevel;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;




    @OneToMany(mappedBy = "villageExportPotentialProfile", cascade = CascadeType.ALL)
    private Set<NicheProductBuyerType> nicheproductbuyertypeList;
    @OneToMany(mappedBy = "villageExportPotentialProfile", cascade = CascadeType.ALL)
    private Set<SurplusProduceType> surplusproducetypeList;
    @OneToMany(mappedBy = "villageExportPotentialProfile", cascade = CascadeType.ALL)
    private Set<MainSurplusMarketsOutsideBlock> mainsurplusmarketsoutsideblockList;
    @OneToMany(mappedBy = "villageExportPotentialProfile", cascade = CascadeType.ALL)
    private Set<NicheProductsAvailability> nicheproductsavailabilityList;


    @Builder
    public VillageExportPotentialProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Boolean areNicheProductsSoldOutside,
        Long id,
        LinkedCode linkedActor,
        LinkedCode village,
        String surplusExportLevel,
        LinkedCode linkedContext,
        Set<NicheProductBuyerType> nicheproductbuyertypeList,
        Set<SurplusProduceType> surplusproducetypeList,
        Set<MainSurplusMarketsOutsideBlock> mainsurplusmarketsoutsideblockList,
        Set<NicheProductsAvailability> nicheproductsavailabilityList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.areNicheProductsSoldOutside = areNicheProductsSoldOutside;
        this.id = id;
        this.linkedActor = linkedActor;
        this.village = village;
        this.surplusExportLevel = surplusExportLevel;
        this.linkedContext = linkedContext;
        this.nicheproductbuyertypeList=nicheproductbuyertypeList;
        this.nicheproductbuyertypeList.forEach(it->it.setVillageExportPotentialProfile(this));
        this.surplusproducetypeList=surplusproducetypeList;
        this.surplusproducetypeList.forEach(it->it.setVillageExportPotentialProfile(this));
        this.mainsurplusmarketsoutsideblockList=mainsurplusmarketsoutsideblockList;
        this.mainsurplusmarketsoutsideblockList.forEach(it->it.setVillageExportPotentialProfile(this));
        this.nicheproductsavailabilityList=nicheproductsavailabilityList;
        this.nicheproductsavailabilityList.forEach(it->it.setVillageExportPotentialProfile(this));


    }


    public void update(VillageExportPotentialProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageExportPotentialProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getAreNicheProductsSoldOutside() != null) {
            this.setAreNicheProductsSoldOutside(toBeUpdated.getAreNicheProductsSoldOutside());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getVillage() != null) {
            this.setVillage(toBeUpdated.getVillage());
        }
        if (toBeUpdated.getSurplusExportLevel() != null) {
            this.setSurplusExportLevel(toBeUpdated.getSurplusExportLevel());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
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
                    incoming.setVillageExportPotentialProfile(this);
                    this.getNicheproductbuyertypeList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getSurplusproducetypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getSurplusproducetypeList() != null) {
                this.getSurplusproducetypeList().forEach(current -> {
                    toBeUpdated.getSurplusproducetypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getSurplusproducetypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageExportPotentialProfile(this);
                    this.getSurplusproducetypeList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getMainsurplusmarketsoutsideblockList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getMainsurplusmarketsoutsideblockList() != null) {
                this.getMainsurplusmarketsoutsideblockList().forEach(current -> {
                    toBeUpdated.getMainsurplusmarketsoutsideblockList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getMainsurplusmarketsoutsideblockList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageExportPotentialProfile(this);
                    this.getMainsurplusmarketsoutsideblockList().add(incoming);
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
                    incoming.setVillageExportPotentialProfile(this);
                    this.getNicheproductsavailabilityList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
