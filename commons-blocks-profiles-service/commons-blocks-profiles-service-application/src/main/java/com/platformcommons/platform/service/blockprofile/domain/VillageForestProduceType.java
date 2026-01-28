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
@Table(name = "village_forest_produce_type")
public class VillageForestProduceType extends  BaseTransactionalEntity implements DomainEntity<VillageForestProduceType> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "village_forest_produce_type"
    )
    private String villageForestProduceType;



    @ManyToOne
    @JoinColumn(name = "villageForestResourceProfile_id")
    private VillageForestResourceProfile villageForestResourceProfile;





    @Builder
    public VillageForestProduceType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        String villageForestProduceType,
        VillageForestResourceProfile villageForestResourceProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.villageForestProduceType = villageForestProduceType;
        this.villageForestResourceProfile = villageForestResourceProfile;


    }


    public void update(VillageForestProduceType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageForestProduceType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageForestProduceType() != null) {
            this.setVillageForestProduceType(toBeUpdated.getVillageForestProduceType());
        }
        if (toBeUpdated.getVillageForestResourceProfile() != null) {
            this.setVillageForestResourceProfile(toBeUpdated.getVillageForestResourceProfile());
        }
    }

    public void init() {

    }
}
