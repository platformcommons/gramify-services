package com.platformcommons.platform.service.post.facade.impl;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.post.application.PostNotificationTemplateService;
import com.platformcommons.platform.service.post.domain.PostNotificationTemplate;
import com.platformcommons.platform.service.post.dto.PostNotificationTemplateDTO;
import com.platformcommons.platform.service.post.facade.PostNotificationTemplateFacade;
import com.platformcommons.platform.service.post.facade.assembler.PostNotificationTemplateDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostNotificationTemplateFacadeImpl implements PostNotificationTemplateFacade {

    @Autowired
    private PostNotificationTemplateService service;

    @Autowired
    private PostNotificationTemplateDTOAssembler assembler;

    @Override
    public Long createDefaultPostNotificationTemplate(PostNotificationTemplateDTO body) {
        if(PlatformSecurityUtil.isPlatformAdmin()) {
            return service.createPostNotificationTemplate(assembler.fromDTO(body),Boolean.TRUE);
        }
        else {
            throw new UnAuthorizedAccessException("Logged in user is not platform admin");
        }

    }

    @Override
    public Long createPostNotificationTemplate(PostNotificationTemplateDTO body) {
        if(PlatformSecurityUtil.isTenantAdmin() && !PlatformSecurityUtil.isPlatformAdmin()) {
            return service.createPostNotificationTemplate(assembler.fromDTO(body),Boolean.FALSE);
        }
        else {
            throw new UnAuthorizedAccessException("Logged in user is not admin");
        }
    }

    @Override
    public PostNotificationTemplateDTO getForLoggedInTenant() {
        if(PlatformSecurityUtil.isTenantAdmin() || PlatformSecurityUtil.isPlatformAdmin()) {
            return assembler.toDTO(service.getForLoggedInTenant());
        }
        else {
            throw new UnAuthorizedAccessException("Logged in user is not admin");
        }
    }

    @Override
    public PostNotificationTemplateDTO patchUpdate(PostNotificationTemplateDTO body) {
        if(PlatformSecurityUtil.isTenantAdmin() || PlatformSecurityUtil.isPlatformAdmin()) {
            return assembler.toDTO(service.patchUpdate(assembler.fromDTO(body)));
        }
        else {
            throw new UnAuthorizedAccessException("Logged in user is not admin");
        }
    }
}
