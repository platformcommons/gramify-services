package com.platformcommons.platform.service.post.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "post_tenant_config",
        uniqueConstraints = {
        @UniqueConstraint(name = "UniqueTenantId", columnNames = {"tenant_id"}),
        @UniqueConstraint(name = "UniqueTenantLogin",columnNames = {"tenant_login"})
        },
        indexes = {
                @Index(name = "IdxTenantId",columnList = "tenant_id"),
                @Index(name = "IdxTenantLogin",columnList = "tenant_login")
        }
)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PostTenantConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "tenantId must not be null")
    @Column(name = "tenant_id")
    private Long tenantId;

    @NotNull(message = "tenantLogin must not be null")
    @Column(name = "tenant_login")
    private String tenantLogin;

    @NotNull(message = "isPublicFeedAllowed must not be null")
    @Column(name = "is_public_feed_allowed")
    private Boolean isPublicFeedAllowed;

    @Column(name = "is_logged_in_feed_allowed")
    private Boolean isLoggedInFeedAllowed;

    @Column(name = "is_user_profile_public")
    private Boolean isUserProfilePublic;

    @Column(name = "is_user_feed_public")
    private Boolean isUserFeedPublic;

    @Column(name = "count_of_post_type_difference_shown")
    private Integer countOfPostTypeDifferenceShown;

    @Column(name = "tenant_logo_url")
    private String tenantLogoUrl;

    @Column(name = "is_response_and_reaction_public")
    private Boolean isResponseAndReactionPublic;

    @Builder
    public PostTenantConfig(Long id, Long tenantId, String tenantLogin, Boolean isPublicFeedAllowed,Integer countOfPostTypeDifferenceShown,
                            Boolean isUserProfilePublic, Boolean isUserFeedPublic,String tenantLogoUrl,Boolean isLoggedInFeedAllowed,
                            Boolean isResponsePublic) {
        this.id = id;
        this.tenantId = tenantId;
        this.tenantLogin = tenantLogin;
        this.isPublicFeedAllowed = isPublicFeedAllowed;
        this.isUserFeedPublic = isUserFeedPublic;
        this.countOfPostTypeDifferenceShown = countOfPostTypeDifferenceShown;
        this.isUserProfilePublic = isUserProfilePublic;
        this.tenantLogoUrl = tenantLogoUrl;
        this.isLoggedInFeedAllowed = isLoggedInFeedAllowed;
        this.isResponseAndReactionPublic = isResponseAndReactionPublic;
    }

    public void init() {
        this.id=0L;
    }

    public void patchUpdate(PostTenantConfig toBeUpdated) {
        if(toBeUpdated.getTenantId() != null) {
            this.tenantId = toBeUpdated.getTenantId();
        }
        if(toBeUpdated.getTenantLogin() != null) {
            this.tenantLogin = toBeUpdated.getTenantLogin();
        }
        if(toBeUpdated.getIsPublicFeedAllowed() != null) {
            this.isPublicFeedAllowed = toBeUpdated.getIsPublicFeedAllowed();
        }
        if(toBeUpdated.getIsLoggedInFeedAllowed() != null) {
            this.isLoggedInFeedAllowed = toBeUpdated.getIsLoggedInFeedAllowed();
        }
        if(toBeUpdated.getIsUserFeedPublic() != null) {
            this.isUserFeedPublic = toBeUpdated.getIsUserFeedPublic();
        }
        if(toBeUpdated.getCountOfPostTypeDifferenceShown() != null) {
            this.countOfPostTypeDifferenceShown = toBeUpdated.getCountOfPostTypeDifferenceShown();
        }
        if(toBeUpdated.getIsUserProfilePublic() != null) {
            this.isUserProfilePublic = toBeUpdated.getIsUserProfilePublic();
        }
        if(toBeUpdated.getTenantLogoUrl() != null) {
            this.tenantLogoUrl = toBeUpdated.getTenantLogoUrl();
        }
        if(toBeUpdated.getIsResponseAndReactionPublic() != null) {
            this.isResponseAndReactionPublic = toBeUpdated.getIsResponseAndReactionPublic();
        }

    }


}
