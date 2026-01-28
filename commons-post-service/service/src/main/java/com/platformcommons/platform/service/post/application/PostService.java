package com.platformcommons.platform.service.post.application;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.Response;
import com.platformcommons.platform.service.post.domain.vo.*;
import com.platformcommons.platform.service.post.dto.PostDTO;
import com.platformcommons.platform.service.post.dto.ReactionForUpdateDTO;
import org.hibernate.search.engine.search.query.SearchResult;
import org.springframework.data.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PostService {

    Post save(Post post);

    void savePostAfterAttachment(Post post);

    Post createPostWithAttachment(Post post,MultipartFile file) throws IOException;

    Post savePostWithMultipleAttachments(Post post, Set<Long> attachmentIds);


    Post update(Post post,Boolean mapNulls);

    void deletePost(Long id, String identifier, String reason);

    Post getById(Long id);

    Post getByIdForPublic(Long id, String tenantLogin);

    Post getByUUIDNonMT(String uuid);

    Post getByUUId(String uuid);

    Slice<Post> getByPage(Integer page, Integer size);

    Post findPost(Long postId, String postIdentifier);

    void existsByIdOrUUID(Long postId, String uuid);

    void existsByIdOrUUIDFromNonMTRepo(Long postId, String uuid);

    Attachment createAttachmentForPost(Long postId, MultipartFile file, String attachmentName) throws IOException;

    List<AttachmentDTO> getAttachmentForPost(Long postId);

    List<AttachmentDTO> getAttachmentsByPostUUID(String postUUID);

    List<AttachmentDTO> getAttachmentsByPostUUIDNonMT(String postUUID);

    Map<Long,List<AttachmentDTO>> getAttachmentsForGivenPosts(Set<Long> postIds);

    Map<Long,List<AttachmentDTO>> getPublicAttachmentsForGivenPosts(Set<Long> postIds, String tenantLogin);

    Map<Long,List<AttachmentDTO>> getPublicAttachmentsForGivenPostsV3(Set<Long> postIds);

    Map<Long,List<AttachmentDTO>> getAttachmentsForGivenPostsFromNonMTRepo(Set<Long> postIds);

    Map<Long,List<Response>> getResponseForPosts(Set<Long> postIds, Integer size);

    Slice<Post> fetchTimeline(String appContext, String domainContext, String languageContext, String locationContext,
                              String postType, String taggedEntityType,Long taggedEntityId, Integer page, Integer size,
                              Sort sort,String category,String subCategory, String status);

    Page<Post> fetchTimelineV2(String appContext, String domainContext, String languageContext, String locationContext,
                                String postType, Set<String> postSubTypes, String taggedEntityType, Long taggedEntityId,
                                String category, String subCategory, String status,Set<String> causes, String actorType, Long actorId,
                                Set<String> statusToBeIgnored, Pageable pageable, Boolean isModerated,
                                Boolean includeBaseColumnForLanguage,Long responseCount, Boolean excludeReportedPost);

    Page<Post> fetchTimelineV3(String appContext, String domainContext, String languageContext, String locationContext,
                               String postType, Set<String> postSubTypes, String taggedEntityType, Long taggedEntityId,
                               String category, String subCategory, String status,Set<String> causes, String actorType, Long actorId,
                               Set<String> statusToBeIgnored, Pageable pageable, Boolean isModerated,
                               Boolean includeBaseColumnForLanguage,Long responseCount, Boolean excludeReportedPost);


    Slice<PostAttachmentVO> fetchTimelineWithAttachments(String appContext,
                                                         String domainContext,
                                                         String languageContext,
                                                         String locationContext,
                                                         String postType,
                                                         String taggedEntityType, Long taggedEntityId,
                                                         Integer page, Integer size, Sort sort);
    Slice<PostAttachmentVO> fetchPublicTimelineWithAttachments(String tenantLogin, String appContext,
                                                         String domainContext,
                                                         String languageContext,
                                                         String locationContext,
                                                         String postType,
                                                         String taggedEntityType, Long taggedEntityId,
                                                         Integer page, Integer size, Sort sort);

    Page<Post> fetchPublicTimeline(String tenantLogin, String appContext, String domainContext, String languageContext,
                                    String locationContext, String postType, Set<String> postSubTypes, String taggedEntityType,
                                    Long taggedEntityId, String category, String subCategory, String status,Set<String> causes, String actorType,
                                    Long actorId, Set<String> statusToBeIgnored,Pageable pageable, Boolean isModerated,
                                    Boolean includeBaseColumnForLanguage,Long responseCount, Boolean excludeReportedPost);

    void createUserReadEventForPost(Long postId);

    Map<Long, List<Response>> getPublicResponseForPosts(Set<Long> postIds, Integer size, String tenantLogin);

    Post patchPostStatus(Post post);

    Page<PostAttachmentResourceVO> getPostBlogResources(String tenantLogin, String appContext, String domainContext,
                                                        String languageContext, String locationContext,
                                                        String postType, String taggedEntityType,
                                                        Long taggedEntityId, Integer page, Integer size, Sort sort,
                                                        String category, String subCategory, String status) ;

    Page<Post> fetchPostForAdmin(String appContext, String postType, Set<String> postSubTypes, String category, String subCategory,
                                 String status, String actorType, Long actorId, String excludeStatus, Pageable pageable);

    Post patchUpdatePostAndAssignCertificate(Post post,Boolean sendMail);

    List<PostDTO> changeStatusAndAssignCertificateInBulk(Set<Long> postIds, String status, Boolean assignCertificate,
                                                         Long tenantCertificateTemplateId, Boolean sendMail);


    SearchResult<Post> fetchPostForAdminWithSearch(String appContext, String postType, Set<String> postSubTypes, String category,
                                                   String subCategory, String status, String excludeStatus, Long actorId, String actorName,
                                                   String actorType, Date startPostTime, Date endPostTime, Integer page, Integer size,
                                                   String sortBy, String direction, Set<String> causes);

    ReactionForUpdateDTO getMyReactionAndTotalCount(Long postId);

    List<PostTypeCountVO> getPostCountAnalyticsByUserId(Set<String> postTypes, String appContext, String domainContext,Long userId);

    List<PostTypeCountVO> getPostCountAnalyticsByUserIdForPublic(Set<String> postTypes, String appContext, String domainContext,
                                                                 Long userId,String tenantLogin);

    Page<Post> fetchPostsForLoggedInUser(String appContext, String domainContext, String postType, String postSubType,
                                         String status, Pageable pageable);

    Page<Post> fetchPostsForLoggedInUserV2(String appContext, String domainContext, String postType, String postSubType,
                                           Set<String> includeStatusSet, Set<String> excludeStatusSet, Pageable pageable);

    Page<PostAttachmentVO> fetchPublishedPostAttachmentsByUserId(String appContext, String domainContext, String postType, String postSubType,
                                                                 Long userId,Set<String> statusToBeIgnored, Pageable pageable);

    Page<PostAttachmentVO> fetchPublishedPostAttachmentsByUserIdForPublic(String appContext, String domainContext, String postType,
                                                                          String postSubType, Long userId, Set<String> statusToBeIgnored,
                                                                          String tenantLogin, Pageable pageable);

    Post patchUpdateForLoggedInUser(Post post,Set<Long> addedAttachmentIds,Set<Long> deleteAttachmentIds);

    Post patchUpdateWithAttachmentReplace(Long postId, String attachmentDecision, Post post, MultipartFile file);

    Page<Post> fetchPublishedPostsByUserId(String appContext, String domainContext, String postType, String postSubType,
                                           Long userId,Set<String> statusToBeIgnored, Pageable pageable);

    Page<Post> fetchPublishedPostsByUserIdForPublic(String appContext, String domainContext, String postType, String postSubType,
                                                  Long userId,Set<String> statusToBeIgnored,String tenantLogin, Pageable pageable);

    SearchResult<Post> fetchPostBySearchText(String appContext, String postType, String searchText, Integer page, Integer size,
                                             String direction, String sortBy, Long tenantId, Boolean isModerated,
                                             String domainContext, String postSubType, String status, Boolean isPublic,
                                             String visibility, String category, String subCategory, String languageContext,
                                             Boolean includeBaseColumnForLanguage);

    Set<Post> findByIds(Set<Long> postIds);

    Page<Post> getPostsByTenantId(Long tenantId, Pageable pageable);

    Map<Long, List<PostActiveMemberVO>> findActiveMembersForPosts(Set<Long> postIds, Long tenantId);

    Set<PostVO> getPostsWithLinkedEntityCodeByUserId(Long userId, String postType, String postSubType, String status);

    Post patchUpdateWithAttachment(Post post, Set<Long> addedAttachmentIds, Set<Long> deleteAttachmentIds);

    Post patchUpdateWithAttachmentV4(Post post, Set<Long> addedAttachmentIds, Set<Long> deleteAttachmentIds);
}