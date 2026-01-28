package com.platformcommons.platform.service.profile.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Transient;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
@Table(name = "person_profession")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PersonProfession extends BaseTransactionalEntity implements DomainEntity<PersonProfession> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "personId must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, updatable = false)
    private Person personId;

    @Column(name = "work_type")
    private String workType;

    @Column(name = "work_details")
    private String workDetails;

    @Column(name = "designation")
    private String designation;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "employer_details")
    private String employerDetails;

    @Column(name = "employer_type")
    private String employerType;

    @Column(name = "employer_name")
    private String employerName;

    @Column(name = "job_sector")
    private String jobSector;

    @Column(name = "reason_for_leaving")
    private String reasonForLeaving;

    @Transient
    private boolean isNew;

    @Builder
    public PersonProfession(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Person personId, String workType, String workDetails, String designation, Date fromDate, Date toDate, String employeeId, String employerDetails, String employerType, String employerName, String jobSector, String reasonForLeaving) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.personId = personId;
        this.workType = workType;
        this.workDetails = workDetails;
        this.designation = designation;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.employeeId = employeeId;
        this.employerDetails = employerDetails;
        this.employerType = employerType;
        this.employerName = employerName;
        this.jobSector = jobSector;
        this.reasonForLeaving = reasonForLeaving;
    }

    public Date getFrom_date() {
        return new Date(fromDate != null ? fromDate.getTime() : new Date().getTime());
    }

    public void setFrom_date(Date fromDate) {
        this.fromDate = new Date(fromDate != null ? fromDate.getTime() : new Date().getTime());
    }

    public Date getTo_date() {
        return new Date(toDate != null ? toDate.getTime() : new Date().getTime());
    }

    public void setTo_date(Date toDate) {
        this.toDate = new Date(toDate != null ? toDate.getTime() : new Date().getTime());
    }

    public void init() {
    }

    public void update(PersonProfession toBeUpdated) {
    }
}