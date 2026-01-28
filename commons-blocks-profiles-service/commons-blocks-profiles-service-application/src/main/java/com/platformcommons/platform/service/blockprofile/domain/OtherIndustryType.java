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
@Table(name = "other_industry_type")
public class OtherIndustryType extends  BaseTransactionalEntity implements DomainEntity<OtherIndustryType> {

    @ManyToOne
    @JoinColumn(name = "nonAgriculturalMarketActivies_id")
    private NonAgriculturalMarketActivies nonAgriculturalMarketActivies;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "other_industry_type"
    )
    private String otherIndustryType;






    @Builder
    public OtherIndustryType(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        NonAgriculturalMarketActivies nonAgriculturalMarketActivies,
        Long id,
        String otherIndustryType
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.nonAgriculturalMarketActivies = nonAgriculturalMarketActivies;
        this.id = id;
        this.otherIndustryType = otherIndustryType;


    }


    public void update(OtherIndustryType toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(OtherIndustryType toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getNonAgriculturalMarketActivies() != null) {
            this.setNonAgriculturalMarketActivies(toBeUpdated.getNonAgriculturalMarketActivies());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getOtherIndustryType() != null) {
            this.setOtherIndustryType(toBeUpdated.getOtherIndustryType());
        }
    }

    public void init() {

    }
}
