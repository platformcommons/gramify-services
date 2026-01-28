package com.platformcommons.platform.service.post.facade.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.post.application.PostTenantConfigService;
import com.platformcommons.platform.service.post.application.ResponseService;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.application.utility.PlatformUtil;
import com.platformcommons.platform.service.post.domain.PostActor;
import com.platformcommons.platform.service.post.domain.PostTenantConfig;
import com.platformcommons.platform.service.post.domain.Response;
import com.platformcommons.platform.service.post.domain.vo.PostCommentCountVO;
import com.platformcommons.platform.service.post.dto.AuthorResponseDTO;
import com.platformcommons.platform.service.post.dto.ReactionForUpdateDTO;
import com.platformcommons.platform.service.post.dto.ResponseDTO;
import com.platformcommons.platform.service.post.facade.ResponseFacade;
import com.platformcommons.platform.service.post.facade.assembler.ResponseDTOAssembler;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class ResponseFacadeImpl implements ResponseFacade {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private ResponseDTOAssembler responseDTOAssembler;

    @Autowired
    private PostTenantConfigService postTenantConfigService;

    @Autowired
    private PlatformUtil platformUtil;

    @Override
    public ResponseDTO postResponse(ResponseDTO body, Long entityId, String entityType) {
        return responseDTOAssembler.toDTO(responseService.save(responseDTOAssembler.fromDTO(body), entityId,
                entityType));
    }

    @Override
    public List<PostCommentCountVO> getResponseCountByEntityIdsAndEntityType(Set<Long> entityIds, String entityType, String tenantLogin) {
        Long tenantId = platformUtil.getTenantIdByContext(tenantLogin);
        return responseService.getResponseCountByEntityIdsAndEntityType(entityIds, entityType, tenantId);
    }

    @Override
    public void createAuthorResponses(Long id,String type, Set<AuthorResponseDTO> authorResponseDTOS) {
        if(authorResponseDTOS!=null && !authorResponseDTOS.isEmpty()){
          Set<Response> responses=  authorResponseDTOS.stream().map(it->
                  buildAuthorResponse(it,id,type )).collect(Collectors.toCollection(LinkedHashSet::new));

          responseService.saveResponses(responses);
        }
    }


    private Response buildAuthorResponse(AuthorResponseDTO authorResponseDTO, Long entityId, String entityType){
        return Response.builder()
                .payload(authorResponseDTO.getPayload())
                .responseType(authorResponseDTO.getType())
                .responseOnEntityId(entityId)
                .responseOnEntityType(entityType)
                .build();

    }

    @Override
    public ResponseDTO findResponse(Long responseID, String responseIdentifier) {
        return responseDTOAssembler.toDTO(responseService
                .findResponse(responseID, responseIdentifier));
    }

    @Override
    public ResponseDTO updateResponse(ResponseDTO responseDTO,Boolean mapNulls) {
        return responseDTOAssembler.toDTO(responseService
                .updateResponse(responseDTOAssembler.fromDTO(responseDTO),mapNulls));
    }

    @Override
    public ResponseDTO getResponseById(Long responseId) {
        return responseDTOAssembler.toDTO(responseService
                .getResponseById(responseId));
    }

    @Override
    public ResponseDTO getResponseByUUId(String uuid) {
        return responseDTOAssembler.toDTO(responseService
                .getResponseByUUId(uuid));
    }

    @Override
    public void deleteResponse(Long responseId, String responseIdentifier, String reason) {
        responseService.deleteResponse(responseId,responseIdentifier,reason);
    }

    @Override
    public ResponseDTO createResponseForPost(ResponseDTO ResponseDTO, Long postId,
                                             String postIdentifier) {
        return responseDTOAssembler.toDTO(responseService
                .createResponseForPost(responseDTOAssembler.fromDTO(ResponseDTO), postId,
                        postIdentifier));
    }

    @Override
    public ResponseDTO createResponseForResponse(ResponseDTO ResponseDTO, Long responseId,
                                                 String responseIdentifier) {
        return responseDTOAssembler.toDTO(responseService
                .createResponseForResponse(responseDTOAssembler.fromDTO(ResponseDTO), responseId,
                        responseIdentifier));
    }

    @Override
    public PageDTO<ResponseDTO> getResponseForPostUsingPagination(Integer page, Integer size,
                                                                  String sortBy, String direction, Long postId,
                                                                  String postIdentifier,String responseType) {
        Slice<Response> result = responseService.getResponseForPostUsingPagination(
                page, size, JPAUtility.convertToSort(sortBy, direction), postId, postIdentifier,responseType);
        return new PageDTO<>(result.stream().map(responseDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getNumberOfElements());
    }

    @Override
    public PageDTO<ResponseDTO> getResponseForPostUsingPaginationForPublic(Integer page, Integer size,
                                                                  String sortBy, String direction, Long postId,
                                                                  String postIdentifier,String tenantLogin) {
        Page<Response> result = null;
        if(PlatformSecurityUtil.isPlatformAppToken()) {
            result = responseService.getResponseForPostUsingPaginationForPublic(
                    page, size, JPAUtility.convertToSort(sortBy, direction), postId, postIdentifier,tenantLogin);
        }
        else {
            result = responseService.getResponseForPostUsingPagination(
                    page, size, JPAUtility.convertToSort(sortBy, direction), postId, postIdentifier,null);
        }
        return new PageDTO<>(result.stream().map(responseDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getNumberOfElements());
    }

    @Override
    public PageDTO<ResponseDTO> getResponseByPostUUIDForPublic(Integer page, Integer size, String sortBy, String direction,
                                                               String postUUID) {
        Slice<Response> result = null;
        if(PlatformSecurityUtil.isPlatformAppToken()) {
            result = responseService.getResponseByPostUUIDForPublic(
                    PageRequest.of(page,size,JPAUtility.convertToSort(sortBy,direction)), postUUID);
        }
        else {
            result = responseService.getResponseForPostUsingPagination(
                    page, size, JPAUtility.convertToSort(sortBy, direction), null, postUUID,null);
        }
        return new PageDTO<>(result.stream().map(responseDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getNumberOfElements());
    }

    @Override
    public PageDTO<ResponseDTO> getResponseForResponseUsingPagination(Integer page, Integer size,
                                                                      String sortBy, String direction, Long responseId,
                                                                      String responseIdentifier) {
        Slice<Response> result = responseService.getResponseForResponseUsingPagination(
                page, size, JPAUtility.convertToSort(sortBy, direction), responseId, responseIdentifier);
        return new PageDTO<>(result.stream().map(responseDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public ReactionForUpdateDTO getMyReactionAndTotalCountOnAResponse(Long responseId) {
        return responseService.getMyReactionAndTotalCountOnAResponse(responseId);
    }

    @Override
    public void deleteResponseOnAPost(Long postId,Long responseId, String reason) {
        responseService.deleteResponseOnAPost(postId,responseId,reason);
    }

    @Override
    public PageDTO<ResponseDTO> getResponsePageByEntityIdAndType(Long entityId, String entityType,String tenantLogin,
                                                                  Integer page, Integer size, String sortBy, String direction) {
        Long tenantId = platformUtil.getTenantIdByContext(tenantLogin);
        Page<Response> result = responseService.getResponsePageByEntityIdAndType(PageRequest.of(page,size,JPAUtility.convertToSort(sortBy,direction)),
                entityId,entityType,tenantId);
        return new PageDTO<>(result.stream()
                .map(responseDTOAssembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());
    }

}
