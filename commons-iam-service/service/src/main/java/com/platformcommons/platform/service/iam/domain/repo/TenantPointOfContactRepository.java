package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.TenantPointOfContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface TenantPointOfContactRepository extends NonMultiTenantBaseRepository<TenantPointOfContact, Long> {

    @Query(" SELECT t FROM TenantPointOfContact t " +
           " JOIN t.tenantProfile tp " +
           " JOIN Tenant tenant ON tenant.tenantProfile = tp " +
           " WHERE tenant.id = :tenantId " +
           " AND t.isActive = 1 " +
           " AND tp.isActive = 1 " +
           " AND tenant.isActive = 1 ")
    Page<TenantPointOfContact> findByTenantId(@Param("tenantId") Long tenantId, Pageable pageable);
}
