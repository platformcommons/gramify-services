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
@Table(name = "person_organization")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PersonOrganization extends BaseTransactionalEntity implements DomainEntity<PersonOrganization> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "personId must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, updatable = false)
    private Person personId;

    @Column(name = "organization")
    private String organization;

    @Column(name = "org_type_code")
    private String orgTypeCode;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_type_code")
    private String entityTypeCode;

    @Column(name = "gst_availablility")
    private Boolean gstAvailablility;

    @Column(name = "is_msme")
    private Boolean isMsme;

    @Column(name = "msme_type_code")
    private String msmeTypeCode;

    @Column(name = "msme_uam_number")
    private String msmeUamNumber;

    @Column(name = "has_gst")
    private Boolean hasGst;

    @Column(name = "has_12a")
    private Boolean has12a;

    @Column(name = "has_80g")
    private Boolean has80g;

    @Column(name = "has_export_experience")
    private Boolean hasExportExperience;

    @Column(name = "has_fcra")
    private Boolean hasFcra;

    @Column(name = "year_of_registration")
    private Long yearOfRegistration;

    @Column(name = "shipping_info")
    private String shippingInfo;

    @Column(name = "lisence")
    private String lisence;

    @Transient
    private boolean isNew;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "personOrganizationId")
    private OrganizationFinance organizationFinance;

    @Builder
    public PersonOrganization(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Person personId, String organization, String orgTypeCode, String entityName, String entityTypeCode, Boolean gstAvailablility, Boolean isMsme, String msmeTypeCode, String msmeUamNumber, Boolean hasGst, Boolean has12a, Boolean has80g, Boolean hasExportExperience, Boolean hasFcra, Long yearOfRegistration, String shippingInfo, String lisence, OrganizationFinance organizationFinance) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.personId = personId;
        this.organization = organization;
        this.orgTypeCode = orgTypeCode;
        this.entityName = entityName;
        this.entityTypeCode = entityTypeCode;
        this.gstAvailablility = gstAvailablility;
        this.isMsme = isMsme;
        this.msmeTypeCode = msmeTypeCode;
        this.msmeUamNumber = msmeUamNumber;
        this.hasGst = hasGst;
        this.has12a = has12a;
        this.has80g = has80g;
        this.hasExportExperience = hasExportExperience;
        this.hasFcra = hasFcra;
        this.yearOfRegistration = yearOfRegistration;
        this.shippingInfo = shippingInfo;
        this.lisence = lisence;
        this.organizationFinance = organizationFinance;
        if (null != organizationFinance) {
            this.organizationFinance.setPersonOrganizationId(this);
        }
    }

    public void init() {
    }

    public void update(PersonOrganization toBeUpdated) {
    }
}