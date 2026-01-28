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
@Table(name = "key_constraints_for_surplus_export")
public class KeyConstraintsForSurplusExport extends  BaseTransactionalEntity implements DomainEntity<KeyConstraintsForSurplusExport> {

    @Column(
             name = "key_constraints_for_surplus_export"
    )
    private String keyConstraintsForSurplusExport;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "villageSurplusProduceProfile_id")
    private VillageSurplusProduceProfile villageSurplusProduceProfile;





    @Builder
    public KeyConstraintsForSurplusExport(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String keyConstraintsForSurplusExport,
        Long id,
        VillageSurplusProduceProfile villageSurplusProduceProfile
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.keyConstraintsForSurplusExport = keyConstraintsForSurplusExport;
        this.id = id;
        this.villageSurplusProduceProfile = villageSurplusProduceProfile;


    }


    public void update(KeyConstraintsForSurplusExport toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(KeyConstraintsForSurplusExport toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getKeyConstraintsForSurplusExport() != null) {
            this.setKeyConstraintsForSurplusExport(toBeUpdated.getKeyConstraintsForSurplusExport());
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
