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
@Table(name = "n_g_o_thematic_area")
public class NGOThematicArea extends  BaseTransactionalEntity implements DomainEntity<NGOThematicArea> {

    @Column(
             name = "ngo_thematic_area"
    )
    private String ngoThematicArea;



    @ManyToOne
    @JoinColumn(name = "villageInstitutionalResourcePro_id")
    private VillageInstitutionalResourcePro villageInstitutionalResourcePro;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public NGOThematicArea(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String ngoThematicArea,
        VillageInstitutionalResourcePro villageInstitutionalResourcePro,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.ngoThematicArea = ngoThematicArea;
        this.villageInstitutionalResourcePro = villageInstitutionalResourcePro;
        this.id = id;


    }


    public void update(NGOThematicArea toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(NGOThematicArea toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getNgoThematicArea() != null) {
            this.setNgoThematicArea(toBeUpdated.getNgoThematicArea());
        }
        if (toBeUpdated.getVillageInstitutionalResourcePro() != null) {
            this.setVillageInstitutionalResourcePro(toBeUpdated.getVillageInstitutionalResourcePro());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
