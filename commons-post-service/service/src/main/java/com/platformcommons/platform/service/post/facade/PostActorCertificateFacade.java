package com.platformcommons.platform.service.post.facade;

import com.platformcommons.platform.service.post.dto.PostActorCertificateDTO;

import java.util.Map;
import java.util.Set;

public interface PostActorCertificateFacade {
    Map<Long, Boolean> getExistenceByPostIds(Set<Long> postIds);

    PostActorCertificateDTO getByPostId(Long postId);

    PostActorCertificateDTO save(PostActorCertificateDTO postActorCertificateDTO, Long postId, Boolean sendCertificateMail);
}
