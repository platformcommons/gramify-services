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
@Table(name = "village_consumption_pattern")
public class VillageConsumptionPattern extends  BaseTransactionalEntity implements DomainEntity<VillageConsumptionPattern> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
             name = "demand_for_packaged_foods_level"
    )
    private String demandForPackagedFoodsLevel;



    @Column(
         name = "is_staple_mostly_locally_produced"
    )
    private Boolean isStapleMostlyLocallyProduced;

    @Column(
             name = "demand_for_toiletries_level"
    )
    private String demandForToiletriesLevel;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "preference_for_local_produce_level"
    )
    private String preferenceForLocalProduceLevel;



    @Column(
             name = "demand_for_basic_electronics_level"
    )
    private String demandForBasicElectronicsLevel;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;




    @OneToMany(mappedBy = "villageConsumptionPattern", cascade = CascadeType.ALL)
    private Set<CommonConsumerGoodsPurchased> commonconsumergoodspurchasedList;
    @OneToMany(mappedBy = "villageConsumptionPattern", cascade = CascadeType.ALL)
    private Set<ItemsUsuallyBoughtFromOutside> itemsusuallyboughtfromoutsideList;
    @OneToMany(mappedBy = "villageConsumptionPattern", cascade = CascadeType.ALL)
    private Set<StapleFoodsConsumed> staplefoodsconsumedList;
    @OneToMany(mappedBy = "villageConsumptionPattern", cascade = CascadeType.ALL)
    private Set<ItemsUsuallyBoughtLocally> itemsusuallyboughtlocallyList;


    @Builder
    public VillageConsumptionPattern(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedActor,
        String demandForPackagedFoodsLevel,
        Boolean isStapleMostlyLocallyProduced,
        String demandForToiletriesLevel,
        Long id,
        String preferenceForLocalProduceLevel,
        String demandForBasicElectronicsLevel,
        LinkedCode linkedContext,
        Set<CommonConsumerGoodsPurchased> commonconsumergoodspurchasedList,
        Set<ItemsUsuallyBoughtFromOutside> itemsusuallyboughtfromoutsideList,
        Set<StapleFoodsConsumed> staplefoodsconsumedList,
        Set<ItemsUsuallyBoughtLocally> itemsusuallyboughtlocallyList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedActor = linkedActor;
        this.demandForPackagedFoodsLevel = demandForPackagedFoodsLevel;
        this.isStapleMostlyLocallyProduced = isStapleMostlyLocallyProduced;
        this.demandForToiletriesLevel = demandForToiletriesLevel;
        this.id = id;
        this.preferenceForLocalProduceLevel = preferenceForLocalProduceLevel;
        this.demandForBasicElectronicsLevel = demandForBasicElectronicsLevel;
        this.linkedContext = linkedContext;
        this.commonconsumergoodspurchasedList=commonconsumergoodspurchasedList;
        this.commonconsumergoodspurchasedList.forEach(it->it.setVillageConsumptionPattern(this));
        this.itemsusuallyboughtfromoutsideList=itemsusuallyboughtfromoutsideList;
        this.itemsusuallyboughtfromoutsideList.forEach(it->it.setVillageConsumptionPattern(this));
        this.staplefoodsconsumedList=staplefoodsconsumedList;
        this.staplefoodsconsumedList.forEach(it->it.setVillageConsumptionPattern(this));
        this.itemsusuallyboughtlocallyList=itemsusuallyboughtlocallyList;
        this.itemsusuallyboughtlocallyList.forEach(it->it.setVillageConsumptionPattern(this));


    }


    public void update(VillageConsumptionPattern toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageConsumptionPattern toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getDemandForPackagedFoodsLevel() != null) {
            this.setDemandForPackagedFoodsLevel(toBeUpdated.getDemandForPackagedFoodsLevel());
        }
        if (toBeUpdated.getIsStapleMostlyLocallyProduced() != null) {
            this.setIsStapleMostlyLocallyProduced(toBeUpdated.getIsStapleMostlyLocallyProduced());
        }
        if (toBeUpdated.getDemandForToiletriesLevel() != null) {
            this.setDemandForToiletriesLevel(toBeUpdated.getDemandForToiletriesLevel());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getPreferenceForLocalProduceLevel() != null) {
            this.setPreferenceForLocalProduceLevel(toBeUpdated.getPreferenceForLocalProduceLevel());
        }
        if (toBeUpdated.getDemandForBasicElectronicsLevel() != null) {
            this.setDemandForBasicElectronicsLevel(toBeUpdated.getDemandForBasicElectronicsLevel());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getCommonconsumergoodspurchasedList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getCommonconsumergoodspurchasedList() != null) {
                this.getCommonconsumergoodspurchasedList().forEach(current -> {
                    toBeUpdated.getCommonconsumergoodspurchasedList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getCommonconsumergoodspurchasedList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageConsumptionPattern(this);
                    this.getCommonconsumergoodspurchasedList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getItemsusuallyboughtfromoutsideList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getItemsusuallyboughtfromoutsideList() != null) {
                this.getItemsusuallyboughtfromoutsideList().forEach(current -> {
                    toBeUpdated.getItemsusuallyboughtfromoutsideList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getItemsusuallyboughtfromoutsideList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageConsumptionPattern(this);
                    this.getItemsusuallyboughtfromoutsideList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getStaplefoodsconsumedList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getStaplefoodsconsumedList() != null) {
                this.getStaplefoodsconsumedList().forEach(current -> {
                    toBeUpdated.getStaplefoodsconsumedList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getStaplefoodsconsumedList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageConsumptionPattern(this);
                    this.getStaplefoodsconsumedList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getItemsusuallyboughtlocallyList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getItemsusuallyboughtlocallyList() != null) {
                this.getItemsusuallyboughtlocallyList().forEach(current -> {
                    toBeUpdated.getItemsusuallyboughtlocallyList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getItemsusuallyboughtlocallyList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageConsumptionPattern(this);
                    this.getItemsusuallyboughtlocallyList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
