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
@Table(name = "person")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Person extends BaseTransactionalEntity implements DomainEntity<Person> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "has_organization")
    private Boolean hasOrganization;

    @Transient
    private boolean isNew;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<Address> addressList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<Contact> contactList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<PersonBankDetail> personBankDetailList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<PersonEducation> personEducationList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<PersonFamily> personFamilyList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<PersonIdentifier> personIdentifierList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<PersonInsurance> personInsuranceList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<PersonInterest> personInterestList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<PersonLanguage> personLanguageList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<PersonOrganization> personOrganizationList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<PersonPoc> personPocList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<PersonProfession> personProfessionList;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "personId")
    private PersonProfile personProfile;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personId")
    @BatchSize(size = 20)
    private Set<PersonSkill> personSkillList;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "personId")
    private PersonSocialMedia personSocialMedia;

    @Builder
    public Person(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String entityType, Long entityId, Boolean hasOrganization, Set<Address> addressList, Set<Contact> contactList, Set<PersonBankDetail> personBankDetailList, Set<PersonEducation> personEducationList, Set<PersonFamily> personFamilyList, Set<PersonIdentifier> personIdentifierList, Set<PersonInsurance> personInsuranceList, Set<PersonInterest> personInterestList, Set<PersonLanguage> personLanguageList, Set<PersonOrganization> personOrganizationList, Set<PersonPoc> personPocList, Set<PersonProfession> personProfessionList, PersonProfile personProfile, Set<PersonSkill> personSkillList, PersonSocialMedia personSocialMedia) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.entityType = entityType;
        this.entityId = entityId;
        this.hasOrganization = hasOrganization;
        this.addressList = addressList;
        this.contactList = contactList;
        this.personBankDetailList = personBankDetailList;
        this.personEducationList = personEducationList;
        this.personFamilyList = personFamilyList;
        this.personIdentifierList = personIdentifierList;
        this.personInsuranceList = personInsuranceList;
        this.personInterestList = personInterestList;
        this.personLanguageList = personLanguageList;
        this.personOrganizationList = personOrganizationList;
        this.personPocList = personPocList;
        this.personProfessionList = personProfessionList;
        this.personProfile = personProfile;
        this.personSkillList = personSkillList;
        this.personSocialMedia = personSocialMedia;
        if (null != addressList) {
            this.addressList.forEach(it -> it.setPersonId(this));
        }
        if (null != contactList) {
            this.contactList.forEach(it -> it.setPersonId(this));
        }
        if (null != personBankDetailList) {
            this.personBankDetailList.forEach(it -> it.setPersonId(this));
        }
        if (null != personEducationList) {
            this.personEducationList.forEach(it -> it.setPersonId(this));
        }
        if (null != personFamilyList) {
            this.personFamilyList.forEach(it -> it.setPersonId(this));
        }
        if (null != personIdentifierList) {
            this.personIdentifierList.forEach(it -> it.setPersonId(this));
        }
        if (null != personInsuranceList) {
            this.personInsuranceList.forEach(it -> it.setPersonId(this));
        }
        if (null != personInterestList) {
            this.personInterestList.forEach(it -> it.setPersonId(this));
        }
        if (null != personLanguageList) {
            this.personLanguageList.forEach(it -> it.setPersonId(this));
        }
        if (null != personOrganizationList) {
            this.personOrganizationList.forEach(it -> it.setPersonId(this));
        }
        if (null != personPocList) {
            this.personPocList.forEach(it -> it.setPersonId(this));
        }
        if (null != personProfessionList) {
            this.personProfessionList.forEach(it -> it.setPersonId(this));
        }
        if (null != personProfile) {
            this.personProfile.setPersonId(this);
        }
        if (null != personSkillList) {
            this.personSkillList.forEach(it -> it.setPersonId(this));
        }
        if (null != personSocialMedia) {
            this.personSocialMedia.setPersonId(this);
        }
    }

    public void init() {
    }

    public void update(Person toBeUpdated) {
        if (toBeUpdated.getEntityType() != null) {
            this.setEntityType(toBeUpdated.getEntityType());
        }
        if (toBeUpdated.getEntityId() != null) {
            this.setEntityId(toBeUpdated.getEntityId());
        }
        if (toBeUpdated.getHasOrganization() != null) {
            this.setHasOrganization(toBeUpdated.getHasOrganization());
        }

    }

    public void patch(Person toBeUpdated) {
        if (toBeUpdated.getEntityType() != null) {
            this.setEntityType(toBeUpdated.getEntityType());
        }
        if (toBeUpdated.getEntityId() != null) {
            this.setEntityId(toBeUpdated.getEntityId());
        }
        if (toBeUpdated.getHasOrganization() != null) {
            this.setHasOrganization(toBeUpdated.getHasOrganization());
        }
        if (toBeUpdated.getPersonEducationList()!= null && !toBeUpdated.getPersonEducationList().isEmpty()) {
            this.getPersonEducationList().forEach(personEducation -> toBeUpdated.getPersonEducationList().forEach(personEducationToBeUpdated->{
                if(personEducation.getId().equals(personEducationToBeUpdated.getId())){
                    personEducation.patch(personEducationToBeUpdated);
                }
            }));
            toBeUpdated.getPersonEducationList().forEach(personEducation-> {
                if (personEducation.getId().equals(0L)) {
                    personEducation.setId(null);
                    this.getPersonEducationList().add(personEducation);
                }
            });
        }
        if (toBeUpdated.getContactList()!= null && !toBeUpdated.getContactList().isEmpty()) {
            this.getContactList().forEach(contact -> toBeUpdated.getContactList().forEach(contactToBeUpdated->{
                if(contact.getId().equals(contactToBeUpdated.getId())){
                    contact.patch(contactToBeUpdated);
                }
            }));
            toBeUpdated.getContactList().forEach(contact-> {
                if (contact.getId().equals(0L)) {
                    contact.setId(null);
                    this.getContactList().add(contact);
                }
            });
        }
        if (toBeUpdated.getAddressList() != null && !toBeUpdated.getAddressList().isEmpty()) {
            this.getAddressList().forEach(address -> toBeUpdated.getAddressList().forEach(addressToBeUpdated->{
                if(address.getId().equals(addressToBeUpdated.getId())){
                    address.patch(addressToBeUpdated);
                }
            }));
            toBeUpdated.getAddressList().forEach(address -> {
                if (address.getId().equals(0L)) {
                    address.setId(null);
                    this.getAddressList().add(address);
                }
            });
        }
        if (toBeUpdated.getPersonFamilyList()!= null && !toBeUpdated.getPersonFamilyList().isEmpty()) {
            this.getPersonFamilyList().forEach(personFamily -> toBeUpdated.getPersonFamilyList().forEach(personFamilyToBeUpdated->{
                if(personFamily.getId().equals(personFamilyToBeUpdated.getId())){
                    personFamily.patch(personFamilyToBeUpdated);
                }
            }));
            toBeUpdated.getPersonFamilyList().forEach(personFamily-> {
                if (personFamily.getId().equals(0L)) {
                    personFamily.setId(null);
                    this.getPersonFamilyList().add(personFamily);
                }
            });
        }
        if (toBeUpdated.getPersonProfile() != null) {
            this.getPersonProfile().patch(toBeUpdated.getPersonProfile());
        }
    }
}