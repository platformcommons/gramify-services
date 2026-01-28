package com.platformcommons.platform.service.post.facade.impl;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.post.application.PostActorCertificateService;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.dto.PostActorCertificateDTO;
import com.platformcommons.platform.service.post.facade.PostActorCertificateFacade;
import com.platformcommons.platform.service.post.facade.assembler.PostActorCertificateDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Transactional
@Component
public class PostActorCertificateFacadeImpl implements PostActorCertificateFacade {

    @Autowired
    private PostActorCertificateService service;

    @Autowired
    private PostActorCertificateDTOAssembler assembler;

    @Override
    public Map<Long, Boolean> getExistenceByPostIds(Set<Long> postIds) {
        return service.getExistenceByPostIds(postIds);
    }

    @Override
    public PostActorCertificateDTO getByPostId(Long postId) {
        return  assembler.toDTO(service.getByPostId(postId));
    }

    @Override
    public PostActorCertificateDTO save(PostActorCertificateDTO postActorCertificateDTO, Long postId, Boolean sendCertificateMail) {
        if(PlatformSecurityUtil.isTenantAdmin() || PlatformSecurityUtil.hasRole(PostConstant.PROLE_POST_ADMIN)) {
            return  assembler.toDTO(service.save(assembler.fromDTO(postActorCertificateDTO),postId,sendCertificateMail));
        }
        else {
            throw new UnAuthorizedAccessException("Unauthorized to patch a post");
        }
    }
}
