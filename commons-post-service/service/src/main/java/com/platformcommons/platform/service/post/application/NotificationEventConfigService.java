package com.platformcommons.platform.service.post.application;

import com.platformcommons.platform.service.post.domain.NotificationEventConfig;
import com.platformcommons.platform.service.post.domain.Post;

import java.util.Optional;

public interface NotificationEventConfigService {
    Optional<NotificationEventConfig> getByPostParametersForLoggedInTenant(Post post);

    Optional<NotificationEventConfig> getByPostParametersByDefault(Post post);
}
