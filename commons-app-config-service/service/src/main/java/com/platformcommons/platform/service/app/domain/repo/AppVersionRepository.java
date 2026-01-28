package com.platformcommons.platform.service.app.domain.repo;

import com.platformcommons.platform.service.app.domain.App;
import com.platformcommons.platform.service.app.domain.AppVersion;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppVersionRepository extends NonMultiTenantBaseRepository<AppVersion, Long> {

    @Query("From #{#entityName} apv where  apv.appId.code = :appCode and apv.version  = :version ")
    Optional<AppVersion> findByIdAppId_codeAndVersion(String appCode, String version);

    Page<AppVersion> findByAppId(App appId, Pageable pageable);
}