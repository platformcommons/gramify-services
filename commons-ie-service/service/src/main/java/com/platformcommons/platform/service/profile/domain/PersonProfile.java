package com.platformcommons.platform.service.profile.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.Function;
import java.util.stream.Collectors;
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
@Table(name = "person_profile")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PersonProfile extends BaseTransactionalEntity implements DomainEntity<PersonProfile> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "personId must not be null")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id",updatable = false,nullable = false)
    private Person personId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "spouse_name")
    private String spouseName;

    @Column(name = "age")
    private Long age;

    @Column(name = "age_as_on_date")
    private Date ageAsOnDate;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "gender")
    private String gender;

    @Column(name = "religion")
    private String religion;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "total_work_experience")
    private Long totalWorkExperience;

    @Column(name = "category")
    private String category;

    @Column(name = "mother_tongue")
    private String motherTongue;

    @Column(name = "caste")
    private String caste;

    @Column(name = "salutation")
    private String salutation;

    @Column(name = "disability_status")
    private String disabilityStatus;

    @Column(name = "disability_details")
    private String disabilityDetails;

    @Column(name = "life_status")
    private String lifeStatus;

    @Column(name = "average_monthly_income")
    private BigDecimal averageMonthlyIncome;

    @Column(name = "icon_pic_url")
    private String iconPicUrl;

    @Column(name = "cover_pic_url")
    private String coverPicUrl;

    @Column(name = "intro_video_url")
    private String introVideoUrl;

    @Column(name = "headline")
    private String headline;

    @Column(name = "notes")
    private String notes;

    @Column(name = "hobby")
    private String hobby;

    @Transient
    private boolean isNew;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(
            name = "person_profile_meta_data",
            joinColumns = @JoinColumn(name = "person_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "meta_data_id")
    )
    @BatchSize(size = 20)
    private List<MetaData> metaDataList;

    @Builder
    public PersonProfile(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Person personId, String firstName, String middleName, String displayName, String lastName, Long age, Date ageAsOnDate, String bloodGroup, Date dateOfBirth, String maritalStatus, String gender, String religion, String occupation, Long totalWorkExperience, String category, String motherTongue, String caste, String salutation, String disabilityStatus, String disabilityDetails, String lifeStatus, BigDecimal averageMonthlyIncome, String iconPicUrl, String coverPicUrl, String introVideoUrl, String headline, String notes, String hobby, String spouseName, List<MetaData> metaDataList) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.personId = personId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.displayName = displayName;
        this.lastName = lastName;
        this.age = age;
        this.ageAsOnDate = ageAsOnDate;
        this.bloodGroup = bloodGroup;
        this.dateOfBirth = dateOfBirth;
        this.maritalStatus = maritalStatus;
        this.gender = gender;
        this.religion = religion;
        this.occupation = occupation;
        this.totalWorkExperience = totalWorkExperience;
        this.category = category;
        this.motherTongue = motherTongue;
        this.caste = caste;
        this.salutation = salutation;
        this.disabilityStatus = disabilityStatus;
        this.disabilityDetails = disabilityDetails;
        this.lifeStatus = lifeStatus;
        this.averageMonthlyIncome = averageMonthlyIncome;
        this.iconPicUrl = iconPicUrl;
        this.coverPicUrl = coverPicUrl;
        this.introVideoUrl = introVideoUrl;
        this.headline = headline;
        this.notes = notes;
        this.hobby = hobby;
        this.spouseName = spouseName;
        this.metaDataList = metaDataList;
    }

    public Date getAge_as_on_date() {
        return new Date(ageAsOnDate != null ? ageAsOnDate.getTime() : new Date().getTime());
    }

    public void setAge_as_on_date(Date ageAsOnDate) {
        this.ageAsOnDate = new Date(ageAsOnDate != null ? ageAsOnDate.getTime() : new Date().getTime());
    }

    public Date getDate_of_birth() {
        return new Date(dateOfBirth != null ? dateOfBirth.getTime() : new Date().getTime());
    }

    public void setDate_of_birth(Date dateOfBirth) {
        this.dateOfBirth = new Date(dateOfBirth != null ? dateOfBirth.getTime() : new Date().getTime());
    }

    public void init() {
    }

    public void update(PersonProfile toBeUpdated) {
    }

    public void patch(PersonProfile toBeUpdated) {
        if (toBeUpdated.getFirstName() != null) {
            this.setFirstName(toBeUpdated.getFirstName());
        }
        if (toBeUpdated.getMiddleName() != null) {
            this.setMiddleName(toBeUpdated.getMiddleName());
        }
        if (toBeUpdated.getDisplayName() != null) {
            this.setDisplayName(toBeUpdated.getDisplayName());
        }
        if (toBeUpdated.getLastName() != null) {
            this.setLastName(toBeUpdated.getLastName());
        }
        if (toBeUpdated.getAge() != null) {
            this.setAge(toBeUpdated.getAge());
        }
        if (toBeUpdated.getAgeAsOnDate() != null) {
            this.setAgeAsOnDate(toBeUpdated.getAgeAsOnDate());
        }
        if (toBeUpdated.getBloodGroup() != null) {
            this.setBloodGroup(toBeUpdated.getBloodGroup());
        }
        if (toBeUpdated.getDateOfBirth() != null) {
            this.setDateOfBirth(toBeUpdated.getDateOfBirth());
        }
        if (toBeUpdated.getMaritalStatus() != null) {
            this.setMaritalStatus(toBeUpdated.getMaritalStatus());
        }
        if (toBeUpdated.getGender() != null) {
            this.setGender(toBeUpdated.getGender());
        }
        if (toBeUpdated.getReligion() != null) {
            this.setReligion(toBeUpdated.getReligion());
        }
        if (toBeUpdated.getOccupation() != null) {
            this.setOccupation(toBeUpdated.getOccupation());
        }
        if (toBeUpdated.getTotalWorkExperience() != null) {
            this.setTotalWorkExperience(toBeUpdated.getTotalWorkExperience());
        }
        if (toBeUpdated.getCategory() != null) {
            this.setCategory(toBeUpdated.getCategory());
        }
        if (toBeUpdated.getMotherTongue() != null) {
            this.setMotherTongue(toBeUpdated.getMotherTongue());
        }
        if (toBeUpdated.getCaste() != null) {
            this.setCaste(toBeUpdated.getCaste());
        }
        if (toBeUpdated.getSalutation() != null) {
            this.setSalutation(toBeUpdated.getSalutation());
        }
        if (toBeUpdated.getDisabilityStatus() != null) {
            this.setDisabilityStatus(toBeUpdated.getDisabilityStatus());
        }
        if (toBeUpdated.getDisabilityDetails() != null) {
            this.setDisabilityDetails(toBeUpdated.getDisabilityDetails());
        }
        if (toBeUpdated.getLifeStatus() != null) {
            this.setLifeStatus(toBeUpdated.getLifeStatus());
        }
        if (toBeUpdated.getAverageMonthlyIncome() != null) {
            this.setAverageMonthlyIncome(toBeUpdated.getAverageMonthlyIncome());
        }
        if (toBeUpdated.getIconPicUrl() != null) {
            this.setIconPicUrl(toBeUpdated.getIconPicUrl());
        }
        if (toBeUpdated.getCoverPicUrl() != null) {
            this.setCoverPicUrl(toBeUpdated.getCoverPicUrl());
        }
        if (toBeUpdated.getIntroVideoUrl() != null) {
            this.setIntroVideoUrl(toBeUpdated.getIntroVideoUrl());
        }
        if (toBeUpdated.getHeadline() != null) {
            this.setHeadline(toBeUpdated.getHeadline());
        }
        if (toBeUpdated.getNotes() != null) {
            this.setNotes(toBeUpdated.getNotes());
        }
        if (toBeUpdated.getHobby() != null) {
            this.setHobby(toBeUpdated.getHobby());
        }
        if (toBeUpdated.getMetaDataList() != null && !toBeUpdated.getMetaDataList().isEmpty()) {
            this.getMetaDataList().forEach(metaData -> toBeUpdated.getMetaDataList().forEach(metaDataToBeUpdated -> {
                if (metaData.getId().equals(metaDataToBeUpdated.getId())) {
                    metaData.setMetaKey(metaDataToBeUpdated.getMetaKey());
                    metaData.setMetaValue(metaDataToBeUpdated.getMetaValue());
                }
            }));
            toBeUpdated.getMetaDataList().forEach(metaData -> {
                if (metaData.getId() == null || metaData.getId().equals(0L)) {
                    metaData.setId(null);
                    this.getMetaDataList().add(metaData);
                }
            });
        }
    }
}