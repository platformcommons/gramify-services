package com.platformcommons.platform.service.profile.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.profile.domain.IEIdCardTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IEIdCardTemplateRepository extends BaseRepository<IEIdCardTemplate, Long> {

    @Query("SELECT t FROM #{#entityName} t WHERE t.templateType = :templateType")
    Optional<IEIdCardTemplate> findByType(@Param("templateType") String templateType);
}
