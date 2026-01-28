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
@Table(name = "h_h_savings_mode")
public class HHSavingsMode extends  BaseTransactionalEntity implements DomainEntity<HHSavingsMode> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "savings_mode"
    )
    private String savingsMode;



    @ManyToOne
    @JoinColumn(name = "householdFinancialBehaviour_id")
    private HouseholdFinancialBehaviour householdFinancialBehaviour;





    @Builder
    public HHSavingsMode(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        String savingsMode,
        HouseholdFinancialBehaviour householdFinancialBehaviour
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.savingsMode = savingsMode;
        this.householdFinancialBehaviour = householdFinancialBehaviour;


    }


    public void update(HHSavingsMode toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HHSavingsMode toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getSavingsMode() != null) {
            this.setSavingsMode(toBeUpdated.getSavingsMode());
        }
        if (toBeUpdated.getHouseholdFinancialBehaviour() != null) {
            this.setHouseholdFinancialBehaviour(toBeUpdated.getHouseholdFinancialBehaviour());
        }
    }

    public void init() {

    }
}
