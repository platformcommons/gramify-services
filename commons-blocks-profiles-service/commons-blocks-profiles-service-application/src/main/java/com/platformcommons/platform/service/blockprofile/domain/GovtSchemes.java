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
@Table(name = "govt_schemes")
public class GovtSchemes extends  BaseTransactionalEntity implements DomainEntity<GovtSchemes> {

    @Column(
         name = "central_scheme_impact"
    )
    private String centralSchemeImpact;

    @Column(
         name = "state_agri_scheme_coverage"
    )
    private String stateAgriSchemeCoverage;

    @Column(
         name = "scheme_implementation_impact"
    )
    private String schemeImplementationImpact;

    @Column(
         name = "central_scheme_reach"
    )
    private Double centralSchemeReach;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
         name = "state_social_welfare_coverage"
    )
    private String stateSocialWelfareCoverage;

    @Column(
         name = "state_skill_dev_coverage"
    )
    private String stateSkillDevCoverage;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
         name = "scheme_implementation_reach"
    )
    private Boolean schemeImplementationReach;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;




    @OneToMany(mappedBy = "govtSchemes", cascade = CascadeType.ALL)
    private Set<SchemeImplementationChallenge> schemeimplementationchallengeList;
    @OneToMany(mappedBy = "govtSchemes", cascade = CascadeType.ALL)
    private Set<CentralSchemeList> centralschemelistList;


    @Builder
    public GovtSchemes(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String centralSchemeImpact,
        String stateAgriSchemeCoverage,
        String schemeImplementationImpact,
        Double centralSchemeReach,
        LinkedCode linkedContext,
        String stateSocialWelfareCoverage,
        String stateSkillDevCoverage,
        Long id,
        Boolean schemeImplementationReach,
        LinkedCode linkedActor,
        Set<SchemeImplementationChallenge> schemeimplementationchallengeList,
        Set<CentralSchemeList> centralschemelistList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.centralSchemeImpact = centralSchemeImpact;
        this.stateAgriSchemeCoverage = stateAgriSchemeCoverage;
        this.schemeImplementationImpact = schemeImplementationImpact;
        this.centralSchemeReach = centralSchemeReach;
        this.linkedContext = linkedContext;
        this.stateSocialWelfareCoverage = stateSocialWelfareCoverage;
        this.stateSkillDevCoverage = stateSkillDevCoverage;
        this.id = id;
        this.schemeImplementationReach = schemeImplementationReach;
        this.linkedActor = linkedActor;
        this.schemeimplementationchallengeList=schemeimplementationchallengeList;
        this.schemeimplementationchallengeList.forEach(it->it.setGovtSchemes(this));
        this.centralschemelistList=centralschemelistList;
        this.centralschemelistList.forEach(it->it.setGovtSchemes(this));


    }


    public void update(GovtSchemes toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(GovtSchemes toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getCentralSchemeImpact() != null) {
            this.setCentralSchemeImpact(toBeUpdated.getCentralSchemeImpact());
        }
        if (toBeUpdated.getStateAgriSchemeCoverage() != null) {
            this.setStateAgriSchemeCoverage(toBeUpdated.getStateAgriSchemeCoverage());
        }
        if (toBeUpdated.getSchemeImplementationImpact() != null) {
            this.setSchemeImplementationImpact(toBeUpdated.getSchemeImplementationImpact());
        }
        if (toBeUpdated.getCentralSchemeReach() != null) {
            this.setCentralSchemeReach(toBeUpdated.getCentralSchemeReach());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getStateSocialWelfareCoverage() != null) {
            this.setStateSocialWelfareCoverage(toBeUpdated.getStateSocialWelfareCoverage());
        }
        if (toBeUpdated.getStateSkillDevCoverage() != null) {
            this.setStateSkillDevCoverage(toBeUpdated.getStateSkillDevCoverage());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getSchemeImplementationReach() != null) {
            this.setSchemeImplementationReach(toBeUpdated.getSchemeImplementationReach());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getSchemeimplementationchallengeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getSchemeimplementationchallengeList() != null) {
                this.getSchemeimplementationchallengeList().forEach(current -> {
                    toBeUpdated.getSchemeimplementationchallengeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getSchemeimplementationchallengeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setGovtSchemes(this);
                    this.getSchemeimplementationchallengeList().add(incoming);
                }
            });
        }
        if (toBeUpdated.getCentralschemelistList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getCentralschemelistList() != null) {
                this.getCentralschemelistList().forEach(current -> {
                    toBeUpdated.getCentralschemelistList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getCentralschemelistList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setGovtSchemes(this);
                    this.getCentralschemelistList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
