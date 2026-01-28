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
@Table(name = "purpose_of_credit")
public class PurposeOfCredit extends  BaseTransactionalEntity implements DomainEntity<PurposeOfCredit> {

    @ManyToOne
    @JoinColumn(name = "villageAgriInputDemandProfile_id")
    private VillageAgriInputDemandProfile villageAgriInputDemandProfile;


    @Column(
             name = "purpose_of_credit"
    )
    private String purposeOfCredit;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public PurposeOfCredit(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageAgriInputDemandProfile villageAgriInputDemandProfile,
        String purposeOfCredit,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageAgriInputDemandProfile = villageAgriInputDemandProfile;
        this.purposeOfCredit = purposeOfCredit;
        this.id = id;


    }


    public void update(PurposeOfCredit toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(PurposeOfCredit toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageAgriInputDemandProfile() != null) {
            this.setVillageAgriInputDemandProfile(toBeUpdated.getVillageAgriInputDemandProfile());
        }
        if (toBeUpdated.getPurposeOfCredit() != null) {
            this.setPurposeOfCredit(toBeUpdated.getPurposeOfCredit());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
