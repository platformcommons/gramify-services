package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.UserMap;

import java.util.Set;

public interface UserMapService {

    Long addUserMap(Long tenantPartnerId, Long targetUserId, Long sourceUserId, String metaValue);

    Set<UserMap> getTenantPartnersForLoggedInContext();

    boolean existsForSourceAndTarget(Long parentTenantId, Long sourceUserId, Long tenantId, Long targetUserId);
}
