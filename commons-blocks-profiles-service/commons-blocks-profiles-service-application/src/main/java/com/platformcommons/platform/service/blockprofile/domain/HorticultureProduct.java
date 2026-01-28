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
@Table(name = "horticulture_product")
public class HorticultureProduct extends  BaseTransactionalEntity implements DomainEntity<HorticultureProduct> {

    @ManyToOne
    @JoinColumn(name = "villageHorticultureProfile_id")
    private VillageHorticultureProfile villageHorticultureProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "horticulture_product"
    )
    private String horticultureProduct;






    @Builder
    public HorticultureProduct(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageHorticultureProfile villageHorticultureProfile,
        Long id,
        String horticultureProduct
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageHorticultureProfile = villageHorticultureProfile;
        this.id = id;
        this.horticultureProduct = horticultureProduct;


    }


    public void update(HorticultureProduct toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HorticultureProduct toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageHorticultureProfile() != null) {
            this.setVillageHorticultureProfile(toBeUpdated.getVillageHorticultureProfile());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getHorticultureProduct() != null) {
            this.setHorticultureProduct(toBeUpdated.getHorticultureProduct());
        }
    }

    public void init() {

    }
}
