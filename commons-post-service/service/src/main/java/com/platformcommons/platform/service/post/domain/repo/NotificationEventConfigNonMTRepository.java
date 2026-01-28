package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.post.domain.NotificationEventConfig;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NotificationEventConfigNonMTRepository extends NonMultiTenantBaseRepository<NotificationEventConfig,Long> {

    @Query(" SELECT n FROM #{#entityName} n WHERE n.postNotificationTemplate.isDefault = 1" +
            " AND ( :postType IS NULL OR n.postType = :postType ) " +
            " AND ( :postSubType IS NULL OR n.postSubType = :postSubType ) " +
            " AND ( :status IS NULL OR n.postStatus = :status ) " +
            " AND n.isActive = 1 " +
            " AND n.postNotificationTemplate.isActive = 1" )
    Optional<NotificationEventConfig> findByPostParametersAndIsDefault(String postType, String postSubType, String status);
}
