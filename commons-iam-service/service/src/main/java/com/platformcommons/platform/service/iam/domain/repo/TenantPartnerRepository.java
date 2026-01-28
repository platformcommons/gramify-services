package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.TenantPartner;
import org.springframework.data.jpa.repository.Query;

public interface TenantPartnerRepository extends NonMultiTenantBaseRepository<TenantPartner, String> {


    @Query("SELECT tp.id FROM TenantPartner tp " +
            "WHERE tp.parentTenant.id = :parentTenantId " +
            "AND tp.tenant.id = :tenantId ")
    Long getTenantPartner(Long tenantId, Long parentTenantId);
}
