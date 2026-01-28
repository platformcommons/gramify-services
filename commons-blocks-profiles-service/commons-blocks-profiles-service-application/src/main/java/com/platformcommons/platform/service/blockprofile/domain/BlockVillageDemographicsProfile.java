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
@Table(name = "block_village_demographics_profile")
public class BlockVillageDemographicsProfile extends  BaseTransactionalEntity implements DomainEntity<BlockVillageDemographicsProfile> {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageAgeDistribution15to59;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villagePopulationDensity;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageAgeDistribution60plus;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageLiteracyRateMale;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageGenderRatio;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageLiteracyRateFemale;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villagePopulation;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageLiteracyRateOverall;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageAgeDistribution0to14;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public BlockVillageDemographicsProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        UoMValue villageAgeDistribution15to59,
        UoMValue villagePopulationDensity,
        UoMValue villageAgeDistribution60plus,
        UoMValue villageLiteracyRateMale,
        LinkedCode linkedContext,
        UoMValue villageGenderRatio,
        UoMValue villageLiteracyRateFemale,
        LinkedCode linkedActor,
        UoMValue villagePopulation,
        UoMValue villageLiteracyRateOverall,
        UoMValue villageAgeDistribution0to14,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageAgeDistribution15to59 = villageAgeDistribution15to59;
        this.villagePopulationDensity = villagePopulationDensity;
        this.villageAgeDistribution60plus = villageAgeDistribution60plus;
        this.villageLiteracyRateMale = villageLiteracyRateMale;
        this.linkedContext = linkedContext;
        this.villageGenderRatio = villageGenderRatio;
        this.villageLiteracyRateFemale = villageLiteracyRateFemale;
        this.linkedActor = linkedActor;
        this.villagePopulation = villagePopulation;
        this.villageLiteracyRateOverall = villageLiteracyRateOverall;
        this.villageAgeDistribution0to14 = villageAgeDistribution0to14;
        this.id = id;


    }


    public void update(BlockVillageDemographicsProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(BlockVillageDemographicsProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageAgeDistribution15to59() != null) {
            this.setVillageAgeDistribution15to59(toBeUpdated.getVillageAgeDistribution15to59());
        }
        if (toBeUpdated.getVillagePopulationDensity() != null) {
            this.setVillagePopulationDensity(toBeUpdated.getVillagePopulationDensity());
        }
        if (toBeUpdated.getVillageAgeDistribution60plus() != null) {
            this.setVillageAgeDistribution60plus(toBeUpdated.getVillageAgeDistribution60plus());
        }
        if (toBeUpdated.getVillageLiteracyRateMale() != null) {
            this.setVillageLiteracyRateMale(toBeUpdated.getVillageLiteracyRateMale());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getVillageGenderRatio() != null) {
            this.setVillageGenderRatio(toBeUpdated.getVillageGenderRatio());
        }
        if (toBeUpdated.getVillageLiteracyRateFemale() != null) {
            this.setVillageLiteracyRateFemale(toBeUpdated.getVillageLiteracyRateFemale());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getVillagePopulation() != null) {
            this.setVillagePopulation(toBeUpdated.getVillagePopulation());
        }
        if (toBeUpdated.getVillageLiteracyRateOverall() != null) {
            this.setVillageLiteracyRateOverall(toBeUpdated.getVillageLiteracyRateOverall());
        }
        if (toBeUpdated.getVillageAgeDistribution0to14() != null) {
            this.setVillageAgeDistribution0to14(toBeUpdated.getVillageAgeDistribution0to14());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
