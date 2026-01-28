package com.platformcommons.platform.service.post.application;

import com.platformcommons.platform.service.post.domain.PostNotificationTemplate;

public interface PostNotificationTemplateService {
    Long createPostNotificationTemplate(PostNotificationTemplate postNotificationTemplate,Boolean isDefault);

    PostNotificationTemplate getForLoggedInTenant();

    PostNotificationTemplate patchUpdate(PostNotificationTemplate postNotificationTemplate);
}
