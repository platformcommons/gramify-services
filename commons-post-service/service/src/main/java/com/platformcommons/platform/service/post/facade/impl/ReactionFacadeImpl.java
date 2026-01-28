package com.platformcommons.platform.service.post.facade.impl;

import com.platformcommons.platform.service.post.application.ReactionService;
import com.platformcommons.platform.service.post.domain.vo.ReactionCountVO;
import com.platformcommons.platform.service.post.dto.ReactionCountDTO;
import com.platformcommons.platform.service.post.dto.ReactionDTO;
import com.platformcommons.platform.service.post.facade.ReactionFacade;
import com.platformcommons.platform.service.post.facade.assembler.ReactionCountAssembler;
import com.platformcommons.platform.service.post.facade.assembler.ReactionDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class ReactionFacadeImpl implements ReactionFacade {


    @Autowired
    private ReactionService service;

    @Autowired
    private ReactionDTOAssembler assembler;

    @Autowired
    private ReactionCountAssembler reactionCountAssembler;


    @Override
    public Long createReactionForPost(ReactionDTO reaction, Long postId, String postIdentifier) {
        return service.createReactionForPost(assembler.fromDTO(reaction), postId, postIdentifier);
    }

    @Override
    public Long createReactionForResponse(ReactionDTO reactionDTO, Long responseId) {
        return service.createReactionForResponse(assembler.fromDTO(reactionDTO), responseId);
    }

    @Override
    public void deleteResponse(Long id,String reactionIdentifier,String reason) {
         service.deleteReaction(id,reactionIdentifier,reason);
    }

    @Override
    public ReactionDTO getReaction(Long id) {
        return assembler.toDTO(service.getReactionById(id));
    }

    @Override
    public Map<Long, Boolean> reactedByCurrentUserByEntityTypeAndEntityIds(Set<Long> postIds,String entityType) {
         return service.reactedByCurrentUserOnEntityIdsAndEntityType(postIds, entityType);
    }


    @Override
    public Long postReactionByEntityType(Long entityId, String entityType, ReactionDTO body) {
        return service.postReactionByEntityType(assembler.fromDTO(body),entityId,entityType);
    }

    @Override
    public Long countOfAllReactionsByEntityIdAndType(Long entityId, String entityType, String tenantLogin) {
        return service.countOfAllReactionsByEntityIdAndType(entityId,entityType,tenantLogin);
    }

    @Override
    public Set<ReactionCountDTO> countOfAllReactionsByEntityIdAndType(Set<Long> entityIds, String entityType, String reactionType,
                                                                      Boolean includeIfLoggedInUserReacted, String tenantLogin) {

        Set<ReactionCountVO> reactionCountVOS = service.countOfAllReactionsByEntityIdsAndType(entityIds, entityType, reactionType, tenantLogin);
        Map<Long, Boolean> isReactedByUser;
        if (includeIfLoggedInUserReacted) {
            isReactedByUser = service.reactedByCurrentUserOnEntityIdsAndEntityTypeAndReactionType(entityIds, entityType, reactionType);
        } else {
            isReactedByUser = new LinkedHashMap<>();
        }
        return reactionCountVOS.stream().map(it -> reactionCountAssembler.toDTO(it,
                        includeIfLoggedInUserReacted ? isReactedByUser.get(it.getEntityId()) : null) )
                .collect(Collectors.toCollection(LinkedHashSet::new));


    }
}
