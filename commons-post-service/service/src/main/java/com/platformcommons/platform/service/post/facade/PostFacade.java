package com.platformcommons.platform.service.post.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.post.domain.vo.*;
import com.platformcommons.platform.service.post.dto.PostDTO;
import com.platformcommons.platform.service.post.dto.ReactionForUpdateDTO;
import com.platformcommons.platform.service.post.dto.ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PostFacade {

    Long save(PostDTO postDTO);

    Long savePostWithAttachment(PostDTO body,MultipartFile file) throws IOException;

    Long savePostWithMultipleAttachments(PostDTO postDTO, Set<Long> attachmentIds);

    PostDTO update(PostDTO postDTO);


    void delete(Long id, String identifier, String reason);

    PostDTO getById(Long id);

    PostDTO getByIdForPublic(Long id, String tenantLogin);

    PostDTO getPostByUUIDForPublic(String uuid);

    PostDTO findPost(Long postId, String postIdentifier);

    PageDTO<PostDTO> getByPage(Integer page,Integer size);

    AttachmentDTO createAttachment(Long postId, MultipartFile file, String attachmentName) throws IOException;

    List<AttachmentDTO> getAttachment(Long postId);

    List<AttachmentDTO> getAttachmentsByPostIdNonMT(Long postId);

    List<AttachmentDTO> getAttachmentsByPostUUIDForPublic(String postUUID);

    Map<Long,List<AttachmentDTO>> getAttachmentsForGivenPosts(Set<Long> postIds);

    Map<Long,List<AttachmentDTO>> getPublicAttachmentsForGivenPosts(Set<Long> postIds, String tenantLogin);

    Map<Long,List<AttachmentDTO>> getPublicAttachmentsForGivenPostsV3(Set<Long> postIds);

    Map<Long,List<ResponseDTO>> getResponsesForPosts(Set<Long> postIds, Integer size);

    PageDTO<PostDTO> fetchTimeline(String appContext,
                                   String domainContext,
                                   String languageContext,
                                   String locationContext,
                                   String postType,
                                   String taggedEntityType,
                                   Long taggedEntityId,
                                   Integer page, Integer size,
                                   String sortBy, String direction,String category,String subCategory,String status);

    PostDTO patchUpdate(PostDTO body);


    Set<PostAttachmentVO> fetchTimelineWithAttachments(String appContext,
                                                       String domainContext,
                                                       String languageContext,
                                                       String locationContext,
                                                       String postType,
                                                       String taggedEntityType,
                                                       Long taggedEntityId,
                                                       Integer page, Integer size,
                                                       String sortBy, String direction);

    Set<PostAttachmentVO> fetchPublicTimelineWithAttachments(String tenantLogin, String appContext,
                                                       String domainContext,
                                                       String languageContext,
                                                       String locationContext,
                                                       String postType,
                                                       String taggedEntityType,
                                                       Long taggedEntityId,
                                                       Integer page, Integer size,
                                                       String sortBy, String direction);

    PageDTO<PostDTO> fetchPublicTimeline(String tenantLogin, String appContext, String domainContext, String languageContext,
                                         String locationContext, String postType,Set<String> postSubTypes,String taggedEntityType,
                                         Long taggedEntityId, Integer page, Integer size, String sortBy, String direction,
                                         String category,String subCategory, String status,Set<String> causes,String actorType,Long actorId,String fetch,
                                         Boolean isModerated,Boolean includeBaseColumnForLanguage,Long responseCount, Boolean excludeReportedPost);

    void createUserReadEventForPost(Long postId);

    Map<Long, List<ResponseDTO>> getPublicResponsesForPosts(Set<Long> postIds, Integer size,String tenantLogin);

    PostDTO patchPostStatus(PostDTO postDTO);

    PageDTO<PostAttachmentResourceVO> getPostBlogResources(String tenantLogin,String appContext,
                                                        String domainContext, String languageContext,
                                                        String locationContext, String postType,
                                                        String taggedEntityType, Long taggedEntityId,
                                                        Integer page, Integer size, String sortBy,
                                                        String direction,String category,String subCategory,
                                                        String status);

    PageDTO<PostDTO> fetchPostForAdmin(String appContext, String postType, Set<String> postSubTypes, String sortBy, String direction,
                                       String category, String subCategory, String status, String actorType, Long actorId,
                                       Integer page, Integer size, String excludeStatus);

    void patchUpdatePostAndAssignCertificate(PostDTO postDTO,Boolean sendMail);

    PageDTO<PostDTO> fetchPostForAdminWithSearch(String appContext, String postType, Set<String> postSubTypes, String category, String subCategory,
                                                 String status, String excludeStatus, Long actorId, String actorName, String actorType,
                                                 Date startPostTime, Date endPostTime, Integer page, Integer size, String sortBy, String direction, Set<String> causes);

    ReactionForUpdateDTO getMyReactionAndTotalCount(Long postId);


    Integer changeStatusAndAssignCertificateInBulk(Set<Long> postIds, String status, Boolean assignCertificate,
                                                Long tenantCertificateTemplateId, Boolean sendMail);

    String getTaskStatus(Integer taskId);

    List<PostTypeCountVO> getPostCountAnalyticsByUserId(Set<String> postTypes, String appContext, String domainContext,
                                                        Long userId,String tenantLogin);

    PageDTO<PostDTO> fetchPostsForLoggedInUser(String appContext, String domainContext, String postType, String postSubType,
                                               String sortBy, String direction, String status, Integer page, Integer size);

    PageDTO<PostDTO> fetchPostsForLoggedInUserV2(String appContext, String domainContext, String postType, String postSubType,
                                               String sortBy, String direction, Set<String> includeStatusSet, Set<String> excludeStatusSet,
                                                 Integer page, Integer size);

    PageDTO<PostAttachmentVO> fetchPublishedPostAttachmentsByUserId(String appContext, String domainContext, String postType, String postSubType,
                                                                    String sortBy, String direction, Integer page, Integer size, String tenantLogin, Long userId);

    PostDTO patchUpdateForLoggedInUser(PostDTO body,Set<Long> addedAttachmentIds,Set<Long> deleteAttachmentIds);

    PostDTO patchUpdateWithAttachment(PostDTO postDTO, Set<Long> addedAttachmentIds, Set<Long> deleteAttachmentIds);

    PostDTO patchUpdateWithAttachmentReplace(Long postId, String attachmentDecision, PostDTO postDTO, MultipartFile file);


    PageDTO<PostDTO> fetchPublishedPostsByUserId(String appContext, String domainContext, String postType, String postSubType,
                                               String sortBy, String direction, Integer page, Integer size, String tenantLogin, Long userId);

    PageDTO<PostDTO> fetchPostBySearchText(String appContext, String postType, String searchText, Integer page, Integer size,
                                           String direction, String sortBy, String tenantLogin, Boolean isModerated, String domainContext,
                                           String postSubType, String status, Boolean isPublic, String visibility, String category,
                                           String subCategory, String languageContext, Boolean includeBaseColumnForLanguage);

    void syncPostsToSearchEngine(Set<Long> postIds, Integer pageSize, Integer pageNumbers);

    Map<Long , List<PostActiveMemberVO>> findActiveMembersForPosts(Set<Long> postIds, String tenantLogin);

    Set<PostVO> getPostsWithLinkedEntityCodeForLoggedInUser(String postType, String postSubType, String status);

    PageDTO<PostDTO> fetchPublicTimelineV3(String appContext, String domainContext, String languageContext,
                                         String locationContext, String postType,Set<String> postSubTypes,String taggedEntityType,
                                         Long taggedEntityId, Integer page, Integer size, String sortBy, String direction,
                                         String category,String subCategory, String status,Set<String> causes,String actorType,Long actorId,String fetch,
                                         Boolean isModerated,Boolean includeBaseColumnForLanguage,Long responseCount, Boolean excludeReportedPost);

    PostDTO patchUpdateWithAttachmentV4(PostDTO postDTO, Set<Long> addedAttachmentIds, Set<Long> deleteAttachmentIds);
}
