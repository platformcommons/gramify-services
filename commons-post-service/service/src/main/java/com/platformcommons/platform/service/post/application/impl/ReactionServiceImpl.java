package com.platformcommons.platform.service.post.application.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.post.application.*;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.domain.*;
import com.platformcommons.platform.service.post.domain.repo.PostRepository;
import com.platformcommons.platform.service.post.domain.repo.ReactionRepository;
import com.platformcommons.platform.service.post.domain.repo.ResponseRepository;
import com.platformcommons.platform.service.post.domain.vo.ReactionCountVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    private ReactionNonMTRepository nonMTRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private PostActorService postActorService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    public ResponseRepository responseRepository;
    @Autowired
    private PostTenantConfigService postTenantConfigService;


    @Override
    public Long createReactionForPost(Reaction reaction, Long postId, String postIdentifier) {
        Reaction finalReaction = null;
        Post post=postService.findPost(postId,postIdentifier);
        Optional<Reaction> reactionForPostOptional= existsForLoggedInUser(post.getId(),PostConstant.ENTITY_TYPE_POST);
        if(reactionForPostOptional.isPresent()) {
            finalReaction = reactionForPostOptional.get();
            finalReaction.toggle();
        }
        else {
            finalReaction = initializeReaction(reaction,post.getId(),PostConstant.ENTITY_TYPE_POST);
        }
        return reactionRepository.save(finalReaction).getId();
    }

    @Override
    public Long createReactionForResponse(Reaction reaction, Long responseId) {
        Reaction finalReaction = null;
        Response response = responseService.getResponseById(responseId);
        Optional<Reaction> optionalReaction = existsForLoggedInUser(response.getId(),PostConstant.ENTITY_TYPE_RESPONSE);
        if(optionalReaction.isPresent()) {
            finalReaction = optionalReaction.get();
            finalReaction.toggle();
        }
        else {
            finalReaction = initializeReaction(reaction,response.getId(),PostConstant.ENTITY_TYPE_RESPONSE);
        }
        return reactionRepository.save(finalReaction).getId();
    }

    @Override
    public Reaction getReactionById(Long reactionId) {
        Optional<Reaction> reactionOptional= reactionRepository.findByReactionId(reactionId);
        if(!reactionOptional.isPresent() || !reactionOptional.get().getReactionStatus()
                                                     .equals(PostConstant.REACTION_STATUS_ACTIVE))
        {
            throw new NotFoundException(String.format("Reaction with Id %d not found",reactionId));
        }
        return reactionOptional.get();
    }

    @Override
    public Reaction getReactionByUUId(String uuid) {
        Optional<Reaction> reactionOptional= reactionRepository.findByUUID(uuid);
        if(!reactionOptional.isPresent() || !reactionOptional.get().getReactionStatus()
                .equals(PostConstant.REACTION_STATUS_ACTIVE))
        {
            throw new NotFoundException(String.format("Reaction with UUID %s not found",uuid));
        }
        return reactionOptional.get();
    }

    @Override
    public void deleteReaction(Long id, String reactionIdentifier, String reason) {
        Reaction reaction=findReaction(id,reactionIdentifier);
        reaction.isAuthorizedForUpdate();
        reaction.deactivate(reason);
        reactionRepository.save(reaction);
    }

    @Override
    public Map<Long, Boolean> reactedByCurrentUserOnEntityIdsAndEntityType(Set<Long> entityIds, String entityType) {
          List<Long> reactedOnEntityIds= reactionRepository.findPostIdsReactedByCurrentUser(entityIds,entityType,
                                        PlatformSecurityUtil.getCurrentUserId(),
                                        PlatformSecurityUtil.getActorContext().getActorContext());
          return entityIds.stream()
                  .collect(Collectors.toMap(Function.identity(),
                          reactedOnEntityIds::contains));
    }

    @Override
    public Map<Long, Boolean> reactedByCurrentUserOnEntityIdsAndEntityTypeAndReactionType(Set<Long> entityIds, String entityType, String reactionType) {
        List<Long> reactedOnEntityIds= reactionRepository.findPostIdsReactedByCurrentUser(entityIds,entityType,
                PlatformSecurityUtil.getCurrentUserId(),
                PlatformSecurityUtil.getActorContext().getActorContext(), reactionType);
        return entityIds.stream()
                .collect(Collectors.toMap(Function.identity(),
                        reactedOnEntityIds::contains));
    }

    @Override
    public Boolean ifCurrentUserReactedOnEntity(Long entityId, String entityType) {
        return reactionRepository.findIfCurrentUserReactedOnEntity(entityId,entityType, PlatformSecurityUtil.getCurrentUserId(),
                                      PlatformSecurityUtil.getActorContext().getActorContext());
    }

    public Reaction findReaction(Long reactionId, String reactionIdentifier) {
        if (null == reactionId && StringUtils.isEmpty(reactionIdentifier)) {
            throw new InvalidInputException("Either reactionId or reactionIdentifier is required");
        }
        Reaction reaction;
        if (reactionId != null) {
            reaction = reactionRepository.findByReactionId(reactionId)
                    .orElseThrow(() -> new NotFoundException(String.format("Reaction with Id %d not found",reactionId)));
        } else {
            reaction = reactionRepository.findByUUID(reactionIdentifier)
                    .orElseThrow(()-> new NotFoundException(String.format("Reaction with UUID %s not found",reactionIdentifier)));
        }
        return reaction;
    }


    @Override
    public Long postReactionByEntityType(Reaction reaction, Long entityId, String entityType) {
        Reaction finalReaction = null;
        Optional<Reaction> reactionForPostOptional= existsForLoggedInUser(entityId,entityType);
        if(reactionForPostOptional.isPresent()) {
            finalReaction = reactionForPostOptional.get();
            finalReaction.toggle();
        }
        else {
            finalReaction = initializeReaction(reaction,entityId,entityType);
        }
        return reactionRepository.save(finalReaction).getId();
    }

    @Override
    public Long countOfAllReactionsByEntityIdAndType(Long entityId, String entityType,String tenantLogin) {
        Long tenantId;
        if(PlatformSecurityUtil.isPlatformAppToken()) {
            PostTenantConfig postTenantConfig = postTenantConfigService.validateIsResponseAndReactionPublic(tenantLogin);
            tenantId = postTenantConfig.getTenantId();
        }
        else {
            tenantId = PlatformSecurityUtil.getCurrentTenantId();
        }
        return nonMTRepository.findTotalCountByEntityIdAndEntityType(entityId,entityType,tenantId);
    }



    @Override
    public Set<ReactionCountVO> countOfAllReactionsByEntityIdsAndType(Set<Long> entityIds, String entityType,String reactionType ,String tenantLogin) {
        Long tenantId;
        if(PlatformSecurityUtil.isPlatformAppToken()) {
            PostTenantConfig postTenantConfig = postTenantConfigService.validateIsResponseAndReactionPublic(tenantLogin);
            tenantId = postTenantConfig.getTenantId();
        }
        else {
            tenantId = PlatformSecurityUtil.getCurrentTenantId();
        }
        return nonMTRepository.findTotalCountByEntityIdsAndEntityType(entityIds,entityType,reactionType,tenantId);
    }

    private Reaction initializeReaction(Reaction reaction, Long entityId, String entityType) {
        PostActor postActor = postActorService.getOrAddForCurrentUser();
        reaction.init(entityId, entityType, postActor);
        return reaction;
    }

    private Optional<Reaction> existsForLoggedInUser(Long entityId, String entityType) {
        return reactionRepository.findForLoggedInUser(entityType, entityId, PlatformSecurityUtil.getCurrentUserId(), PlatformSecurityUtil
                        .getActorContext().getActorContext());
    }
}