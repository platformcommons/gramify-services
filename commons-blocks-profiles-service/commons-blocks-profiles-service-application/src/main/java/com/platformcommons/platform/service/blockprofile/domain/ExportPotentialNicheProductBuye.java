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
@Table(name = "export_potential_niche_product_buye")
public class ExportPotentialNicheProductBuye extends  BaseTransactionalEntity implements DomainEntity<ExportPotentialNicheProductBuye> {

    @Column(
             name = "niche_product_buyer_type"
    )
    private String nicheProductBuyerType;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public ExportPotentialNicheProductBuye(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String nicheProductBuyerType,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.nicheProductBuyerType = nicheProductBuyerType;
        this.id = id;


    }


    public void update(ExportPotentialNicheProductBuye toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(ExportPotentialNicheProductBuye toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getNicheProductBuyerType() != null) {
            this.setNicheProductBuyerType(toBeUpdated.getNicheProductBuyerType());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
