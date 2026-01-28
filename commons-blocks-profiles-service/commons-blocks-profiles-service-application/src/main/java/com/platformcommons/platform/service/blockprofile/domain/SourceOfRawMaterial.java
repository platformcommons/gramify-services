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
@Table(name = "source_of_raw_material")
public class SourceOfRawMaterial extends  BaseTransactionalEntity implements DomainEntity<SourceOfRawMaterial> {

    @Column(
             name = "source_of_raw_material"
    )
    private String sourceOfRawMaterial;



    @ManyToOne
    @JoinColumn(name = "villageAgriInputDemandProfile_id")
    private VillageAgriInputDemandProfile villageAgriInputDemandProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public SourceOfRawMaterial(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String sourceOfRawMaterial,
        VillageAgriInputDemandProfile villageAgriInputDemandProfile,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.sourceOfRawMaterial = sourceOfRawMaterial;
        this.villageAgriInputDemandProfile = villageAgriInputDemandProfile;
        this.id = id;


    }


    public void update(SourceOfRawMaterial toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(SourceOfRawMaterial toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getSourceOfRawMaterial() != null) {
            this.setSourceOfRawMaterial(toBeUpdated.getSourceOfRawMaterial());
        }
        if (toBeUpdated.getVillageAgriInputDemandProfile() != null) {
            this.setVillageAgriInputDemandProfile(toBeUpdated.getVillageAgriInputDemandProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
