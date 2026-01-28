package com.platformcommons.platform.service.post.facade;

import com.platformcommons.platform.service.post.domain.PostNotificationTemplate;
import com.platformcommons.platform.service.post.dto.PostNotificationTemplateDTO;

public interface PostNotificationTemplateFacade {
    Long createDefaultPostNotificationTemplate(PostNotificationTemplateDTO body);

    Long createPostNotificationTemplate(PostNotificationTemplateDTO body);

    PostNotificationTemplateDTO getForLoggedInTenant();

    PostNotificationTemplateDTO patchUpdate(PostNotificationTemplateDTO body);
}
