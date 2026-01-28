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
@Table(name = "village_service_demand_profile")
public class VillageServiceDemandProfile extends  BaseTransactionalEntity implements DomainEntity<VillageServiceDemandProfile> {

    @Column(
             name = "demand_for_repair_services_level"
    )
    private String demandForRepairServicesLevel;



    @Column(
             name = "use_of_digital_payments_level"
    )
    private String useOfDigitalPaymentsLevel;



    @Column(
             name = "financial_service_demand_level"
    )
    private String financialServiceDemandLevel;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
             name = "healthcare_demand_level"
    )
    private String healthcareDemandLevel;



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
         name = "demand_for_tuitions_or_digital_classes"
    )
    private Boolean demandForTuitionsOrDigitalClasses;

    @Column(
             name = "education_demand_level"
    )
    private String educationDemandLevel;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue childrenStudyLocallyShare;


    @Column(
         name = "is_local_health_facility_sufficient"
    )
    private Boolean isLocalHealthFacilitySufficient;

    @Column(
         name = "need_for_closer_bank_or_atm"
    )
    private Boolean needForCloserBankOrAtm;


    @OneToMany(mappedBy = "villageServiceDemandProfile", cascade = CascadeType.ALL)
    private Set<WherePeopleGoForRepairs> wherepeoplegoforrepairsList;
    @OneToMany(mappedBy = "villageServiceDemandProfile", cascade = CascadeType.ALL)
    private Set<WherePeopleGoForCommonIllness> wherepeoplegoforcommonillnessList;
    @OneToMany(mappedBy = "villageServiceDemandProfile", cascade = CascadeType.ALL)
    private Set<CommonRepairNeed> commonrepairneedList;


    @Builder
    public VillageServiceDemandProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String demandForRepairServicesLevel,
        String useOfDigitalPaymentsLevel,
        String financialServiceDemandLevel,
        LinkedCode linkedContext,
        String healthcareDemandLevel,
        Long id,
        LinkedCode linkedActor,
        Boolean demandForTuitionsOrDigitalClasses,
        String educationDemandLevel,
        UoMValue childrenStudyLocallyShare,
        Boolean isLocalHealthFacilitySufficient,
        Boolean needForCloserBankOrAtm,
        Set<WherePeopleGoForRepairs> wherepeoplegoforrepairsList,
        Set<WherePeopleGoForCommonIllness> wherepeoplegoforcommonillnessList,
        Set<CommonRepairNeed> commonrepairneedList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.demandForRepairServicesLevel = demandForRepairServicesLevel;
        this.useOfDigitalPaymentsLevel = useOfDigitalPaymentsLevel;
        this.financialServiceDemandLevel = financialServiceDemandLevel;
        this.linkedContext = linkedContext;
        this.healthcareDemandLevel = healthcareDemandLevel;
        this.id = id;
        this.linkedActor = linkedActor;
        this.demandForTuitionsOrDigitalClasses = demandForTuitionsOrDigitalClasses;
        this.educationDemandLevel = educationDemandLevel;
        this.childrenStudyLocallyShare = childrenStudyLocallyShare;
        this.isLocalHealthFacilitySufficient = isLocalHealthFacilitySufficient;
        this.needForCloserBankOrAtm = needForCloserBankOrAtm;
        this.wherepeoplegoforrepairsList=wherepeoplegoforrepairsList;
        this.wherepeoplegoforrepairsList.forEach(it->it.setVillageServiceDemandProfile(this));
        this.wherepeoplegoforcommonillnessList=wherepeoplegoforcommonillnessList;
        this.wherepeoplegoforcommonillnessList.forEach(it->it.setVillageServiceDemandProfile(this));
        this.commonrepairneedList=commonrepairneedList;
        this.commonrepairneedList.forEach(it->it.setVillageServiceDemandProfile(this));


    }


    public void update(VillageServiceDemandProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageServiceDemandProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getDemandForRepairServicesLevel() != null) {
            this.setDemandForRepairServicesLevel(toBeUpdated.getDemandForRepairServicesLevel());
        }
        if (toBeUpdated.getUseOfDigitalPaymentsLevel() != null) {
            this.setUseOfDigitalPaymentsLevel(toBeUpdated.getUseOfDigitalPaymentsLevel());
        }
        if (toBeUpdated.getFinancialServiceDemandLevel() != null) {
            this.setFinancialServiceDemandLevel(toBeUpdated.getFinancialServiceDemandLevel());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getHealthcareDemandLevel() != null) {
            this.setHealthcareDemandLevel(toBeUpdated.getHealthcareDemandLevel());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getDemandForTuitionsOrDigitalClasses() != null) {
            this.setDemandForTuitionsOrDigitalClasses(toBeUpdated.getDemandForTuitionsOrDigitalClasses());
        }
        if (toBeUpdated.getEducationDemandLevel() != null) {
            this.setEducationDemandLevel(toBeUpdated.getEducationDemandLevel());
        }
        if (toBeUpdated.getChildrenStudyLocallyShare() != null) {
            this.setChildrenStudyLocallyShare(toBeUpdated.getChildrenStudyLocallyShare());
        }
        if (toBeUpdated.getIsLocalHealthFacilitySufficient() != null) {
            this.setIsLocalHealthFacilitySufficient(toBeUpdated.getIsLocalHealthFacilitySufficient());
        }
        if (toBeUpdated.getNeedForCloserBankOrAtm() != null) {
            this.setNeedForCloserBankOrAtm(toBeUpdated.getNeedForCloserBankOrAtm());
        }
        if (toBeUpdated.getWherepeoplegoforrepairsList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getWherepeoplegoforrepairsList() != null) {
                this.getWherepeoplegoforrepairsList().forEach(current -> {
                    toBeUpdated.getWherepeoplegoforrepairsList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getWherepeoplegoforrepairsList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageServiceDemandProfile(this);
                    this.getWherepeoplegoforrepairsList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getWherepeoplegoforcommonillnessList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getWherepeoplegoforcommonillnessList() != null) {
                this.getWherepeoplegoforcommonillnessList().forEach(current -> {
                    toBeUpdated.getWherepeoplegoforcommonillnessList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getWherepeoplegoforcommonillnessList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageServiceDemandProfile(this);
                    this.getWherepeoplegoforcommonillnessList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getCommonrepairneedList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getCommonrepairneedList() != null) {
                this.getCommonrepairneedList().forEach(current -> {
                    toBeUpdated.getCommonrepairneedList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getCommonrepairneedList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageServiceDemandProfile(this);
                    this.getCommonrepairneedList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
