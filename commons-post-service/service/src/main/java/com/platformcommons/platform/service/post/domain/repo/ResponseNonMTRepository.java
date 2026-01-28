package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.post.domain.Response;
import com.platformcommons.platform.service.post.domain.vo.PostCommentCountVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface ResponseNonMTRepository extends NonMultiTenantBaseRepository<Response,Long> {


    @Transactional
    @Query(value = "call get_latest_n_response(:postIds, :size);",nativeQuery = true)
    List<String> findResponseByPostsUsingPage(@Param(value="postIds") String postIds, @Param(value="size") Integer size);


    @Query("FROM #{#entityName} r WHERE r.id IN (:responseIds) and r.isActive = 1")
    List<Response> findAllResponseById(List<Long> responseIds);

    @Query(" FROM #{#entityName} r " +
            " WHERE r.responseOnEntityType = :entityType  " +
            " AND r.responseOnEntityId = :entityId " +
            " AND r.tenantId = :tenantId " +
            " AND r.isActive = 1 ")
    Page<Response> findAllForEntity(String entityType, Long entityId, Pageable pageable, Long tenantId);

    @Query("FROM #{#entityName} r WHERE r.responseOnEntityType = :entityType AND " +
            "r.responseOnEntityId = :entityId AND r.isActive = 1 ")
    Slice<Response> findAllByPostUUID(String entityType, Long entityId, Pageable pageable);


    @Query("SELECT new com.platformcommons.platform.service.post.domain.vo.PostCommentCountVO(" +
            "r.responseOnEntityId, COUNT(r.responseOnEntityId)) " +
            "FROM Response r " +
            "WHERE r.responseOnEntityId IN (:entityIds) " +
            "AND r.responseOnEntityType = :entityType " +
            "AND r.isActive = 1 " +
            "AND r.tenantId = :tenantId " +
            "GROUP BY r.responseOnEntityId")
    List<PostCommentCountVO> getResponseCountByEntityIdsAndEntityType(Set<Long> entityIds, String entityType, Long tenantId);

}
