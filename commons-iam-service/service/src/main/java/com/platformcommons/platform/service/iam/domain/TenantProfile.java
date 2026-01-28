package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import com.platformcommons.platform.service.iam.application.utility.PlatformUtil;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@BatchSize(size = 100)
@Table(name = "tenant_profile")
public class TenantProfile extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<TenantProfile> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type")
    private String type;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="tenant_profile_industries",
            joinColumns = @JoinColumn(name =  "tenant_profile_id"))
    @Column(name = "industries")
    private Set<String> industries;

    @Column(name = "size")
    private String size;

    @Column(name = "headline")
    private String headline;

    @Column(name = "year_of_establishment")
    private Integer yearOfEstablishment;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "legal_name")
    private String legalName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tenantProfile", cascade = CascadeType.ALL)
    private List<TenantAddress> tenantAddresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tenantProfile", cascade = CascadeType.ALL)
    private List<TenantSocialMedia> tenantSocialMedia;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tenant_profile_attachments",
            joinColumns = @JoinColumn(name = "tenant_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private List<Attachment> attachments;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(
            name = "tenant_profile_extra_attributes",
            joinColumns = @JoinColumn(name = "tenant_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "extra_attribute_id")
    )
    private Set<ExtraAttribute> extraAttributes;

    @Builder
    public TenantProfile(String uuid, Boolean isActive, String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                         Long id, String type, Set<String> industries, String size, String headline, Integer yearOfEstablishment,
                         String coverImage, String panNumber, String registrationNumber, String legalName, List<TenantAddress> tenantAddresses,
                         List<TenantSocialMedia> tenantSocialMedia, List<Attachment> attachments,
                         Set<ExtraAttribute> extraAttributes) {
        super(uuid, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.type = type;
        this.industries = industries;
        this.size = size;
        this.headline = headline;
        this.yearOfEstablishment = yearOfEstablishment;
        this.coverImage = coverImage;
        this.panNumber = panNumber;
        this.registrationNumber = registrationNumber;
        this.legalName = legalName;
        this.tenantAddresses = tenantAddresses;
        this.tenantSocialMedia = tenantSocialMedia;
        this.attachments = attachments;
        this.extraAttributes = extraAttributes;
    }

    public void init() {
        if (null!=this.id && this.id == 0)
            this.id = null;
        if (this.tenantAddresses != null) {
            this.tenantAddresses.forEach(it -> it.init(this));
        }
        if (this.tenantSocialMedia != null) {
            this.tenantSocialMedia.forEach(it -> it.init(this));
        }
        if (this.extraAttributes != null) {
            this.extraAttributes.forEach(ExtraAttribute::init);
        }
    }

    public void putUpdate(TenantProfile toBeUpdated) {
        this.type = toBeUpdated.getType();
        this.industries = toBeUpdated.getIndustries();
        this.size = toBeUpdated.getSize();
        this.headline = toBeUpdated.getHeadline();
        this.yearOfEstablishment = toBeUpdated.getYearOfEstablishment();
        this.coverImage = toBeUpdated.getCoverImage();
        this.panNumber = toBeUpdated.getPanNumber();
        this.registrationNumber = toBeUpdated.getRegistrationNumber();
        this.legalName = toBeUpdated.getLegalName();
        PlatformUtil.deactivateAnObject(this,toBeUpdated.getIsActive(),toBeUpdated.getInactiveReason());
        if (toBeUpdated.getTenantAddresses() != null && !toBeUpdated.getTenantAddresses().isEmpty()) {
            this.getTenantAddresses().forEach(tenantAddress -> {
                toBeUpdated.getTenantAddresses().forEach(address -> {
                    if (tenantAddress.getId().equals(address.getId())) {
                        tenantAddress.putUpdate(address);
                    }
                });
            });
            toBeUpdated.getTenantAddresses().forEach(toBeSavedtenantAddress -> {
                if(toBeSavedtenantAddress.getId() == null || toBeSavedtenantAddress.getId().equals(0L)) {
                    toBeSavedtenantAddress.init(this);
                    this.getTenantAddresses().add(toBeSavedtenantAddress);
                }
            });
        }
        if (toBeUpdated.getTenantSocialMedia() != null && !toBeUpdated.getTenantSocialMedia().isEmpty()) {
            this.getTenantSocialMedia().forEach(tenantSocialMedia -> {
                toBeUpdated.getTenantSocialMedia().forEach(socialMedia -> {
                    if (tenantSocialMedia.getId().equals(socialMedia.getId())) {
                        tenantSocialMedia.putUpdate(socialMedia);
                    }
                });
            });
            toBeUpdated.getTenantSocialMedia().forEach(toBeSavedTenantSocialMedia -> {
                if(toBeSavedTenantSocialMedia.getId() == null || toBeSavedTenantSocialMedia.getId().equals(0L)) {
                    toBeSavedTenantSocialMedia.init(this);
                    this.getTenantSocialMedia().add(toBeSavedTenantSocialMedia);
                }
            });
        }

        if (toBeUpdated.getExtraAttributes() != null){
            this.getExtraAttributes().forEach(extraAttribute -> {
                toBeUpdated.getExtraAttributes().forEach(toBeUpdatedExtraAttribute -> {
                    if (extraAttribute.getId().equals(toBeUpdatedExtraAttribute.getId())) {
                        extraAttribute.putUpdate(toBeUpdatedExtraAttribute);
                    }
                });
            });
            toBeUpdated.getExtraAttributes().forEach(toBeSavedExtraAttribute -> {
                if(toBeSavedExtraAttribute.getId() == null || toBeSavedExtraAttribute.getId().equals(0L)) {
                    toBeSavedExtraAttribute.init();
                    this.getExtraAttributes().add(toBeSavedExtraAttribute);
                }
            });
        }
    }

    public void patchUpdate(TenantProfile toBeUpdated) {
        if (toBeUpdated.getType() != null) {
            this.setType(toBeUpdated.getType());
        }
        if (toBeUpdated.getIndustries() != null) {
            this.setIndustries(toBeUpdated.getIndustries());
        }
        if (toBeUpdated.getSize() != null) {
            this.setSize(toBeUpdated.getSize());
        }
        if (toBeUpdated.getHeadline() != null) {
            this.setHeadline(toBeUpdated.getHeadline());
        }
        if (toBeUpdated.getYearOfEstablishment() != null) {
            this.setYearOfEstablishment(toBeUpdated.getYearOfEstablishment());
        }
        if (toBeUpdated.getCoverImage() != null) {
            this.setCoverImage(toBeUpdated.getCoverImage());
        }
        if (toBeUpdated.getPanNumber() != null){
            this.setPanNumber(toBeUpdated.getPanNumber());
        }
        if (toBeUpdated.getRegistrationNumber() != null){
            this.setRegistrationNumber(toBeUpdated.getRegistrationNumber());
        }
        if (toBeUpdated.getLegalName() != null){
            this.setLegalName(toBeUpdated.getLegalName());
        }
        PlatformUtil.deactivateAnObject(this,toBeUpdated.getIsActive(),toBeUpdated.getInactiveReason());
        if (toBeUpdated.getTenantAddresses() != null && !toBeUpdated.getTenantAddresses().isEmpty()) {
            this.getTenantAddresses().forEach(tenantAddress -> {
                toBeUpdated.getTenantAddresses().forEach(address -> {
                    if (tenantAddress.getId().equals(address.getId())) {
                        tenantAddress.patchUpdate(address);
                    }
                });
            });
            toBeUpdated.getTenantAddresses().forEach(toBeSavedtenantAddress -> {
                if(toBeSavedtenantAddress.getId() == null || toBeSavedtenantAddress.getId().equals(0L)) {
                    toBeSavedtenantAddress.init(this);
                    this.getTenantAddresses().add(toBeSavedtenantAddress);
                }
            });
        }
        if (toBeUpdated.getTenantSocialMedia() != null && !toBeUpdated.getTenantSocialMedia().isEmpty()) {
            this.getTenantSocialMedia().forEach(tenantSocialMedia -> {
                toBeUpdated.getTenantSocialMedia().forEach(socialMedia -> {
                    if (tenantSocialMedia.getId().equals(socialMedia.getId())) {
                        tenantSocialMedia.patchUpdate(socialMedia);
                    }
                });
            });
            toBeUpdated.getTenantSocialMedia().forEach(toBeSavedTenantSocialMedia -> {
                if(toBeSavedTenantSocialMedia.getId() == null || toBeSavedTenantSocialMedia.getId().equals(0L)) {
                    toBeSavedTenantSocialMedia.init(this);
                    this.getTenantSocialMedia().add(toBeSavedTenantSocialMedia);
                }
            });
        }
        if (toBeUpdated.getExtraAttributes() != null){
            this.getExtraAttributes().forEach(extraAttribute -> {
                toBeUpdated.getExtraAttributes().forEach(toBeUpdatedExtraAttribute -> {
                    if (extraAttribute.getId().equals(toBeUpdatedExtraAttribute.getId())) {
                        extraAttribute.patchUpdate(toBeUpdatedExtraAttribute);
                    }
                });
            });
            toBeUpdated.getExtraAttributes().forEach(toBeSavedExtraAttribute -> {
                if(toBeSavedExtraAttribute.getId() == null || toBeSavedExtraAttribute.getId().equals(0L)) {
                    toBeSavedExtraAttribute.init();
                    this.getExtraAttributes().add(toBeSavedExtraAttribute);
                }
            });
        }
    }

}
