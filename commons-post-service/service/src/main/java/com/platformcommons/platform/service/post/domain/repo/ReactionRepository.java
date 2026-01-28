package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.post.domain.Reaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReactionRepository extends BaseRepository<Reaction, Long> {

    @Query("FROM #{#entityName} r WHERE r.uuid=:uuid ")
    Optional<Reaction> findByUUID(String uuid);

    @Query("FROM #{#entityName} r WHERE r.id=:id ")
    Optional<Reaction> findByReactionId(Long id);

    @Query("Select r.reactedOnEntityId from #{#entityName} r where r.reactedOnEntityType=:entityType "+
            " AND r.reactedBy.actorId= :actorId AND " +
            " r.reactedBy.actorType= :actorType AND r.reactedOnEntityId IN (:postIds) AND r.reactionStatus='ACTIVE'")
    List<Long> findPostIdsReactedByCurrentUser(Set<Long> postIds,String entityType,Long actorId,String actorType);



    @Query("Select r.reactedOnEntityId from #{#entityName} r where r.reactedOnEntityType=:entityType "+
            " AND r.reactedBy.actorId= :actorId  " +
            " AND ( r.reactionType = :reactionType OR :reactionType is NULL ) " +
            " AND r.reactedBy.actorType= :actorType AND r.reactedOnEntityId IN (:postIds) AND r.reactionStatus='ACTIVE'")
    List<Long> findPostIdsReactedByCurrentUser(Set<Long> postIds,String entityType,Long actorId,String actorType, String reactionType);


    @Query("SELECT CASE WHEN COUNT(r.reactedOnEntityId)>0 THEN TRUE ELSE FALSE END " +
            " FROM #{#entityName} r where r.reactedOnEntityType=:entityType "+
            " AND r.reactedBy.actorId= :actorId AND " +
            " r.reactedBy.actorType= :actorType AND r.reactedOnEntityId = :entityId AND r.reactionStatus='ACTIVE'")
    Boolean findIfCurrentUserReactedOnEntity(Long entityId,String entityType,Long actorId,String actorType);

    @Query("FROM #{#entityName} r WHERE r.reactedOnEntityType = :entityType AND" +
            " r.reactedOnEntityId = :entityId ")
    Slice<Reaction> findAllForEntity(String entityType, Long entityId, Pageable pageable);

    @Query("FROM #{#entityName} r WHERE r.reactedOnEntityType = :entityType AND r.reactedOnEntityId = :entityId " +
            "AND r.reactedBy.actorId= :actorId AND  r.reactedBy.actorType= :actorType ")
    Optional<Reaction> findForLoggedInUser(String entityType, Long entityId, Long actorId,
                                           String actorType);

    @Query("FROM #{#entityName} r WHERE r.reactedOnEntityType = :entityType AND r.reactedOnEntityId in( :entityIds )" +
            "AND r.reactedBy.actorId= :actorId AND  r.reactedBy.actorType= :actorType ")
    Set<Reaction> findReactionForEntitiesByUser(Set<Long> entityIds, String entityType, Long actorId, String actorType);

}