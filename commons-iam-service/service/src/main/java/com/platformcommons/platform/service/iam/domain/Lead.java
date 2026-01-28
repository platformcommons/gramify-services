package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.common.MLText;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity(name = "lead")
@Table(name = "`lead`")
@Setter
public class Lead extends AuthBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "mobile_country_code")
    private String mobileCountryCode;

    @Column(nullable = false, name = "type")
    private String type;

    @Column(nullable = false, name = "activation_status")
    private String activationStatus; //leadStatus

    @Column(nullable = false, name = "lead_key", unique = true)
    private String key;

    @Column(nullable = false, name = "app_context")
    private String appContext;

    @Column(nullable = true, name = "market_context")
    private String marketContext;

    @Column(nullable = false, updatable = false, name = "registered_on")
    private Date registeredOn;

    @Column(updatable = false, name = "activated_on")
    private Date activatedOn;

    @Column(updatable = false, name = "tenant_created_on")
    private Date tenantCreatedOn;

    @Column(name = "user_created_on",updatable = false)
    private Date userCreatedOn;

    @Column(name = "lead_contact_person_name")
    private String leadContactPersonName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "preferred_domain_name")
    private String preferredDomainName;

    @Column(name = "solution_subscription_code")
    private String solutionSubscriptionCode;

    @Column(name = "use_mobile_as_login")
    private Boolean useMobileAsLogin;

    @Column(name = "whatsapp_number")
    private String whatsappNumber;

    @Column(name = "whatsapp_number_country_code")
    private String whatsappNumberCountryCode;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "organisation_type")
    private String organisationType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="organisation_industries",
            joinColumns = @JoinColumn(name =  "lead_id"))
    @Column(name = "industries")
    private Set<String> organisationIndustries;

    @Column(name = "organisation_size")
    private String organisationSize;

    @Column(name = "organisation_logo")
    private String organisationLogo;

    @Column(name = "website")
    private String website;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    @Column(name = "is_mobile_verified")
    private Boolean isMobileVerified;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExtraAttribute> extraAttributes;

    @Transient
    private Boolean isMobileNumberChanged;

    public static final String STATUS_REGISTERED = "REGISTERED";

    public static final String STATUS_ACTIVATED = "ACTIVATED";

    public static final String STATUS_TENANT_CREATED = "TENANT_CREATED";

    public static final String STATUS_USER_CREATED = "USER_CREATED";

    @Builder
    public Lead(Long createdTimestamp, Long lastModifiedTimestamp, Long id, String organizationName, String email, String mobile,
                String type, String activationStatus, String key, String appContext, String marketContext, Date registeredOn,
                Date activatedOn, Date tenantCreatedOn, String leadContactPersonName, String firstName, String lastName,
                String preferredDomainName, String solutionSubscriptionCode, Boolean useMobileAsLogin, Boolean isMobileNumberChanged,
                Date userCreatedOn,Long tenantId,String whatsappNumber,String whatsappNumberCountryCode,String mobileCountryCode,
                String organisationType,Set<String> organisationIndustries,String organisationSize,String organisationLogo,
                String website,Boolean isEmailVerified,Boolean isMobileVerified,Set<ExtraAttribute> extraAttributes) {
        super(createdTimestamp, lastModifiedTimestamp);
        this.id = id;
        this.organizationName = organizationName;
        this.email = email;
        this.mobile = mobile;
        this.type = type;
        this.activationStatus = activationStatus;
        this.key = key;
        this.appContext = appContext;
        this.marketContext = marketContext;
        this.registeredOn = registeredOn;
        this.activatedOn = activatedOn;
        this.tenantCreatedOn = tenantCreatedOn;
        this.leadContactPersonName = leadContactPersonName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferredDomainName = preferredDomainName;
        this.solutionSubscriptionCode = solutionSubscriptionCode;
        this.useMobileAsLogin = useMobileAsLogin;
        this.isMobileNumberChanged = isMobileNumberChanged;
        this.userCreatedOn = userCreatedOn;
        this.tenantId = tenantId;
        this.whatsappNumber = whatsappNumber;
        this.whatsappNumberCountryCode = whatsappNumberCountryCode;
        this.mobileCountryCode = mobileCountryCode;
        this.organisationType = organisationType;
        this.organisationIndustries = organisationIndustries;
        this.organisationSize = organisationSize;
        this.organisationLogo = organisationLogo;
        this.website = website;
        this.isEmailVerified = isEmailVerified;
        this.isMobileVerified = isMobileVerified;
        this.extraAttributes = extraAttributes;

    }

    public void init() {
        this.id = null;
        this.activationStatus = STATUS_REGISTERED;
        this.key = UUID.randomUUID().toString();
        this.registeredOn = new Date();
        if(this.useMobileAsLogin == null) {
            this.useMobileAsLogin = Boolean.FALSE;
        }
        this.isMobileVerified = Boolean.FALSE;
        this.isEmailVerified = Boolean.FALSE;
    }


    public void activate() {
        this.activationStatus = STATUS_ACTIVATED;
        this.activatedOn = new Date();
    }

    public void tenantCreated() {
        this.activationStatus = STATUS_TENANT_CREATED;
        this.tenantCreatedOn = new Date();
    }

    public void userCreated() {
        this.activationStatus = STATUS_USER_CREATED;
        this.userCreatedOn = new Date();
    }

    public void update(String organizationName, String mobile, String leadContactPersonName,
                       Boolean isMobileNumberChanged) {
        this.organizationName = organizationName;
        this.leadContactPersonName = leadContactPersonName;
        if(isMobileNumberChanged!=null && isMobileNumberChanged){
            this.mobile = mobile;
        }
    }

    public void patchUpdate(Lead lead) {
        if(!StringUtils.isBlank(lead.getOrganizationName())) {
            organizationName = lead.getOrganizationName();
        }
        if(!StringUtils.isBlank(lead.getLeadContactPersonName())) {
            leadContactPersonName = lead.getLeadContactPersonName();
        }
        if(!StringUtils.isBlank(lead.getFirstName())) {
            firstName = lead.getFirstName();
        }
        if(!StringUtils.isBlank(lead.getLastName())) {
            lastName = lead.getLastName();
        }
        if(!StringUtils.isBlank(lead.getWhatsappNumber())) {
            whatsappNumber = lead.getWhatsappNumber();
        }
        if(!StringUtils.isBlank(lead.getWhatsappNumberCountryCode())) {
            whatsappNumberCountryCode = lead.getWhatsappNumberCountryCode();
        }
        if(!StringUtils.isBlank(lead.getMobileCountryCode())) {
            mobileCountryCode = lead.getMobileCountryCode();
        }
        if(!StringUtils.isBlank(lead.getOrganisationType())) {
            organisationType = lead.getOrganisationType();
        }
        if(lead.getOrganisationIndustries() != null) {
            organisationIndustries = lead.getOrganisationIndustries();
        }
        if(!StringUtils.isBlank(lead.getOrganisationSize())) {
            organisationSize = lead.getOrganisationSize();
        }
        if(!StringUtils.isBlank(lead.getOrganisationLogo())) {
            organisationLogo = lead.getOrganisationLogo();
        }
        if(!StringUtils.isBlank(lead.getWebsite())) {
            website = lead.getWebsite();
        }
        if(!StringUtils.isBlank(lead.getEmail())) {
            email = lead.getEmail();
            isEmailVerified = Boolean.FALSE;
        }
        if(!StringUtils.isBlank(lead.getMobile())) {
            mobile = lead.getMobile();
            isMobileVerified = Boolean.FALSE;
        }
    }

    public void updateContactVerification(Lead lead) {
        if(lead.getIsMobileVerified() != null) {
            isMobileVerified = lead.getIsMobileVerified();
        }
        if(lead.getIsEmailVerified() != null) {
            isEmailVerified = lead.getIsEmailVerified();
        }
    }
}
