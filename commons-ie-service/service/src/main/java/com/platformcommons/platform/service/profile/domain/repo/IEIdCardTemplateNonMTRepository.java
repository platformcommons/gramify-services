package com.platformcommons.platform.service.profile.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.profile.domain.IEIdCardTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IEIdCardTemplateNonMTRepository extends NonMultiTenantBaseRepository<IEIdCardTemplate, Long> {

    @Query("SELECT t FROM #{#entityName} t WHERE t.templateType = :templateType")
    Optional<IEIdCardTemplate> findByType(@Param("templateType") String templateType);
}
