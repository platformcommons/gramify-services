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
@Table(name = "village_forest_resource_profile")
public class VillageForestResourceProfile extends  BaseTransactionalEntity implements DomainEntity<VillageForestResourceProfile> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue villageForestAreaShare;


    @Column(
             name = "village_forest_produce_dependence_level"
    )
    private String villageForestProduceDependenceLevel;



    @Column(
             name = "village_forest_type"
    )
    private String villageForestType;



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



    @OneToMany(mappedBy = "villageForestResourceProfile", cascade = CascadeType.ALL)
    private Set<VillageForestProduceType> villageforestproducetypeList;
    @OneToMany(mappedBy = "villageForestResourceProfile", cascade = CascadeType.ALL)
    private Set<VillageCommonWildlife> villagecommonwildlifeList;


    @Builder
    public VillageForestResourceProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedContext,
        UoMValue villageForestAreaShare,
        String villageForestProduceDependenceLevel,
        String villageForestType,
        LinkedCode linkedActor,
        Long id,
        Set<VillageForestProduceType> villageforestproducetypeList,
        Set<VillageCommonWildlife> villagecommonwildlifeList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedContext = linkedContext;
        this.villageForestAreaShare = villageForestAreaShare;
        this.villageForestProduceDependenceLevel = villageForestProduceDependenceLevel;
        this.villageForestType = villageForestType;
        this.linkedActor = linkedActor;
        this.id = id;
        this.villageforestproducetypeList=villageforestproducetypeList;
        this.villageforestproducetypeList.forEach(it->it.setVillageForestResourceProfile(this));
        this.villagecommonwildlifeList=villagecommonwildlifeList;
        this.villagecommonwildlifeList.forEach(it->it.setVillageForestResourceProfile(this));


    }


    public void update(VillageForestResourceProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageForestResourceProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getVillageForestAreaShare() != null) {
            this.setVillageForestAreaShare(toBeUpdated.getVillageForestAreaShare());
        }
        if (toBeUpdated.getVillageForestProduceDependenceLevel() != null) {
            this.setVillageForestProduceDependenceLevel(toBeUpdated.getVillageForestProduceDependenceLevel());
        }
        if (toBeUpdated.getVillageForestType() != null) {
            this.setVillageForestType(toBeUpdated.getVillageForestType());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageforestproducetypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getVillageforestproducetypeList() != null) {
                this.getVillageforestproducetypeList().forEach(current -> {
                    toBeUpdated.getVillageforestproducetypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getVillageforestproducetypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageForestResourceProfile(this);
                    this.getVillageforestproducetypeList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getVillagecommonwildlifeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getVillagecommonwildlifeList() != null) {
                this.getVillagecommonwildlifeList().forEach(current -> {
                    toBeUpdated.getVillagecommonwildlifeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getVillagecommonwildlifeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageForestResourceProfile(this);
                    this.getVillagecommonwildlifeList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
