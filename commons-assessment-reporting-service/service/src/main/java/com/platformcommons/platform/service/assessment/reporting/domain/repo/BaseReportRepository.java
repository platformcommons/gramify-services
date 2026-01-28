package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.PlatformBaseRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseReportRepository<T, ID> extends PlatformBaseRepository<T, ID> {

}
