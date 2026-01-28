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
@Table(name = "village_cultural_resource_profile")
public class VillageCulturalResourceProfile extends  BaseTransactionalEntity implements DomainEntity<VillageCulturalResourceProfile> {

    @Column(
         name = "uses_traditional_medicinal_plants"
    )
    private Boolean usesTraditionalMedicinalPlants;

    @Column(
         name = "folk_knowledge_description"
    )
    private String folkKnowledgeDescription;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
         name = "has_historical_site"
    )
    private Boolean hasHistoricalSite;

    @Column(
         name = "has_local_art_forms"
    )
    private Boolean hasLocalArtForms;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
         name = "historical_site_description"
    )
    private String historicalSiteDescription;


    @OneToMany(mappedBy = "villageCulturalResourceProfile", cascade = CascadeType.ALL)
    private Set<MajorFestival> majorfestivalList;
    @OneToMany(mappedBy = "villageCulturalResourceProfile", cascade = CascadeType.ALL)
    private Set<MainReligiousPlace> mainreligiousplaceList;
    @OneToMany(mappedBy = "villageCulturalResourceProfile", cascade = CascadeType.ALL)
    private Set<LocalFestival> localfestivalList;
    @OneToMany(mappedBy = "villageCulturalResourceProfile", cascade = CascadeType.ALL)
    private Set<LocalArtFormType> localartformtypeList;


    @Builder
    public VillageCulturalResourceProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Boolean usesTraditionalMedicinalPlants,
        String folkKnowledgeDescription,
        LinkedCode linkedActor,
        LinkedCode linkedContext,
        Boolean hasHistoricalSite,
        Boolean hasLocalArtForms,
        Long id,
        String historicalSiteDescription,
        Set<MajorFestival> majorfestivalList,
        Set<MainReligiousPlace> mainreligiousplaceList,
        Set<LocalFestival> localfestivalList,
        Set<LocalArtFormType> localartformtypeList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.usesTraditionalMedicinalPlants = usesTraditionalMedicinalPlants;
        this.folkKnowledgeDescription = folkKnowledgeDescription;
        this.linkedActor = linkedActor;
        this.linkedContext = linkedContext;
        this.hasHistoricalSite = hasHistoricalSite;
        this.hasLocalArtForms = hasLocalArtForms;
        this.id = id;
        this.historicalSiteDescription = historicalSiteDescription;
        this.majorfestivalList=majorfestivalList;
        this.majorfestivalList.forEach(it->it.setVillageCulturalResourceProfile(this));
        this.mainreligiousplaceList=mainreligiousplaceList;
        this.mainreligiousplaceList.forEach(it->it.setVillageCulturalResourceProfile(this));
        this.localfestivalList=localfestivalList;
        this.localfestivalList.forEach(it->it.setVillageCulturalResourceProfile(this));
        this.localartformtypeList=localartformtypeList;
        this.localartformtypeList.forEach(it->it.setVillageCulturalResourceProfile(this));


    }


    public void update(VillageCulturalResourceProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageCulturalResourceProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getUsesTraditionalMedicinalPlants() != null) {
            this.setUsesTraditionalMedicinalPlants(toBeUpdated.getUsesTraditionalMedicinalPlants());
        }
        if (toBeUpdated.getFolkKnowledgeDescription() != null) {
            this.setFolkKnowledgeDescription(toBeUpdated.getFolkKnowledgeDescription());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getHasHistoricalSite() != null) {
            this.setHasHistoricalSite(toBeUpdated.getHasHistoricalSite());
        }
        if (toBeUpdated.getHasLocalArtForms() != null) {
            this.setHasLocalArtForms(toBeUpdated.getHasLocalArtForms());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getHistoricalSiteDescription() != null) {
            this.setHistoricalSiteDescription(toBeUpdated.getHistoricalSiteDescription());
        }
        if (toBeUpdated.getMajorfestivalList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getMajorfestivalList() != null) {
                this.getMajorfestivalList().forEach(current -> {
                    toBeUpdated.getMajorfestivalList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getMajorfestivalList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageCulturalResourceProfile(this);
                    this.getMajorfestivalList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getMainreligiousplaceList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getMainreligiousplaceList() != null) {
                this.getMainreligiousplaceList().forEach(current -> {
                    toBeUpdated.getMainreligiousplaceList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getMainreligiousplaceList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageCulturalResourceProfile(this);
                    this.getMainreligiousplaceList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getLocalfestivalList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getLocalfestivalList() != null) {
                this.getLocalfestivalList().forEach(current -> {
                    toBeUpdated.getLocalfestivalList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getLocalfestivalList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageCulturalResourceProfile(this);
                    this.getLocalfestivalList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getLocalartformtypeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getLocalartformtypeList() != null) {
                this.getLocalartformtypeList().forEach(current -> {
                    toBeUpdated.getLocalartformtypeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getLocalartformtypeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageCulturalResourceProfile(this);
                    this.getLocalartformtypeList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
