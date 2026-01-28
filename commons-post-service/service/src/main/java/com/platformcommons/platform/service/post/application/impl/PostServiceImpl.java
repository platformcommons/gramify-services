package com.platformcommons.platform.service.post.application.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.entity.common.UoMValue;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.post.application.*;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.application.utility.NotificationUtility;
import com.platformcommons.platform.service.post.application.utility.PlatformUtil;
import com.platformcommons.platform.service.post.domain.*;
import com.platformcommons.platform.service.post.domain.repo.*;
import com.platformcommons.platform.service.post.domain.vo.*;
import com.platformcommons.platform.service.post.dto.PostDTO;
import com.platformcommons.platform.service.post.dto.ReactionForUpdateDTO;
import com.platformcommons.platform.service.post.facade.assembler.PostDTOAssembler;
import com.platformcommons.platform.service.post.facade.cache.validator.TenantMetaConfigValidator;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.search.sort.dsl.SortOrder;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostActorService postActorService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private PostNonMTRepository postNonMTRepository;

    @Autowired
    private AttachmentInfoService attachmentInfoService;

    @Autowired
    private UserReadEventLogService userReadEventLogService;

    @Autowired
    private ResponseNonMTRepository responseNonMTRepository;

    @Autowired
    private PostActorCertificateService postActorCertificateService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private PostTenantConfigService postTenantConfigService;

    @Autowired
    private NotificationUtility notificationUtility;

    @Autowired
    private PostDTOAssembler postDTOAssembler;
    
    @Autowired
    private TenantMetaConfigValidator tenantMetaConfigValidator;

    @Autowired
    private PlatformUtil platformUtil;

    @Override
    public Post save(Post post) {
        PostActor postActor = postActorService.getOrAddForCurrentUser();
        post.init(postActor);
        if(post.getPostImpacts() != null) {
            post.getPostImpacts().forEach(postImpact -> postImpact.init(post));
        }
        return postRepository.save(post);
    }

    @Override
    public void savePostAfterAttachment(Post post) {
        post.setHasAtttachment(true);
        postRepository.save(post);
    }

    @Override
    public Post createPostWithAttachment(Post post, MultipartFile file) throws IOException {
        Post savedPost = save(post);
        if (file != null) {
            Attachment attachment = createAttachmentForPost(savedPost.getId(), file, null);
            if (savedPost.getVisibility().equals(PostConstant.POST_VISIBILITY_VISIBLE) && attachment.getCompleteURL() != null) {
                savedPost.setAttachmentUrl(attachment.getCompleteURL());
                savedPost.setHasAtttachment(true);
                postRepository.save(savedPost);
            }
        }
        return savedPost;
    }

    @Override
    public Post savePostWithMultipleAttachments(Post post, Set<Long> attachmentIds) {
        Post savedPost = save(post);
        linkAttachmentsToPost(savedPost,attachmentIds);
        return savedPost;
    }

    public void linkAttachmentsToPost(Post post,Set<Long> attachmentIds) {
        if(attachmentIds != null) {
            List<Attachment> attachmentList = attachmentService.getByIds(attachmentIds);
            attachmentList.forEach(attachment -> {
                attachment.setEntityId(post.getId());
                attachment.setEntityType(PostConstant.ENTITY_TYPE_POST);
            });
            attachmentService.saveAll(attachmentList);
            savePostAfterAttachment(post);
        }
    }

    public void deleteAttachmentsToPost(Set<Long> attachmentIds) {
        if(attachmentIds != null) {
            List<Attachment> attachmentList = attachmentService.getByIds(attachmentIds);
            attachmentList.forEach(attachment -> attachment.deactivate("attachment deleted"));
            attachmentService.saveAll(attachmentList);
        }
    }

    @Override
    public Post update(Post post,Boolean mapNulls) {
        Post fetchedPost = findPost(post.getId(), post.getUuid());
        fetchedPost.isAuthorizedForUpdate();
        fetchedPost.update(post, mapNulls);
        return postRepository.save(fetchedPost);
    }

    @Override
    public Post patchUpdateForLoggedInUser(Post post,Set<Long> addedAttachmentIds,Set<Long> deleteAttachmentIds) {
        Post fetchedPost = getByIdForLoggedInUser(post.getId());
        checkForPatchUpdatePermission(fetchedPost);
        fetchedPost.patchUpdateForPostCreator(post);
        fetchedPost = postRepository.save(fetchedPost);
        linkAttachmentsToPost(fetchedPost,addedAttachmentIds);
        deleteAttachmentsToPost(deleteAttachmentIds);
        return fetchedPost;
    }

    @Override
    public Post patchUpdateWithAttachment(Post post,Set<Long> addedAttachmentIds,Set<Long> deleteAttachmentIds) {
        Post fetchedPost = getById(post.getId());
        fetchedPost.patchUpdateForPostCreator(post);
        fetchedPost = postRepository.save(fetchedPost);
        linkAttachmentsToPost(fetchedPost,addedAttachmentIds);
        deleteAttachmentsToPost(deleteAttachmentIds);
        return fetchedPost;
    }

    @Override
    public Post patchUpdateWithAttachmentV4(Post post,Set<Long> addedAttachmentIds,Set<Long> deleteAttachmentIds) {
        Post fetchedPost = getById(post.getId());
        fetchedPost.isAuthorizedForUpdate();
        fetchedPost.patchUpdateForPostCreator(post);
        fetchedPost = postRepository.save(fetchedPost);
        linkAttachmentsToPost(fetchedPost,addedAttachmentIds);
        deleteAttachmentsToPost(deleteAttachmentIds);
        return fetchedPost;
    }

    public void checkForPatchUpdatePermission(Post post) {
        if(Objects.equals(post.getPostType(),PostConstant.POST_TYPE_FEED)
                && Objects.equals(post.getPostSubType(),PostConstant.POST_SUB_TYPE_IMPACT_TYPE)
                && Objects.equals(post.getStatus(),PostConstant.POST_STATUS_APPROVED)) {
            throw new UnAuthorizedAccessException("Approved Impact Story post can not be edited");
        }
    }

    @Override
    public Post patchUpdateWithAttachmentReplace(Long postId, String attachmentDecision, Post post, MultipartFile file) {
        Post fetchedPost = findPost(postId, null);
        fetchedPost.isAuthorizedForUpdate();

        if(Objects.equals(attachmentDecision,"DELETE_ATTACHMENT")) {
            deleteAttachmentsForAPost(fetchedPost);
        }
        else if(Objects.equals(attachmentDecision,"REPLACE_ATTACHMENT")) {
            if(file == null) {
                throw new InvalidInputException("file must not be null for REPLACE_ATTACHMENT option");
            }
            deleteAttachmentsForAPost(fetchedPost);
            Attachment attachment=new Attachment();
            attachment.setEntityId(postId);
            attachment.setEntityType(PostConstant.ENTITY_TYPE_POST);
            attachment.setPublic(fetchedPost.getVisibility().equals(PostConstant.POST_VISIBILITY_VISIBLE));
            try {
                attachment = attachmentService.uploadAttachment(file,attachment);
            } catch (IOException e) {
                throw new InvalidInputException(String.format("Attachment could not be uploaded. Error - %s", ExceptionUtils.getMessage(e)));
            }
            if (fetchedPost.getVisibility().equals(PostConstant.POST_VISIBILITY_VISIBLE) && attachment.getCompleteURL() != null) {
                fetchedPost.setAttachmentUrl(attachment.getCompleteURL());
                fetchedPost.setHasAtttachment(true);
            }
        }

        if(post != null) {
            if(PlatformSecurityUtil.isTenantAdmin()) {
                fetchedPost.patchUpdate(post);
            }
            else {
                fetchedPost.patchUpdateForPostCreator(post);
            }
        }
        return postRepository.save(fetchedPost);
    }

    public void deleteAttachmentsForAPost(Post post) {
        List<Attachment> attachmentList = attachmentService.getAttachmentsByEntityIdAndEntityTypeV2(post.getId(),PostConstant.ENTITY_TYPE_POST);
        attachmentList.forEach(attachment -> attachment.deactivate("Attachment deleted"));
        attachmentService.saveAll(attachmentList);
        post.setAttachmentUrl(null);
        post.setHasAtttachment(Boolean.FALSE);
    }

    @Override
    public void deletePost(Long id, String identifier, String reason) {
        Post fetchedPost = findPost(id,identifier);
        fetchedPost.isAuthorizedForUpdate();
        fetchedPost.deactivate(reason);
        postRepository.save(fetchedPost);
    }

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Post with Id %d not found",id)));
    }

    @Override
    public Post getByIdForPublic(Long id, String tenantLogin) {
        Long tenantId = platformUtil.getTenantIdByContext(tenantLogin);
        return postNonMTRepository.findById(id,tenantId)
                    .orElseThrow(() -> new NotFoundException(String.format("Post with Id %d not found", id)));
    }


    @Override
    public Post getByUUIDNonMT(String uuid) {
        return postNonMTRepository.findByUUID(uuid)
                    .orElseThrow(() -> new NotFoundException(String.format("Post with uuid %s not found", uuid)));
    }

    public Post getByIdForLoggedInUser(Long id) {
        return postRepository.findByIdByActorId(id,PlatformSecurityUtil.getCurrentUserId(),PlatformSecurityUtil.getActorContext().getActorContext())
                .orElseThrow(() -> new NotFoundException(String.format("Post with Id %d not found",id)));
    }

    @Override
    public Post getByUUId(String uuid) {
        return postRepository.findByUUID(uuid)
                .orElseThrow(() -> new NotFoundException(String.format("Post with UUID %s not found",uuid)));
    }

    @Override
    public Slice<Post> getByPage(Integer page, Integer size) {
        return postRepository.findAllViaSlice(PageRequest.of(page, size));
    }

    @Override
    public Slice<Post> fetchTimeline(String appContext, String domainContext, String languageContext,
                                     String locationContext, String postType,
                                     String taggedEntityType,Long taggedEntityId,
                                     Integer page, Integer size, Sort sort,String category,String subCategory, String status) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return postRepository.fetchTimeline
                (appContext, domainContext, languageContext,
                   locationContext, postType,taggedEntityType,taggedEntityId,
                        category,subCategory,status,pageRequest);
    }

    @Override
    public Page<Post> fetchTimelineV2(String appContext, String domainContext, String languageContext, String locationContext,
                                       String postType, Set<String> postSubTypes, String taggedEntityType, Long taggedEntityId,
                                       String category, String subCategory, String status,Set<String> causes, String actorType, Long actorId,
                                       Set<String> statusToBeIgnored, Pageable pageable, Boolean isModerated,
                                       Boolean includeBaseColumnForLanguage,Long responseCount, Boolean excludeReportedPost) {
        return postRepository.fetchTimelineV2(appContext, domainContext, languageContext, locationContext, postType,postSubTypes,
                taggedEntityType,taggedEntityId, category,subCategory,status,causes,actorType,actorId,statusToBeIgnored,pageable
                ,isModerated, includeBaseColumnForLanguage,responseCount, excludeReportedPost);
    }

    @Override
    public Page<Post> fetchTimelineV3(String appContext, String domainContext, String languageContext, String locationContext,
                                      String postType, Set<String> postSubTypes, String taggedEntityType, Long taggedEntityId,
                                      String category, String subCategory, String status,Set<String> causes, String actorType, Long actorId,
                                      Set<String> statusToBeIgnored, Pageable pageable, Boolean isModerated,
                                      Boolean includeBaseColumnForLanguage,Long responseCount, Boolean excludeReportedPost) {
        return postNonMTRepository.fetchTimelineV3(appContext, domainContext, languageContext, locationContext, postType,postSubTypes,
                taggedEntityType,taggedEntityId, category,subCategory,status,causes,actorType,actorId,statusToBeIgnored,pageable
                ,isModerated, includeBaseColumnForLanguage,responseCount, excludeReportedPost);
    }

    @Override
    public Post findPost(Long postId, String postIdentifier) {
        if (null == postId && StringUtils.isEmpty(postIdentifier)) {
            throw new InvalidInputException("Either postId or PostUUID is required");
        }
        Post fetchedPost;
        if (postId != null) {
            fetchedPost = getById(postId);
        } else {
            fetchedPost = getByUUId(postIdentifier);
        }
        return fetchedPost;
    }

    @Override
    public void existsByIdOrUUID(Long postId, String uuid) {
        if(!postRepository.existsByPostIdORUUId(postId,uuid)) {
            if(postId==null) {
                throw new NotFoundException(String.format("Post with uuid %d not found",uuid));
            }
            else {
                throw new NotFoundException(String.format("Post with Id %d not found",postId));
            }
        }
    }

    @Override
    public void existsByIdOrUUIDFromNonMTRepo(Long postId, String uuid) {
        if(!postNonMTRepository.existsByPostIdORUUId(postId,uuid)) {
            if(postId==null) {
                throw new NotFoundException(String.format("Post with uuid %d not found",uuid));
            }
            else {
                throw new NotFoundException(String.format("Post with Id %d not found",postId));
            }
        }
    }

    @Override
    public Attachment createAttachmentForPost(Long postId,MultipartFile file, String attachmentName) throws IOException {
        Post post= getById(postId);
        Attachment inputAttachment=new Attachment();
        inputAttachment.setEntityId(postId);
        inputAttachment.setEntityType(PostConstant.ENTITY_TYPE_POST);
        inputAttachment.setPublic(post.getVisibility().equals(PostConstant.POST_VISIBILITY_VISIBLE));
        inputAttachment.setName(attachmentName != null && !attachmentName.isEmpty() ? attachmentName : null);
        savePostAfterAttachment(post);
        return attachmentService.uploadAttachment(file,inputAttachment);
    }

    @Override
    public List<AttachmentDTO> getAttachmentForPost(Long postId) {
        existsByIdOrUUID(postId,null);
        return attachmentService.getAttachmentsByEntityIdAndEntityType(postId,PostConstant.ENTITY_TYPE_POST);
    }

    @Override
    public List<AttachmentDTO> getAttachmentsByPostUUID(String postUUID) {
        Post post = getByUUId(postUUID);
        return attachmentService.getAttachmentsByEntityIdAndEntityType(post.getId(),PostConstant.ENTITY_TYPE_POST);
    }

    @Override
    public List<AttachmentDTO> getAttachmentsByPostUUIDNonMT(String postUUID) {
        Post post = getByUUIDNonMT(postUUID);
        return attachmentInfoService.getAttachmentsByEntityIdAndEntityType(post.getId(),PostConstant.ENTITY_TYPE_POST);
    }

    @Override
    public Map<Long,List<AttachmentDTO>> getAttachmentsForGivenPosts(Set<Long> postIds) {
        Set<Long> genuinePostIds=postRepository.findAllWhichExist(postIds);
        return genuinePostIds.stream()
                .collect(Collectors.toMap(Function.identity(), this::getAttachmentForPost));
    }

    @Override
    public Map<Long, List<AttachmentDTO>> getPublicAttachmentsForGivenPosts(Set<Long> postIds, String tenantLogin) {
        postTenantConfigService.validateIsPublicFeedAllowed(tenantLogin);
        Set<Long> genuinePostIds = postNonMTRepository.findAllWhichExist(postIds);
        return genuinePostIds.stream()
                .collect(Collectors.toMap(Function.identity(), this::getAttachmentForPostFromNonMTRepo));
    }

    @Override
    public Map<Long, List<AttachmentDTO>> getPublicAttachmentsForGivenPostsV3(Set<Long> postIds) {
        Set<Long> genuinePostIds = postNonMTRepository.findAllWhichExist(postIds);
        return genuinePostIds.stream()
                .collect(Collectors.toMap(Function.identity(), this::getAttachmentForPostFromNonMTRepo));
    }

    @Override
    public Map<Long, List<AttachmentDTO>> getAttachmentsForGivenPostsFromNonMTRepo(Set<Long> postIds) {
        Set<Long> genuinePostIds = postNonMTRepository.findAllWhichExist(postIds);
        return genuinePostIds.stream()
                .collect(Collectors.toMap(Function.identity(),
                        this::getAttachmentForPostFromNonMTRepo));
    }

    public List<AttachmentDTO> getAttachmentForPostFromNonMTRepo(Long postId) {
        existsByIdOrUUIDFromNonMTRepo(postId,null);
        return attachmentInfoService.getAttachmentsByEntityIdAndEntityType(postId,PostConstant.ENTITY_TYPE_POST);
    }

    @Override
    public Map<Long, List<Response>> getResponseForPosts(Set<Long> postIds, Integer size) {
        StringJoiner stringJoiner=new StringJoiner(",");
        postIds.stream().forEach(postId->stringJoiner.add(String.valueOf(postId)));
        List<String> list= responseRepository.findResponseByPostsUsingPage(stringJoiner.toString(),size);

        List<Long> listOfResponseIds= Stream.of(list.get(0).split(","))
                                    .filter(id->!id.isEmpty())
                                    .map(id->Long.parseLong(id))
                                     .collect(Collectors.toList());

        List<Response> responseList= responseRepository.findAllResponseById(listOfResponseIds);
        Map<Long,List<Response>> responseMap=responseList.stream()
                                            .collect(Collectors.groupingBy(Response::getResponseOnEntityId));

        responseMap.entrySet().stream().forEach(entry->Collections.sort(entry.getValue(),(r1,r2)-> (int) (r2.getId()-r1.getId())));

//        Set<Long> genuinePostIds=postRepository.findAllWhichExist(postIds);
//        Map<Long,List<Response>> responseMap=genuinePostIds.stream()
//                .collect(Collectors.toMap(Function.identity(),
//                        postId-> responseService.getResponseForPostUsingPagination(0,size,
//                                Sort.by(Sort.Direction.ASC,"id"), postId, null).getContent()));

        return responseMap;
    }
    @Override
    public Slice<PostAttachmentVO> fetchTimelineWithAttachments(String appContext, String domainContext, String languageContext,
                                                                String locationContext, String postType,
                                                                String taggedEntityType, Long taggedEntityId,
                                                                Integer page, Integer size, Sort sort) {
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Slice<PostAttachmentVO> postAttachmentVOS = postRepository.fetchTimelineWithAttachments(appContext, domainContext, languageContext,
                locationContext, postType, taggedEntityType, taggedEntityId, pageRequest);
        Set<Long> postIds = postAttachmentVOS.stream().map(PostAttachmentVO::getId).collect(Collectors.toSet());
        Map<Long, List<AttachmentDTO>> attachmentsForGivenPosts = getAttachmentsForGivenPosts(postIds);
        postAttachmentVOS.forEach(postAttachmentVO -> {
            if (attachmentsForGivenPosts.containsKey(postAttachmentVO.getId())) {
                postAttachmentVO.setAttachmentDTOS(attachmentsForGivenPosts.get(postAttachmentVO.getId()));
            }
        });
        return postAttachmentVOS;
    }



    @Override
    public Slice<PostAttachmentVO> fetchPublicTimelineWithAttachments(String tenantLogin, String appContext, String domainContext, String languageContext,
                                                                      String locationContext, String postType,
                                                                      String taggedEntityType, Long taggedEntityId,
                                                                      Integer page, Integer size, Sort sort) {
        postTenantConfigService.validateIsPublicFeedAllowed(tenantLogin);
        Long tenantId = platformUtil.getTenantIdByContext(tenantLogin);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Slice<PostAttachmentVO> postAttachmentVOS = postNonMTRepository.fetchTimelineWithAttachments(tenantId,
                appContext, domainContext, languageContext, locationContext, postType, taggedEntityType, taggedEntityId, pageRequest);
        Set<Long> postIds = postAttachmentVOS.stream().map(PostAttachmentVO::getId).collect(Collectors.toSet());
        Map<Long, List<AttachmentDTO>> attachmentsForGivenPosts = getAttachmentsForGivenPostsFromNonMTRepo(postIds);
        postAttachmentVOS.forEach(postAttachmentVO -> {
            if (attachmentsForGivenPosts.containsKey(postAttachmentVO.getId())) {
                postAttachmentVO.setAttachmentDTOS(attachmentsForGivenPosts.get(postAttachmentVO.getId()));
            }
        });
        return postAttachmentVOS;
    }

    @Override
    public Page<Post> fetchPublicTimeline(String tenantLogin, String appContext, String domainContext, String languageContext,
                                           String locationContext, String postType, Set<String> postSubTypes, String taggedEntityType,
                                           Long taggedEntityId, String category,
                                           String subCategory, String status,Set<String> causes, String actorType, Long actorId, Set<String> statusToBeIgnored,
                                           Pageable pageable, Boolean isModerated, Boolean includeBaseColumnForLanguage,
                                          Long responseCount, Boolean excludeReportedPost) {
        postTenantConfigService.validateIsPublicFeedAllowed(tenantLogin);
        Long tenantId = platformUtil.getTenantIdByContext(tenantLogin);
        return postNonMTRepository.fetchTimeline(tenantId, appContext, domainContext,
                languageContext,locationContext, postType,postSubTypes,taggedEntityType,taggedEntityId,category,subCategory,
                status,causes,actorType,actorId,statusToBeIgnored,pageable, isModerated,includeBaseColumnForLanguage,
                responseCount, excludeReportedPost);

    }

    @Override
    public void createUserReadEventForPost(Long postId) {
        // updating the Count for every call
        if (PlatformSecurityUtil.isPlatformAppToken()) {
            postNonMTRepository.updatePostReadCount(postId,PostConstant.READ_COUNT_TO_BE_INCREASED);
        }
        else {
            postRepository.updatePostReadCount(postId,PostConstant.READ_COUNT_TO_BE_INCREASED);
        }
    }

    @Override
    public Map<Long, List<Response>> getPublicResponseForPosts(Set<Long> postIds, Integer size, String tenantLogin) {
        postTenantConfigService.validateIsPublicFeedAllowed(tenantLogin);
        StringJoiner stringJoiner=new StringJoiner(",");
        postIds.stream().forEach(postId->stringJoiner.add(String.valueOf(postId)));
        List<String> list= responseNonMTRepository.findResponseByPostsUsingPage(stringJoiner.toString(), size);

        List<Long> listOfResponseIds= Stream.of(list.get(0).split(","))
                .filter(id->!id.isEmpty())
                .map(id->Long.parseLong(id))
                .collect(Collectors.toList());

        List<Response> responseList= responseNonMTRepository.findAllResponseById(listOfResponseIds);
        Map<Long,List<Response>> responseMap=responseList.stream()
                .collect(Collectors.groupingBy(Response::getResponseOnEntityId));

        responseMap.entrySet().stream().forEach(entry -> Collections.sort(entry.getValue(), (r1, r2) -> (int) (r2.getId() - r1.getId())));
        return responseMap;
    }

    @Override
    public Post patchPostStatus(Post post) {
        Post fetchedPost = findPost(post.getId(), post.getUuid());
        fetchedPost.isAuthorizedForUpdate();
        fetchedPost.updateStatus(post.getStatus());
        return postRepository.save(fetchedPost);
    }

    @Override
    public Page<PostAttachmentResourceVO>  getPostBlogResources(String tenantLogin,String appContext, String domainContext,
                                                         String languageContext, String locationContext,
                                                         String postType, String taggedEntityType,
                                                         Long taggedEntityId, Integer page, Integer size, Sort sort,
                                                         String category,String subCategory, String status) {
        Page<PostAttachmentResourceVO> attachmentResourceVOS;
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        if (PlatformSecurityUtil.isPlatformAppToken()) {
            postTenantConfigService.validateIsPublicFeedAllowed(tenantLogin);
            Long tenantId = platformUtil.getTenantIdByContext(tenantLogin);
            attachmentResourceVOS = postNonMTRepository.getPostBlogResources(tenantId, appContext,
                    domainContext, languageContext, locationContext, postType, taggedEntityType, taggedEntityId, category,
                    subCategory, status, PostConstant.POST_VISIBILITY_VISIBLE, pageRequest);

        }
        else {
            attachmentResourceVOS = postRepository.getPostBlogResources(appContext, domainContext,languageContext,
                    locationContext,postType,taggedEntityType,taggedEntityId,category, subCategory,status,
                    PostConstant.POST_VISIBILITY_VISIBLE,pageRequest);
        }
        List<Attachment> attachmentList = attachmentResourceVOS.stream()
                                                                .map(PostAttachmentResourceVO::getAttachment)
                                                                .collect(Collectors.toList());
        Map<Long, AttachmentDTO> attachmentMap = attachmentInfoService.getAttachmentDTOList(attachmentList)
                .stream()
                .collect(Collectors.toMap(AttachmentDTO::getId, attachment -> attachment));
        attachmentResourceVOS.forEach(attachmentResource -> {
            if (attachmentMap.containsKey(attachmentResource.getAttachment().getId())) {
                attachmentResource.setAttachmentDTO(attachmentMap.get(attachmentResource.getAttachment().getId()));
            }
            attachmentResource.setAttachment(null);
        });
        return attachmentResourceVOS;
    }

    @Override
    public Page<Post> fetchPostForAdmin(String appContext, String postType, Set<String> postSubTypes, String category,
                                        String subCategory, String status, String actorType, Long actorId, String excludeStatus, Pageable pageable) {
        return postRepository.fetchPostForAdmin(appContext,postType,postSubTypes,category,subCategory,status,actorType,actorId,
                excludeStatus,pageable);
    }

    @Override
    public Post patchUpdatePostAndAssignCertificate(Post post, Boolean sendMail) {
        Post fetchedPost = getById(post.getId());
        fetchedPost.patchUpdate(post);

        if(Objects.equals(fetchedPost.getStatus(),PostConstant.POST_STATUS_APPROVED)) {
            updateApprovedEffortAndApprovedImpact(fetchedPost);
        }
        fetchedPost = postRepository.save(fetchedPost);
        PostActorCertificate postActorCertificate = null;
        if(post.getPostActorCertificate() != null) {
            postActorCertificate = postActorCertificateService.saveOrUpdate(post.getPostActorCertificate(),fetchedPost.getPostedBy(),
                    fetchedPost,sendMail);
        }

        if(Objects.equals(sendMail,Boolean.TRUE)) {
            notificationUtility.sendPostMail(postActorCertificate,fetchedPost,fetchedPost.getPostedBy());
            if(postActorCertificate != null) {
                postActorCertificate.updateFieldsForMailSent();
                postActorCertificate =  postActorCertificateService.save(postActorCertificate);
            }
        }
        return fetchedPost;
    }

    @Transactional
    @Override
    public List<PostDTO> changeStatusAndAssignCertificateInBulk(Set<Long> postIds, String status, Boolean assignCertificate,
                                                                Long tenantCertificateTemplateId, Boolean sendMail) {
        Set<Post> postSet = postRepository.findByIds(postIds);
        Set<Post> finalPostSet = new HashSet<>();
        postSet.forEach(post->{
            if(!Objects.equals(post.getStatus(),status)) {
                post.setStatus(status);
                post.setStatusUpdatedDate(new Date());
                if(Objects.equals(status,PostConstant.POST_STATUS_APPROVED)) {
                    updateApprovedEffortAndApprovedImpact(post);
                }
                finalPostSet.add(post);
            }
        });
        List<Post> savedPosts= postRepository.saveAll(finalPostSet);
        List<PostActorCertificate> savedPostActorCertificates = null;
        if(Objects.equals(assignCertificate, Boolean.TRUE) && !savedPosts.isEmpty()
                && Objects.equals(status,PostConstant.POST_STATUS_APPROVED)) {
            savedPostActorCertificates = postActorCertificateService.createCertificateInBulk(savedPosts,tenantCertificateTemplateId);
        }

        if(Objects.equals(sendMail, Boolean.TRUE)) {
            notificationUtility.sendPostNotificationInBulk(savedPostActorCertificates,savedPosts);
            if(savedPostActorCertificates != null && !savedPostActorCertificates.isEmpty()) {
                savedPostActorCertificates.forEach(PostActorCertificate::updateFieldsForMailSent);
                postActorCertificateService.saveAll(savedPostActorCertificates);
            }
        }
        return postDTOAssembler.toDTOs(savedPosts);

    }

    public void updateApprovedEffortAndApprovedImpact(Post post) {
        if(post.getApprovedEffort() == null && post.getEffort() != null) {
            post.setApprovedEffort(UoMValue.builder()
                    .id(0L)
                    .value(post.getEffort().getValue())
                    .maxValue(post.getEffort().getMaxValue())
                    .minValue(post.getEffort().getMinValue())
                    .uoM(post.getEffort().getUoM())
                    .build());
        }
        if(post.getPostImpacts() != null) {
            post.getPostImpacts().forEach(postImpact -> {
                if(postImpact.getApprovedImpact() == null && postImpact.getActualImpact() != null) {
                    postImpact.setApprovedImpact(UoMValue.builder()
                            .id(0L)
                            .value(postImpact.getActualImpact().getValue())
                            .maxValue(postImpact.getActualImpact().getMaxValue())
                            .minValue(postImpact.getActualImpact().getMinValue())
                            .uoM(postImpact.getActualImpact().getUoM())
                            .build());
                }
            });
        }
    }

    @Override
    public SearchResult<Post> fetchPostForAdminWithSearch(String appContext, String postType, Set<String> postSubTypes,
                                                          String category, String subCategory, String status, String excludeStatus,
                                                          Long actorId, String searchText, String actorType, Date startPostTime, Date endPostTime,
                                                          Integer page, Integer size, String sortBy, String direction, Set<String> causes) {
        SearchResult<Post> searchResult;
        SearchSession session = Search.session(entityManager);
        SearchScope<Post> scope = session.scope(Post.class);
        BooleanPredicateClausesStep<?> bool = scope.predicate().bool();

        Long currentTenantId = PlatformSecurityUtil.getCurrentTenantId();
        bool.must(scope.predicate().match().field("tenantId").matching(currentTenantId));
        bool.must(scope.predicate().match().field("isActive").matching(Boolean.TRUE));
        bool.must(scope.predicate().match().field("appContext").matching(appContext));

        if (searchText != null) {
            if(searchText.contains(PostConstant.MAIL_SEPARATOR)){
                bool = bool.must(scope.predicate().match().fields("postedBy.emailAddress").matching(searchText));
            }
            else {
                bool = bool.must(scope.predicate().match().fields("postedBy.name", "postedBy.emailAddress").matching("*" + searchText + "*"));
            }
        }
        if (postType != null) {
            bool = bool.must(scope.predicate().match().field("postType").matching(postType));
        }
        if (postSubTypes != null && !postSubTypes.isEmpty()) {
            bool = bool.filter(scope.predicate().terms().field("postSubType").matchingAny(postSubTypes));
        }
        if (category != null) {
            bool = bool.must(scope.predicate().match().field("category").matching(category));
        }
        if (subCategory != null) {
            bool = bool.must(scope.predicate().match().field("subCategory").matching(subCategory));
        }
        if (status != null) {
            bool = bool.must(scope.predicate().match().field("status").matching(status));
        }
        if (excludeStatus != null) {
            bool = bool.mustNot(scope.predicate().match().field("status").matching(excludeStatus));
        }
        if (actorId != null) {
            bool = bool.must(scope.predicate().match().field("postedBy.actorId").matching(actorId));
        }
        if (actorType != null) {
            bool = bool.must(scope.predicate().match().field("postedBy.actorType").matching(actorType));
        }
        if(endPostTime!=null && startPostTime!=null){
            bool = bool.must(scope.predicate().range().field("postTime").between(startPostTime,
                    DateUtils.addDays(endPostTime,1)));
        }
        if(causes != null && !causes.isEmpty()){
            bool = bool.filter(scope.predicate().terms().field("causes").matchingAny(causes));
        }

        return sortLogicForFilterSearch(sortBy,direction,page,size,bool,session);
    }

    public SearchResult<Post> sortLogicForFilterSearch(String sortBy, String direction,Integer page, Integer size,
                                                        BooleanPredicateClausesStep<?> bool, SearchSession session) {
        SearchResult<Post> searchResult;
        if (sortBy != null ) {
            searchResult = session.search(Post.class )
                    .where(bool.toPredicate())
                    .sort(f-> f.field(sortBy).order(getHBSearchSortOrder(direction)))
                    .fetch(page*size,size);
        }
        else{
            searchResult = session.search(Post.class )
                    .where(bool.toPredicate())
                    .sort(f-> f.field("postTime").desc())
                    .fetch(page*size,size);
        }
        return searchResult;
    }

    @Override
    public ReactionForUpdateDTO getMyReactionAndTotalCount(Long postId) {
        Boolean reactedByMe = reactionService.ifCurrentUserReactedOnEntity(postId,PostConstant.ENTITY_TYPE_POST);
        Long totalReactCount = getReactCountByPostId(postId);
        return ReactionForUpdateDTO.builder()
                .reactedByMe(reactedByMe)
                .totalReactCount(totalReactCount)
                .build();
    }

    @Override
    public List<PostTypeCountVO> getPostCountAnalyticsByUserId(Set<String> postTypes, String appContext, String domainContext,Long userId) {
        return postRepository.findPostCountAnalyticsByActorId(userId ,postTypes,appContext,domainContext);
    }

    @Override
    public List<PostTypeCountVO> getPostCountAnalyticsByUserIdForPublic(Set<String> postTypes, String appContext, String domainContext,
                                                                        Long userId,String tenantLogin) {
        postTenantConfigService.validateIsPublicFeedAllowed(tenantLogin);
        Long tenantId = platformUtil.getTenantIdByContext(tenantLogin);
        return postNonMTRepository.findPostCountAnalyticsByActorId(userId, postTypes,appContext,domainContext,
                tenantId);
    }

    @Override
    public Page<Post> fetchPostsForLoggedInUser(String appContext, String domainContext, String postType, String postSubType,
                                                String status, Pageable pageable) {
        return postRepository.findPostsByActorIdInPagination(appContext,domainContext,postType,postSubType,status,pageable,
                PlatformSecurityUtil.getCurrentUserId(),PlatformSecurityUtil.getActorContext().getActorContext());
    }

    @Override
    public Page<Post> fetchPostsForLoggedInUserV2(String appContext, String domainContext, String postType, String postSubType,
                                           Set<String> includeStatusSet, Set<String> excludeStatusSet, Pageable pageable) {
        return postRepository.findPostsByActorIdInPaginationV2(appContext,domainContext,postType,postSubType,includeStatusSet,
                excludeStatusSet,pageable, PlatformSecurityUtil.getCurrentUserId(),PlatformSecurityUtil.getActorContext().getActorContext());
    }

    @Override
    public Page<Post> fetchPublishedPostsByUserIdForPublic(String appContext, String domainContext, String postType, String postSubType,
                                                  Long userId,Set<String> statusToBeIgnored,String tenantLogin, Pageable pageable) {
        postTenantConfigService.validateIsUserFeedPublic(tenantLogin);
        Long tenantId = platformUtil.getTenantIdByContext(tenantLogin);
        return postNonMTRepository.findPublishedPostsByActorIdInPagination(appContext,domainContext,postType,postSubType,
                    statusToBeIgnored, pageable, userId, tenantId);
    }

    @Override
    public Page<Post> fetchPublishedPostsByUserId(String appContext, String domainContext, String postType, String postSubType,
                                                  Long userId,Set<String> statusToBeIgnored, Pageable pageable) {
        return postRepository.findPublishedPostsByActorIdInPagination(appContext,domainContext,postType,postSubType,statusToBeIgnored,
                pageable, userId);
    }

    @Override
    public Page<PostAttachmentVO> fetchPublishedPostAttachmentsByUserId(String appContext, String domainContext, String postType, String postSubType,
                                                                        Long userId,Set<String> statusToBeIgnored, Pageable pageable) {
        Page<PostAttachmentVO> postAttachmentVOPage = postRepository.findPublishedPostsWithAttachmentPresentByUserId(appContext,
                domainContext,postType,postSubType,statusToBeIgnored,pageable,userId);

        List<PostAttachmentVO> postAttachmentVOsList = postAttachmentVOPage.toList();
        Set<Long> postIdsSet = postAttachmentVOPage.stream()
                .map(PostAttachmentVO::getId)
                .collect(Collectors.toSet());
        Map<Long, List<AttachmentDTO>> postAttachmentsMap = getAttachmentsForGivenPosts(postIdsSet);

        postAttachmentVOPage.forEach(postAttachmentVO-> {
            postAttachmentVO.setAttachmentDTOS(postAttachmentsMap.getOrDefault(postAttachmentVO.getId(),null));
        });
        return new PageImpl<>(postAttachmentVOsList,pageable,postAttachmentVOPage.getTotalElements());
    }

    @Override
    public Page<PostAttachmentVO> fetchPublishedPostAttachmentsByUserIdForPublic(String appContext, String domainContext, String postType,
                                                                                 String postSubType, Long userId, Set<String> statusToBeIgnored,
                                                                                 String tenantLogin, Pageable pageable) {
        postTenantConfigService.validateIsUserFeedPublic(tenantLogin);
        Long tenantId = platformUtil.getTenantIdByContext(tenantLogin);
        Page<PostAttachmentVO> postAttachmentVOPage = postNonMTRepository.findPublishedPostsWithAttachmentPresentByUserId(appContext,
                domainContext,postType,postSubType,statusToBeIgnored,pageable,userId, tenantId);

        List<PostAttachmentVO> postAttachmentVOsList = postAttachmentVOPage.toList();
        Set<Long> postIdsSet = postAttachmentVOPage.stream()
                .map(PostAttachmentVO::getId)
                .collect(Collectors.toSet());
        Map<Long, List<AttachmentDTO>> postAttachmentsMap = getAttachmentsForGivenPostsFromNonMTRepo(postIdsSet);

        postAttachmentVOPage.forEach(postAttachmentVO-> {
            postAttachmentVO.setAttachmentDTOS(postAttachmentsMap.getOrDefault(postAttachmentVO.getId(),null));
        });

        return new PageImpl<>(postAttachmentVOsList,pageable,postAttachmentVOPage.getTotalElements());
    }

    public Long getReactCountByPostId(Long postId){
        Long reactCount =  postRepository.findReactCountByPostId(postId);
        if(reactCount == null) {
            throw new NotFoundException(String.format("Post with id %d not found",postId));
        }
        return reactCount;
    }


    public SortOrder getHBSearchSortOrder(String order){
        if(order!=null){
            return order.equalsIgnoreCase("ASC")?SortOrder.ASC:SortOrder.DESC;
        }
        return SortOrder.DESC;
    }

    @Override
    public SearchResult<Post> fetchPostBySearchText(String appContext, String postType, String searchText, Integer page,
                                                    Integer size, String direction, String sortBy, Long tenantId,
                                                    Boolean isModerated, String domainContext, String postSubType,
                                                    String status, Boolean isPublic, String visibility,
                                                    String category, String subCategory, String languageContext,
                                                    Boolean includeBaseColumnForLanguage) {

        SearchResult<Post> searchResult;
        SearchSession session = Search.session(entityManager);
        SearchScope<Post> scope = session.scope(Post.class);
        BooleanPredicateClausesStep<?> bool = scope.predicate().bool();

        if(PlatformSecurityUtil.isPlatformAppToken() || (PlatformSecurityUtil.isSession() && !PlatformSecurityUtil.isTenantAdmin())) {
            status = PostConstant.POST_STATUS_PUBLISHED;
            isPublic = Boolean.TRUE;
            visibility = PostConstant.POST_VISIBILITY_VISIBLE;
        }

        bool.must(scope.predicate().match().field("tenantId").matching(tenantId));
        bool.must(scope.predicate().match().field("isActive").matching(Boolean.TRUE));
        bool.must(scope.predicate().match().field("appContext").matching(appContext));
        bool.must(scope.predicate().match().field("isPublic").matching(isPublic));
        bool.must(scope.predicate().match().field("visibility").matching(visibility));
        bool = bool.must(scope.predicate().match().field("status").matching(status));

        if (searchText != null) {
            bool = bool.must(scope.predicate().match().fields("title","payload","titles.text","payloads.text")
                    .matching(StringUtils.trim(searchText)));
        }
        if (postType != null) {
            bool = bool.must(scope.predicate().match().field("postType").matching(postType));
        }
        if (postSubType != null) {
            bool = bool.must(scope.predicate().match().field("postSubType").matching(postSubType));
        }
        if (isModerated != null) {
            bool = bool.must(scope.predicate().match().field("isModerated").matching(isModerated));
        }
        if (domainContext != null) {
            bool = bool.must(scope.predicate().match().field("domainContext").matching(domainContext));
        }
        if (category != null) {
            bool = bool.must(scope.predicate().match().field("category").matching(category));
        }
        if (subCategory != null) {
            bool = bool.must(scope.predicate().match().field("subCategory").matching(subCategory));
        }
        if (languageContext != null) {
            if (includeBaseColumnForLanguage)
                bool = bool.must(scope.predicate().match().fields("languageContext", "titles.languageContext", "payloads.languageContext")
                        .matching(languageContext));
            else
                bool = bool.must(scope.predicate().match().fields("titles.languageContext", "payloads.languageContext")
                        .matching(languageContext));
        }

        return sortLogicForFilterSearch(sortBy,direction,page,size,bool,session);
    }

    @Override
    public Set<Post> findByIds(Set<Long> postIds) {
        return postNonMTRepository.findByIds(postIds);
    }

    @Override
    public Page<Post> getPostsByTenantId(Long tenantId, Pageable pageable) {
        return postNonMTRepository.getPostsByTenantId(tenantId, pageable);
    }

    @Override
    public Map<Long, List<PostActiveMemberVO>> findActiveMembersForPosts(Set<Long> postIds, Long tenantId) {
        List<PostActiveMemberVO> activeMembers = postNonMTRepository.findActiveMembersForPosts(postIds, tenantId);
        return activeMembers.stream()
                .collect(Collectors.groupingBy(PostActiveMemberVO::getPostId));
    }

    @Override
    public Set<PostVO> getPostsWithLinkedEntityCodeByUserId(Long userId, String postType, String postSubType, String status){
        return postRepository.findPostsWithLinkedEntityCodeByUserId(userId, postType, postSubType, status);
    }

}