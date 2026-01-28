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
@Table(name = "main_surplus_destination")
public class MainSurplusDestination extends  BaseTransactionalEntity implements DomainEntity<MainSurplusDestination> {

    @Column(
             name = "main_surplus_destinations"
    )
    private String mainSurplusDestinations;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageSurplusProduceProfile_id")
    private VillageSurplusProduceProfile villageSurplusProduceProfile;





    @Builder
    public MainSurplusDestination(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String mainSurplusDestinations,
        Long id,
        VillageSurplusProduceProfile villageSurplusProduceProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.mainSurplusDestinations = mainSurplusDestinations;
        this.id = id;
        this.villageSurplusProduceProfile = villageSurplusProduceProfile;


    }


    public void update(MainSurplusDestination toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(MainSurplusDestination toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getMainSurplusDestinations() != null) {
            this.setMainSurplusDestinations(toBeUpdated.getMainSurplusDestinations());
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
