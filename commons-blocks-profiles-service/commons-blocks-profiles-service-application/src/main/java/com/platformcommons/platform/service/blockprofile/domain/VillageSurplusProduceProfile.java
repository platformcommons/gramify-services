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
@Table(name = "village_surplus_produce_profile")
public class VillageSurplusProduceProfile extends  BaseTransactionalEntity implements DomainEntity<VillageSurplusProduceProfile> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
             name = "surplus_export_level"
    )
    private String surplusExportLevel;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;




    @OneToMany(mappedBy = "villageSurplusProduceProfile", cascade = CascadeType.ALL)
    private Set<SurplusMovedThrough> surplusmovedthroughList;
    @OneToMany(mappedBy = "villageSurplusProduceProfile", cascade = CascadeType.ALL)
    private Set<SeasonalityOfSurplus> seasonalityofsurplusList;
    @OneToMany(mappedBy = "villageSurplusProduceProfile", cascade = CascadeType.ALL)
    private Set<KeyConstraintsForSurplusExport> keyconstraintsforsurplusexportList;
    @OneToMany(mappedBy = "villageSurplusProduceProfile", cascade = CascadeType.ALL)
    private Set<MainSurplusDestination> mainsurplusdestinationList;
    @OneToMany(mappedBy = "villageSurplusProduceProfile", cascade = CascadeType.ALL)
    private Set<SurplusProduceType> surplusproducetypeList;


    @Builder
    public VillageSurplusProduceProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedActor,
        String surplusExportLevel,
        Long id,
        LinkedCode linkedContext,
        Set<SurplusMovedThrough> surplusmovedthroughList,
        Set<SeasonalityOfSurplus> seasonalityofsurplusList,
        Set<KeyConstraintsForSurplusExport> keyconstraintsforsurplusexportList,
        Set<MainSurplusDestination> mainsurplusdestinationList,
        Set<SurplusProduceType> surplusproducetypeList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedActor = linkedActor;
        this.surplusExportLevel = surplusExportLevel;
        this.id = id;
        this.linkedContext = linkedContext;
        this.surplusmovedthroughList=surplusmovedthroughList;
        this.surplusmovedthroughList.forEach(it->it.setVillageSurplusProduceProfile(this));
        this.seasonalityofsurplusList=seasonalityofsurplusList;
        this.seasonalityofsurplusList.forEach(it->it.setVillageSurplusProduceProfile(this));
        this.keyconstraintsforsurplusexportList=keyconstraintsforsurplusexportList;
        this.keyconstraintsforsurplusexportList.forEach(it->it.setVillageSurplusProduceProfile(this));
        this.mainsurplusdestinationList=mainsurplusdestinationList;
        this.mainsurplusdestinationList.forEach(it->it.setVillageSurplusProduceProfile(this));
        this.surplusproducetypeList=surplusproducetypeList;
        this.surplusproducetypeList.forEach(it->it.setVillageSurplusProduceProfile(this));


    }


    public void update(VillageSurplusProduceProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageSurplusProduceProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getSurplusExportLevel() != null) {
            this.setSurplusExportLevel(toBeUpdated.getSurplusExportLevel());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getSurplusmovedthroughList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getSurplusmovedthroughList() != null) {
                this.getSurplusmovedthroughList().forEach(current -> {
                    toBeUpdated.getSurplusmovedthroughList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getSurplusmovedthroughList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageSurplusProduceProfile(this);
                    this.getSurplusmovedthroughList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getSeasonalityofsurplusList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getSeasonalityofsurplusList() != null) {
                this.getSeasonalityofsurplusList().forEach(current -> {
                    toBeUpdated.getSeasonalityofsurplusList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getSeasonalityofsurplusList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageSurplusProduceProfile(this);
                    this.getSeasonalityofsurplusList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getKeyconstraintsforsurplusexportList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getKeyconstraintsforsurplusexportList() != null) {
                this.getKeyconstraintsforsurplusexportList().forEach(current -> {
                    toBeUpdated.getKeyconstraintsforsurplusexportList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getKeyconstraintsforsurplusexportList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageSurplusProduceProfile(this);
                    this.getKeyconstraintsforsurplusexportList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getMainsurplusdestinationList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getMainsurplusdestinationList() != null) {
                this.getMainsurplusdestinationList().forEach(current -> {
                    toBeUpdated.getMainsurplusdestinationList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getMainsurplusdestinationList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageSurplusProduceProfile(this);
                    this.getMainsurplusdestinationList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getSurplusproducetypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getSurplusproducetypeList() != null) {
                this.getSurplusproducetypeList().forEach(current -> {
                    toBeUpdated.getSurplusproducetypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getSurplusproducetypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageSurplusProduceProfile(this);
                    this.getSurplusproducetypeList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
