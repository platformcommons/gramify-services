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
@Table(name = "production_seasonality")
public class ProductionSeasonality extends  BaseTransactionalEntity implements DomainEntity<ProductionSeasonality> {

    @ManyToOne
    @JoinColumn(name = "villageLivestockProfile_id")
    private VillageLivestockProfile villageLivestockProfile;


    @Column(
             name = "production_seasonality"
    )
    private String productionSeasonality;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public ProductionSeasonality(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageLivestockProfile villageLivestockProfile,
        String productionSeasonality,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageLivestockProfile = villageLivestockProfile;
        this.productionSeasonality = productionSeasonality;
        this.id = id;


    }


    public void update(ProductionSeasonality toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(ProductionSeasonality toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageLivestockProfile() != null) {
            this.setVillageLivestockProfile(toBeUpdated.getVillageLivestockProfile());
        }
        if (toBeUpdated.getProductionSeasonality() != null) {
            this.setProductionSeasonality(toBeUpdated.getProductionSeasonality());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
