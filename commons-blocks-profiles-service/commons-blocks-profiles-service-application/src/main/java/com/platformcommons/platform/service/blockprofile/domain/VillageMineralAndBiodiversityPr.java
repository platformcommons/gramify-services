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
@Table(name = "village_mineral_and_biodiversity_pr")
public class VillageMineralAndBiodiversityPr extends  BaseTransactionalEntity implements DomainEntity<VillageMineralAndBiodiversityPr> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
         name = "village_has_mineral_extraction"
    )
    private Boolean villageHasMineralExtraction;

    @Column(
         name = "village_has_protected_area"
    )
    private Boolean villageHasProtectedArea;

    @Column(
             name = "village_mineral_extraction_type"
    )
    private String villageMineralExtractionType;



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


    @Column(
         name = "village_protected_area_name"
    )
    private String villageProtectedAreaName;


    @OneToMany(mappedBy = "villageMineralAndBiodiversityPr", cascade = CascadeType.ALL)
    private Set<VillageCommonFlora> villagecommonfloraList;
    @OneToMany(mappedBy = "villageMineralAndBiodiversityPr", cascade = CascadeType.ALL)
    private Set<VillageCommonFauna> villagecommonfaunaList;


    @Builder
    public VillageMineralAndBiodiversityPr(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedActor,
        Boolean villageHasMineralExtraction,
        Boolean villageHasProtectedArea,
        String villageMineralExtractionType,
        LinkedCode linkedContext,
        Long id,
        String villageProtectedAreaName,
        Set<VillageCommonFlora> villagecommonfloraList,
        Set<VillageCommonFauna> villagecommonfaunaList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedActor = linkedActor;
        this.villageHasMineralExtraction = villageHasMineralExtraction;
        this.villageHasProtectedArea = villageHasProtectedArea;
        this.villageMineralExtractionType = villageMineralExtractionType;
        this.linkedContext = linkedContext;
        this.id = id;
        this.villageProtectedAreaName = villageProtectedAreaName;
        this.villagecommonfloraList=villagecommonfloraList;
        this.villagecommonfloraList.forEach(it->it.setVillageMineralAndBiodiversityPr(this));
        this.villagecommonfaunaList=villagecommonfaunaList;
        this.villagecommonfaunaList.forEach(it->it.setVillageMineralAndBiodiversityPr(this));


    }


    public void update(VillageMineralAndBiodiversityPr toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageMineralAndBiodiversityPr toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getVillageHasMineralExtraction() != null) {
            this.setVillageHasMineralExtraction(toBeUpdated.getVillageHasMineralExtraction());
        }
        if (toBeUpdated.getVillageHasProtectedArea() != null) {
            this.setVillageHasProtectedArea(toBeUpdated.getVillageHasProtectedArea());
        }
        if (toBeUpdated.getVillageMineralExtractionType() != null) {
            this.setVillageMineralExtractionType(toBeUpdated.getVillageMineralExtractionType());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageProtectedAreaName() != null) {
            this.setVillageProtectedAreaName(toBeUpdated.getVillageProtectedAreaName());
        }
        if (toBeUpdated.getVillagecommonfloraList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getVillagecommonfloraList() != null) {
                this.getVillagecommonfloraList().forEach(current -> {
                    toBeUpdated.getVillagecommonfloraList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getVillagecommonfloraList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageMineralAndBiodiversityPr(this);
                    this.getVillagecommonfloraList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getVillagecommonfaunaList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getVillagecommonfaunaList() != null) {
                this.getVillagecommonfaunaList().forEach(current -> {
                    toBeUpdated.getVillagecommonfaunaList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getVillagecommonfaunaList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageMineralAndBiodiversityPr(this);
                    this.getVillagecommonfaunaList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
