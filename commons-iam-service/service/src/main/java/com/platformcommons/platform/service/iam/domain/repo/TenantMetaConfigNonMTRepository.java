package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.TenantMetaConfig;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TenantMetaConfigNonMTRepository extends NonMultiTenantBaseRepository<TenantMetaConfig, Long> {

    @Query("select t from TenantMetaConfig t where t.tenantLogin = ?1")
    Optional<TenantMetaConfig> findByTenantLogin(String tenantLogin);

}