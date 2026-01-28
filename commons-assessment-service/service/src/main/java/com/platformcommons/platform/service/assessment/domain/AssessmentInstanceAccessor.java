package com.platformcommons.platform.service.assessment.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "assessmentinstanceaccessor")
@NoArgsConstructor
public class AssessmentInstanceAccessor extends BaseTransactionalEntity implements DomainEntity<AssessmentInstanceAccessor> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    private AssessmentInstance assessmentInstance;
    @Builder
    public AssessmentInstanceAccessor(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,String login,String firstName, String lastName,AssessmentInstance assessmentInstance) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.assessmentInstance = assessmentInstance;
    }

    public String getUserDisplayName(){
        return this.firstName+(this.lastName==null?"":this.lastName);
    }


}
