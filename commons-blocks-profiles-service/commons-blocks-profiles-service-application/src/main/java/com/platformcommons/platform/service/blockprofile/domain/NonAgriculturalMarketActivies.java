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
@Table(name = "non_agricultural_market_activies")
public class NonAgriculturalMarketActivies extends  BaseTransactionalEntity implements DomainEntity<NonAgriculturalMarketActivies> {

    @Column(
         name = "has_plumber"
    )
    private Boolean hasPlumber;

    @Column(
         name = "local_consumer_economy_strength"
    )
    private String localConsumerEconomyStrength;

    @Column(
         name = "has_carpenter"
    )
    private Boolean hasCarpenter;

    @Column(
             name = "other_retail_type"
    )
    private String otherRetailType;



    @Column(
         name = "carpentry_unit_count"
    )
    private Integer carpentryUnitCount;

    @Column(
         name = "has_mobile_repair_service"
    )
    private Boolean hasMobileRepairService;

    @Column(
         name = "eatery_count"
    )
    private Integer eateryCount;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
         name = "general_store_count"
    )
    private Integer generalStoreCount;

    @Column(
             name = "service_availability_level"
    )
    private String serviceAvailabilityLevel;



    @Column(
         name = "has_mechanic"
    )
    private Boolean hasMechanic;

    @Column(
         name = "pottery_unit_count"
    )
    private Integer potteryUnitCount;

    @Column(
         name = "has_small_scale_industries"
    )
    private Boolean hasSmallScaleIndustries;

    @Column(
         name = "mobile_repair_shop_count"
    )
    private Integer mobileRepairShopCount;

    @Column(
         name = "handloom_unit_count"
    )
    private Integer handloomUnitCount;

    @Column(
             name = "non_farm_livelihood_significance"
    )
    private String nonFarmLivelihoodSignificance;



    @Column(
         name = "has_electrician"
    )
    private Boolean hasElectrician;

    @Column(
         name = "pharmacy_count"
    )
    private Integer pharmacyCount;

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




    @OneToMany(mappedBy = "nonAgriculturalMarketActivies", cascade = CascadeType.ALL)
    private Set<OtherIndustryType> otherindustrytypeList;


    @Builder
    public NonAgriculturalMarketActivies(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Boolean hasPlumber,
        String localConsumerEconomyStrength,
        Boolean hasCarpenter,
        String otherRetailType,
        Integer carpentryUnitCount,
        Boolean hasMobileRepairService,
        Integer eateryCount,
        LinkedCode linkedContext,
        Integer generalStoreCount,
        String serviceAvailabilityLevel,
        Boolean hasMechanic,
        Integer potteryUnitCount,
        Boolean hasSmallScaleIndustries,
        Integer mobileRepairShopCount,
        Integer handloomUnitCount,
        String nonFarmLivelihoodSignificance,
        Boolean hasElectrician,
        Integer pharmacyCount,
        Long id,
        LinkedCode linkedActor,
        Set<OtherIndustryType> otherindustrytypeList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.hasPlumber = hasPlumber;
        this.localConsumerEconomyStrength = localConsumerEconomyStrength;
        this.hasCarpenter = hasCarpenter;
        this.otherRetailType = otherRetailType;
        this.carpentryUnitCount = carpentryUnitCount;
        this.hasMobileRepairService = hasMobileRepairService;
        this.eateryCount = eateryCount;
        this.linkedContext = linkedContext;
        this.generalStoreCount = generalStoreCount;
        this.serviceAvailabilityLevel = serviceAvailabilityLevel;
        this.hasMechanic = hasMechanic;
        this.potteryUnitCount = potteryUnitCount;
        this.hasSmallScaleIndustries = hasSmallScaleIndustries;
        this.mobileRepairShopCount = mobileRepairShopCount;
        this.handloomUnitCount = handloomUnitCount;
        this.nonFarmLivelihoodSignificance = nonFarmLivelihoodSignificance;
        this.hasElectrician = hasElectrician;
        this.pharmacyCount = pharmacyCount;
        this.id = id;
        this.linkedActor = linkedActor;
        this.otherindustrytypeList=otherindustrytypeList;
        this.otherindustrytypeList.forEach(it->it.setNonAgriculturalMarketActivies(this));


    }


    public void update(NonAgriculturalMarketActivies toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(NonAgriculturalMarketActivies toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getHasPlumber() != null) {
            this.setHasPlumber(toBeUpdated.getHasPlumber());
        }
        if (toBeUpdated.getLocalConsumerEconomyStrength() != null) {
            this.setLocalConsumerEconomyStrength(toBeUpdated.getLocalConsumerEconomyStrength());
        }
        if (toBeUpdated.getHasCarpenter() != null) {
            this.setHasCarpenter(toBeUpdated.getHasCarpenter());
        }
        if (toBeUpdated.getOtherRetailType() != null) {
            this.setOtherRetailType(toBeUpdated.getOtherRetailType());
        }
        if (toBeUpdated.getCarpentryUnitCount() != null) {
            this.setCarpentryUnitCount(toBeUpdated.getCarpentryUnitCount());
        }
        if (toBeUpdated.getHasMobileRepairService() != null) {
            this.setHasMobileRepairService(toBeUpdated.getHasMobileRepairService());
        }
        if (toBeUpdated.getEateryCount() != null) {
            this.setEateryCount(toBeUpdated.getEateryCount());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getGeneralStoreCount() != null) {
            this.setGeneralStoreCount(toBeUpdated.getGeneralStoreCount());
        }
        if (toBeUpdated.getServiceAvailabilityLevel() != null) {
            this.setServiceAvailabilityLevel(toBeUpdated.getServiceAvailabilityLevel());
        }
        if (toBeUpdated.getHasMechanic() != null) {
            this.setHasMechanic(toBeUpdated.getHasMechanic());
        }
        if (toBeUpdated.getPotteryUnitCount() != null) {
            this.setPotteryUnitCount(toBeUpdated.getPotteryUnitCount());
        }
        if (toBeUpdated.getHasSmallScaleIndustries() != null) {
            this.setHasSmallScaleIndustries(toBeUpdated.getHasSmallScaleIndustries());
        }
        if (toBeUpdated.getMobileRepairShopCount() != null) {
            this.setMobileRepairShopCount(toBeUpdated.getMobileRepairShopCount());
        }
        if (toBeUpdated.getHandloomUnitCount() != null) {
            this.setHandloomUnitCount(toBeUpdated.getHandloomUnitCount());
        }
        if (toBeUpdated.getNonFarmLivelihoodSignificance() != null) {
            this.setNonFarmLivelihoodSignificance(toBeUpdated.getNonFarmLivelihoodSignificance());
        }
        if (toBeUpdated.getHasElectrician() != null) {
            this.setHasElectrician(toBeUpdated.getHasElectrician());
        }
        if (toBeUpdated.getPharmacyCount() != null) {
            this.setPharmacyCount(toBeUpdated.getPharmacyCount());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getOtherindustrytypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getOtherindustrytypeList() != null) {
                this.getOtherindustrytypeList().forEach(current -> {
                    toBeUpdated.getOtherindustrytypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getOtherindustrytypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setNonAgriculturalMarketActivies(this);
                    this.getOtherindustrytypeList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
