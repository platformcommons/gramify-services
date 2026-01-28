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
@Table(name = "village_human_resource_profile")
public class VillageHumanResourceProfile extends  BaseTransactionalEntity implements DomainEntity<VillageHumanResourceProfile> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue artisanFamilyCount;


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
             name = "skilled_labor_shortage_level"
    )
    private String skilledLaborShortageLevel;



    @Column(
         name = "key_community_leaders_description"
    )
    private String keyCommunityLeadersDescription;


    @OneToMany(mappedBy = "villageHumanResourceProfile", cascade = CascadeType.ALL)
    private Set<HHSkilledWorkerType> hhskilledworkertypeList;
    @OneToMany(mappedBy = "villageHumanResourceProfile", cascade = CascadeType.ALL)
    private Set<EmergingEnterpriseType> emergingenterprisetypeList;
    @OneToMany(mappedBy = "villageHumanResourceProfile", cascade = CascadeType.ALL)
    private Set<MainSkilledTradesPresent> mainskilledtradespresentList;
    @OneToMany(mappedBy = "villageHumanResourceProfile", cascade = CascadeType.ALL)
    private Set<ArtisanType> artisantypeList;


    @Builder
    public VillageHumanResourceProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedActor,
        UoMValue artisanFamilyCount,
        LinkedCode linkedContext,
        Long id,
        String skilledLaborShortageLevel,
        String keyCommunityLeadersDescription,
        Set<HHSkilledWorkerType> hhskilledworkertypeList,
        Set<EmergingEnterpriseType> emergingenterprisetypeList,
        Set<MainSkilledTradesPresent> mainskilledtradespresentList,
        Set<ArtisanType> artisantypeList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedActor = linkedActor;
        this.artisanFamilyCount = artisanFamilyCount;
        this.linkedContext = linkedContext;
        this.id = id;
        this.skilledLaborShortageLevel = skilledLaborShortageLevel;
        this.keyCommunityLeadersDescription = keyCommunityLeadersDescription;
        this.hhskilledworkertypeList=hhskilledworkertypeList;
        this.hhskilledworkertypeList.forEach(it->it.setVillageHumanResourceProfile(this));
        this.emergingenterprisetypeList=emergingenterprisetypeList;
        this.emergingenterprisetypeList.forEach(it->it.setVillageHumanResourceProfile(this));
        this.mainskilledtradespresentList=mainskilledtradespresentList;
        this.mainskilledtradespresentList.forEach(it->it.setVillageHumanResourceProfile(this));
        this.artisantypeList=artisantypeList;
        this.artisantypeList.forEach(it->it.setVillageHumanResourceProfile(this));


    }


    public void update(VillageHumanResourceProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageHumanResourceProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getArtisanFamilyCount() != null) {
            this.setArtisanFamilyCount(toBeUpdated.getArtisanFamilyCount());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getSkilledLaborShortageLevel() != null) {
            this.setSkilledLaborShortageLevel(toBeUpdated.getSkilledLaborShortageLevel());
        }
        if (toBeUpdated.getKeyCommunityLeadersDescription() != null) {
            this.setKeyCommunityLeadersDescription(toBeUpdated.getKeyCommunityLeadersDescription());
        }
        if (toBeUpdated.getHhskilledworkertypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHhskilledworkertypeList() != null) {
                this.getHhskilledworkertypeList().forEach(current -> {
                    toBeUpdated.getHhskilledworkertypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHhskilledworkertypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageHumanResourceProfile(this);
                    this.getHhskilledworkertypeList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getEmergingenterprisetypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getEmergingenterprisetypeList() != null) {
                this.getEmergingenterprisetypeList().forEach(current -> {
                    toBeUpdated.getEmergingenterprisetypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getEmergingenterprisetypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageHumanResourceProfile(this);
                    this.getEmergingenterprisetypeList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getMainskilledtradespresentList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getMainskilledtradespresentList() != null) {
                this.getMainskilledtradespresentList().forEach(current -> {
                    toBeUpdated.getMainskilledtradespresentList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getMainskilledtradespresentList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageHumanResourceProfile(this);
                    this.getMainskilledtradespresentList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getArtisantypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getArtisantypeList() != null) {
                this.getArtisantypeList().forEach(current -> {
                    toBeUpdated.getArtisantypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getArtisantypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageHumanResourceProfile(this);
                    this.getArtisantypeList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
