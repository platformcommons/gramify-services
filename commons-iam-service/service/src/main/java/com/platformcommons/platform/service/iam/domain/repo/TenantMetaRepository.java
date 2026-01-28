package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.TenantMeta;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TenantMetaRepository extends NonMultiTenantBaseRepository<TenantMeta, Long> {

    @Query("select t from TenantMeta t where t.tenant.tenantLogin = :tenantLogin and t.metaCode = :metaCode")
    Optional<TenantMeta> findByTenantLoginAndMetaCode(String tenantLogin, String metaCode);

    @Query("select t.metaValue from TenantMeta t " +
            "where t.metaCode = :code and t.tenant.id =:tenant")
    Optional<String> getTenantMetaByMetaCode(String code,Long tenant);
}
