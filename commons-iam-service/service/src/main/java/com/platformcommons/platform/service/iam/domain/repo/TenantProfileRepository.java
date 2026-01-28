package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.TenantProfile;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TenantProfileRepository extends NonMultiTenantBaseRepository<TenantProfile, Long> {

    @Query(" SELECT t.tenantProfile FROM Tenant t " +
            " WHERE t.id = :tenantId " +
            " AND t.isActive = 1 " +
            " AND t.tenantProfile.isActive = 1 ")
    Optional<TenantProfile> findByTenantId(Long tenantId);
}
