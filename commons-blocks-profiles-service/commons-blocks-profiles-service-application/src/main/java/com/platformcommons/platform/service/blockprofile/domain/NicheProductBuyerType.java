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
@Table(name = "niche_product_buyer_type")
public class NicheProductBuyerType extends  BaseTransactionalEntity implements DomainEntity<NicheProductBuyerType> {

    @Column(
             name = "nicheproduct_buyer_type"
    )
    private String nicheproductBuyerType;



    @ManyToOne
    @JoinColumn(name = "villageExportPotentialProfile_id")
    private VillageExportPotentialProfile villageExportPotentialProfile;


    @ManyToOne
    @JoinColumn(name = "villageNicheProductProfile_id")
    private VillageNicheProductProfile villageNicheProductProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public NicheProductBuyerType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String nicheproductBuyerType,
        VillageExportPotentialProfile villageExportPotentialProfile,
        VillageNicheProductProfile villageNicheProductProfile,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.nicheproductBuyerType = nicheproductBuyerType;
        this.villageExportPotentialProfile = villageExportPotentialProfile;
        this.villageNicheProductProfile = villageNicheProductProfile;
        this.id = id;


    }


    public void update(NicheProductBuyerType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(NicheProductBuyerType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getNicheproductBuyerType() != null) {
            this.setNicheproductBuyerType(toBeUpdated.getNicheproductBuyerType());
        }
        if (toBeUpdated.getVillageExportPotentialProfile() != null) {
            this.setVillageExportPotentialProfile(toBeUpdated.getVillageExportPotentialProfile());
        }
        if (toBeUpdated.getVillageNicheProductProfile() != null) {
            this.setVillageNicheProductProfile(toBeUpdated.getVillageNicheProductProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
