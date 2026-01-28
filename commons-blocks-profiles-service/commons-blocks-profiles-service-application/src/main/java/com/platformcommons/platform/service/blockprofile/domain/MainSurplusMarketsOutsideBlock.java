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
@Table(name = "main_surplus_markets_outside_block")
public class MainSurplusMarketsOutsideBlock extends  BaseTransactionalEntity implements DomainEntity<MainSurplusMarketsOutsideBlock> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "main_surplus_market_outside_block"
    )
    private String mainSurplusMarketOutsideBlock;



    @ManyToOne
    @JoinColumn(name = "villageExportPotentialProfile_id")
    private VillageExportPotentialProfile villageExportPotentialProfile;





    @Builder
    public MainSurplusMarketsOutsideBlock(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        String mainSurplusMarketOutsideBlock,
        VillageExportPotentialProfile villageExportPotentialProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.mainSurplusMarketOutsideBlock = mainSurplusMarketOutsideBlock;
        this.villageExportPotentialProfile = villageExportPotentialProfile;


    }


    public void update(MainSurplusMarketsOutsideBlock toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(MainSurplusMarketsOutsideBlock toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getMainSurplusMarketOutsideBlock() != null) {
            this.setMainSurplusMarketOutsideBlock(toBeUpdated.getMainSurplusMarketOutsideBlock());
        }
        if (toBeUpdated.getVillageExportPotentialProfile() != null) {
            this.setVillageExportPotentialProfile(toBeUpdated.getVillageExportPotentialProfile());
        }
    }

    public void init() {

    }
}
