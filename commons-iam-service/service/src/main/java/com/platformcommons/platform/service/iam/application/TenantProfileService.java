package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.TenantProfile;

public interface TenantProfileService {
    TenantProfile getOrCreateByTenantId(Long tenantId);
}
