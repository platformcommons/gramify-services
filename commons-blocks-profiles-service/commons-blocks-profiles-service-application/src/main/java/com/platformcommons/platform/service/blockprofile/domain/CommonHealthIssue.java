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
@Table(name = "common_health_issue")
public class CommonHealthIssue extends  BaseTransactionalEntity implements DomainEntity<CommonHealthIssue> {

    @ManyToOne
    @JoinColumn(name = "villageHealthInfrastructure_id")
    private VillageHealthInfrastructure villageHealthInfrastructure;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(
             name = "common_health_issue"
    )
    private String CommonHealthIssue;






    @Builder
    public CommonHealthIssue(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageHealthInfrastructure villageHealthInfrastructure,
        Long id,
        String CommonHealthIssue
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageHealthInfrastructure = villageHealthInfrastructure;
        this.id = id;
        this.CommonHealthIssue = CommonHealthIssue;


    }


    public void update(CommonHealthIssue toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(CommonHealthIssue toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageHealthInfrastructure() != null) {
            this.setVillageHealthInfrastructure(toBeUpdated.getVillageHealthInfrastructure());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getCommonHealthIssue() != null) {
            this.setCommonHealthIssue(toBeUpdated.getCommonHealthIssue());
        }
    }

    public void init() {

    }
}
