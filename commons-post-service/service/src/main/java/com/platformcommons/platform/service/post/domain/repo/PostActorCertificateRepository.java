package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.post.domain.PostActorCertificate;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PostActorCertificateRepository extends BaseRepository<PostActorCertificate,Long> {


    @Query(" SELECT c.post.id FROM #{#entityName} c WHERE c.post.id IN (:postIds)")
    Set<Long> findExistenceByPostIds(Set<Long> postIds);

    @Query("SELECT c FROM #{#entityName} c WHERE c.post.id = :postId")
    List<PostActorCertificate> findByPostId(Long postId);

    @Query(" SELECT c FROM #{#entityName} c WHERE c.post.id = :postId AND c.certificateTemplateCode = :templateCode")
    List<PostActorCertificate> findByPostIdAndTemplateCode(Long postId, String templateCode);
}
