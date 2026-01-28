package com.platformcommons.platform.service.post.application;

import com.platformcommons.platform.service.post.domain.Reaction;
import com.platformcommons.platform.service.post.domain.vo.ReactionCountVO;

import java.util.Map;
import java.util.Set;

public interface ReactionService {

    Long createReactionForPost(Reaction reaction, Long postId, String postIdentifier);

    Long createReactionForResponse(Reaction reaction, Long responseId);

    Reaction getReactionById(Long responseId);

    Reaction getReactionByUUId(String uuid);

    void deleteReaction(Long id, String reactionIdentifier,String reason);

    Map<Long,Boolean> reactedByCurrentUserOnEntityIdsAndEntityType(Set<Long> entityIds, String entityType);

    Map<Long, Boolean> reactedByCurrentUserOnEntityIdsAndEntityTypeAndReactionType(Set<Long> entityIds, String entityType, String reactionType);

    Boolean ifCurrentUserReactedOnEntity(Long entityId, String entityType);


    Long postReactionByEntityType(Reaction reaction, Long entityId, String entityType);

    Long countOfAllReactionsByEntityIdAndType(Long entityId, String entityType,String tenantLogin);

    Set<ReactionCountVO> countOfAllReactionsByEntityIdsAndType(Set<Long> entityIds, String entityType,String reactionType, String tenantLogin);

}