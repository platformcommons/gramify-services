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
@Table(name = "person_education")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PersonEducation extends BaseTransactionalEntity implements DomainEntity<PersonEducation> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "personId must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, updatable = false)
    private Person personId;

    @Column(name = "institute")
    private String institute;

    @Column(name = "year_of_joining")
    private Date yearOfJoining;

    @Column(name = "year_of_passing")
    private Date yearOfPassing;

    @Column(name = "stream")
    private String stream;

    @Column(name = "subject")
    private String subject;

    @Column(name = "education_category")
    private String educationCategory;

    @Column(name = "drop_out_status")
    private String dropOutStatus;

    @Column(name = "drop_out_reason")
    private String dropOutReason;

    @Column(name = "percentage_of_marks")
    private BigDecimal percentageOfMarks;

    @Column(name = "education_type")
    private String educationType;

    @Column(name = "education_board")
    private String educationBoard;

    @Column(name = "remarks")
    private String remarks;

    @Transient
    private boolean isNew;

    @Builder
    public PersonEducation(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Person personId, String institute, Date yearOfJoining, Date yearOfPassing, String stream, String subject, String educationCategory, String dropOutStatus, String dropOutReason, BigDecimal percentageOfMarks, String educationType, String educationBoard, String remarks) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.personId = personId;
        this.institute = institute;
        this.yearOfJoining = yearOfJoining;
        this.yearOfPassing = yearOfPassing;
        this.stream = stream;
        this.subject = subject;
        this.educationCategory = educationCategory;
        this.dropOutStatus = dropOutStatus;
        this.dropOutReason = dropOutReason;
        this.percentageOfMarks = percentageOfMarks;
        this.educationType = educationType;
        this.educationBoard = educationBoard;
        this.remarks = remarks;
    }

    public Date getYear_of_joining() {
        return new Date(yearOfJoining != null ? yearOfJoining.getTime() : new Date().getTime());
    }

    public void setYear_of_joining(Date yearOfJoining) {
        this.yearOfJoining = new Date(yearOfJoining != null ? yearOfJoining.getTime() : new Date().getTime());
    }

    public Date getYear_of_passing() {
        return new Date(yearOfPassing != null ? yearOfPassing.getTime() : new Date().getTime());
    }

    public void setYear_of_passing(Date yearOfPassing) {
        this.yearOfPassing = new Date(yearOfPassing != null ? yearOfPassing.getTime() : new Date().getTime());
    }

    public void init() {
    }

    public void update(PersonEducation toBeUpdated) {
    }

    public void patch(PersonEducation toBeUpdated) {
        if (toBeUpdated.getInstitute() != null) {
            this.setInstitute(toBeUpdated.getInstitute());
        }
        if (toBeUpdated.getYearOfJoining() != null) {
            this.setYearOfJoining(toBeUpdated.getYearOfJoining());
        }
        if (toBeUpdated.getYearOfPassing() != null) {
            this.setYearOfPassing(toBeUpdated.getYearOfPassing());
        }
        if (toBeUpdated.getStream() != null) {
            this.setStream(toBeUpdated.getStream());
        }
        if (toBeUpdated.getSubject() != null) {
            this.setSubject(toBeUpdated.getSubject());
        }
        if (toBeUpdated.getEducationCategory() != null) {
            this.setEducationCategory(toBeUpdated.getEducationCategory());
        }
        if (toBeUpdated.getDropOutStatus() != null) {
            this.setDropOutStatus(toBeUpdated.getDropOutStatus());
        }
        if (toBeUpdated.getDropOutReason() != null) {
            this.setDropOutReason(toBeUpdated.getDropOutReason());
        }
        if (toBeUpdated.getPercentageOfMarks() != null) {
            this.setPercentageOfMarks(toBeUpdated.getPercentageOfMarks());
        }
        if (toBeUpdated.getEducationType() != null) {
            this.setEducationType(toBeUpdated.getEducationType());
        }
        if (toBeUpdated.getEducationBoard() != null) {
            this.setEducationBoard(toBeUpdated.getEducationBoard());
        }
        if (toBeUpdated.getRemarks() != null) {
            this.setRemarks(toBeUpdated.getRemarks());
        }
    }
}