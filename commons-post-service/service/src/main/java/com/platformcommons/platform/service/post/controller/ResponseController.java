package com.platformcommons.platform.service.post.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.post.client.ResponseAPI;
import com.platformcommons.platform.service.post.domain.vo.PostCommentCountVO;
import com.platformcommons.platform.service.post.dto.ReactionForUpdateDTO;
import com.platformcommons.platform.service.post.dto.ResponseDTO;
import com.platformcommons.platform.service.post.facade.ResponseFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;


@RestController
@Tag(name = "Response")
public class ResponseController implements ResponseAPI {

    @Autowired
    private ResponseFacade facade;


    @Override
    public ResponseEntity<Void> deleteResponse(String reason, Long responseId, String responseIdentifier) {
        facade.deleteResponse(responseId,responseIdentifier,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> deleteResponseOnAPost(Long responseId, Long postId, String reason) {
        facade.deleteResponseOnAPost(postId,responseId,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Override
    public ResponseEntity<ReactionForUpdateDTO> getMyReactionAndTotalCountOnAResponse(Long responseId) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getMyReactionAndTotalCountOnAResponse(responseId));
    }

    @Override
    public ResponseEntity<ResponseDTO> getResponse(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getResponseById(id));
    }


    @Override
    public ResponseEntity<PageDTO<ResponseDTO>> getResponsePageForPost(Integer page, Integer size, Long postId,
                                                                       String postIdentifier, String sortBy,
                                                                       String direction,String responseType) {
        PageDTO<ResponseDTO> result= facade.getResponseForPostUsingPagination(page,size,sortBy,
                direction,postId,postIdentifier,responseType);
        return ResponseEntity.status(result.hasNext()?HttpStatus.PARTIAL_CONTENT :HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<PageDTO<ResponseDTO>> getResponsePageForPostForPublic(Long postId, Integer page, Integer size,
                                                                                String tenantLogin, String postIdentifier,
                                                                                String sortBy, String direction) {
        PageDTO<ResponseDTO> result= facade.getResponseForPostUsingPaginationForPublic(page,size,sortBy,
                direction,postId,postIdentifier,tenantLogin);
        return ResponseEntity.status(result.hasNext()?HttpStatus.PARTIAL_CONTENT :HttpStatus.OK).body(result);
    }


    @Override
    public ResponseEntity<PageDTO<ResponseDTO>> getResponseByPostUUIDForPublic(Integer page, Integer size, String postUUID,
                                                                               String sortBy, String direction) {
        PageDTO<ResponseDTO> result= facade.getResponseByPostUUIDForPublic(page,size,sortBy,
                direction,postUUID);
        return ResponseEntity.status(result.hasNext()?HttpStatus.PARTIAL_CONTENT :HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<PageDTO<ResponseDTO>> getResponsePageByEntityIdAndType(Long entityId, String entityType,
                                                                                 Integer page, Integer size, String tenantLogin,
                                                                                 String sortBy, String direction) {
        PageDTO<ResponseDTO> result=facade.getResponsePageByEntityIdAndType(entityId,entityType,tenantLogin,page,size,sortBy,direction);
        return ResponseEntity.status(result.hasNext()?HttpStatus.PARTIAL_CONTENT :HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<ResponseDTO> postResponseForPost(ResponseDTO body, Long postId, String postIdentifier) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.createResponseForPost(body,postId,postIdentifier));
    }


    @Override
    public ResponseEntity<ResponseDTO> putResponse(ResponseDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.updateResponse(body,Boolean.TRUE));
    }

    @Override
    public ResponseEntity<ResponseDTO> patchResponse(ResponseDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.updateResponse(body,Boolean.FALSE));
    }

    @Override
    public ResponseEntity<ResponseDTO> postResponseByEntityType(Long entityId, String entityType, ResponseDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.postResponse(body,entityId,entityType));
    }

    @ApiOperation(value = "Get Response Count By entityId and type In Bulk", response = List.class, tags={ "Post" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class) })
    @RequestMapping(value = "api/v1/response/count/bulk",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<PostCommentCountVO>> getResponseCountByEntityIdsAndEntityType(
            @RequestParam(name="entityIds") Set<Long> entityIds,
            @RequestParam(name="entityType") String entityType,
            @NotNull @Valid @RequestParam(value = "tenantLogin") String tenantLogin){
        List<PostCommentCountVO> replies = facade.getResponseCountByEntityIdsAndEntityType(entityIds,entityType, tenantLogin);
        return ResponseEntity.status(HttpStatus.OK).body(replies);
    }
}
