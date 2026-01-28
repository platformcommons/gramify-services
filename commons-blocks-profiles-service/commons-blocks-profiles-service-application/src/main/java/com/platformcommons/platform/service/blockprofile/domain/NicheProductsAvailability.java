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
@Table(name = "niche_products_availability")
public class NicheProductsAvailability extends  BaseTransactionalEntity implements DomainEntity<NicheProductsAvailability> {

    @Column(
             name = "niche_products_available"
    )
    private String nicheProductsAvailable;



    @ManyToOne
    @JoinColumn(name = "villageNicheProductProfile_id")
    private VillageNicheProductProfile villageNicheProductProfile;


    @ManyToOne
    @JoinColumn(name = "villageExportPotentialProfile_id")
    private VillageExportPotentialProfile villageExportPotentialProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public NicheProductsAvailability(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String nicheProductsAvailable,
        VillageNicheProductProfile villageNicheProductProfile,
        VillageExportPotentialProfile villageExportPotentialProfile,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.nicheProductsAvailable = nicheProductsAvailable;
        this.villageNicheProductProfile = villageNicheProductProfile;
        this.villageExportPotentialProfile = villageExportPotentialProfile;
        this.id = id;


    }


    public void update(NicheProductsAvailability toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(NicheProductsAvailability toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getNicheProductsAvailable() != null) {
            this.setNicheProductsAvailable(toBeUpdated.getNicheProductsAvailable());
        }
        if (toBeUpdated.getVillageNicheProductProfile() != null) {
            this.setVillageNicheProductProfile(toBeUpdated.getVillageNicheProductProfile());
        }
        if (toBeUpdated.getVillageExportPotentialProfile() != null) {
            this.setVillageExportPotentialProfile(toBeUpdated.getVillageExportPotentialProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
