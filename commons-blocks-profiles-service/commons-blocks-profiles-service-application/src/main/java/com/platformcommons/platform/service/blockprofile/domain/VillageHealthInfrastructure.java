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
@Table(name = "village_health_infrastructure")
public class VillageHealthInfrastructure extends  BaseTransactionalEntity implements DomainEntity<VillageHealthInfrastructure> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "anm_availability_level"
    )
    private String anmAvailabilityLevel;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue doctorVacancyCount;


    @Column(
             name = "immunisation_coverage_level"
    )
    private String immunisationCoverageLevel;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue infantMortalityRate;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue institutionalDeliveryRate;


    @Column(
         name = "phc_name_covering_village"
    )
    private String phcNameCoveringVillage;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue subCenterCount;



    @OneToMany(mappedBy = "villageHealthInfrastructure", cascade = CascadeType.ALL)
    private Set<CommonHealthIssue> commonhealthissueList;


    @Builder
    public VillageHealthInfrastructure(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        String anmAvailabilityLevel,
        LinkedCode linkedContext,
        UoMValue doctorVacancyCount,
        String immunisationCoverageLevel,
        UoMValue infantMortalityRate,
        UoMValue institutionalDeliveryRate,
        String phcNameCoveringVillage,
        LinkedCode linkedActor,
        UoMValue subCenterCount,
        Set<CommonHealthIssue> commonhealthissueList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.anmAvailabilityLevel = anmAvailabilityLevel;
        this.linkedContext = linkedContext;
        this.doctorVacancyCount = doctorVacancyCount;
        this.immunisationCoverageLevel = immunisationCoverageLevel;
        this.infantMortalityRate = infantMortalityRate;
        this.institutionalDeliveryRate = institutionalDeliveryRate;
        this.phcNameCoveringVillage = phcNameCoveringVillage;
        this.linkedActor = linkedActor;
        this.subCenterCount = subCenterCount;
        this.commonhealthissueList=commonhealthissueList;
        this.commonhealthissueList.forEach(it->it.setVillageHealthInfrastructure(this));


    }


    public void update(VillageHealthInfrastructure toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageHealthInfrastructure toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getAnmAvailabilityLevel() != null) {
            this.setAnmAvailabilityLevel(toBeUpdated.getAnmAvailabilityLevel());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getDoctorVacancyCount() != null) {
            this.setDoctorVacancyCount(toBeUpdated.getDoctorVacancyCount());
        }
        if (toBeUpdated.getImmunisationCoverageLevel() != null) {
            this.setImmunisationCoverageLevel(toBeUpdated.getImmunisationCoverageLevel());
        }
        if (toBeUpdated.getInfantMortalityRate() != null) {
            this.setInfantMortalityRate(toBeUpdated.getInfantMortalityRate());
        }
        if (toBeUpdated.getInstitutionalDeliveryRate() != null) {
            this.setInstitutionalDeliveryRate(toBeUpdated.getInstitutionalDeliveryRate());
        }
        if (toBeUpdated.getPhcNameCoveringVillage() != null) {
            this.setPhcNameCoveringVillage(toBeUpdated.getPhcNameCoveringVillage());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getSubCenterCount() != null) {
            this.setSubCenterCount(toBeUpdated.getSubCenterCount());
        }
        if (toBeUpdated.getCommonhealthissueList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getCommonhealthissueList() != null) {
                this.getCommonhealthissueList().forEach(current -> {
                    toBeUpdated.getCommonhealthissueList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getCommonhealthissueList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageHealthInfrastructure(this);
                    this.getCommonhealthissueList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
