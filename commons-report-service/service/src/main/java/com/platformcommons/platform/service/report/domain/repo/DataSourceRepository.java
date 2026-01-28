package com.platformcommons.platform.service.report.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.report.domain.DataSource;
public interface DataSourceRepository extends NonMultiTenantBaseRepository<DataSource, Long> {
}