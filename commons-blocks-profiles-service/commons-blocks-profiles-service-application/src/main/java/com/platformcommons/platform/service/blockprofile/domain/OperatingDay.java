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
@Table(name = "operating_day")
public class OperatingDay extends  BaseTransactionalEntity implements DomainEntity<OperatingDay> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageMarketProfile_id")
    private VillageMarketProfile villageMarketProfile;


    @Column(
             name = "operating_day"
    )
    private String operatingDay;






    @Builder
    public OperatingDay(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        VillageMarketProfile villageMarketProfile,
        String operatingDay
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.villageMarketProfile = villageMarketProfile;
        this.operatingDay = operatingDay;


    }


    public void update(OperatingDay toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(OperatingDay toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageMarketProfile() != null) {
            this.setVillageMarketProfile(toBeUpdated.getVillageMarketProfile());
        }
        if (toBeUpdated.getOperatingDay() != null) {
            this.setOperatingDay(toBeUpdated.getOperatingDay());
        }
    }

    public void init() {

    }
}
