package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.post.domain.LinkedPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkedPostRepository extends BaseRepository<LinkedPost, Long> {

    @Query("FROM #{#entityName} lp where ( :postId is null or (lp.sourcePost.id= :postId or lp.targetPost.id = :postId ))" +
            " and ( :linkingType is Null or lp.linkingType= :linkingType)" +
            "and lp.sourcePost.isActive=true and lp.targetPost.isActive=true")
    Page<LinkedPost> getAllLinkedPost(String linkingType, Long postId, Pageable pageable);

    @Query("FROM #{#entityName} lp where lp.id= :linkedPostId and lp.sourcePost.isActive=true and lp.targetPost.isActive=true")
    Optional<LinkedPost> getLinkedPostById(Long linkedPostId);

    @Query("FROM #{#entityName} lp where lp.sourcePost.id= :sourcePostId and lp.targetPost.id = :targetSourceId and lp.linkingType= :linkingType")
    LinkedPost getLinkedPostBySourceAndTarget(Long sourcePostId, Long targetSourceId, String linkingType);
}