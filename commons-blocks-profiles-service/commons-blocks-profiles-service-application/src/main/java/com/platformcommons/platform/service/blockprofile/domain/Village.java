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
@Table(name = "village")
public class Village extends  BaseTransactionalEntity implements DomainEntity<Village> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @Column(
         name = "description"
    )
    private String description;

    @Column(
             name = "district"
    )
    private String district;



    @Column(
             name = "state"
    )
    private String state;



    @Column(
             name = "village"
    )
    private String village;






    @Builder
    public Village(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        Long id,
        LinkedCode linkedContext,
        LinkedCode linkedActor,
        String description,
        String district,
        String state,
        String village
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.linkedContext = linkedContext;
        this.linkedActor = linkedActor;
        this.description = description;
        this.district = district;
        this.state = state;
        this.village = village;


    }


    public void update(Village toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(Village toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getDescription() != null) {
            this.setDescription(toBeUpdated.getDescription());
        }
        if (toBeUpdated.getDistrict() != null) {
            this.setDistrict(toBeUpdated.getDistrict());
        }
        if (toBeUpdated.getState() != null) {
            this.setState(toBeUpdated.getState());
        }
        if (toBeUpdated.getVillage() != null) {
            this.setVillage(toBeUpdated.getVillage());
        }
    }

    public void init() {

    }
}
