package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.TenantMetaMaster;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TenantMetaMasterRepository extends NonMultiTenantBaseRepository<TenantMetaMaster, Long> {
    @Query("select t from TenantMetaMaster t where t.appContext = ?1 and t.metaCode = ?2")
    Optional<TenantMetaMaster> findByAppContextAndMetaCode(String appContext, String metaCode);

    List<TenantMetaMaster> findByAppContext(String appContext);

    @Query("select t from TenantMetaMaster t " +
            " where t.metaCode = :code ")
    Optional<TenantMetaMaster> getTenantMetaMasterByMetaCode(String code);
}
