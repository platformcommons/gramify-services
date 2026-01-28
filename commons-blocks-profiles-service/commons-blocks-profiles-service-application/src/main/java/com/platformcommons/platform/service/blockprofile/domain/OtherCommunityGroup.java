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
@Table(name = "other_community_group")
public class OtherCommunityGroup extends  BaseTransactionalEntity implements DomainEntity<OtherCommunityGroup> {

    @ManyToOne
    @JoinColumn(name = "villageSocialStructureProfile_id")
    private VillageSocialStructureProfile villageSocialStructureProfile;


    @Column(
             name = "other_community_group"
    )
    private String otherCommunityGroup;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public OtherCommunityGroup(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageSocialStructureProfile villageSocialStructureProfile,
        String otherCommunityGroup,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageSocialStructureProfile = villageSocialStructureProfile;
        this.otherCommunityGroup = otherCommunityGroup;
        this.id = id;


    }


    public void update(OtherCommunityGroup toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(OtherCommunityGroup toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageSocialStructureProfile() != null) {
            this.setVillageSocialStructureProfile(toBeUpdated.getVillageSocialStructureProfile());
        }
        if (toBeUpdated.getOtherCommunityGroup() != null) {
            this.setOtherCommunityGroup(toBeUpdated.getOtherCommunityGroup());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
