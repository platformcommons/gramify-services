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
@Table(name = "h_h_loan_source")
public class HHLoanSource extends  BaseTransactionalEntity implements DomainEntity<HHLoanSource> {

    @Column(
             name = "loan_source"
    )
    private String loanSource;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;





    @Builder
    public HHLoanSource(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String loanSource,
        Long id,
        Household household
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.loanSource = loanSource;
        this.id = id;
        this.household = household;


    }


    public void update(HHLoanSource toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HHLoanSource toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLoanSource() != null) {
            this.setLoanSource(toBeUpdated.getLoanSource());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getHousehold() != null) {
            this.setHousehold(toBeUpdated.getHousehold());
        }
    }

    public void init() {

    }
}
