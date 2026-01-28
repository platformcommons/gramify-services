package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.domain.domain.Feature;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface FeatureRepository extends NonMultiTenantBaseRepository<Feature, Long> {

    @Query("FROM #{#entityName} e WHERE e.code IN( ?1 ) and e.isActive = 1")
    List<Feature> findAllByCode(Iterable<String> codes);

}