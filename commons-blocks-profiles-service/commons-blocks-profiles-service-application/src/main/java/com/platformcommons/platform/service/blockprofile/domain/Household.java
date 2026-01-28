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
@Table(name = "household")
public class Household extends  BaseTransactionalEntity implements DomainEntity<Household> {

    @Column(
             name = "type"
    )
    private String type;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Column(
         name = "owner_name"
    )
    private String ownerName;

    @Column(
         name = "name"
    )
    private String name;

    @Column(
             name = "geo_code"
    )
    private String geoCode;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue size;


    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;




    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL)
    private Set<HHLoanSource> hhloansourceList;


    @Builder
    public Household(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String type,
        LinkedCode linkedContext,
        String ownerName,
        String name,
        String geoCode,
        Long id,
        UoMValue size,
        LinkedCode linkedActor,
        Set<HHLoanSource> hhloansourceList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.type = type;
        this.linkedContext = linkedContext;
        this.ownerName = ownerName;
        this.name = name;
        this.geoCode = geoCode;
        this.id = id;
        this.size = size;
        this.linkedActor = linkedActor;
        this.hhloansourceList=hhloansourceList;
        this.hhloansourceList.forEach(it->it.setHousehold(this));


    }


    public void update(Household toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(Household toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getType() != null) {
            this.setType(toBeUpdated.getType());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getOwnerName() != null) {
            this.setOwnerName(toBeUpdated.getOwnerName());
        }
        if (toBeUpdated.getName() != null) {
            this.setName(toBeUpdated.getName());
        }
        if (toBeUpdated.getGeoCode() != null) {
            this.setGeoCode(toBeUpdated.getGeoCode());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getSize() != null) {
            this.setSize(toBeUpdated.getSize());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getHhloansourceList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHhloansourceList() != null) {
                this.getHhloansourceList().forEach(current -> {
                    toBeUpdated.getHhloansourceList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHhloansourceList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setHousehold(this);
                    this.getHhloansourceList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
