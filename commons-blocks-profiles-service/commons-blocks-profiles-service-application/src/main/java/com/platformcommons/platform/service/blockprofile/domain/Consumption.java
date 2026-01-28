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
@Table(name = "consumption")
public class Consumption extends  BaseTransactionalEntity implements DomainEntity<Consumption> {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue frequency;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue requiredUnit;


    @Column(
             name = "item_code"
    )
    private String itemCode;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue actualUnit;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;






    @Builder
    public Consumption(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        UoMValue frequency,
        UoMValue requiredUnit,
        String itemCode,
        Long id,
        LinkedCode linkedActor,
        UoMValue actualUnit,
        LinkedCode linkedContext
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.frequency = frequency;
        this.requiredUnit = requiredUnit;
        this.itemCode = itemCode;
        this.id = id;
        this.linkedActor = linkedActor;
        this.actualUnit = actualUnit;
        this.linkedContext = linkedContext;


    }


    public void update(Consumption toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(Consumption toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getFrequency() != null) {
            this.setFrequency(toBeUpdated.getFrequency());
        }
        if (toBeUpdated.getRequiredUnit() != null) {
            this.setRequiredUnit(toBeUpdated.getRequiredUnit());
        }
        if (toBeUpdated.getItemCode() != null) {
            this.setItemCode(toBeUpdated.getItemCode());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getActualUnit() != null) {
            this.setActualUnit(toBeUpdated.getActualUnit());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
    }

    public void init() {

    }
}
