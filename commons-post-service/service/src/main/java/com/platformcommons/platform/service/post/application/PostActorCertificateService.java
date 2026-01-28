package com.platformcommons.platform.service.post.application;

import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.PostActor;
import com.platformcommons.platform.service.post.domain.PostActorCertificate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PostActorCertificateService {

    PostActorCertificate saveOrUpdate(PostActorCertificate postActorCertificate, PostActor postActor,
                                      Post post, Boolean sendCertificateMail);

    PostActorCertificate save(PostActorCertificate postActorCertificate, Long postId, Boolean sendCertificateMail);

    PostActorCertificate save(PostActorCertificate postActorCertificate);

    void saveAll(List<PostActorCertificate> postActorCertificates);

    Map<Long, Boolean> getExistenceByPostIds(Set<Long> postIds);

    PostActorCertificate getByPostId(Long postId);

    List<PostActorCertificate> createCertificateInBulk(List<Post> savedPosts, Long tenantCertificateTemplateId);
}
