package com.platformcommons.platform.service.app.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.app.domain.AppConfigScopeMaster;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;

public interface AppConfigScopeMasterRepository extends NonMultiTenantBaseRepository<AppConfigScopeMaster, Long> {
}