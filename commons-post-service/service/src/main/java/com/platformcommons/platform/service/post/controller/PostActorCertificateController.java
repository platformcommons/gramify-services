package com.platformcommons.platform.service.post.controller;

import com.platformcommons.platform.service.post.client.PostActorCertificateAPI;
import com.platformcommons.platform.service.post.domain.PostActorCertificate;
import com.platformcommons.platform.service.post.dto.PostActorCertificateDTO;
import com.platformcommons.platform.service.post.dto.ResponseDTO;
import com.platformcommons.platform.service.post.facade.PostActorCertificateFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name="PostActorCertificate")
public class PostActorCertificateController implements PostActorCertificateAPI {

    @Autowired
    private PostActorCertificateFacade facade;

    @Override
    public ResponseEntity<PostActorCertificateDTO> getByPostId(Long postId) {
        PostActorCertificateDTO postActorCertificateDTO = facade.getByPostId(postId);
        return ResponseEntity.status(postActorCertificateDTO == null ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(postActorCertificateDTO);
    }

    @ApiOperation(value = "Create PostActorCertificate",  response = PostActorCertificateDTO.class, tags={ "PostActorCertificate", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostActorCertificateDTO.class) })
    @RequestMapping(value = "/api/v1/post-actor-certificates/",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<PostActorCertificateDTO> save(@NotNull @Valid @RequestParam(value = "postId") Long postId,
                                                 PostActorCertificateDTO postActorCertificateDTO,
                                                 Boolean sendCertificateMail) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(postActorCertificateDTO,postId,sendCertificateMail));
    }


    @ApiOperation(value = "Get Existence of Certificate by Post Ids", notes = "", response = Map.class,
            tags={ "PostActorCertificate"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class) })
    @RequestMapping(value = "/api/v1/post-actor-certificates/post/exists",
            method = RequestMethod.GET)
    public ResponseEntity<Map<Long, Boolean>> getExistenceByPostIds(@NotNull @Valid @RequestParam(value="postIds") Set<Long> postIds) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getExistenceByPostIds(postIds));
    }


}
