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
@Table(name = "village_financial_institution_prof")
public class VillageFinancialInstitutionProf extends  BaseTransactionalEntity implements DomainEntity<VillageFinancialInstitutionProf> {

    @Column(
             name = "shg_movement_strength"
    )
    private String shgMovementStrength;



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
         name = "bank_names"
    )
    private String bankNames;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue cooperativeMemberCount;


    @Column(
         name = "has_mfi_presence"
    )
    private Boolean hasMfiPresence;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue bankCount;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue cooperativeCount;


    @Column(
         name = "mfi_names"
    )
    private String mfiNames;




    @Builder
    public VillageFinancialInstitutionProf(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String shgMovementStrength,
        LinkedCode linkedActor,
        Long id,
        String bankNames,
        UoMValue cooperativeMemberCount,
        Boolean hasMfiPresence,
        UoMValue bankCount,
        LinkedCode linkedContext,
        UoMValue cooperativeCount,
        String mfiNames
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.shgMovementStrength = shgMovementStrength;
        this.linkedActor = linkedActor;
        this.id = id;
        this.bankNames = bankNames;
        this.cooperativeMemberCount = cooperativeMemberCount;
        this.hasMfiPresence = hasMfiPresence;
        this.bankCount = bankCount;
        this.linkedContext = linkedContext;
        this.cooperativeCount = cooperativeCount;
        this.mfiNames = mfiNames;


    }


    public void update(VillageFinancialInstitutionProf toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageFinancialInstitutionProf toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getShgMovementStrength() != null) {
            this.setShgMovementStrength(toBeUpdated.getShgMovementStrength());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getBankNames() != null) {
            this.setBankNames(toBeUpdated.getBankNames());
        }
        if (toBeUpdated.getCooperativeMemberCount() != null) {
            this.setCooperativeMemberCount(toBeUpdated.getCooperativeMemberCount());
        }
        if (toBeUpdated.getHasMfiPresence() != null) {
            this.setHasMfiPresence(toBeUpdated.getHasMfiPresence());
        }
        if (toBeUpdated.getBankCount() != null) {
            this.setBankCount(toBeUpdated.getBankCount());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getCooperativeCount() != null) {
            this.setCooperativeCount(toBeUpdated.getCooperativeCount());
        }
        if (toBeUpdated.getMfiNames() != null) {
            this.setMfiNames(toBeUpdated.getMfiNames());
        }
    }

    public void init() {

    }
}
