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
@Table(name = "household_income_and_poverty_profil")
public class HouseholdIncomeAndPovertyProfil extends  BaseTransactionalEntity implements DomainEntity<HouseholdIncomeAndPovertyProfil> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
         name = "hh_has_bank_account"
    )
    private Boolean hhHasBankAccount;

    @Column(
         name = "hh_bpl_status"
    )
    private Boolean hhBplStatus;

    @Column(
         name = "hh_shg_membership"
    )
    private Boolean hhShgMembership;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private CurrencyValue hhAnnualIncome;





    @Builder
    public HouseholdIncomeAndPovertyProfil(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedActor,
        Long id,
        Boolean hhHasBankAccount,
        Boolean hhBplStatus,
        Boolean hhShgMembership,
        LinkedCode linkedContext,
        CurrencyValue hhAnnualIncome
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedActor = linkedActor;
        this.id = id;
        this.hhHasBankAccount = hhHasBankAccount;
        this.hhBplStatus = hhBplStatus;
        this.hhShgMembership = hhShgMembership;
        this.linkedContext = linkedContext;
        this.hhAnnualIncome = hhAnnualIncome;


    }


    public void update(HouseholdIncomeAndPovertyProfil toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HouseholdIncomeAndPovertyProfil toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getHhHasBankAccount() != null) {
            this.setHhHasBankAccount(toBeUpdated.getHhHasBankAccount());
        }
        if (toBeUpdated.getHhBplStatus() != null) {
            this.setHhBplStatus(toBeUpdated.getHhBplStatus());
        }
        if (toBeUpdated.getHhShgMembership() != null) {
            this.setHhShgMembership(toBeUpdated.getHhShgMembership());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getHhAnnualIncome() != null) {
            this.setHhAnnualIncome(toBeUpdated.getHhAnnualIncome());
        }
    }

    public void init() {

    }
}
