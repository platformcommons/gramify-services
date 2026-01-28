package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.domain.domain.Benefit;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;

public interface BenefitRepository extends NonMultiTenantBaseRepository<Benefit, Long> {
}