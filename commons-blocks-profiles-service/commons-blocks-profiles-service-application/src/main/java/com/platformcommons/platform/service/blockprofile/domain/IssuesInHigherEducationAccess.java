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
@Table(name = "issues_in_higher_education_access")
public class IssuesInHigherEducationAccess extends  BaseTransactionalEntity implements DomainEntity<IssuesInHigherEducationAccess> {

    @ManyToOne
    @JoinColumn(name = "villageEducationInfrastructure_id")
    private VillageEducationInfrastructure villageEducationInfrastructure;


    @Column(
             name = "issue_in_higher_education_access"
    )
    private String issueInHigherEducationAccess;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;





    @Builder
    public IssuesInHigherEducationAccess(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        VillageEducationInfrastructure villageEducationInfrastructure,
        String issueInHigherEducationAccess,
        Long id
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.villageEducationInfrastructure = villageEducationInfrastructure;
        this.issueInHigherEducationAccess = issueInHigherEducationAccess;
        this.id = id;


    }


    public void update(IssuesInHigherEducationAccess toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(IssuesInHigherEducationAccess toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getVillageEducationInfrastructure() != null) {
            this.setVillageEducationInfrastructure(toBeUpdated.getVillageEducationInfrastructure());
        }
        if (toBeUpdated.getIssueInHigherEducationAccess() != null) {
            this.setIssueInHigherEducationAccess(toBeUpdated.getIssueInHigherEducationAccess());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
    }

    public void init() {

    }
}
