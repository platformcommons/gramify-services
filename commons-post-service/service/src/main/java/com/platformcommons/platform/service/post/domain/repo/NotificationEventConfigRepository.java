package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.post.domain.NotificationEventConfig;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NotificationEventConfigRepository extends BaseRepository<NotificationEventConfig, Long> {

    @Query(" SELECT n FROM #{#entityName} n WHERE n.postNotificationTemplate.isDefault = 0 " +
            " AND ( :postType IS NULL OR n.postType = :postType ) " +
            " AND ( :postSubType IS NULL OR n.postSubType = :postSubType ) " +
            " AND ( :status IS NULL OR n.postStatus = :status ) " )
    Optional<NotificationEventConfig> findByPostParameters(String postType, String postSubType, String status);
}
