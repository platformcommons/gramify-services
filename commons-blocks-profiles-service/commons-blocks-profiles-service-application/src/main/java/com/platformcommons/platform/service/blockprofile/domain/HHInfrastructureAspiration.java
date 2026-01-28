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
@Table(name = "h_h_infrastructure_aspiration")
public class HHInfrastructureAspiration extends  BaseTransactionalEntity implements DomainEntity<HHInfrastructureAspiration> {

    @Column(
             name = "infrastructure_aspiration"
    )
    private String infrastructureAspiration;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "hHInfrastructureAspiration_id")
    private HHInfrastructureAspiration hHInfrastructureAspiration;



    @OneToMany(mappedBy = "hHInfrastructureAspiration", cascade = CascadeType.ALL)
    private Set<HHInfrastructureAspiration> hhinfrastructureaspirationList;


    @Builder
    public HHInfrastructureAspiration(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        String infrastructureAspiration,
        Long id,
        HHInfrastructureAspiration hHInfrastructureAspiration,
        Set<HHInfrastructureAspiration> hhinfrastructureaspirationList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.infrastructureAspiration = infrastructureAspiration;
        this.id = id;
        this.hHInfrastructureAspiration = hHInfrastructureAspiration;
        this.hhinfrastructureaspirationList=hhinfrastructureaspirationList;
        this.hhinfrastructureaspirationList.forEach(it->it.setHHInfrastructureAspiration(this));


    }


    public void update(HHInfrastructureAspiration toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(HHInfrastructureAspiration toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getInfrastructureAspiration() != null) {
            this.setInfrastructureAspiration(toBeUpdated.getInfrastructureAspiration());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getHHInfrastructureAspiration() != null) {
            this.setHHInfrastructureAspiration(toBeUpdated.getHHInfrastructureAspiration());
        }
        if (toBeUpdated.getHhinfrastructureaspirationList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getHhinfrastructureaspirationList() != null) {
                this.getHhinfrastructureaspirationList().forEach(current -> {
                    toBeUpdated.getHhinfrastructureaspirationList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getHhinfrastructureaspirationList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setHHInfrastructureAspiration(this);
                    this.getHhinfrastructureaspirationList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
