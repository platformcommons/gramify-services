package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.AppDetail;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppDetailsRepository extends NonMultiTenantBaseRepository<AppDetail,Long> {
    Optional<AppDetail> findByAppKeyAndAppCode(String appKey, String appCode);

    @Query("SELECT a FROM #{#entityName} a WHERE a.appKey = :apiKey AND a.tenantLogin = :tenantLogin AND a.isActive = 1 ")
    Optional<AppDetail> findByApiKeyAndTenantLogin(String apiKey, String tenantLogin);
}
