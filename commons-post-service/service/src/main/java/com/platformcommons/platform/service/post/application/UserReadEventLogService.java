package com.platformcommons.platform.service.post.application;

import com.platformcommons.platform.service.post.domain.UserReadEventLog;

public interface UserReadEventLogService {

    Long save(UserReadEventLog userReadEventLog);

    Long validateLogCreatedElseSave(Long userId, Long entityId, String entityType);

}
