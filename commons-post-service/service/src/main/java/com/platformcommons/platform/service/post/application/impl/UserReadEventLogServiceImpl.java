package com.platformcommons.platform.service.post.application.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.post.application.UserReadEventLogService;
import com.platformcommons.platform.service.post.domain.UserReadEventLog;
import com.platformcommons.platform.service.post.domain.repo.UserReadEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserReadEventLogServiceImpl implements UserReadEventLogService {

    @Autowired
    private UserReadEventLogRepository repository;


    @Override
    public Long save(UserReadEventLog userReadEventLog) {
        userReadEventLog.init();
        return repository.save(userReadEventLog).getUserId();
    }

    @Override
    public Long validateLogCreatedElseSave(Long userId, Long entityId, String entityType) {
        Optional<UserReadEventLog> optionalLog = repository.findByUserIdEntityIdAndType(userId,entityId,entityType);
        if (optionalLog.isPresent()) {
            return optionalLog.get().getId();
        }
        else {
            UserReadEventLog userReadEventLog = UserReadEventLog.builder()
                    .userId(userId)
                    .entityId(entityId)
                    .entityType(entityType)
                    .build();
            return save(userReadEventLog);
        }
    }
}
