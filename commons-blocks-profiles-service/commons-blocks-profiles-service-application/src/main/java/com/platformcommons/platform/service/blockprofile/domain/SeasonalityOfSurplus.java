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
@Table(name = "seasonality_of_surplus")
public class SeasonalityOfSurplus extends  BaseTransactionalEntity implements DomainEntity<SeasonalityOfSurplus> {

    @Column(
             name = "seasonality_of_surplus"
    )
    private String seasonalityOfSurplus;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageSurplusProduceProfile_id")
    private VillageSurplusProduceProfile villageSurplusProduceProfile;





    @Builder
    public SeasonalityOfSurplus(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String seasonalityOfSurplus,
        Long id,
        VillageSurplusProduceProfile villageSurplusProduceProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.seasonalityOfSurplus = seasonalityOfSurplus;
        this.id = id;
        this.villageSurplusProduceProfile = villageSurplusProduceProfile;


    }


    public void update(SeasonalityOfSurplus toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(SeasonalityOfSurplus toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getSeasonalityOfSurplus() != null) {
            this.setSeasonalityOfSurplus(toBeUpdated.getSeasonalityOfSurplus());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getVillageSurplusProduceProfile() != null) {
            this.setVillageSurplusProduceProfile(toBeUpdated.getVillageSurplusProduceProfile());
        }
    }

    public void init() {

    }
}
