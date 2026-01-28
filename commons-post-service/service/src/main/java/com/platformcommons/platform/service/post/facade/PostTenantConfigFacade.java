package com.platformcommons.platform.service.post.facade;

import com.platformcommons.platform.service.post.dto.PostTenantConfigDTO;

public interface PostTenantConfigFacade {
    PostTenantConfigDTO getForLoggedInTenant();

    PostTenantConfigDTO getByTenantLogin(String tenantLogin);

    PostTenantConfigDTO save(PostTenantConfigDTO body);

    PostTenantConfigDTO patchUpdate(PostTenantConfigDTO body);
}
