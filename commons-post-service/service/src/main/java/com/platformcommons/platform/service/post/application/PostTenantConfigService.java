package com.platformcommons.platform.service.post.application;

import com.platformcommons.platform.service.post.domain.PostTenantConfig;

public interface PostTenantConfigService {
    PostTenantConfig getForLoggedInTenant();

    PostTenantConfig getPostTenantConfigByTenantLoginV2(String tenantLogin);

    PostTenantConfig getPostTenantConfigByTenantLogin(String tenantLogin);

    PostTenantConfig save(PostTenantConfig postTenantConfig);

    PostTenantConfig patchUpdate(PostTenantConfig postTenantConfig);

    void validateIsPublicFeedAllowed(String tenantLogin);

    PostTenantConfig validateIsResponseAndReactionPublic(String tenantLogin) ;

    void validateIsUserFeedPublic(String tenantLogin);

}
