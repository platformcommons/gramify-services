package com.platformcommons.platform.service.post.controller;

import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.client.ReactionAPI;
import com.platformcommons.platform.service.post.dto.ReactionCountDTO;
import com.platformcommons.platform.service.post.dto.ReactionDTO;
import com.platformcommons.platform.service.post.facade.ReactionFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "Reaction")
public class ReactionController implements ReactionAPI {

    @Autowired
    private ReactionFacade facade;

    @Override
    public ResponseEntity<Void> deleteReaction(String reason, Long id, String reactionIdentifier) {
        facade.deleteResponse(id,reactionIdentifier,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<ReactionDTO> getReaction(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getReaction(id));
    }

    @Override
    public ResponseEntity<PageDTO<ReactionDTO>> getReactionPage(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<Long> postReactionByEntityType(Long entityId, String entityType, ReactionDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.postReactionByEntityType(entityId,entityType,body));
    }


    @Override
    public ResponseEntity<Long> createReactionForPost(ReactionDTO body, Long postId, String postIdentifier) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.createReactionForPost(body,postId,postIdentifier));
    }

    @Override
    public ResponseEntity<Long> createReactionForResponse(Long responseId, ReactionDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.createReactionForResponse(body,responseId));
    }

    @Override
    public ResponseEntity<ReactionDTO> putReaction(ReactionDTO body) {
        return null;
    }

    @ApiOperation(value = "Reacted By Current User on Given PostIds", nickname = "reactedByCurrentUserOnPosts", notes = "", response = Map.class,
            tags={ "Reaction"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class) })
    @RequestMapping(value = "/api/v1/reaction/me",
            method = RequestMethod.GET)
    public ResponseEntity<Map<Long,Boolean>> reactedByCurrentUserOnPosts(
                             @NotNull @Valid @RequestParam(value="postIds", required = true) Set<Long> postIds) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.reactedByCurrentUserByEntityTypeAndEntityIds(postIds,PostConstant.ENTITY_TYPE_POST));
    }

    @ApiOperation(value = "Reacted By Current User By EntityType And EntityIds",  notes = "", response = Map.class,
            tags={ "Reaction"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class) })
    @RequestMapping(value = "/api/v1/reaction/entity-type/me",
            method = RequestMethod.GET)
    public ResponseEntity<Map<Long,Boolean>> reactedByCurrentUserByEntityTypeAndIds(
            @NotNull @Valid @RequestParam(value="entityIds") Set<Long> entityIds,
            @NotNull @Valid @RequestParam(value = "entityType") String entityType) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.reactedByCurrentUserByEntityTypeAndEntityIds(entityIds,entityType));
    }

    @ApiOperation(value = "Count of All Reactions By EntityId And EntityType",  notes = "", response = Long.class,
            tags={ "Reaction"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Long.class) })
    @RequestMapping(value = "/api/v1/reaction/count",
            method = RequestMethod.GET)
    public ResponseEntity<Long> countOfAllReactionsByEntityIdAndType(@NotNull @Valid @RequestParam(value="entityId") Long entityId,
                                                                     @NotNull @Valid @RequestParam(value = "entityType") String entityType,
                                                                     @NotNull @Valid @RequestParam(value = "tenantLogin") String tenantLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.countOfAllReactionsByEntityIdAndType(entityId,entityType,tenantLogin));
    }


    @ApiOperation(value = "Count of All Reactions By EntityId And EntityType",  notes = "", response = Long.class,
            tags={ "Reaction"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Long.class) })
    @RequestMapping(value = "/api/v2/reaction/count",
            method = RequestMethod.GET)
    public ResponseEntity<Set<ReactionCountDTO>> countOfAllReactionsByEntityIdAndType(@NotNull @Valid @RequestParam(value="entityIds") Set<Long> entityIds,
                                                                                      @NotNull @Valid @RequestParam(value = "entityType") String entityType,
                                                                                      @NotNull @Valid @RequestParam(value = "tenantLogin") String tenantLogin,
                                                                                      @RequestParam(required = false, value = "reactionType") String reactionType,
                                                                                      @RequestParam(required = false, value = "includeIfLoggedInUserReacted") Boolean includeIfLoggedInUserReacted ) {
        includeIfLoggedInUserReacted = includeIfLoggedInUserReacted!=null ? includeIfLoggedInUserReacted : Boolean.FALSE ;
        return ResponseEntity.status(HttpStatus.OK).body(facade.countOfAllReactionsByEntityIdAndType(
                entityIds,entityType, reactionType, includeIfLoggedInUserReacted, tenantLogin));
    }
}
