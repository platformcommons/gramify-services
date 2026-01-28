package com.platformcommons.platform.service.iam.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tenant")
@BatchSize(size = 20)
public class Tenant extends AuthBaseEntity {

    @Id
    @GenericGenerator(name = "sequence_tenant_id", strategy = "com.platformcommons.platform.service.iam.application.config.TLDIdentifierGenerator")
    @GeneratedValue(generator = "sequence_tenant_id")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(nullable = false, updatable = false, name = "tenant_uuid", unique = true)
    private String tenantUUID;

    @Column(nullable = false, name = "tenant_login", unique = true)
    @Length(min = 3, max = 150)
    private String tenantLogin;

    @Column(name = "tenant_domain", unique = true)
    @Length(min = 3, max = 150)
    private String tenantDomain;

    @Column(nullable = false, name = "tenant_name")
    private String tenantName;

    @Email
    @Column(name = "tenant_email")
    private String email;

    private String mobile;

    private String iconpic;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String website;

    @Pattern(regexp = "^[A-Z-_.]*$")
    private String tenantType;

    private LocalDateTime onBoardedDateTime;

    @Pattern(regexp = "^[A-Z-_.]*$")
    private String tenantStatus;

    @Column(name = "use_mobile_as_login")
    private Boolean useMobileAsLogin;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "slug", unique = true)
    private String slug;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id")
    private Lead lead;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_profile_id")
    private TenantProfile tenantProfile;




    @Builder
    public Tenant(Long id, String tenantUUID, String tenantLogin,
                  String tenantDomain, String tenantName, String email, String mobile,
                  String iconpic, String description, String website, String tenantType,
                  LocalDateTime onBoardedDateTime,String tenantStatus,Boolean useMobileAsLogin,Boolean isActive, String slug,
                  Lead lead, TenantProfile tenantProfile) {
        this.id = id;
        this.tenantUUID = tenantUUID;
        this.tenantLogin = tenantLogin;
        this.tenantDomain = tenantDomain;
        this.tenantName = tenantName;
        this.email = email;
        this.mobile = mobile;
        this.iconpic = iconpic;
        this.description = description;
        this.website = website;
        this.tenantType = tenantType;
        this.onBoardedDateTime = onBoardedDateTime;
        this.tenantStatus = tenantStatus;
        this.useMobileAsLogin = useMobileAsLogin;
        this.isActive = isActive;
        this.slug = slug;
        this.lead = lead;
        this.tenantProfile = tenantProfile;
    }

    /**
     * This constructor is used by JPA to provide selected projections for getting tenant public profile.
     * Don't change sequence of param.
     * See { @link com.platformcommons.platform.service.iam.domain.repo.TenantRepository#findByTenantLoginForPublic() }
     * See { @link com.platformcommons.platform.service.iam.domain.repo.TenantRepository#findByTenantIdForPublic() }
     */
    public Tenant(Long id, String tenantLogin, String tenantName, String iconpic,
                  String description, String website, String tenantDomain,String slug) {
        this.id = id;
        this.tenantLogin = tenantLogin;
        this.tenantName = tenantName;
        this.iconpic = iconpic;
        this.description = description;
        this.website = website;
        this.tenantDomain = tenantDomain;
        this.slug = slug;
    }

    public void init() {
        if (null!=this.id && this.id == 0)
            this.id = null;
        if (this.onBoardedDateTime == null)
            this.onBoardedDateTime = LocalDateTime.now();
        if (this.tenantUUID == null || this.tenantUUID.isEmpty()) {
            this.tenantUUID = tenantUUID(tenantLogin);
        }
        this.slug = this.tenantLogin;
        this.isActive = Boolean.TRUE;
        if(this.tenantProfile == null) {
            this.tenantProfile = new TenantProfile();
        }
        this.tenantProfile.init();
    }

    public void putUpdate(Tenant tenantToBeUpdated) {
        this.tenantName = tenantToBeUpdated.tenantName;
        this.email = tenantToBeUpdated.email;
        this.mobile = tenantToBeUpdated.mobile;
        this.iconpic = tenantToBeUpdated.iconpic;
        this.description = tenantToBeUpdated.description;
        this.website = tenantToBeUpdated.website;
        this.tenantType = tenantToBeUpdated.tenantType;
        this.slug = tenantToBeUpdated.slug;
        if (tenantToBeUpdated.getTenantProfile() != null) {
            if (this.tenantProfile == null) {
                this.tenantProfile = tenantToBeUpdated.getTenantProfile();
                this.tenantProfile.init();
            } else {
                this.tenantProfile.putUpdate(tenantToBeUpdated.getTenantProfile());
            }
        }
    }

    public void patchUpdate(Tenant tenantToBeUpdated) {
        this.tenantName = tenantToBeUpdated.tenantName != null
                ? tenantToBeUpdated.tenantName : this.tenantName;
        this.email = tenantToBeUpdated.email != null
                ? tenantToBeUpdated.email : this.email;
        this.mobile = tenantToBeUpdated.mobile != null
                ? tenantToBeUpdated.mobile : mobile;
        this.iconpic = tenantToBeUpdated.iconpic != null
                ? tenantToBeUpdated.iconpic : iconpic;
        this.description = tenantToBeUpdated.description != null
                ? tenantToBeUpdated.description : description;
        this.website = tenantToBeUpdated.website != null
                ? tenantToBeUpdated.website : website;
        this.tenantType = tenantToBeUpdated.tenantType != null
                ? tenantToBeUpdated.tenantType : tenantType;
        this.slug = tenantToBeUpdated.slug != null
                ? tenantToBeUpdated.slug : slug;
        if (tenantToBeUpdated.getTenantProfile() != null){
            if (this.tenantProfile != null
                    && Objects.equals(this.tenantProfile.getId(), tenantToBeUpdated.getTenantProfile().getId())) {
                this.tenantProfile.patchUpdate(tenantToBeUpdated.getTenantProfile());
            } else if (tenantToBeUpdated.getTenantProfile().getId() == null || tenantToBeUpdated.getTenantProfile().getId().equals(0L)) {
                this.setTenantProfile(tenantToBeUpdated.getTenantProfile());
                this.tenantProfile.init();
            }
        }
    }

    private String tenantUUID(String tenantLogin) {
        return "PC_" + StringUtils.upperCase(tenantLogin) +
                "_" + System.currentTimeMillis();
    }

    public void addDomain(String domain) {
        this.tenantDomain = domain;
    }
}
