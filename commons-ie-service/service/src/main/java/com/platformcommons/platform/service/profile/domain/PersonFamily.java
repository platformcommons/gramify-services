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
@Table(name = "person_family")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PersonFamily extends BaseTransactionalEntity implements DomainEntity<PersonFamily> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "personId must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, updatable = false)
    private Person personId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "relationship")
    private String relationship;

    @Column(name = "primary_contact_number")
    private String primaryContactNumber;

    @Column(name = "primary_mail")
    private String primaryMail;

    @Column(name = "gender")
    private String gender;

    @Column(name = "education_level")
    private String educationLevel;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "average_monthly_income")
    private BigDecimal averageMonthlyIncome;

    @Column(name = "linked_person_id")
    private String linkedPersonId;

    @Transient
    private boolean isNew;

    @Builder
    public PersonFamily(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                        Long id, Person personId, String memberName, Date dateOfBirth, String relationship, String primaryContactNumber,
                        String primaryMail, String gender, String educationLevel, String occupation, BigDecimal averageMonthlyIncome,
                        String linkedPersonId) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.personId = personId;
        this.memberName = memberName;
        this.dateOfBirth = dateOfBirth;
        this.relationship = relationship;
        this.primaryContactNumber = primaryContactNumber;
        this.primaryMail = primaryMail;
        this.gender = gender;
        this.educationLevel = educationLevel;
        this.occupation = occupation;
        this.averageMonthlyIncome = averageMonthlyIncome;
        this.linkedPersonId = linkedPersonId;
    }

    public Date getDate_of_birth() {
        return new Date(dateOfBirth != null ? dateOfBirth.getTime() : new Date().getTime());
    }

    public void setDate_of_birth(Date dateOfBirth) {
        this.dateOfBirth = new Date(dateOfBirth != null ? dateOfBirth.getTime() : new Date().getTime());
    }

    public void init() {
    }

    public void update(PersonFamily toBeUpdated) {
    }

    public void patch(PersonFamily toBeUpdated) {
        if (toBeUpdated.getMemberName() != null) {
            this.setMemberName(toBeUpdated.getMemberName());
        }
        if (toBeUpdated.getDate_of_birth() != null) {
            this.setDateOfBirth(toBeUpdated.getDateOfBirth());
        }
        if (toBeUpdated.getRelationship() != null) {
            this.setRelationship(toBeUpdated.getRelationship());
        }
        if (toBeUpdated.getPrimaryContactNumber() != null) {
            this.setPrimaryContactNumber(toBeUpdated.getPrimaryContactNumber());
        }
        if (toBeUpdated.getPrimaryMail() != null) {
            this.setPrimaryMail(toBeUpdated.getPrimaryMail());
        }
        if (toBeUpdated.getGender() != null) {
            this.setGender(toBeUpdated.getGender());
        }
        if (toBeUpdated.getEducationLevel() != null) {
            this.setEducationLevel(toBeUpdated.getEducationLevel());
        }
        if (toBeUpdated.getOccupation() != null) {
            this.setOccupation(toBeUpdated.getOccupation());
        }
        if (toBeUpdated.getAverageMonthlyIncome() != null) {
            this.setAverageMonthlyIncome(toBeUpdated.getAverageMonthlyIncome());
        }
        if (toBeUpdated.getLinkedPersonId() != null) {
            this.setLinkedPersonId(toBeUpdated.getLinkedPersonId());
        }
    }
}