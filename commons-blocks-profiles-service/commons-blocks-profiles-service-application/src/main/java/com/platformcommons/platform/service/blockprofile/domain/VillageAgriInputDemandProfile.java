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
@Table(name = "village_agri_input_demand_profile")
public class VillageAgriInputDemandProfile extends  BaseTransactionalEntity implements DomainEntity<VillageAgriInputDemandProfile> {

    @Column(
             name = "credit_constraint_level"
    )
    private String creditConstraintLevel;



    @Column(
             name = "raw_material_shortage_level"
    )
    private String rawMaterialShortageLevel;



    @Column(
             name = "storage_capacity_shortage_level"
    )
    private String storageCapacityShortageLevel;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "ease_of_access_to_machinery"
    )
    private String easeOfAccessToMachinery;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
         name = "willingness_to_pay_for_storage"
    )
    private Boolean willingnessToPayForStorage;

    @Column(
         name = "has_credit_demand"
    )
    private Boolean hasCreditDemand;

    @Column(
         name = "nearest_machinery_provider_location"
    )
    private String nearestMachineryProviderLocation;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
         name = "is_machine_rental_common"
    )
    private Boolean isMachineRentalCommon;


    @OneToMany(mappedBy = "villageAgriInputDemandProfile", cascade = CascadeType.ALL)
    private Set<MachinesInDemand> machinesindemandList;
    @OneToMany(mappedBy = "villageAgriInputDemandProfile", cascade = CascadeType.ALL)
    private Set<PurposeOfCredit> purposeofcreditList;
    @OneToMany(mappedBy = "villageAgriInputDemandProfile", cascade = CascadeType.ALL)
    private Set<FertilizersInDemand> fertilizersindemandList;
    @OneToMany(mappedBy = "villageAgriInputDemandProfile", cascade = CascadeType.ALL)
    private Set<SourceOfRawMaterial> sourceofrawmaterialList;
    @OneToMany(mappedBy = "villageAgriInputDemandProfile", cascade = CascadeType.ALL)
    private Set<SeedsInDemand> seedsindemandList;
    @OneToMany(mappedBy = "villageAgriInputDemandProfile", cascade = CascadeType.ALL)
    private Set<MainCreditSource> maincreditsourceList;
    @OneToMany(mappedBy = "villageAgriInputDemandProfile", cascade = CascadeType.ALL)
    private Set<ExistingStorageIssue> existingstorageissueList;
    @OneToMany(mappedBy = "villageAgriInputDemandProfile", cascade = CascadeType.ALL)
    private Set<StorageNeededForCrop> storageneededforcropList;
    @OneToMany(mappedBy = "villageAgriInputDemandProfile", cascade = CascadeType.ALL)
    private Set<WhereFarmersBuyInput> wherefarmersbuyinputList;
    @OneToMany(mappedBy = "villageAgriInputDemandProfile", cascade = CascadeType.ALL)
    private Set<RawMaterialsNeededForIndustry> rawmaterialsneededforindustryList;
    @OneToMany(mappedBy = "villageAgriInputDemandProfile", cascade = CascadeType.ALL)
    private Set<PesticidesInDemand> pesticidesindemandList;
    @OneToMany(mappedBy = "villageAgriInputDemandProfile", cascade = CascadeType.ALL)
    private Set<CurrentStorageMethod> currentstoragemethodList;


    @Builder
    public VillageAgriInputDemandProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String creditConstraintLevel,
        String rawMaterialShortageLevel,
        String storageCapacityShortageLevel,
        Long id,
        String easeOfAccessToMachinery,
        LinkedCode linkedContext,
        Boolean willingnessToPayForStorage,
        Boolean hasCreditDemand,
        String nearestMachineryProviderLocation,
        LinkedCode linkedActor,
        Boolean isMachineRentalCommon,
        Set<MachinesInDemand> machinesindemandList,
        Set<PurposeOfCredit> purposeofcreditList,
        Set<FertilizersInDemand> fertilizersindemandList,
        Set<SourceOfRawMaterial> sourceofrawmaterialList,
        Set<SeedsInDemand> seedsindemandList,
        Set<MainCreditSource> maincreditsourceList,
        Set<ExistingStorageIssue> existingstorageissueList,
        Set<StorageNeededForCrop> storageneededforcropList,
        Set<WhereFarmersBuyInput> wherefarmersbuyinputList,
        Set<RawMaterialsNeededForIndustry> rawmaterialsneededforindustryList,
        Set<PesticidesInDemand> pesticidesindemandList,
        Set<CurrentStorageMethod> currentstoragemethodList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.creditConstraintLevel = creditConstraintLevel;
        this.rawMaterialShortageLevel = rawMaterialShortageLevel;
        this.storageCapacityShortageLevel = storageCapacityShortageLevel;
        this.id = id;
        this.easeOfAccessToMachinery = easeOfAccessToMachinery;
        this.linkedContext = linkedContext;
        this.willingnessToPayForStorage = willingnessToPayForStorage;
        this.hasCreditDemand = hasCreditDemand;
        this.nearestMachineryProviderLocation = nearestMachineryProviderLocation;
        this.linkedActor = linkedActor;
        this.isMachineRentalCommon = isMachineRentalCommon;
        this.machinesindemandList=machinesindemandList;
        this.machinesindemandList.forEach(it->it.setVillageAgriInputDemandProfile(this));
        this.purposeofcreditList=purposeofcreditList;
        this.purposeofcreditList.forEach(it->it.setVillageAgriInputDemandProfile(this));
        this.fertilizersindemandList=fertilizersindemandList;
        this.fertilizersindemandList.forEach(it->it.setVillageAgriInputDemandProfile(this));
        this.sourceofrawmaterialList=sourceofrawmaterialList;
        this.sourceofrawmaterialList.forEach(it->it.setVillageAgriInputDemandProfile(this));
        this.seedsindemandList=seedsindemandList;
        this.seedsindemandList.forEach(it->it.setVillageAgriInputDemandProfile(this));
        this.maincreditsourceList=maincreditsourceList;
        this.maincreditsourceList.forEach(it->it.setVillageAgriInputDemandProfile(this));
        this.existingstorageissueList=existingstorageissueList;
        this.existingstorageissueList.forEach(it->it.setVillageAgriInputDemandProfile(this));
        this.storageneededforcropList=storageneededforcropList;
        this.storageneededforcropList.forEach(it->it.setVillageAgriInputDemandProfile(this));
        this.wherefarmersbuyinputList=wherefarmersbuyinputList;
        this.wherefarmersbuyinputList.forEach(it->it.setVillageAgriInputDemandProfile(this));
        this.rawmaterialsneededforindustryList=rawmaterialsneededforindustryList;
        this.rawmaterialsneededforindustryList.forEach(it->it.setVillageAgriInputDemandProfile(this));
        this.pesticidesindemandList=pesticidesindemandList;
        this.pesticidesindemandList.forEach(it->it.setVillageAgriInputDemandProfile(this));
        this.currentstoragemethodList=currentstoragemethodList;
        this.currentstoragemethodList.forEach(it->it.setVillageAgriInputDemandProfile(this));


    }


    public void update(VillageAgriInputDemandProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageAgriInputDemandProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getCreditConstraintLevel() != null) {
            this.setCreditConstraintLevel(toBeUpdated.getCreditConstraintLevel());
        }
        if (toBeUpdated.getRawMaterialShortageLevel() != null) {
            this.setRawMaterialShortageLevel(toBeUpdated.getRawMaterialShortageLevel());
        }
        if (toBeUpdated.getStorageCapacityShortageLevel() != null) {
            this.setStorageCapacityShortageLevel(toBeUpdated.getStorageCapacityShortageLevel());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getEaseOfAccessToMachinery() != null) {
            this.setEaseOfAccessToMachinery(toBeUpdated.getEaseOfAccessToMachinery());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getWillingnessToPayForStorage() != null) {
            this.setWillingnessToPayForStorage(toBeUpdated.getWillingnessToPayForStorage());
        }
        if (toBeUpdated.getHasCreditDemand() != null) {
            this.setHasCreditDemand(toBeUpdated.getHasCreditDemand());
        }
        if (toBeUpdated.getNearestMachineryProviderLocation() != null) {
            this.setNearestMachineryProviderLocation(toBeUpdated.getNearestMachineryProviderLocation());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getIsMachineRentalCommon() != null) {
            this.setIsMachineRentalCommon(toBeUpdated.getIsMachineRentalCommon());
        }
        if (toBeUpdated.getMachinesindemandList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getMachinesindemandList() != null) {
                this.getMachinesindemandList().forEach(current -> {
                    toBeUpdated.getMachinesindemandList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getMachinesindemandList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageAgriInputDemandProfile(this);
                    this.getMachinesindemandList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getPurposeofcreditList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getPurposeofcreditList() != null) {
                this.getPurposeofcreditList().forEach(current -> {
                    toBeUpdated.getPurposeofcreditList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getPurposeofcreditList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageAgriInputDemandProfile(this);
                    this.getPurposeofcreditList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getFertilizersindemandList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getFertilizersindemandList() != null) {
                this.getFertilizersindemandList().forEach(current -> {
                    toBeUpdated.getFertilizersindemandList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getFertilizersindemandList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageAgriInputDemandProfile(this);
                    this.getFertilizersindemandList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getSourceofrawmaterialList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getSourceofrawmaterialList() != null) {
                this.getSourceofrawmaterialList().forEach(current -> {
                    toBeUpdated.getSourceofrawmaterialList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getSourceofrawmaterialList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageAgriInputDemandProfile(this);
                    this.getSourceofrawmaterialList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getSeedsindemandList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getSeedsindemandList() != null) {
                this.getSeedsindemandList().forEach(current -> {
                    toBeUpdated.getSeedsindemandList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getSeedsindemandList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageAgriInputDemandProfile(this);
                    this.getSeedsindemandList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getMaincreditsourceList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getMaincreditsourceList() != null) {
                this.getMaincreditsourceList().forEach(current -> {
                    toBeUpdated.getMaincreditsourceList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getMaincreditsourceList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageAgriInputDemandProfile(this);
                    this.getMaincreditsourceList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getExistingstorageissueList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getExistingstorageissueList() != null) {
                this.getExistingstorageissueList().forEach(current -> {
                    toBeUpdated.getExistingstorageissueList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getExistingstorageissueList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageAgriInputDemandProfile(this);
                    this.getExistingstorageissueList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getStorageneededforcropList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getStorageneededforcropList() != null) {
                this.getStorageneededforcropList().forEach(current -> {
                    toBeUpdated.getStorageneededforcropList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getStorageneededforcropList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageAgriInputDemandProfile(this);
                    this.getStorageneededforcropList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getWherefarmersbuyinputList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getWherefarmersbuyinputList() != null) {
                this.getWherefarmersbuyinputList().forEach(current -> {
                    toBeUpdated.getWherefarmersbuyinputList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getWherefarmersbuyinputList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageAgriInputDemandProfile(this);
                    this.getWherefarmersbuyinputList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getRawmaterialsneededforindustryList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getRawmaterialsneededforindustryList() != null) {
                this.getRawmaterialsneededforindustryList().forEach(current -> {
                    toBeUpdated.getRawmaterialsneededforindustryList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getRawmaterialsneededforindustryList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageAgriInputDemandProfile(this);
                    this.getRawmaterialsneededforindustryList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getPesticidesindemandList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getPesticidesindemandList() != null) {
                this.getPesticidesindemandList().forEach(current -> {
                    toBeUpdated.getPesticidesindemandList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getPesticidesindemandList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageAgriInputDemandProfile(this);
                    this.getPesticidesindemandList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getCurrentstoragemethodList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getCurrentstoragemethodList() != null) {
                this.getCurrentstoragemethodList().forEach(current -> {
                    toBeUpdated.getCurrentstoragemethodList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getCurrentstoragemethodList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageAgriInputDemandProfile(this);
                    this.getCurrentstoragemethodList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
