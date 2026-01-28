package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.post.domain.UserReadEventLog;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserReadEventLogRepository extends BaseRepository<UserReadEventLog,Long> {


    @Query("SELECT e FROM #{#entityName} e where e.userId = :userId AND e.entityId = :entityId AND e.entityType = :entityType ")
    Optional<UserReadEventLog> findByUserIdEntityIdAndType(Long userId, Long entityId, String entityType);

}
