package com.platformcommons.platform.service.post.application;

import com.platformcommons.platform.service.post.domain.Response;
import com.platformcommons.platform.service.post.domain.vo.PostCommentCountVO;
import com.platformcommons.platform.service.post.dto.ReactionForUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

public interface ResponseService {

    Response findResponse(Long responseID, String responseIdentifier);

    Response updateResponse(Response response,Boolean mapNulls);

    Response getResponseById(Long responseId);

    Response getResponseByUUId(String uuid);

    void deleteResponse(Long responseId, String responseIdentifier, String reason);

    Response createResponseForPost(Response response, Long postId,String postIdentifier);

    Response createResponseForResponse(Response response, Long responseId,String responseIdentifier);

    Page<Response> getResponseForPostUsingPagination(Integer page, Integer size, Sort convertToSort, Long postId,String postIdentifier,String responseType);

    Page<Response> getResponseForPostUsingPaginationForPublic(Integer page, Integer size, Sort sort, Long postId, String postIdentifier,
                                                               String tenantLogin);

    Slice<Response> getResponseByPostUUIDForPublic(Pageable pageable, String postUUID);

    Slice<Response> getResponseForResponseUsingPagination(Integer page, Integer size, Sort convertToSort,
                                                          Long responseId,String responseIdentifier);

    ReactionForUpdateDTO getMyReactionAndTotalCountOnAResponse(Long responseId);

    void deleteResponseOnAPost(Long postId, Long responseId, String reason);

    Response save(Response response, Long entityId, String entityType);

    Page<Response> getResponsePageByEntityIdAndType(Pageable pageable, Long entityId, String entityType, Long tenantId);

    List<PostCommentCountVO> getResponseCountByEntityIdsAndEntityType(Set<Long> entityIds, String entityType, Long tenantId);

    void saveResponses(Set<Response> responses);
}