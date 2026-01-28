package com.platformcommons.platform.service.post.controller;


import com.google.gson.Gson;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.application.utility.SearchIndexingUtil;
import com.platformcommons.platform.service.post.client.PostAPI;
import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.vo.*;
import com.platformcommons.platform.service.post.dto.PostDTO;
import com.platformcommons.platform.service.post.dto.ReactionForUpdateDTO;
import com.platformcommons.platform.service.post.dto.ResponseDTO;
import com.platformcommons.platform.service.post.facade.PostFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@Tag(name="Post")
public class PostController implements PostAPI {

    @Autowired
    private PostFacade postFacade;

    @Autowired
    private SearchIndexingUtil searchIndexingUtil;

    @Override
    public ResponseEntity<Long> createUserReadEventForPost(Long postId) {
        postFacade.createUserReadEventForPost(postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deletePost(String reason, Long id, String identifier) {
        postFacade.delete(id,identifier,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Fetch Public Timeline", nickname = "fetchPublicTimeline", notes = "", response = PostDTO.class,
            responseContainer = "Set", tags={ "Post", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v2/post/timeline",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<PostDTO>> fetchPublicTimeline(@NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
                                                         @NotNull @Valid @RequestParam(value = "size", required = true) Integer size,
                                                         @NotNull @Valid @RequestParam(value = "tenantLogin", required = true) String tenantLogin,
                                                         @Valid @RequestParam(value = "appContext", required = false) String appContext,
                                                         @Valid @RequestParam(value = "domainContext", required = false) String domainContext,
                                                         @Valid @RequestParam(value = "languageContext", required = false) String languageContext,
                                                         @Valid @RequestParam(value = "locationContext", required = false) String locationContext,
                                                         @Valid @RequestParam(value = "postType", required = false) String postType,
                                                         @Valid @RequestParam(value = "postSubTypes", required = false) Set<String> postSubTypes,
                                                         @Valid @RequestParam(value = "sortBy", required = false) String sortBy,
                                                         @Valid @RequestParam(value = "direction", required = false) String direction,
                                                         @Valid @RequestParam(value = "taggedToEntityType", required = false) String taggedToEntityType,
                                                         @Valid @RequestParam(value = "taggedToEntityId", required = false) Long taggedToEntityId,
                                                         @Valid @RequestParam(value = "category", required = false) String category,
                                                         @Valid @RequestParam(value = "subCategory", required = false) String subCategory,
                                                         @Valid @RequestParam(value = "status", required = false) String status,
                                                         @Valid @RequestParam(value = "causes", required = false) Set<String> causes,
                                                         @Valid @RequestParam(value = "actorType", required = false) String actorType,
                                                         @Valid @RequestParam(value = "actorId", required = false) Long actorId,
                                                         @ApiParam(value = "fetch",allowableValues ="FETCH_TYPE.COMMENT,FETCH_TYPE.LIKE,FETCH_TYPE.LATEST")
                                                         @Valid @RequestParam( required = false) String fetch,
                                                         @Valid @RequestParam(value = "isModerated", required = false) Boolean isModerated,
                                                         @Valid @RequestParam(value = "responseCount", required = false) Long responseCount,
                                                         @Valid @RequestParam(value = "includeBaseColumnForLanguage", required = false) Boolean includeBaseColumnForLanguage,
                                                         @Valid @RequestParam(value = "excludeReportedPost", required = false) Boolean excludeReportedPost) {
        PageDTO<PostDTO> results= postFacade.fetchPublicTimeline(tenantLogin, appContext,domainContext,languageContext,
                locationContext, postType,postSubTypes,taggedToEntityType,taggedToEntityId,page,size,sortBy,direction,category,
                subCategory,status,causes,actorType,actorId,fetch,isModerated,includeBaseColumnForLanguage,responseCount, excludeReportedPost);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT :HttpStatus.OK).body(results);
    }


    @ApiOperation(value = "Create Attachment For Post", nickname = "createAttachmentForPost", notes = "",
            response = AttachmentDTO.class, tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = AttachmentDTO.class) })
    @RequestMapping(value = "/api/v1/post/attachment",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<AttachmentDTO> createAttachmentForPost(
                                    @NotNull @Valid @RequestParam(value="postId",required = true) Long postId,
                                    @RequestParam(value="attachmentName",required = false) String attachmentName,
                                    @RequestPart(value = "file",required = true) MultipartFile file)
                                                                  throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(postFacade.createAttachment(postId,file,attachmentName));
    }

    @GetMapping("/api/v1/post/attachment")
    @ApiOperation(value = "Get Attachments For Post", nickname = "getAttachmentForPost", notes = "", response = List.class,
            tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class) })
    @RequestMapping(value = "/api/v1/post/attachment",
            method = RequestMethod.GET)
    public ResponseEntity<List<AttachmentDTO>> getAttachmentForPost(@NotNull @Valid @RequestParam(value = "postId",
            required = true) Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getAttachment(postId));
    }

    @GetMapping("/api/v1/post/attachment")
    @ApiOperation(value = "Get Attachments For Post -  Public", nickname = "getAttachmentForPost", notes = "", response = List.class,
            tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class) })
    @RequestMapping(value = "/api/v1/post/attachment/public",
            method = RequestMethod.GET)
    public ResponseEntity<List<AttachmentDTO>> getAttachmentForPostPublic(@NotNull @Valid @RequestParam(value = "postId") Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getAttachmentsByPostIdNonMT(postId));
    }

    @GetMapping("/api/v1/post/attachment")
    @ApiOperation(value = "Get Attachments For Post By postUUId", nickname = "getAttachmentForPost", notes = "", response = List.class,
            tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class) })
    @RequestMapping(value = "/api/v1/post/uuid/attachment/public",
            method = RequestMethod.GET)
    public ResponseEntity<List<AttachmentDTO>> getAttachmentsByPostUUIDForPublic(@NotNull @Valid @RequestParam(value = "postUUID",
            required = true) String postUUID) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getAttachmentsByPostUUIDForPublic(postUUID));
    }

    @ApiOperation(value = "Get Attachments For Posts", nickname = "getAttachmentsForGivenPosts", notes = "", response = Map.class,
            tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class) })
    @RequestMapping(value = "/api/v2/post/attachments",
            method = RequestMethod.GET)
    public ResponseEntity<Map<Long,List<AttachmentDTO>>> getAttachmentsForGivenPosts(@NotNull @Valid
                                                                                         @RequestParam(value="postIds",
                                                                                                 required = true) Set<Long> postIds) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getAttachmentsForGivenPosts(postIds));
    }

    @ApiOperation(value = "Get Responses For Posts", nickname = "getResponsesForPosts", notes = "", response = Map.class,
            tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class) })
    @RequestMapping(value = "/api/v1/post/response",
            method = RequestMethod.GET)
    public ResponseEntity<Map<Long,List<ResponseDTO>>> getResponsesForPosts(@NotNull @Valid
                                                                                     @RequestParam(value="postIds",
                                                                                             required = true) Set<Long> postIds,
                                                                            @NotNull @RequestParam(value="size", required = true) Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getResponsesForPosts(postIds,size));
    }

    @Override
    public ResponseEntity<PageDTO<PostDTO>> fetchTimeline(Integer page, Integer size, String appContext, String domainContext,
                                                          String languageContext, String locationContext, String postType,
                                                          String sortBy, String direction,String taggedEntityType,
                                                          Long taggedEntityId,String category,String subCategory,
                                                          String status) {

        PageDTO<PostDTO> results= postFacade.fetchTimeline(appContext,domainContext,languageContext,
                locationContext,postType,taggedEntityType,taggedEntityId,page,size,sortBy,direction,category,subCategory,status);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT :HttpStatus.OK).body(results);
    }

    @Override
    public ResponseEntity<PostDTO> getPost(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getById(id));
    }

    @Override
    public ResponseEntity<PostDTO> getPostByUUIDForPublic(String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getPostByUUIDForPublic(uuid));
    }

    @Override
    public ResponseEntity<PostDTO> getPostByIdForPublic(Long id, String tenantLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getByIdForPublic(id,tenantLogin));
    }

    @Override
    public ResponseEntity<PageDTO<PostDTO>> getPostPage(Integer page, Integer size) {
        PageDTO<PostDTO> result= postFacade.getByPage(page,size);
        return ResponseEntity.status(result.hasNext()?HttpStatus.PARTIAL_CONTENT :HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<PostDTO> patchPost(PostDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.patchUpdate(body));
    }

    @Override
    public ResponseEntity<PostDTO> patchUpdateForLoggedInUser(PostDTO body,Set<Long> addedAttachmentIds,Set<Long> deleteAttachmentIds) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.patchUpdateForLoggedInUser(body,addedAttachmentIds,deleteAttachmentIds));
    }


    @ApiOperation(value = "Patch Post For Logged In User", nickname = "patchUpdateForLoggedInUser", notes = "", response = PostDTO.class, tags={ "Post", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostDTO.class) })
    @RequestMapping(value = "/api/v3/post",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<PostDTO> patchUpdateWithAttachment(@Valid @RequestBody PostDTO body,
                                                      @Valid @RequestParam(value = "addedAttachmentIds", required = false) Set<Long> addedAttachmentIds,
                                                      @Valid @RequestParam(value = "deleteAttachmentIds", required = false) Set<Long> deleteAttachmentIds){
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.patchUpdateWithAttachment(body,addedAttachmentIds,deleteAttachmentIds));
    }

    @ApiOperation(value = "Patch Post With Attachment Replace", response = PostDTO.class, tags={ "Post", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostDTO.class) })
    @RequestMapping(value = "/api/v2/post/{postId}",
            method = RequestMethod.PATCH)
    ResponseEntity<PostDTO> patchUpdateWithAttachmentReplace(@PathVariable(value = "postId") Long postId,
                                                             @ApiParam(value = "attachmentDecision",defaultValue = "NO_ACTION",
                                                                     allowableValues = "REPLACE_ATTACHMENT,DELETE_ATTACHMENT") @RequestParam(required = false) String attachmentDecision,
                                                             @RequestPart(value = "postDTO",required = false) String body,
                                                             @RequestPart(value = "file",required = false) MultipartFile file) {
        PostDTO postDTO = null;
        if(body != null) {
            postDTO = new Gson().fromJson(body,PostDTO.class);
        }
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.patchUpdateWithAttachmentReplace(postId,attachmentDecision,postDTO,file));
    }



    @Override
    public ResponseEntity<PostDTO> patchPostStatus(PostDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.patchPostStatus(body));
    }

    @Override
    public ResponseEntity<Long> postPost(PostDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postFacade.save(body));
    }

    @ApiOperation(value = "Publish Post with Attachment", nickname = "postPostWithAttachment", notes = "",
            response = Long.class, tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = Long.class) })
    @RequestMapping(value = "/api/v2/post",
            produces = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<Long> postPostWithAttachment(@RequestPart String body,
                                                       @RequestPart(value = "file",required = false) MultipartFile file)
                                                          throws IOException {

        PostDTO post=new Gson().fromJson(body,PostDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(postFacade.savePostWithAttachment(post,file));
    }

    @Override
    public ResponseEntity<Long> savePostWithMultipleAttachments(PostDTO body, Set<Long> attachmentIds) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postFacade.savePostWithMultipleAttachments(body,attachmentIds));
    }

    @Override
    public ResponseEntity<PostDTO> putPost(PostDTO body)
    {
       return ResponseEntity.status(HttpStatus.OK).body(postFacade.update(body));
    }


    @ApiOperation(value = "Get Responses For Posts along with attachments", nickname = "getResponsesForPostsWithAttachments", notes = "", response = Set.class,
            tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Set.class) })
    @RequestMapping(value = "/api/v1/post/attachments",
            method = RequestMethod.GET)
    public ResponseEntity<Set<PostAttachmentVO>> fetchTimelineWithAttachments(Integer page, Integer size, String appContext, String domainContext,
                                                                                       String languageContext, String locationContext, String postType,
                                                                                       String sortBy, String direction,String taggedEntityType,Long taggedEntityId) {

        Set<PostAttachmentVO> results = postFacade.fetchTimelineWithAttachments(appContext, domainContext, languageContext,
                locationContext, postType, taggedEntityType, taggedEntityId, page, size, sortBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "Get Responses For Public Posts along with attachments", nickname = "getResponsesForPublicPostsWithAttachments", notes = "", response = Set.class,
            tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Set.class) })
    @RequestMapping(value = "/api/v1/post/attachments/public",
            method = RequestMethod.GET)
    public ResponseEntity<Set<PostAttachmentVO>> fetchPublicTimelineWithAttachments(
            @javax.validation.constraints.NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
            @javax.validation.constraints.NotNull @Valid @RequestParam(value = "size", required = true) Integer size,
            @Valid @RequestParam(value = "appContext", required = false) String appContext,
            @Valid @RequestParam(value = "domainContext", required = false) String domainContext,
            @Valid @RequestParam(value = "languageContext", required = false) String languageContext,
            @Valid @RequestParam(value = "locationContext", required = false) String locationContext,
            @Valid @RequestParam(value = "postType", required = false) String postType,
            @Valid @RequestParam(value = "sortBy", required = false) String sortBy,
            @Valid @RequestParam(value = "direction", required = false) String direction,
            @Valid @RequestParam(value = "taggedToEntityType", required = false) String taggedToEntityType,
            @Valid @RequestParam(value = "taggedToEntityId", required = false) Long taggedToEntityId,
            @Valid @RequestParam(value = "tenantLogin", required = true) String tenantLogin) {
        Set<PostAttachmentVO> results = postFacade.fetchPublicTimelineWithAttachments(tenantLogin, appContext, domainContext, languageContext,
                locationContext, postType, taggedToEntityType, taggedToEntityId, page, size, sortBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }


    @ApiOperation(value = "Get Public Attachments For Posts", nickname = "getPublicAttachmentsForGivenPosts", notes = "", response = Map.class,
            tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class) })
    @RequestMapping(value = "/api/v2/post/attachments/public",
            method = RequestMethod.GET)
    public ResponseEntity<Map<Long,List<AttachmentDTO>>> getPublicAttachmentsForGivenPosts(
            @NotNull @Valid @RequestParam(value="postIds", required = true) Set<Long> postIds,
            @NotNull @RequestParam(value = "tenantLogin",required = true) String tenantLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getPublicAttachmentsForGivenPosts(postIds,tenantLogin));
    }

    @ApiOperation(value = "Get Public Attachments For PostsV3", nickname = "getPublicAttachmentsForGivenPostsV3", notes = "", response = Map.class,
            tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class) })
    @RequestMapping(value = "/api/v3/post/attachments/public",
            method = RequestMethod.GET)
    public ResponseEntity<Map<Long,List<AttachmentDTO>>> getPublicAttachmentsForGivenPostsV3(
            @NotNull @Valid @RequestParam(value="postIds", required = true) Set<Long> postIds) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getPublicAttachmentsForGivenPostsV3(postIds));
    }


    @ApiOperation(value = "Get Public Responses For Posts", nickname = "getPublicResponsesForPosts", notes = "", response = Map.class,
            tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class) })
    @RequestMapping(value = "/api/v1/post/response/public",
            method = RequestMethod.GET)
    public ResponseEntity<Map<Long,List<ResponseDTO>>> getPublicResponsesForPosts(
            @NotNull @Valid @RequestParam(value="postIds", required = true) Set<Long> postIds,
            @NotNull @RequestParam(value="size", required = true) Integer size,
            @NotNull @RequestParam(value="tenantLogin", required = true) String tenantLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getPublicResponsesForPosts(postIds,size,tenantLogin));
    }


    @ApiOperation(value = "Fetch Post resources", nickname = "getPostBlogResources", notes = "",
            response = PostAttachmentResourceVO .class, responseContainer = "Set", tags={ "Post", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostAttachmentResourceVO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/posts/attachments/resources",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<PostAttachmentResourceVO>> getPostBlogResources(
            @RequestParam(value = "page", required = true) Integer page,
            @RequestParam(value = "size", required = true) Integer size,
            @RequestParam(value = "tenantLogin", required = true) String tenantLogin,
            @RequestParam(value = "appContext", required = false) String appContext,
            @RequestParam(value = "domainContext", required = false) String domainContext,
            @RequestParam(value = "languageContext", required = false) String languageContext,
            @RequestParam(value = "locationContext", required = false) String locationContext,
            @RequestParam(value = "postType", required = false) String postType,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "taggedToEntityType", required = false) String taggedToEntityType,
            @RequestParam(value = "taggedToEntityId", required = false) Long taggedToEntityId,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "subCategory", required = false) String subCategory,
            @RequestParam(value = "status", required = false) String status) {
        PageDTO<PostAttachmentResourceVO> postAttachmentResources = postFacade.getPostBlogResources(tenantLogin,
                appContext,domainContext,languageContext,locationContext,postType,taggedToEntityType,taggedToEntityId,
                page,size,sortBy,direction,category,subCategory,status);
        return ResponseEntity.status(HttpStatus.OK).body(postAttachmentResources);
    }

    @Override
    public ResponseEntity<PageDTO<PostDTO>> fetchPostForAdmin(Integer page, Integer size, String appContext, String postType,
                                                              Set<String> postSubTypes, String sortBy, String direction,
                                                              String category, String subCategory, String status,
                                                              String actorType, Long actorId,String excludeStatus) {
        PageDTO<PostDTO> result = postFacade.fetchPostForAdmin(appContext,postType,postSubTypes,sortBy,direction,category,
                subCategory,status,actorType,actorId,page,size,excludeStatus);
        return ResponseEntity.status(result.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<Void> patchUpdatePostAndAssignCertificate(PostDTO postDTO,Boolean sendMail) {
        postFacade.patchUpdatePostAndAssignCertificate(postDTO, sendMail);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Integer> changeStatusAndAssignCertificateInBulk( Set<Long> postIds, String status, Boolean assignCertificate,
                                                                        Long tenantCertificateTemplateId, Boolean sendMail){
        Integer taskId = postFacade.changeStatusAndAssignCertificateInBulk(postIds,status,
                assignCertificate,tenantCertificateTemplateId,sendMail);
        return ResponseEntity.status(HttpStatus.OK).body(taskId);
    }
    @Override
    public ResponseEntity<String> getTaskStatus(Integer taskId) {
        String response = postFacade.getTaskStatus(taskId);
        HttpStatus httpStatus ;
        if(Objects.equals(response, PostConstant.TASK_STATUS_COMPLETED)) {
            httpStatus = HttpStatus.OK;
        }
        else if(Objects.equals(response, PostConstant.TASK_STATUS_IN_PROGRESS)){
            httpStatus = HttpStatus.NO_CONTENT;
        }
        else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ApiOperation(value = "Fetch Post For Admin With Search Filter", nickname = "fetchPostForAdminWithFilter", notes = "", response = PostDTO.class, responseContainer = "Set", tags={ "Post", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v2/post/admin",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<PostDTO>> fetchPostForAdminWithSearch(@NotNull @Valid @RequestParam(value = "page") Integer page,
                                                                 @NotNull @Valid @RequestParam(value = "size") Integer size,
                                                                 @Valid @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                 @Valid @RequestParam(value = "direction", required = false) String direction,
                                                                 @NotNull @Valid @RequestParam(value = "appContext") String appContext,
                                                                 @Valid @RequestParam(value = "postType", required = false) String postType,
                                                                 @Valid @RequestParam(value = "postSubTypes", required = false) Set<String> postSubTypes,
                                                                 @Valid @RequestParam(value = "category", required = false) String category,
                                                                 @Valid @RequestParam(value = "subCategory", required = false) String subCategory,
                                                                 @Valid @RequestParam(value = "status", required = false) String status,
                                                                 @Valid @RequestParam(value = "excludeStatus", required = false) String excludeStatus,
                                                                 @Valid @RequestParam(value = "actorId", required = false) Long actorId,
                                                                 @Valid @RequestParam(value = "searchText", required = false) String searchText,
                                                                 @Valid @RequestParam(value = "actorType", required = false) String actorType,
                                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") @Valid @RequestParam(value = "startPostTime", required = false) Date startPostTime,
                                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") @Valid @RequestParam(value = "endPostTime", required = false) Date endPostTime,
                                                                 @Valid @RequestParam(value = "causes", required = false) Set<String> causes){

        PageDTO<PostDTO> list = postFacade.fetchPostForAdminWithSearch(appContext,postType,postSubTypes,category,subCategory,
                status,excludeStatus,actorId,searchText,actorType,startPostTime,endPostTime,page,size,sortBy,direction,causes);
        return ResponseEntity.status(list.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(list);
    }

    @ApiOperation(value = "Fetch Post Count Analytics By User Id", responseContainer = "Set", response = PostTypeCountVO.class, tags={ "Post", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostTypeCountVO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/post/analytics/public",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<PostTypeCountVO>> getPostCountAnalyticsByUserId(@NotNull @Valid @RequestParam(value = "userId") Long userId,
                                                                        @Valid @RequestParam(value = "postTypes",required = false) Set<String> postTypes,
                                                                        @Valid @RequestParam(value = "appContext", required = false) String appContext,
                                                                        @Valid @RequestParam(value = "domainContext", required = false) String domainContext,
                                                                        @Valid @RequestParam(value = "tenantLogin") String tenantLogin){

        List<PostTypeCountVO> list = postFacade.getPostCountAnalyticsByUserId(postTypes,appContext,domainContext,userId,tenantLogin);
        return ResponseEntity.status(list == null ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(list);
    }


    @Override
    public ResponseEntity<PageDTO<PostDTO>> fetchPostsForLoggedInUser(Integer page, Integer size, String appContext,
                                                                      String domainContext, String postType, String postSubType,
                                                                      String sortBy, String direction, String status) {
        PageDTO<PostDTO> result = postFacade.fetchPostsForLoggedInUser(appContext,domainContext,postType,postSubType,sortBy,
                                                                    direction,status,page,size);
        return ResponseEntity.status(result.hasNext()?HttpStatus.PARTIAL_CONTENT :HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<PageDTO<PostDTO>> fetchPostsForLoggedInUserV2(Integer page, Integer size, String appContext,
                                                                        String domainContext, String postType, String postSubType,
                                                                        String sortBy, String direction, Set<String> includeStatusSet,
                                                                        Set<String> excludeStatusSet) {
        PageDTO<PostDTO> result = postFacade.fetchPostsForLoggedInUserV2(appContext,domainContext,postType,postSubType,sortBy,
                direction,includeStatusSet,excludeStatusSet,page,size);
        return ResponseEntity.status(result.hasNext()?HttpStatus.PARTIAL_CONTENT :HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Fetch All Linked Entity Codes by Type and SubType", nickname = "", notes = "",
            responseContainer = "Set", tags={ "Post", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/post/linked-entity-codes/me",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Set<PostVO>> getPostsWithLinkedEntityCodeForLoggedInUser(@RequestParam(value = "postType") String postType,
                                                                                    @RequestParam(value = "postSubType", required = false) String postSubType,
                                                                                    @RequestParam(value = "status", required = false) String status){
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getPostsWithLinkedEntityCodeForLoggedInUser(postType, postSubType, status));
    }



    @Override
    public ResponseEntity<PageDTO<PostDTO>> fetchPublishedPostsByUserId(Integer page, Integer size, String tenantLogin,
                                                                        Long userId, String appContext, String domainContext,
                                                                        String postType, String postSubType, String sortBy, String direction) {
        PageDTO<PostDTO> result = postFacade.fetchPublishedPostsByUserId(appContext,domainContext,postType,postSubType,sortBy,
                direction,page,size,tenantLogin,userId);
        return ResponseEntity.status(result == null ? HttpStatus.NO_CONTENT : result.hasNext() ? HttpStatus.PARTIAL_CONTENT :HttpStatus.OK).body(result);
    }


    @ApiOperation(value = "Get All Published Post Attachments Along with PostIds By User Id", notes = "", response = PostAttachmentVO.class,responseContainer = "Set",tags={ "Post"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostAttachmentVO.class,responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/post/user/attachments/public",
            method = RequestMethod.GET)
    public ResponseEntity<PageDTO<PostAttachmentVO>> fetchPublishedPostAttachmentsByUserId(@NotNull @Valid @RequestParam(value = "page") Integer page,
                                                                                           @NotNull @Valid @RequestParam(value = "size") Integer size,
                                                                                           @NotNull @Valid @RequestParam(value = "userId") Long userId,
                                                                                           @Valid @RequestParam(value = "appContext", required = false) String appContext,
                                                                                           @Valid @RequestParam(value = "domainContext", required = false) String domainContext,
                                                                                           @Valid @RequestParam(value = "postType", required = false) String postType,
                                                                                           @Valid @RequestParam(value = "postSubType", required = false) String postSubType,
                                                                                           @Valid @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                                           @Valid @RequestParam(value = "direction", required = false) String direction,
                                                                                           @NotNull @Valid @RequestParam(value = "tenantLogin") String tenantLogin) {
        PageDTO<PostAttachmentVO> result = postFacade.fetchPublishedPostAttachmentsByUserId(appContext,domainContext,postType,postSubType,sortBy,
                direction,page,size,tenantLogin,userId);
        return ResponseEntity.status(result == null ? HttpStatus.NO_CONTENT : result.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(result);
    }


    @Override
    public ResponseEntity<ReactionForUpdateDTO> getMyReactionAndTotalCount(Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.getMyReactionAndTotalCount(postId));
    }

    @ApiOperation(value = "ReIndex Post Data In Hibernate Search", nickname = "reindexSearchData", notes = "",  tags={ "Post", })
    @RequestMapping(value = "/api/v1/post/re-index",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> reindexSearchData(){
        searchIndexingUtil.reindexAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Fetch Post By SearchText",  notes = "", response = PostDTO.class, responseContainer = "Set", tags={ "Post", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/post/search/public",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<PostDTO>> fetchPostBySearchText(@NotNull @Valid @RequestParam(value = "page") Integer page,
                                                           @NotNull @Valid @RequestParam(value = "size") Integer size,
                                                           @Valid @RequestParam(value = "sortBy", required = false) String sortBy,
                                                           @Valid @RequestParam(value = "direction", required = false) String direction,
                                                           @NotNull @Valid @RequestParam(value = "appContext") String appContext,
                                                           @ApiParam(value="status",defaultValue = "POST_STATUS.PUBLISHED")@Valid @RequestParam(required = false) String status,
                                                           @ApiParam(value="isPublic",defaultValue = "true")@Valid @RequestParam(required = false) Boolean isPublic,
                                                           @ApiParam(value="visibility",allowableValues = "VISIBLE,NOT_VISIBLE",defaultValue = "VISIBLE")@Valid @RequestParam(required = false) String visibility,
                                                           @Valid @RequestParam(value = "domainContext",required = false) String domainContext,
                                                           @Valid @RequestParam(value = "category", required = false) String category,
                                                           @Valid @RequestParam(value = "subCategory", required = false) String subCategory,
                                                           @RequestParam(value = "languageContext", required = false) String languageContext,
                                                           @Valid @RequestParam(value = "postType", required = false) String postType,
                                                           @Valid @RequestParam(value = "postSubType", required = false) String postSubType,
                                                           @Valid @RequestParam(value = "searchText", required = false) String searchText,
                                                           @Valid @RequestParam(value = "isModerated", required = false) Boolean isModerated,
                                                           @NotNull @Valid @RequestParam(value = "tenantLogin") String tenantLogin,
                                                           @Valid @RequestParam(value = "includeBaseColumnForLanguage", required = false) Boolean includeBaseColumnForLanguage){
        PageDTO<PostDTO> list = postFacade.fetchPostBySearchText(appContext,postType,searchText,page,size,direction,sortBy,
                tenantLogin,isModerated,domainContext,postSubType,status,isPublic,visibility, category, subCategory,languageContext, includeBaseColumnForLanguage);
        return ResponseEntity.status(list.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(list);
    }

    @ApiOperation(value = "Sync Posts To Search Engine", response = Void.class,  tags={ "Post", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/api/v1/posts/sync/search-engine",
            produces = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> syncPostsToSearchEngine(@RequestParam(name="postIds",required = false) Set<Long> postIds,
                                   @RequestParam(name="pageSize",required = false) Integer pageSize,
                                   @RequestParam(name="pageNumbers",required = false) Integer pageNumbers) {
        postFacade.syncPostsToSearchEngine(postIds,pageSize,pageNumbers);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Get post Actors of response,react and createdBy By PostIds", response = Map.class, tags={ "Post" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Map.class) })
    @RequestMapping(value = "/api/v1/posts/active-members",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Map<Long,List<PostActiveMemberVO>>> findActiveMembersForPosts(
            @NotNull @RequestParam(name="postIds") Set<Long> postIds,
            @NotNull @Valid @RequestParam(value = "tenantLogin") String tenantLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.findActiveMembersForPosts(postIds, tenantLogin));
    }

    @ApiOperation(value = "Fetch Public TimelineV3", nickname = "fetchPublicTimelineV3", notes = "", response = PostDTO.class,
            responseContainer = "Set", tags={ "Post", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v3/post/timeline",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<PostDTO>> fetchPublicTimelineV3(@NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
                                                         @NotNull @Valid @RequestParam(value = "size", required = true) Integer size,
                                                         @Valid @RequestParam(value = "appContext", required = false) String appContext,
                                                         @Valid @RequestParam(value = "domainContext", required = false) String domainContext,
                                                         @Valid @RequestParam(value = "languageContext", required = false) String languageContext,
                                                         @Valid @RequestParam(value = "locationContext", required = false) String locationContext,
                                                         @Valid @RequestParam(value = "postType", required = false) String postType,
                                                         @Valid @RequestParam(value = "postSubTypes", required = false) Set<String> postSubTypes,
                                                         @Valid @RequestParam(value = "sortBy", required = false) String sortBy,
                                                         @Valid @RequestParam(value = "direction", required = false) String direction,
                                                         @Valid @RequestParam(value = "taggedToEntityType", required = false) String taggedToEntityType,
                                                         @Valid @RequestParam(value = "taggedToEntityId", required = false) Long taggedToEntityId,
                                                         @Valid @RequestParam(value = "category", required = false) String category,
                                                         @Valid @RequestParam(value = "subCategory", required = false) String subCategory,
                                                         @Valid @RequestParam(value = "status", required = false) String status,
                                                         @Valid @RequestParam(value = "causes", required = false) Set<String> causes,
                                                         @Valid @RequestParam(value = "actorType", required = false) String actorType,
                                                         @Valid @RequestParam(value = "actorId", required = false) Long actorId,
                                                         @ApiParam(value = "fetch",allowableValues ="FETCH_TYPE.COMMENT,FETCH_TYPE.LIKE,FETCH_TYPE.LATEST")
                                                         @Valid @RequestParam( required = false) String fetch,
                                                         @Valid @RequestParam(value = "isModerated", required = false) Boolean isModerated,
                                                         @Valid @RequestParam(value = "responseCount", required = false) Long responseCount,
                                                         @Valid @RequestParam(value = "includeBaseColumnForLanguage", required = false) Boolean includeBaseColumnForLanguage,
                                                         @Valid @RequestParam(value = "excludeReportedPost", required = false) Boolean excludeReportedPost) {
        PageDTO<PostDTO> results= postFacade.fetchPublicTimelineV3(appContext,domainContext,languageContext,
                locationContext, postType,postSubTypes,taggedToEntityType,taggedToEntityId,page,size,sortBy,direction,category,
                subCategory,status,causes,actorType,actorId,fetch,isModerated,includeBaseColumnForLanguage,responseCount, excludeReportedPost);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT :HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "Patch Post For Admin and created User", nickname = "patchUpdateForAdminAndCreatedUser", notes = "", response = PostDTO.class, tags={ "Post", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostDTO.class) })
    @RequestMapping(value = "/api/v4/post",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<PostDTO> patchUpdateWithAttachmentV4(@Valid @RequestBody PostDTO body,
                                                      @Valid @RequestParam(value = "addedAttachmentIds", required = false) Set<Long> addedAttachmentIds,
                                                      @Valid @RequestParam(value = "deleteAttachmentIds", required = false) Set<Long> deleteAttachmentIds){
        return ResponseEntity.status(HttpStatus.OK).body(postFacade.patchUpdateWithAttachmentV4(body,addedAttachmentIds,deleteAttachmentIds));
    }

}
