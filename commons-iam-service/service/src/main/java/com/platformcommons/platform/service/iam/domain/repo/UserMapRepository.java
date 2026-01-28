package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.UserMap;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UserMapRepository extends NonMultiTenantBaseRepository<UserMap, String> {

    @Query("FROM UserMap um " +
            "WHERE um.tenantPartner.parentTenant.id = :parentTenantId " +
            "AND um.sourceUser.id = :sourceUserId")
    Set<UserMap> getTenantPartnersForLoggedInContext(Long parentTenantId, Long sourceUserId);


    @Query("SELECT COUNT(um) > 0 FROM UserMap um " +
            "WHERE um.tenantPartner.parentTenant.id = :parentTenantId " +
            "AND um.tenantPartner.tenant.id = :tenantId " +
            "AND um.targetUser.id = :targetUserId " +
            "AND um.sourceUser.id = :sourceUserId")
    boolean existsForSourceAndTarget(Long parentTenantId, Long sourceUserId, Long tenantId, Long targetUserId);
}
