package com.platformcommons.platform.service.post.facade;

import com.platformcommons.platform.service.post.dto.ReactionCountDTO;
import com.platformcommons.platform.service.post.dto.ReactionDTO;

import java.util.Map;
import java.util.Set;

public interface ReactionFacade {

    Long createReactionForPost(ReactionDTO reaction, Long postId, String postIdentifier);

    Long createReactionForResponse(ReactionDTO reactionDTO, Long responseId);

    void deleteResponse(Long id,String reactionIdentifer,String reason);

    ReactionDTO getReaction(Long id);

    Map<Long, Boolean> reactedByCurrentUserByEntityTypeAndEntityIds(Set<Long> entityIds, String entityType);

    Long postReactionByEntityType(Long entityId, String entityType, ReactionDTO body);

    Long countOfAllReactionsByEntityIdAndType(Long entityId, String entityType, String tenantLogin);

    Set<ReactionCountDTO> countOfAllReactionsByEntityIdAndType(Set<Long> entityIds, String entityType, String reactionType,
                                                               Boolean includeIfLoggedInUserReacted, String tenantLogin);

}
