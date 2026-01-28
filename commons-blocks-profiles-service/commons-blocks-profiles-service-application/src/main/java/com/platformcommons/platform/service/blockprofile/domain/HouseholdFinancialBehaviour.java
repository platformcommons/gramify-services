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
@Table(name = "household_financial_behaviour")
public class HouseholdFinancialBehaviour extends  BaseTransactionalEntity implements DomainEntity<HouseholdFinancialBehaviour> {

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
         name = "hh_remittance_source_location"
    )
    private String hhRemittanceSourceLocation;

    @Column(
         name = "hh_saves_regularly"
    )
    private Boolean hhSavesRegularly;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
         name = "hh_has_remittance_income"
    )
    private Boolean hhHasRemittanceIncome;


    @OneToMany(mappedBy = "householdFinancialBehaviour", cascade = CascadeType.ALL)
    private Set<HHSavingsMode> hhsavingsmodeList;


    @Builder
    public HouseholdFinancialBehaviour(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        LinkedCode linkedContext,
        Long id,
        String hhRemittanceSourceLocation,
        Boolean hhSavesRegularly,
        LinkedCode linkedActor,
        Boolean hhHasRemittanceIncome,
        Set<HHSavingsMode> hhsavingsmodeList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.linkedContext = linkedContext;
        this.id = id;
        this.hhRemittanceSourceLocation = hhRemittanceSourceLocation;
        this.hhSavesRegularly = hhSavesRegularly;
        this.linkedActor = linkedActor;
        this.hhHasRemittanceIncome = hhHasRemittanceIncome;
        this.hhsavingsmodeList=hhsavingsmodeList;
        this.hhsavingsmodeList.forEach(it->it.setHouseholdFinancialBehaviour(this));


    }


    public void update(HouseholdFinancialBehaviour toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HouseholdFinancialBehaviour toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getHhRemittanceSourceLocation() != null) {
            this.setHhRemittanceSourceLocation(toBeUpdated.getHhRemittanceSourceLocation());
        }
        if (toBeUpdated.getHhSavesRegularly() != null) {
            this.setHhSavesRegularly(toBeUpdated.getHhSavesRegularly());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getHhHasRemittanceIncome() != null) {
            this.setHhHasRemittanceIncome(toBeUpdated.getHhHasRemittanceIncome());
        }
        if (toBeUpdated.getHhsavingsmodeList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHhsavingsmodeList() != null) {
                this.getHhsavingsmodeList().forEach(current -> {
                    toBeUpdated.getHhsavingsmodeList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHhsavingsmodeList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setHouseholdFinancialBehaviour(this);
                    this.getHhsavingsmodeList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
