package com.platformcommons.platform.service.post.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.post.domain.vo.PostCommentCountVO;
import com.platformcommons.platform.service.post.dto.AuthorResponseDTO;
import com.platformcommons.platform.service.post.dto.ReactionForUpdateDTO;
import com.platformcommons.platform.service.post.dto.ResponseDTO;

import java.util.List;
import java.util.Set;

public interface ResponseFacade {

    ResponseDTO findResponse(Long responseID, String responseIdentifier);

    ResponseDTO updateResponse(ResponseDTO ResponseDTO,Boolean mapNulls);

    ResponseDTO getResponseById(Long responseId);

    ResponseDTO getResponseByUUId(String uuid);

    void deleteResponse(Long responseId, String responseIdentifier, String reason);

    ResponseDTO createResponseForPost(ResponseDTO ResponseDTO, Long postId, String postIdentifier);

    ResponseDTO createResponseForResponse(ResponseDTO ResponseDTO, Long responseId, String responseIdentifier);

    PageDTO<ResponseDTO> getResponseForPostUsingPagination(Integer page, Integer size,
                                                           String sortBy, String direction, Long postId,
                                                           String postIdentifier, String responseType);

    PageDTO<ResponseDTO> getResponseForPostUsingPaginationForPublic(Integer page, Integer size,
                                                           String sortBy, String direction, Long postId,
                                                           String postIdentifier,String tenantLogin);

    PageDTO<ResponseDTO> getResponseByPostUUIDForPublic(Integer page, Integer size,
                                                        String sortBy, String direction,String postUUID );

    PageDTO<ResponseDTO> getResponseForResponseUsingPagination(Integer page, Integer size, String sortBy, String direction,
                                                               Long responseId, String responseIdentifier);

    ReactionForUpdateDTO getMyReactionAndTotalCountOnAResponse(Long responseId);

    void deleteResponseOnAPost(Long postId,Long responseId, String reason);

    PageDTO<ResponseDTO> getResponsePageByEntityIdAndType(Long entityId, String entityType,String tenantLogin,
                                                          Integer page, Integer size, String sortBy, String direction
                                                          );

    ResponseDTO postResponse(ResponseDTO body, Long entityId, String entityType);

    List<PostCommentCountVO> getResponseCountByEntityIdsAndEntityType(Set<Long> entityIds, String entityType, String tenantLogin);

    void createAuthorResponses(Long id,String type, Set<AuthorResponseDTO> authorResponseDTOS);
}
