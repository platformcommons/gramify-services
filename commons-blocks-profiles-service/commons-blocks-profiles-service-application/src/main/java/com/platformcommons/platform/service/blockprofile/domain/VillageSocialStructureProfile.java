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
@Table(name = "village_social_structure_profile")
public class VillageSocialStructureProfile extends  BaseTransactionalEntity implements DomainEntity<VillageSocialStructureProfile> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
             name = "social_harmony_level"
    )
    private String socialHarmonyLevel;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue womenLedShgShare;


    @Column(
         name = "recent_conflicts_reported"
    )
    private Boolean recentConflictsReported;

    @Column(
             name = "gram_sabha_meeting_frequency"
    )
    private String gramSabhaMeetingFrequency;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue shgCount;



    @OneToMany(mappedBy = "villageSocialStructureProfile", cascade = CascadeType.ALL)
    private Set<OtherCommunityGroup> othercommunitygroupList;


    @Builder
    public VillageSocialStructureProfile(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedActor,
        String socialHarmonyLevel,
        LinkedCode linkedContext,
        UoMValue womenLedShgShare,
        Boolean recentConflictsReported,
        String gramSabhaMeetingFrequency,
        Long id,
        UoMValue shgCount,
        Set<OtherCommunityGroup> othercommunitygroupList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedActor = linkedActor;
        this.socialHarmonyLevel = socialHarmonyLevel;
        this.linkedContext = linkedContext;
        this.womenLedShgShare = womenLedShgShare;
        this.recentConflictsReported = recentConflictsReported;
        this.gramSabhaMeetingFrequency = gramSabhaMeetingFrequency;
        this.id = id;
        this.shgCount = shgCount;
        this.othercommunitygroupList=othercommunitygroupList;
        this.othercommunitygroupList.forEach(it->it.setVillageSocialStructureProfile(this));


    }


    public void update(VillageSocialStructureProfile toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageSocialStructureProfile toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getSocialHarmonyLevel() != null) {
            this.setSocialHarmonyLevel(toBeUpdated.getSocialHarmonyLevel());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getWomenLedShgShare() != null) {
            this.setWomenLedShgShare(toBeUpdated.getWomenLedShgShare());
        }
        if (toBeUpdated.getRecentConflictsReported() != null) {
            this.setRecentConflictsReported(toBeUpdated.getRecentConflictsReported());
        }
        if (toBeUpdated.getGramSabhaMeetingFrequency() != null) {
            this.setGramSabhaMeetingFrequency(toBeUpdated.getGramSabhaMeetingFrequency());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getShgCount() != null) {
            this.setShgCount(toBeUpdated.getShgCount());
        }
        if (toBeUpdated.getOthercommunitygroupList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getOthercommunitygroupList() != null) {
                this.getOthercommunitygroupList().forEach(current -> {
                    toBeUpdated.getOthercommunitygroupList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getOthercommunitygroupList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageSocialStructureProfile(this);
                    this.getOthercommunitygroupList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
