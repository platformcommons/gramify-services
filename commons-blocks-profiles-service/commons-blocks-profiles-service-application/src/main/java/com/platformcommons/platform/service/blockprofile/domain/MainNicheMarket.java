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
@Table(name = "main_niche_market")
public class MainNicheMarket extends  BaseTransactionalEntity implements DomainEntity<MainNicheMarket> {

    @Column(
             name = "main_niche_markets"
    )
    private String mainNicheMarkets;



    @ManyToOne
    @JoinColumn(name = "villageNicheProductProfile_id")
    private VillageNicheProductProfile villageNicheProductProfile;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public MainNicheMarket(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String mainNicheMarkets,
        VillageNicheProductProfile villageNicheProductProfile,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.mainNicheMarkets = mainNicheMarkets;
        this.villageNicheProductProfile = villageNicheProductProfile;
        this.id = id;


    }


    public void update(MainNicheMarket toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(MainNicheMarket toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getMainNicheMarkets() != null) {
            this.setMainNicheMarkets(toBeUpdated.getMainNicheMarkets());
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
