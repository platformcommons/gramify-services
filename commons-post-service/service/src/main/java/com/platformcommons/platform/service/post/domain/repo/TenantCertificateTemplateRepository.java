package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.post.domain.TenantCertificateTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TenantCertificateTemplateRepository extends BaseRepository<TenantCertificateTemplate,Long> {

    @Query(" SELECT tc FROM #{#entityName} tc WHERE tc.code = :code ")
    Optional<TenantCertificateTemplate> findByCode(String code);
}
