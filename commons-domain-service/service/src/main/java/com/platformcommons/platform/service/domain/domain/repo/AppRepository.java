package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.domain.domain.App;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppRepository extends NonMultiTenantBaseRepository<App, Long> {

    @Query("SELECT COUNT(a)>0 FROM #{#entityName} a WHERE a.id = :appId ")
    boolean existsById(Long appId);

    @Query("FROM #{#entityName} a WHERE a.slug = :slug and a.isActive = 1 ")
    Optional<App> findBySlug(String slug);
}