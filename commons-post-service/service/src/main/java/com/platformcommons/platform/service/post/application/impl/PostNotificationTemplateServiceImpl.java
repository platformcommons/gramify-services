package com.platformcommons.platform.service.post.application.impl;

import com.amazonaws.util.Platform;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.post.application.PostNotificationTemplateService;
import com.platformcommons.platform.service.post.domain.PostNotificationTemplate;
import com.platformcommons.platform.service.post.domain.repo.PostNotificationTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostNotificationTemplateServiceImpl implements PostNotificationTemplateService {

    @Autowired
    private PostNotificationTemplateRepository repository;

    @Override
    public Long createPostNotificationTemplate(PostNotificationTemplate postNotificationTemplate,Boolean isDefault) {
        PostNotificationTemplate finalPostNotificationTemplate;
        Optional<PostNotificationTemplate> postNotificationTemplateOptional = getForLoggedInTenantOpt();
        if(postNotificationTemplateOptional.isPresent()) {
            finalPostNotificationTemplate = postNotificationTemplateOptional.get();
            finalPostNotificationTemplate.patchUpdate(postNotificationTemplate);
        }
        else {
            finalPostNotificationTemplate = postNotificationTemplate;
            finalPostNotificationTemplate.init();
            if(finalPostNotificationTemplate.getNotificationEventConfigs() != null) {
                finalPostNotificationTemplate.getNotificationEventConfigs().forEach(it->it.init(finalPostNotificationTemplate));
            }
        }
        finalPostNotificationTemplate.setIsDefault(isDefault);
        return repository.save(finalPostNotificationTemplate).getId();
    }

    @Override
    public PostNotificationTemplate getForLoggedInTenant() {
        return repository.findForLoggedInTenant().orElseThrow(
                ()->new NotFoundException(String.format("Post Notification Config not found for tenant login %s",
                        PlatformSecurityUtil.getCurrentTenantLogin())));
    }

    @Override
    public PostNotificationTemplate patchUpdate(PostNotificationTemplate postNotificationTemplate) {
        PostNotificationTemplate fetchedPostNotificationTemplate = getForLoggedInTenant();
        fetchedPostNotificationTemplate.patchUpdate(postNotificationTemplate);
        return repository.save(fetchedPostNotificationTemplate);
    }


    public Optional<PostNotificationTemplate> getForLoggedInTenantOpt() {
        return repository.findForLoggedInTenant();
    }
}
