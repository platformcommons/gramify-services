package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import com.platformcommons.platform.service.iam.application.utility.PlatformUtil;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tenant_social_media")
public class TenantSocialMedia extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<TenantSocialMedia> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "social_media_account_id")
    private SocialMediaAccount socialMediaAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_profile_id")
    private TenantProfile tenantProfile;

    @Builder
    public TenantSocialMedia(String uuid, Boolean isActive, String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                             Long id, SocialMediaAccount socialMediaAccount, TenantProfile tenantProfile) {
        super(uuid, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.socialMediaAccount = socialMediaAccount;
        this.tenantProfile = tenantProfile;
    }

    public void init(TenantProfile tenantProfile){
        this.id = 0L;
        if (this.socialMediaAccount != null) {
            this.socialMediaAccount.init();
        }
        this.tenantProfile = tenantProfile;
    }

    public void patchUpdate(TenantSocialMedia toBeUpdated) {
        if (toBeUpdated.getSocialMediaAccount() != null) {
            if (this.socialMediaAccount != null
                    && this.socialMediaAccount.getId().equals(toBeUpdated.getSocialMediaAccount().getId())) {
                this.socialMediaAccount.patchUpdate(toBeUpdated.getSocialMediaAccount());
            } else if (toBeUpdated.getSocialMediaAccount().getId() == null || toBeUpdated.getSocialMediaAccount().getId().equals(0L)) {
                this.setSocialMediaAccount(toBeUpdated.getSocialMediaAccount());
                this.socialMediaAccount.init();
            }
        }
        PlatformUtil.deactivateAnObject(this,toBeUpdated.getIsActive(),toBeUpdated.getInactiveReason());
    }

    public void putUpdate(TenantSocialMedia socialMedia) {
        if (socialMedia.getSocialMediaAccount() != null) {
            if (this.socialMediaAccount != null
                    && this.socialMediaAccount.getId().equals(socialMedia.getSocialMediaAccount().getId())) {
                this.socialMediaAccount.putUpdate(socialMedia.getSocialMediaAccount());
            } else if (socialMedia.getSocialMediaAccount().getId() == null || socialMedia.getSocialMediaAccount().getId().equals(0L)) {
                this.setSocialMediaAccount(socialMedia.getSocialMediaAccount());
                this.socialMediaAccount.init();
            }
        }
        PlatformUtil.deactivateAnObject(this,socialMedia.getIsActive(),socialMedia.getInactiveReason());
    }
}
