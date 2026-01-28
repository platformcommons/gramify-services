package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.domain.domain.Testimonial;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;

public interface TestimonialRepository extends NonMultiTenantBaseRepository<Testimonial, Long> {
}