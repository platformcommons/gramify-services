package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "social_media_account")
public class SocialMediaAccount extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<SocialMediaAccount> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "social_media_type")
    private String socialMediaType;

    @Column(name = "social_media_value")
    private String socialMediaValue;

    @Builder
    public SocialMediaAccount(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String socialMediaType, String socialMediaValue) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.socialMediaType = socialMediaType;
        this.socialMediaValue = socialMediaValue;
    }

    public void init(){
        this.id = 0L;
    }

    public void patchUpdate(SocialMediaAccount socialMediaAccount){
        if (socialMediaAccount.getSocialMediaType() != null) {
            this.socialMediaType = socialMediaAccount.getSocialMediaType();
        }
        if (socialMediaAccount.getSocialMediaValue() != null) {
            this.socialMediaValue = socialMediaAccount.getSocialMediaValue();
        }
    }

    public void putUpdate(SocialMediaAccount socialMediaAccount) {
        this.socialMediaType = socialMediaAccount.getSocialMediaType();
        this.socialMediaValue = socialMediaAccount.getSocialMediaValue();
    }
}
