package com.platformcommons.platform.service.app.domain.repo;

import com.platformcommons.platform.service.app.domain.Enum.Platform;
import com.platformcommons.platform.service.app.domain.PlatformAppVersion;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlatformAppVersionRepository extends NonMultiTenantBaseRepository<PlatformAppVersion, Long> {

    @Query(" SELECT p FROM #{#entityName} p " +
            " WHERE p.id = (SELECT MAX(p2.id) FROM #{#entityName} p2 " +
            " WHERE p2.platform = :platform AND p2.identifier = :identifier)")
    Optional<PlatformAppVersion> findLatestByPlatformAndIdentifier(Platform platform, String identifier);

}