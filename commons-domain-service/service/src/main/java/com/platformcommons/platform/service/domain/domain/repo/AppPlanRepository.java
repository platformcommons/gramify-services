package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.domain.domain.AppPlan;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface AppPlanRepository extends NonMultiTenantBaseRepository<AppPlan, Long> {

    @Query("SELECT a FROM #{#entityName} a WHERE a.app.id= :appId ")
    Page<AppPlan> getAppPlansByAppId(Long appId, Pageable pageable);
}
