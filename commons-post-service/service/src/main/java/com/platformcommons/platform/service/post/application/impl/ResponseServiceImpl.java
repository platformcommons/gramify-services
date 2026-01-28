package com.platformcommons.platform.service.post.application.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.post.application.*;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.application.utility.PlatformUtil;
import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.PostActor;
import com.platformcommons.platform.service.post.domain.PostTenantConfig;
import com.platformcommons.platform.service.post.domain.Response;
import com.platformcommons.platform.service.post.domain.repo.ResponseNonMTRepository;
import com.platformcommons.platform.service.post.domain.repo.ResponseRepository;
import com.platformcommons.platform.service.post.domain.vo.PostCommentCountVO;
import com.platformcommons.platform.service.post.dto.ReactionForUpdateDTO;
import com.platformcommons.platform.service.post.facade.cache.validator.TenantMetaConfigValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    public ResponseRepository responseRepository;

    @Autowired
    public ResponseNonMTRepository responseNonMTRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostActorService postActorService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostTenantConfigService postTenantConfigService;

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private PlatformUtil platformUtil;

    @Override
    public Response createResponseForPost(Response response, Long postID, String postIdentifier) {
        Post post = postService.findPost(postID, postIdentifier);
        return save(response, post.getId(), PostConstant.ENTITY_TYPE_POST);

    }


    @Override
    public Response createResponseForResponse(Response response, Long responseID, String responseIdentifier) {
        Response parentResponse = findResponse(responseID, responseIdentifier);
        Response savedResponse = save(response, parentResponse.getId(), PostConstant.ENTITY_TYPE_RESPONSE);
        //as trigger won't work on same table
        triggerCountChange(parentResponse);
        return savedResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Response> getResponseForPostUsingPagination(Integer page, Integer size, Sort sort, Long postID,
                                                             String postIdentifier,String responseType) {
        Long fetchedId = postService.findPost(postID, postIdentifier).getId();
        return responseRepository.findAllForEntityAndResponseType(PostConstant.ENTITY_TYPE_POST, fetchedId,responseType,
                PageRequest.of(page, size, sort));
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<Response> getResponseForResponseUsingPagination(Integer page, Integer size, Sort sort,
                                                                 Long responseID, String responseIdentifier) {
        Long fetchedId = findResponse(responseID, responseIdentifier).getId();
        return responseRepository.findAllForEntity(PostConstant.ENTITY_TYPE_RESPONSE, fetchedId,
                PageRequest.of(page, size, sort));
    }

    @Override
    public ReactionForUpdateDTO getMyReactionAndTotalCountOnAResponse(Long responseId) {
        Boolean reactedByMe = reactionService.ifCurrentUserReactedOnEntity(responseId,PostConstant.ENTITY_TYPE_RESPONSE);
        Long totalReactCount = getReactCountByResponseId(responseId);
        return ReactionForUpdateDTO.builder()
                .reactedByMe(reactedByMe)
                .totalReactCount(totalReactCount)
                .build();
    }

    @Override
    public void deleteResponseOnAPost(Long postId, Long responseId, String reason) {
        Long loggedInUserId = PlatformSecurityUtil.getCurrentUserId();
        Response response = findResponse(responseId,null);
        Post post = postService.getById(postId);
        if(!Objects.equals(response.getResponseOnEntityId(),postId)
                || !Objects.equals(response.getResponseOnEntityType(),PostConstant.ENTITY_TYPE_POST)) {
            throw new InvalidInputException(String.format("Post with id - %d is not linked to the response with id - %d",postId,responseId));
        }
        if(PlatformSecurityUtil.isTenantAdmin()
                || Objects.equals(response.getResponseBy().getActorId(),loggedInUserId)
                || Objects.equals(post.getPostedBy().getActorId(),loggedInUserId)) {
            response.deactivate(reason);
            responseRepository.save(response);
        }
        else {
            throw new UnAuthorizedAccessException(String.format("Logged in user is neither owner of the response with id - %d " +
                    "neither the owner of the post with id - %d",responseId,postId));
        }
    }

    public Long getReactCountByResponseId(Long responseId){
        Long reactCount =  responseRepository.findReactCountByResponseId(responseId);
        if(reactCount == null) {
            throw new NotFoundException(String.format("Response with id %d not found",responseId));
        }
        return reactCount;
    }

    @Override
    public Page<Response> getResponseForPostUsingPaginationForPublic(Integer page, Integer size, Sort sort, Long postId,
                                                                      String postIdentifier, String tenantLogin) {
        postTenantConfigService.validateIsPublicFeedAllowed(tenantLogin);
        Long tenantId = platformUtil.getTenantIdByContext(tenantLogin);
        return responseNonMTRepository.findAllForEntity(PostConstant.ENTITY_TYPE_POST, postId,
                    PageRequest.of(page, size, sort), tenantId);
    }

    @Override
    public Slice<Response> getResponseByPostUUIDForPublic(Pageable pageable, String postUUID) {
        Post post = postService.getByUUIDNonMT(postUUID);
        return responseNonMTRepository.findAllByPostUUID(PostConstant.ENTITY_TYPE_POST, post.getId(), pageable);
    }

    @Override
    public Response updateResponse(Response response,Boolean mapNulls) {
        Response fetchedResponse = findResponse(response.getId(), response.getUuid());
        fetchedResponse.isAuthorizedForUpdate();
        fetchedResponse.update(response, mapNulls);
        return responseRepository.save(fetchedResponse);
    }

    @Override
    public Response getResponseById(Long id) {
        return responseRepository.findByResponseId(id)
                .orElseThrow(() -> new NotFoundException(String.format("Response not found with id %s",id)));
    }

    @Override
    public Response getResponseByUUId(String uuid) {
        return responseRepository.findByUUID(uuid)
                .orElseThrow(() -> new NotFoundException(String.format("Response not found with uuid %s",uuid)));
    }

    @Override
    public void deleteResponse(Long responseId, String responseIdentifier, String reason) {
        Response response = findResponse(responseId,responseIdentifier);
        response.isAuthorizedForDelete();
        response.deactivate(reason);
        responseRepository.save(response);
    }

    @Override
    public Response findResponse(Long responseID, String responseIdentifier) {
        if (null == responseID && StringUtils.isEmpty(responseIdentifier)) {
            throw new InvalidInputException("Either responseID or responseIdentifier is required");
        }
        Response response;
        if (responseID != null) {
            response = getResponseById(responseID);
        } else {
            response = getResponseByUUId(responseIdentifier);
        }
        return response;
    }

    @Override
    public Response save(Response response, Long entityId, String entityType) {
        PostActor postActor = postActorService.getOrAddForCurrentUser();
        response.init(entityId, entityType, postActor);
        return responseRepository.save(response);
    }

    private void triggerCountChange(Response parentResponse) {
        Long tempResponseCount = parentResponse.getResponseCount();
        long parentResponseCount = tempResponseCount != null ? tempResponseCount + 1 : 1;
        jdbcTemplate.execute("update response set response_count=" + parentResponseCount + " " +
                "where id=" + parentResponse.getId());
    }

    @Override
    public Page<Response> getResponsePageByEntityIdAndType(Pageable pageable, Long entityId, String entityType, Long tenantId) {
        Page<Response> result = responseNonMTRepository.findAllForEntity(entityType,entityId,pageable ,tenantId);
        return result;
    }

    @Override
    public List<PostCommentCountVO> getResponseCountByEntityIdsAndEntityType(Set<Long> entityIds, String entityType, Long tenantId) {
        return responseNonMTRepository.getResponseCountByEntityIdsAndEntityType(entityIds, entityType, tenantId);
    }

    @Override
    public void saveResponses(Set<Response> responses) {
        PostActor postActor = postActorService.getOrAddForCurrentUser();
        responses.forEach(it->{
            it.setResponseBy(postActor);
        });
        responseRepository.saveAll(responses);
    }
}