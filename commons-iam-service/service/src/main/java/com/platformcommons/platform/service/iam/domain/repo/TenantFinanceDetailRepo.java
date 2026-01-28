package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.TenantFinanceDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;

public interface TenantFinanceDetailRepo extends NonMultiTenantBaseRepository<TenantFinanceDetail, Long> {

    @Query("DELETE FROM TenantFinanceDetail t WHERE t.id = :#{#entity.id}")
    void delete(@NotNull TenantFinanceDetail entity);

    @Query(" SELECT t FROM TenantFinanceDetail t " +
           " JOIN t.tenantProfile tp " +
           " JOIN Tenant tenant ON tenant.tenantProfile = tp " +
           " WHERE tenant.id = :tenantId " +
           " AND t.isActive = 1 " +
           " AND tp.isActive = 1 " +
           " AND tenant.isActive = 1 ")
    Page<TenantFinanceDetail> findAllByTenantId(@Param("tenantId") Long tenantId, Pageable pageable);
}
