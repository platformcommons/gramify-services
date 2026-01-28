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
@Table(name = "village_institutional_resource_pro")
public class VillageInstitutionalResourcePro extends  BaseTransactionalEntity implements DomainEntity<VillageInstitutionalResourcePro> {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue fpoCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue farmerCooperativeCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue phcCountInBlock;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue anganwadiCount;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
         name = "phc_covering_village_name"
    )
    private String phcCoveringVillageName;

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


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue gramPanchayatCountInBlock;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue ngoCount;


    @Column(
         name = "gram_panchayat_name"
    )
    private String gramPanchayatName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue farmerCoopMemberCount;


    @Column(
         name = "has_block_panchayat_samiti"
    )
    private Boolean hasBlockPanchayatSamiti;


    @OneToMany(mappedBy = "villageInstitutionalResourcePro", cascade = CascadeType.ALL)
    private Set<NGOThematicArea> ngothematicareaList;


    @Builder
    public VillageInstitutionalResourcePro(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        UoMValue fpoCount,
        UoMValue farmerCooperativeCount,
        UoMValue phcCountInBlock,
        UoMValue anganwadiCount,
        LinkedCode linkedContext,
        String phcCoveringVillageName,
        LinkedCode linkedActor,
        Long id,
        UoMValue gramPanchayatCountInBlock,
        UoMValue ngoCount,
        String gramPanchayatName,
        UoMValue farmerCoopMemberCount,
        Boolean hasBlockPanchayatSamiti,
        Set<NGOThematicArea> ngothematicareaList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.fpoCount = fpoCount;
        this.farmerCooperativeCount = farmerCooperativeCount;
        this.phcCountInBlock = phcCountInBlock;
        this.anganwadiCount = anganwadiCount;
        this.linkedContext = linkedContext;
        this.phcCoveringVillageName = phcCoveringVillageName;
        this.linkedActor = linkedActor;
        this.id = id;
        this.gramPanchayatCountInBlock = gramPanchayatCountInBlock;
        this.ngoCount = ngoCount;
        this.gramPanchayatName = gramPanchayatName;
        this.farmerCoopMemberCount = farmerCoopMemberCount;
        this.hasBlockPanchayatSamiti = hasBlockPanchayatSamiti;
        this.ngothematicareaList=ngothematicareaList;
        this.ngothematicareaList.forEach(it->it.setVillageInstitutionalResourcePro(this));


    }


    public void update(VillageInstitutionalResourcePro toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageInstitutionalResourcePro toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getFpoCount() != null) {
            this.setFpoCount(toBeUpdated.getFpoCount());
        }
        if (toBeUpdated.getFarmerCooperativeCount() != null) {
            this.setFarmerCooperativeCount(toBeUpdated.getFarmerCooperativeCount());
        }
        if (toBeUpdated.getPhcCountInBlock() != null) {
            this.setPhcCountInBlock(toBeUpdated.getPhcCountInBlock());
        }
        if (toBeUpdated.getAnganwadiCount() != null) {
            this.setAnganwadiCount(toBeUpdated.getAnganwadiCount());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getPhcCoveringVillageName() != null) {
            this.setPhcCoveringVillageName(toBeUpdated.getPhcCoveringVillageName());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getGramPanchayatCountInBlock() != null) {
            this.setGramPanchayatCountInBlock(toBeUpdated.getGramPanchayatCountInBlock());
        }
        if (toBeUpdated.getNgoCount() != null) {
            this.setNgoCount(toBeUpdated.getNgoCount());
        }
        if (toBeUpdated.getGramPanchayatName() != null) {
            this.setGramPanchayatName(toBeUpdated.getGramPanchayatName());
        }
        if (toBeUpdated.getFarmerCoopMemberCount() != null) {
            this.setFarmerCoopMemberCount(toBeUpdated.getFarmerCoopMemberCount());
        }
        if (toBeUpdated.getHasBlockPanchayatSamiti() != null) {
            this.setHasBlockPanchayatSamiti(toBeUpdated.getHasBlockPanchayatSamiti());
        }
        if (toBeUpdated.getNgothematicareaList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getNgothematicareaList() != null) {
                this.getNgothematicareaList().forEach(current -> {
                    toBeUpdated.getNgothematicareaList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getNgothematicareaList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageInstitutionalResourcePro(this);
                    this.getNgothematicareaList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
