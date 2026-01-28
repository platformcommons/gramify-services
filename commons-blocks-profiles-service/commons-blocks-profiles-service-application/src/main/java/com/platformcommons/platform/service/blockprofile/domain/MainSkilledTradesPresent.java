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
@Table(name = "main_skilled_trades_present")
public class MainSkilledTradesPresent extends  BaseTransactionalEntity implements DomainEntity<MainSkilledTradesPresent> {

    @Column(
             name = "main_skilled_trade_present"
    )
    private String mainSkilledTradePresent;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageHumanResourceProfile_id")
    private VillageHumanResourceProfile villageHumanResourceProfile;





    @Builder
    public MainSkilledTradesPresent(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String mainSkilledTradePresent,
        Long id,
        VillageHumanResourceProfile villageHumanResourceProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.mainSkilledTradePresent = mainSkilledTradePresent;
        this.id = id;
        this.villageHumanResourceProfile = villageHumanResourceProfile;


    }


    public void update(MainSkilledTradesPresent toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(MainSkilledTradesPresent toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getMainSkilledTradePresent() != null) {
            this.setMainSkilledTradePresent(toBeUpdated.getMainSkilledTradePresent());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageHumanResourceProfile() != null) {
            this.setVillageHumanResourceProfile(toBeUpdated.getVillageHumanResourceProfile());
        }
    }

    public void init() {

    }
}
