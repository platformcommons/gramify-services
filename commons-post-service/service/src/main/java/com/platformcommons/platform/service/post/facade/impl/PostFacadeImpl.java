package com.platformcommons.platform.service.post.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.post.application.AttachmentInfoService;
import com.platformcommons.platform.service.post.application.PostService;
import com.platformcommons.platform.service.post.application.PostTenantConfigService;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.application.utility.PlatformUtil;
import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.Response;
import com.platformcommons.platform.service.post.domain.vo.*;
import com.platformcommons.platform.service.post.dto.AuthorResponseDTO;
import com.platformcommons.platform.service.post.dto.PostDTO;
import com.platformcommons.platform.service.post.dto.ReactionForUpdateDTO;
import com.platformcommons.platform.service.post.dto.ResponseDTO;
import com.platformcommons.platform.service.post.facade.PostFacade;
import com.platformcommons.platform.service.post.facade.ResponseFacade;
import com.platformcommons.platform.service.post.facade.assembler.PostDTOAssembler;
import com.platformcommons.platform.service.post.facade.assembler.ResponseDTOAssembler;
import com.platformcommons.platform.service.post.messaging.producer.PostProducer;
import com.platformcommons.platform.service.utility.JPAUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.search.query.SearchResultTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Transactional
@Slf4j
public class PostFacadeImpl implements PostFacade {

    private final Map<Integer, CompletableFuture<String>> taskMap = new ConcurrentHashMap<>();

    private final Random random = new Random();

    @Autowired
    private PostService service;
    @Autowired
    private PostDTOAssembler assembler;

    @Autowired
    private ResponseDTOAssembler responseDTOAssembler;

    @Autowired
    private PostTenantConfigService postTenantConfigService;

    @Autowired(required = false)
    private PostProducer postProducer;

    @Autowired
    private AttachmentInfoService attachmentInfoService;

    @Autowired
    private PlatformUtil platformUtil;

    @Autowired
    private ResponseFacade responseFacade;


    @Override
    public Long save(PostDTO postDTO) {
        Post post = service.save(assembler.fromDTO(postDTO));
        Runnable runnable = () -> {
            if(postProducer != null){
                postProducer.postCreated(assembler.toDTO(post));
            }
        };
        platformUtil.registerTransactionSynchronization(runnable);
        return post.getId();
    }

    @Override
    public Long savePostWithAttachment(PostDTO body, MultipartFile file) throws IOException {
        Post post = service.createPostWithAttachment(assembler.fromDTO(body),file);
        Runnable runnable = () -> {
            if(postProducer != null){
                postProducer.postCreated(assembler.toDTO(post));
            }
        };
        platformUtil.registerTransactionSynchronization(runnable);
        return post.getId();
    }

    @Override
    public Long savePostWithMultipleAttachments(PostDTO postDTO, Set<Long> attachmentIds) {
        Post post = service.savePostWithMultipleAttachments(assembler.fromDTO(postDTO),attachmentIds);
        Set<AuthorResponseDTO> authorResponseDTOS = postDTO.getAuthorResponses();
        if(authorResponseDTOS!=null && !authorResponseDTOS.isEmpty()){
            responseFacade.createAuthorResponses(post.getId(),PostConstant.ENTITY_TYPE_POST, authorResponseDTOS);
        }

        Runnable runnable = () -> {
            if(postProducer != null){
                postProducer.postCreated(assembler.toDTO(post));
            }
        };
        platformUtil.registerTransactionSynchronization(runnable);

        return post.getId();
    }

    @Override
    public PostDTO update(PostDTO postDTO) {
        PostDTO updatedPostDTO = assembler.toDTO(service.update(assembler.fromDTO(postDTO), true));
        Runnable runnable = () ->{
            if (postProducer != null) {
                postProducer.postUpdated(updatedPostDTO);
            }
        };
        platformUtil.registerTransactionSynchronization(runnable);
        return updatedPostDTO;
    }


    @Override
    public PostDTO patchUpdateForLoggedInUser(PostDTO postDTO,Set<Long> addedAttachmentIds,Set<Long> deleteAttachmentIds) {
        PostDTO updatedPostDTO = assembler.toDTO(service.patchUpdateForLoggedInUser(assembler.fromDTO(postDTO),addedAttachmentIds,deleteAttachmentIds));
        Runnable runnable = () ->{
            if (postProducer != null) {
                postProducer.postUpdated(updatedPostDTO);
            }
        };
        platformUtil.registerTransactionSynchronization(runnable);
        return updatedPostDTO;
    }



    @Override
    public PostDTO patchUpdateWithAttachment(PostDTO postDTO, Set<Long> addedAttachmentIds, Set<Long> deleteAttachmentIds) {
        PostDTO updatedPostDTO = assembler.toDTO(service.patchUpdateWithAttachment(assembler.fromDTO(postDTO),addedAttachmentIds,deleteAttachmentIds));
        Runnable runnable = () ->{
            if (postProducer != null) {
                postProducer.postUpdated(updatedPostDTO);
            }
        };
        platformUtil.registerTransactionSynchronization(runnable);
        return updatedPostDTO;
    }


    @Override
    public PostDTO patchUpdateWithAttachmentReplace(Long postId, String attachmentDecision, PostDTO postDTO, MultipartFile file) {
        return assembler.toDTO(service.patchUpdateWithAttachmentReplace(postId,attachmentDecision,assembler.fromDTO(postDTO),file));
    }

    @Override
    public void delete(Long id, String identifier, String reason) {
        service.deletePost(id, identifier, reason);
        if (postProducer != null) {
            postProducer.postDeleted(id);
        }
    }

    @Override
    public PostDTO getById(Long id) {
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public PostDTO getByIdForPublic(Long id, String tenantLogin) {
        Post post = service.getByIdForPublic(id,tenantLogin);
        return assembler.toDTO(post);
    }

    @Override
    public PostDTO getPostByUUIDForPublic(String uuid) {
        Post post = null;
        if(PlatformSecurityUtil.isPlatformAppToken()) {
            post = service.getByUUIDNonMT(uuid);
        }
        else {
            post = service.getByUUId(uuid);
        }
        return assembler.toDTO(post);
    }

    @Override
    public PostDTO findPost(Long postId, String postIdentifier) {
        return assembler.toDTO(service.findPost(postId, postIdentifier));
    }

    @Override
    public PageDTO<PostDTO> getByPage(Integer page, Integer size) {
        Slice<Post> fetchedSlice=service.getByPage(page,size);
        return new PageDTO<>(fetchedSlice
                .stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)),fetchedSlice.hasNext(),fetchedSlice.getNumberOfElements());
    }

    @Override
    public AttachmentDTO createAttachment(Long postId, MultipartFile file, String attachmentName) throws IOException {
         return assembler.toDTO(service.createAttachmentForPost(postId,file,attachmentName));
    }

    @Override
    public List<AttachmentDTO> getAttachment(Long postId) {
        return service.getAttachmentForPost(postId);
    }

    @Override
    public List<AttachmentDTO> getAttachmentsByPostIdNonMT(Long postId) {
        return attachmentInfoService.getAttachmentsByEntityIdAndEntityType(postId,PostConstant.ENTITY_TYPE_POST);
    }


    @Override
    public List<AttachmentDTO> getAttachmentsByPostUUIDForPublic(String postUUID) {
        List<AttachmentDTO> attachmentDTOList = null;
        if(PlatformSecurityUtil.isPlatformAppToken()) {
            attachmentDTOList = service.getAttachmentsByPostUUIDNonMT(postUUID);
        }
        else {
            attachmentDTOList = service.getAttachmentsByPostUUID(postUUID);
        }
        return attachmentDTOList;
    }

    @Override
    public Map<Long, List<AttachmentDTO>> getAttachmentsForGivenPosts(Set<Long> postIds) {
         return service.getAttachmentsForGivenPosts(postIds);
    }

    @Override
    public Map<Long, List<AttachmentDTO>> getPublicAttachmentsForGivenPosts(Set<Long> postIds, String tenantLogin) {
        if (PlatformSecurityUtil.isPlatformAppToken()) {
            return service.getPublicAttachmentsForGivenPosts(postIds,tenantLogin);
        }
        else {
            return service.getAttachmentsForGivenPosts(postIds);
        }
    }

    @Override
    public Map<Long, List<AttachmentDTO>> getPublicAttachmentsForGivenPostsV3(Set<Long> postIds) {
        return service.getPublicAttachmentsForGivenPostsV3(postIds);
    }

    @Override
    public Map<Long, List<ResponseDTO>> getResponsesForPosts(Set<Long> postIds, Integer size) {
        Map<Long,List<Response>> responseList= service.getResponseForPosts(postIds,size);
        Map<Long,List<ResponseDTO>> responseListOfDTO= responseList.entrySet()
                                 .stream()
                                 .collect(Collectors.toMap(Map.Entry::getKey,entry-> entry.getValue().stream()
                                         .map(responseDTOAssembler::toDTO)
                                    .collect(Collectors.toList())));
        return responseListOfDTO;
    }

    public PageDTO<PostDTO> fetchTimeline(String appContext, String domainContext,
                                          String languageContext, String locationContext,
                                          String postType,String taggedEntityType,Long taggedEntityId,
                                          Integer page, Integer size, String sortBy, String direction,String category,
                                          String subCategory, String status) {
        Slice<Post> result = service.fetchTimeline(appContext, domainContext, languageContext,
                locationContext, postType,taggedEntityType,taggedEntityId, page, size, JPAUtility.convertToSort(sortBy, direction),category,subCategory,status);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getNumberOfElements());
    }

    @Override
    public PostDTO patchUpdate(PostDTO postDTO) {
        PostDTO updatedPostDTO = assembler.toDTO(service.update(assembler.fromDTO(postDTO), false));
        Runnable runnable = () -> {
            if (postProducer != null) {
                postProducer.postUpdated(updatedPostDTO);
            }
        };
        platformUtil.registerTransactionSynchronization(runnable);
        return updatedPostDTO;
    }

    @Override
    public Set<PostAttachmentVO> fetchTimelineWithAttachments(String appContext, String domainContext,
                                                                                 String languageContext, String locationContext,
                                                                                 String postType, String taggedEntityType, Long taggedEntityId,
                                                                                 Integer page, Integer size, String sortBy, String direction) {
        Slice<PostAttachmentVO> result = service.fetchTimelineWithAttachments(appContext, domainContext, languageContext,
                locationContext, postType,taggedEntityType,taggedEntityId, page, size, JPAUtility.convertToSort(sortBy, direction));
        return result.toSet();
    }

    @Override
    public Set<PostAttachmentVO> fetchPublicTimelineWithAttachments(String tenantLogin,
                                                                    String appContext, String domainContext,
                                                                    String languageContext, String locationContext,
                                                                    String postType, String taggedEntityType, Long taggedEntityId,
                                                                    Integer page, Integer size, String sortBy, String direction) {
        Slice<PostAttachmentVO> result;
        if (PlatformSecurityUtil.isPlatformAppToken()) {
            result = service.fetchPublicTimelineWithAttachments(tenantLogin, appContext, domainContext, languageContext,
                    locationContext, postType, taggedEntityType, taggedEntityId, page, size, JPAUtility.convertToSort(sortBy, direction));
        }
        else {
            result = service.fetchTimelineWithAttachments(appContext,domainContext,languageContext,locationContext,
                    postType,taggedEntityType,taggedEntityId,page,size,JPAUtility.convertToSort(sortBy, direction));
        }
        return result.toSet();
    }

    @Override
    public PageDTO<PostDTO> fetchPublicTimeline(String tenantLogin,String appContext, String domainContext,
                                                String languageContext, String locationContext, String postType,Set<String> postSubTypes,
                                                String taggedEntityType, Long taggedEntityId, Integer page, Integer size,
                                                String sortBy, String direction,String category,String subCategory,
                                                String status,Set<String> causes,String actorType,Long actorId,String fetch, Boolean isModerated,
                                                Boolean includeBaseColumnForLanguage,Long responseCount, Boolean excludeReportedPost) {
        Set<String> statusToBeIgnored = new HashSet<>(Arrays.asList(PostConstant.POST_STATUS_PENDING_APPROVAL,
                PostConstant.POST_STATUS_REJECTED));
        if(includeBaseColumnForLanguage == null) {
            includeBaseColumnForLanguage = Boolean.TRUE;
        }

        Sort sort = computeSortBasedOnFetchType(fetch,sortBy,direction);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Post> result;
        if (PlatformSecurityUtil.isPlatformAppToken()) {
            result = service.fetchPublicTimeline(tenantLogin, appContext, domainContext, languageContext,
                    locationContext, postType,postSubTypes, taggedEntityType, taggedEntityId,category,subCategory,
                    status,causes,actorType,actorId,statusToBeIgnored,pageable,isModerated, includeBaseColumnForLanguage,
                    responseCount, excludeReportedPost);
        }
        else {
            result = service.fetchTimelineV2(appContext, domainContext, languageContext,
                    locationContext, postType,postSubTypes,taggedEntityType,taggedEntityId,category,subCategory,
                    status,causes,actorType,actorId,statusToBeIgnored,pageable, isModerated, includeBaseColumnForLanguage,
                    responseCount, excludeReportedPost);
        }
        Set<PostDTO> postDTOS = result.stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        filterLanguageContextTexts(languageContext,postDTOS);
        return new PageDTO<>(postDTOS, result.hasNext(),result.getTotalElements());
    }

    public void filterLanguageContextTexts(String languageContext,Set<PostDTO> postDTOSet) {
        if(languageContext != null) {
            postDTOSet.forEach(postDTO -> {
                if(postDTO.getTitles() != null) {
                    postDTO.getTitles().removeIf(title->!Objects.equals(title.getLanguageContext(),languageContext));
                }
                if(postDTO.getPayloads() != null) {
                    postDTO.getPayloads().removeIf(payload->!Objects.equals(payload.getLanguageContext(),languageContext));
                }
                if(postDTO.getContents() != null) {
                    postDTO.getContents().removeIf(content->!Objects.equals(content.getLanguageContext(),languageContext));
                }
            });
        }
    }

    public Sort computeSortBasedOnFetchType(String fetch,String sortBy, String direction) {
        Sort sort = null;
        if(fetch != null) {
            switch (fetch) {
                case PostConstant.FETCH_TYPE_LATEST:
                    sort = JPAUtility.convertToSort("id", "DESC");
                    break;
                case PostConstant.FETCH_TYPE_LIKE:
                    sort = JPAUtility.convertToSort("reactCount", "DESC");
                    break;
                case PostConstant.FETCH_TYPE_COMMENT:
                    sort = JPAUtility.convertToSort("commentCount", "DESC");
                    break;
                default:
                    throw new InvalidInputException("fetch type is invalid");
            }
        }
        else {
            sort = JPAUtility.convertToSort(sortBy,direction);
        }
        return sort;
    }

    @Override
    public void createUserReadEventForPost(Long postId) {
        service.createUserReadEventForPost(postId);
    }

    @Override
    public Map<Long, List<ResponseDTO>> getPublicResponsesForPosts(Set<Long> postIds, Integer size, String tenantLogin) {
        Map<Long, List<Response>> responseList;
        if (PlatformSecurityUtil.isPlatformAppToken()) {
            responseList = service.getPublicResponseForPosts(postIds, size, tenantLogin);
        }
        else {
            responseList = service.getResponseForPosts(postIds,size);
        }
        Map<Long, List<ResponseDTO>> responseListOfDTO = responseList.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,entry-> entry.getValue().stream()
                        .map(responseDTOAssembler::toDTO)
                        .collect(Collectors.toList())));
        return responseListOfDTO;
    }

    @Override
    public PostDTO patchPostStatus(PostDTO postDTO) {
        return assembler.toDTO(service.patchPostStatus(assembler.fromDTO(postDTO)));
    }

    @Override
    public PageDTO<PostAttachmentResourceVO> getPostBlogResources(String tenantLogin,String appContext,
                                                               String domainContext, String languageContext,
                                                               String locationContext, String postType,
                                                               String taggedEntityType, Long taggedEntityId,
                                                               Integer page, Integer size, String sortBy,
                                                               String direction,String category,String subCategory,
                                                               String status) {

        Page<PostAttachmentResourceVO> result =  service.getPostBlogResources(tenantLogin,appContext,domainContext,languageContext,locationContext,
                postType,taggedEntityType,taggedEntityId,page,size,
                JPAUtility.convertToSort(sortBy,direction),category,subCategory,status);
        return new PageDTO<>(new LinkedHashSet<>(result.getContent()), result.hasNext(),result.getTotalElements());
    }

    @Override
    public PageDTO<PostDTO> fetchPostForAdmin(String appContext, String postType, Set<String> postSubTypes, String sortBy,
                                              String direction, String category, String subCategory, String status, String actorType,
                                              Long actorId, Integer page, Integer size, String excludeStatus) {
        if(!PlatformSecurityUtil.isTenantAdmin() && !PlatformSecurityUtil.hasRole(PostConstant.PROLE_POST_ADMIN)) {
            if(postSubTypes != null && postSubTypes.contains(PostConstant.POST_SUB_TYPE_IMPACT_TYPE)) {
                status = PostConstant.POST_STATUS_APPROVED;
                excludeStatus = null;
            }
        }
        Page<Post> result = service.fetchPostForAdmin(appContext,postType,postSubTypes,category, subCategory,status,actorType,
                actorId,excludeStatus, PageRequest.of(page,size,JPAUtility.convertToSort(sortBy,direction)));

        return new PageDTO<>(result.toSet()
                .stream()
                .map(assembler::toDTO)
                .collect(Collectors.toSet()),result.hasNext(),result.getTotalElements() );
    }

    @Override
    public void patchUpdatePostAndAssignCertificate(PostDTO postDTO,Boolean sendMail) {
        if(PlatformSecurityUtil.isTenantAdmin() || PlatformSecurityUtil.hasRole(PostConstant.PROLE_POST_ADMIN)) {
            PostDTO savedPostDTO = assembler.toDTO(service.patchUpdatePostAndAssignCertificate(assembler.fromDTO(postDTO), sendMail));
            Runnable runnable = () ->{
                if (postProducer != null){
                    postProducer.postUpdated(savedPostDTO);
                }
            };
            platformUtil.registerTransactionSynchronization(runnable);
        }
        else {
            throw new UnAuthorizedAccessException("Unauthorized to patch a post");
        }
    }

    @Override
    public Integer changeStatusAndAssignCertificateInBulk(Set<Long> postIds, String status, Boolean assignCertificate,
                                                       Long tenantCertificateTemplateId, Boolean sendMail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(PlatformSecurityUtil.isTenantAdmin() || PlatformSecurityUtil.hasRole(PostConstant.PROLE_POST_ADMIN)) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {

                SecurityContextHolder.getContext().setAuthentication(authentication);
                List<PostDTO> postDTOList = service.changeStatusAndAssignCertificateInBulk(
                        postIds, status, assignCertificate, tenantCertificateTemplateId, sendMail
                );

                Runnable runnable = () ->{
                    if (postProducer != null) {
                        postProducer.postUpdatedBulk(postDTOList);
                    }
                };
                platformUtil.registerTransactionSynchronization(runnable);

                return PostConstant.TASK_STATUS_COMPLETED;
            });
            Integer randomValue =  Math.abs(this.random.nextInt());
            taskMap.put(randomValue,future);
            return  randomValue;
        }
        else {
            throw new UnAuthorizedAccessException("Unauthorized to patch a post");
        }
    }

    @Override
    public String getTaskStatus(Integer taskId) {
        CompletableFuture<String> future = taskMap.get(taskId);
        if(future == null) {
            throw new InvalidInputException(String.format("Task with id - %d not found",taskId));
        }
        else if(future.isDone()) {
            try {
                String response =  future.get();
                taskMap.remove(taskId);
                return response;

            } catch (Exception e) {
                taskMap.remove(taskId);
                throw new InvalidInputException(String.format("Task with id - %d failed with an exception - %s",taskId,e.getMessage()));
            }
        }
        else{
            return PostConstant.TASK_STATUS_IN_PROGRESS;
        }
    }

    @Override
    public List<PostTypeCountVO> getPostCountAnalyticsByUserId(Set<String> postTypes, String appContext, String domainContext,
                                                                      Long userId,String tenantLogin) {
        List<PostTypeCountVO> result = null;
        if(PlatformSecurityUtil.isPlatformAppToken()) {
            result = service.getPostCountAnalyticsByUserIdForPublic(postTypes,appContext,domainContext,userId,tenantLogin);
        }
        else {
            result = service.getPostCountAnalyticsByUserId(postTypes,appContext,domainContext,userId);
        }
        return result;
    }

    @Override
    public PageDTO<PostDTO> fetchPostsForLoggedInUser(String appContext, String domainContext, String postType, String postSubType,
                                                      String sortBy, String direction, String status, Integer page, Integer size) {
        Page<Post> result = service.fetchPostsForLoggedInUser(appContext, domainContext, postType,postSubType,status,
                PageRequest.of(page,size,JPAUtility.convertToSort(sortBy,direction)));
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());
    }

    @Override
    public PageDTO<PostDTO> fetchPostsForLoggedInUserV2(String appContext, String domainContext, String postType, String postSubType,
                                                 String sortBy, String direction, Set<String> includeStatusSet, Set<String> excludeStatusSet,
                                                 Integer page, Integer size) {
        Page<Post> result = service.fetchPostsForLoggedInUserV2(appContext, domainContext, postType,postSubType,includeStatusSet,
                excludeStatusSet, PageRequest.of(page,size,JPAUtility.convertToSort(sortBy,direction)));
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());
    }


    @Override
    public PageDTO<PostDTO> fetchPublishedPostsByUserId(String appContext, String domainContext, String postType, String postSubType,
                                                        String sortBy, String direction, Integer page, Integer size, String tenantLogin, Long userId) {
        Page<Post> result  = null;
        Set<String> statusToBeIgnored = new HashSet<>(Arrays.asList(PostConstant.POST_STATUS_PENDING_APPROVAL,
                PostConstant.POST_STATUS_REJECTED));
        if(PlatformSecurityUtil.isPlatformAppToken()) {
            result = service.fetchPublishedPostsByUserIdForPublic(appContext, domainContext, postType,postSubType,userId,
                    statusToBeIgnored,tenantLogin, PageRequest.of(page,size,JPAUtility.convertToSort(sortBy,direction)));
        }
        else {
            result = service.fetchPublishedPostsByUserId(appContext, domainContext, postType,postSubType,userId,statusToBeIgnored,
                    PageRequest.of(page,size,JPAUtility.convertToSort(sortBy,direction)));
        }
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());
    }

    @Override
    public PageDTO<PostAttachmentVO> fetchPublishedPostAttachmentsByUserId(String appContext, String domainContext, String postType, String postSubType,
                                                                           String sortBy, String direction, Integer page, Integer size, String tenantLogin, Long userId) {
        Page<PostAttachmentVO> result  = null;
        Set<String> statusToBeIgnored = new HashSet<>(Arrays.asList(PostConstant.POST_STATUS_PENDING_APPROVAL,
                PostConstant.POST_STATUS_REJECTED));
        if(PlatformSecurityUtil.isPlatformAppToken()) {
            result = service.fetchPublishedPostAttachmentsByUserIdForPublic(appContext, domainContext, postType,postSubType,userId,
                    statusToBeIgnored,tenantLogin, PageRequest.of(page,size,JPAUtility.convertToSort(sortBy,direction)));
        }
        else {
            result = service.fetchPublishedPostAttachmentsByUserId(appContext, domainContext, postType,postSubType,userId,statusToBeIgnored,
                    PageRequest.of(page,size,JPAUtility.convertToSort(sortBy,direction)));
        }

        return new PageDTO<>(result.stream()
                .collect(Collectors.toCollection(LinkedHashSet::new)),result.hasNext(),result.getTotalElements() );
    }

    @Override
    public PageDTO<PostDTO> fetchPostForAdminWithSearch(String appContext, String postType, Set<String> postSubTypes, String category,
                                                        String subCategory, String status, String excludeStatus, Long actorId,
                                                        String searchText, String actorType, Date startPostTime, Date endPostTime,
                                                        Integer page, Integer size, String sortBy, String direction, Set<String> causes) {
        if(!PlatformSecurityUtil.isTenantAdmin() && !PlatformSecurityUtil.hasRole(PostConstant.PROLE_POST_ADMIN)) {
            if(postSubTypes != null && postSubTypes.contains(PostConstant.POST_SUB_TYPE_IMPACT_TYPE)) {
                status = PostConstant.POST_STATUS_APPROVED;
                excludeStatus = null;
            }
        }
        SearchResult<Post> result = service.fetchPostForAdminWithSearch(appContext,postType,postSubTypes,category,subCategory,status,excludeStatus,
                actorId,searchText,actorType,startPostTime,endPostTime,page,size,sortBy,direction,causes);
        List<Post> posts = result.hits();
        SearchResultTotal total = result.total();
        return  new PageDTO<>(posts.stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)),Boolean.TRUE,total.hitCount());
    }

    @Override
    public ReactionForUpdateDTO getMyReactionAndTotalCount(Long postId) {
        return service.getMyReactionAndTotalCount(postId);
    }

    @Override
    public PageDTO<PostDTO> fetchPostBySearchText(String appContext, String postType, String searchText, Integer page,
                                                  Integer size, String direction, String sortBy, String tenantLogin,
                                                  Boolean isModerated, String domainContext, String postSubType,
                                                  String status, Boolean isPublic, String visibility, String category,
                                                  String subCategory, String languageContext, Boolean includeBaseColumnForLanguage) {
        Long tenantId = platformUtil.getTenantIdByContext(tenantLogin);
        if(includeBaseColumnForLanguage == null) {
            includeBaseColumnForLanguage = Boolean.TRUE;
        }

        SearchResult<Post> result = service.fetchPostBySearchText(appContext,postType,searchText,page,size,direction,sortBy,
                tenantId,isModerated,domainContext,postSubType,status,isPublic,visibility, category,subCategory,languageContext, includeBaseColumnForLanguage);
        List<Post> posts = result.hits();
        SearchResultTotal total = result.total();
        long totalElements = total.hitCount();
        Boolean hasNext = checkForNextPageInHibernateSearch(page,size,totalElements);
        Set<PostDTO> postDTOS = posts.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (languageContext != null) filterLanguageContextTexts(languageContext, postDTOS);
        return  new PageDTO<>(postDTOS,hasNext,totalElements);
    }

    @Async
    @Override
    @Transactional(noRollbackFor = Exception.class)
    public void syncPostsToSearchEngine(Set<Long> postIds, Integer pageSize, Integer pageNumbers) {
        PlatformUtil.validateTenantAdmin();
        Set<Post> postSet;
        Set<Long> finalPostIds = new HashSet<>();
        if (postIds != null){
            finalPostIds = postIds;
        }
        if(!finalPostIds.isEmpty()) {
            postSet = service.findByIds(finalPostIds);
            produceViaKafka(postSet);
        }else if (pageSize != null) {
            Page<Post> postPage = null;
            Pageable pageable = PageRequest.of(0, pageSize, JPAUtility.convertToSort("id", "ASC"));
            do {
                try {
                    postPage = service.getPostsByTenantId(PlatformSecurityUtil.getCurrentTenantId(), pageable);
                    postSet = postPage.toSet();
                    produceViaKafka(postSet);
                } catch (Exception e) {
                    log.debug(ExceptionUtils.getRootCauseMessage(e));
                }
                pageable = postPage.nextPageable();
            } while (postPage.hasNext() && (pageNumbers == null || pageable.getPageNumber() <= pageNumbers));

        } else {
            log.debug("Enter either postIds or pageSize");
        }
    }

    public void produceViaKafka(Set<Post> postSet) {
        for (Post post : postSet) {
            try {
                PostDTO postDTO = assembler.toDTO(post);
                if (postDTO == null) {
                    postProducer.postDeleted(post.getId());
                } else {
                    postDTO.setIsActive(post.getIsActive());
                    postProducer.postSync(postDTO);
                    log.info(String.format("Post with id %d produced in Kafka", post.getId()));
                }
            } catch (Exception e) {
                log.error(String.format("Post with id %d could not be produced to topic", post.getId()), e);
            }
        }
    }

    public Boolean checkForNextPageInHibernateSearch(Integer page,Integer size,long totalElements) {
        long totalElementsFetched = (((long) page *size) + size);
        long remainingElements = totalElements - totalElementsFetched;
        return remainingElements > 0;
     }


    @Override
    public Map<Long, List<PostActiveMemberVO>>findActiveMembersForPosts(Set<Long> postIds, String tenantLogin) {
        Long tenantId = platformUtil.getTenantIdByContext(tenantLogin);
        return service.findActiveMembersForPosts(postIds, tenantId);
    }

    @Override
    public Set<PostVO> getPostsWithLinkedEntityCodeForLoggedInUser(String postType, String postSubType, String status) {
        Long currentUserId = PlatformSecurityUtil.getCurrentUserId();
        return service.getPostsWithLinkedEntityCodeByUserId(currentUserId, postType, postSubType, status);
    }

    @Override
    public PageDTO<PostDTO> fetchPublicTimelineV3(String appContext, String domainContext,
                                                String languageContext, String locationContext, String postType,Set<String> postSubTypes,
                                                String taggedEntityType, Long taggedEntityId, Integer page, Integer size,
                                                String sortBy, String direction,String category,String subCategory,
                                                String status,Set<String> causes,String actorType,Long actorId,String fetch, Boolean isModerated,
                                                Boolean includeBaseColumnForLanguage,Long responseCount, Boolean excludeReportedPost) {
        Set<String> statusToBeIgnored = new HashSet<>(Arrays.asList(PostConstant.POST_STATUS_PENDING_APPROVAL,
                PostConstant.POST_STATUS_REJECTED));
        if(includeBaseColumnForLanguage == null) {
            includeBaseColumnForLanguage = Boolean.TRUE;
        }

        Sort sort = computeSortBasedOnFetchType(fetch,sortBy,direction);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Post> result;
        result = service.fetchTimelineV3(appContext, domainContext, languageContext,
                locationContext, postType,postSubTypes,taggedEntityType,taggedEntityId,category,subCategory,
                status,causes,actorType,actorId,statusToBeIgnored,pageable, isModerated, includeBaseColumnForLanguage,
                responseCount, excludeReportedPost);

        Set<PostDTO> postDTOS = result.stream()
                .map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        filterLanguageContextTexts(languageContext,postDTOS);
        return new PageDTO<>(postDTOS, result.hasNext(),result.getTotalElements());
    }

    @Override
    public PostDTO patchUpdateWithAttachmentV4(PostDTO postDTO, Set<Long> addedAttachmentIds, Set<Long> deleteAttachmentIds) {
        PostDTO updatedPostDTO = assembler.toDTO(service.patchUpdateWithAttachmentV4(assembler.fromDTO(postDTO),addedAttachmentIds,deleteAttachmentIds));
        Runnable runnable = () ->{
            if (postProducer != null) {
                postProducer.postUpdated(updatedPostDTO);
            }
        };
        platformUtil.registerTransactionSynchronization(runnable);
        return updatedPostDTO;
    }
}
