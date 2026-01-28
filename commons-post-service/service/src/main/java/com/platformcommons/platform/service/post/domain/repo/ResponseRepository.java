package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.post.domain.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends BaseRepository<Response, Long> {

    @Query("FROM #{#entityName} r WHERE r.uuid=:uuid ")
    Optional<Response> findByUUID(String uuid);

    @Query("FROM #{#entityName} r WHERE r.id = :id ")
    Optional<Response> findByResponseId(Long id);

    @Query("FROM #{#entityName} r WHERE r.responseOnEntityType = :entityType AND " +
            "r.responseOnEntityId = :entityId ")
    Page<Response> findAllForEntity(String entityType, Long entityId, Pageable pageable);

    @Query("FROM #{#entityName} r WHERE r.id IN (:responseIds)")
    List<Response> findAllResponseById(List<Long> responseIds);

    @Transactional
    @Query(value = "call get_latest_n_response(:postIds, :size);",nativeQuery = true)
    List<String> findResponseByPostsUsingPage(@Param(value="postIds") String postIds,@Param(value="size") Integer size);

    @Query("SELECT r.reactCount FROM #{#entityName} r WHERE r.id = :responseId")
    Long findReactCountByResponseId(Long responseId);

    @Query("FROM #{#entityName} r WHERE r.responseOnEntityType = :entityType AND " +
            "r.responseOnEntityId = :entityId " +
            "AND (:responseType IS NULL OR r.responseType = :responseType)")
    Page<Response> findAllForEntityAndResponseType(String entityType, Long entityId, String responseType, Pageable pageable);
}