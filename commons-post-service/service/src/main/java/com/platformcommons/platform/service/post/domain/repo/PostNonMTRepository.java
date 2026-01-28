package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.vo.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostNonMTRepository extends NonMultiTenantBaseRepository<Post,Long> {

    @Query(value = "SELECT p FROM #{#entityName} p " +
            " LEFT JOIN p.titles titles " +
            " LEFT JOIN p.payloads payloads " +
            " LEFT JOIN p.contents contents " +
            " LEFT JOIN p.causes causes " +
            " WHERE " +
            " p.tenantId = :tenantId AND " +
            " p.isPublic=1 AND " +
            " p.isActive = 1 AND ( "  +
            "(  coalesce(:causes) is NULL OR causes IN (:causes) ) AND " +
            "( :postType is NULL OR p.postType = :postType ) AND " +
            "( coalesce(:postSubTypes) is NULL OR p.postSubType IN (:postSubTypes) ) AND " +
            "( p.appContext = :appContext  OR :appContext IS NULL ) AND " +
            "( p.domainContext = :domainContext  OR :domainContext IS NULL ) AND " +
            "( :languageContext IS NULL OR ( ( :includeBaseColumnForLanguage IS TRUE AND  p.languageContext = :languageContext ) " +
            " OR (titles.languageContext = :languageContext OR payloads.languageContext = :languageContext OR contents.languageContext = :languageContext ) ) ) AND " +
            "( p.locationContext = :locationContext  OR :locationContext IS NULL ) AND " +
            "( p.taggedToEntityType = :taggedEntityType OR :taggedEntityType IS NULL) AND "+
            "( p.taggedToEntityId = :taggedEntityId OR :taggedEntityId IS NULL) AND " +
            "( :category IS NULL OR p.category = :category ) AND " +
            "( :subCategory IS NULL OR p.subCategory = :subCategory) AND " +
            "( :status IS NULL OR p.status = :status ) AND " +
            "( :actorId IS NULL OR p.postedBy.actorId = :actorId ) AND " +
            "( :actorType IS NULL OR p.postedBy.actorType = :actorType ) AND" +
            "( p.status IS NULL OR p.status NOT IN (:statusToBeIgnored) ) AND " +
            "( :isModerated IS NULL OR p.isModerated = :isModerated ) AND "+
            "( :responseCount IS NULL OR p.commentCount = :responseCount ) AND" +
            "( :isReported IS NULL OR p.isReported = :isReported )" +
            " ) " +
            " GROUP BY p.id " )
    Page<Post> fetchTimeline(Long tenantId, String appContext, String domainContext,
                              String languageContext, String locationContext,
                              String postType, Set<String> postSubTypes, String taggedEntityType, Long taggedEntityId,
                              String category, String subCategory, String status,Set<String> causes, String actorType,
                              Long actorId, Set<String> statusToBeIgnored,Pageable pageable,
                              Boolean isModerated, Boolean includeBaseColumnForLanguage,Long responseCount, Boolean isReported);


    @Query(value = "SELECT new com.platformcommons.platform.service.post.domain.vo.PostAttachmentVO(p.id, p.postTime, p.postedBy) " +
            "FROM #{#entityName} p WHERE " +
            " p.tenantId = :tenantId AND " +
            " p.isActive = 1 AND " +
            "p.isPublic=1 AND p.hasAtttachment=1 AND ( "  +
            "( p.postType = :postType  OR :postType IS NULL ) AND " +
            "( p.appContext = :appContext  OR :appContext IS NULL ) AND " +
            "( p.domainContext = :domainContext  OR :domainContext IS NULL ) AND " +
            "( p.languageContext = :languageContext  OR :languageContext IS NULL ) AND " +
            "( p.locationContext = :locationContext  OR :locationContext IS NULL ) AND " +
            "( p.taggedToEntityType = :taggedEntityType OR :taggedEntityType IS NULL) AND "+
            "( p.taggedToEntityId = :taggedEntityId OR :taggedEntityId IS NULL)"+
            " ) " )
    Slice<PostAttachmentVO> fetchTimelineWithAttachments(Long tenantId, String appContext, String domainContext,
                                                         String languageContext, String locationContext,
                                                         String postType, String taggedEntityType, Long taggedEntityId , Pageable pageRequest);

    @Query("SELECT p.id from #{#entityName} p where p.id IN (:postIds) and p.isActive = 1 ")
    Set<Long> findAllWhichExist(Set<Long> postIds);

    @Query("SELECT count(p)>0 from #{#entityName} p where (p.id=:postId OR p.uuid=:uuid) and p.isActive = 1 ")
    Boolean existsByPostIdORUUId(Long postId,String uuid);

    @Modifying
    @Query("UPDATE #{#entityName} p SET p.readCount = (p.readCount + :count) where p.id = :postId")
    void updatePostReadCount(Long postId, Long count);


    @Query("SELECT new com.platformcommons.platform.service.post.domain.vo.PostAttachmentResourceVO( " +
            " p.payload, p.postedBy.name, p.contentType, p.postTime, a ) " +
            " FROM #{#entityName} p " +
            " LEFT JOIN Attachment a on a.entityId = p.id " +
            " WHERE " +
            " p.tenantId = :tenantId AND " +
            " p.isPublic=1 AND " +
            " p.isActive = 1 AND ( "  +
            "( p.postType = :postType  OR :postType IS NULL ) AND " +
            "( p.appContext = :appContext  OR :appContext IS NULL ) AND " +
            "( p.domainContext = :domainContext  OR :domainContext IS NULL ) AND " +
            "( p.languageContext = :languageContext  OR :languageContext IS NULL ) AND " +
            "( p.locationContext = :locationContext  OR :locationContext IS NULL ) AND " +
            "( p.taggedToEntityType = :taggedEntityType OR :taggedEntityType IS NULL) AND "+
            "( p.taggedToEntityId = :taggedEntityId OR :taggedEntityId IS NULL) AND " +
            "( :category IS NULL OR p.category = :category ) AND " +
            "( :subCategory IS NULL OR p.subCategory = :subCategory) AND " +
            "( :status IS NULL OR p.status = :status ) "+
            " ) " +
            " AND p.visibility = :visibility " +
            " AND a.isActive = true " +
            " AND a.mimeType NOT LIKE 'image/%' " +
            " group by a.id" )
    Page<PostAttachmentResourceVO> getPostBlogResources(Long tenantId, String appContext, String domainContext,
                                                        String languageContext, String locationContext,
                                                        String postType, String taggedEntityType, Long taggedEntityId,
                                                        String category, String subCategory, String status,String visibility,
                                                        Pageable pageRequest);

    @Query(" SELECT new com.platformcommons.platform.service.post.domain.vo.PostTypeCountVO(p.postedBy.actorId,p.postType,p.postSubType,COUNT(p.id)) " +
            " FROM #{#entityName} p " +
            " WHERE " +
            " ( coalesce(:postTypes) IS NULL OR p.postType IN (:postTypes) )" +
            " AND ( :appContext IS NULL OR p.appContext = :appContext ) " +
            " AND ( :domainContext IS NULL OR p.domainContext = :domainContext )" +
            " AND p.postedBy.actorId = :currentUserId " +
            " AND p.isActive = 1 " +
            " AND p.postedBy.isActive = 1 " +
            " AND p.tenantId = :tenantId " +
            " AND p.postedBy.tenantId = :tenantId" +
            " GROUP BY p.postType,p.postSubType ")
    List<PostTypeCountVO> findPostCountAnalyticsByActorId(Long currentUserId, Set<String> postTypes,
                                                          String appContext, String domainContext, Long tenantId);

    @Query(" SELECT p FROM #{#entityName} p WHERE " +
            " p.postedBy.actorId = :userId " +
            " AND ( :appContext IS NULL OR p.appContext = :appContext ) " +
            " AND ( :domainContext IS NULL OR p.domainContext = :domainContext ) " +
            " AND ( :postType IS NULL OR p.postType = :postType ) " +
            " AND ( :postSubType IS NULL OR p.postSubType = :postSubType ) " +
            " AND ( p.status IS NULL OR p.status NOT IN (:statusToBeIgnored) ) " +
            " AND p.isActive = 1 " +
            " AND p.postedBy.isActive = 1 " +
            " AND p.tenantId = :tenantId " +
            " AND p.postedBy.tenantId = :tenantId" )
    Page<Post> findPublishedPostsByActorIdInPagination(String appContext, String domainContext, String postType, String postSubType,
                                                       Set<String> statusToBeIgnored, Pageable pageable, Long userId,Long tenantId);

    @Query(" SELECT new com.platformcommons.platform.service.post.domain.vo.PostAttachmentVO(p.id, p.postTime) " +
            " FROM #{#entityName} p WHERE " +
            " p.isPublic = 1 AND p.hasAtttachment = 1  "  +
            " AND p.postedBy.actorId = :userId " +
            " AND ( :appContext IS NULL OR p.appContext = :appContext ) " +
            " AND ( :domainContext IS NULL OR p.domainContext = :domainContext ) " +
            " AND ( :postType IS NULL OR p.postType = :postType ) " +
            " AND ( :postSubType IS NULL OR p.postSubType = :postSubType ) " +
            " AND ( p.status IS NULL OR p.status NOT IN (:statusToBeIgnored) ) " +
            " AND p.isActive = 1 " +
            " AND p.postedBy.isActive = 1 " +
            " AND p.tenantId = :tenantId " +
            " AND p.postedBy.tenantId = :tenantId" )
    Page<PostAttachmentVO>  findPublishedPostsWithAttachmentPresentByUserId(String appContext, String domainContext, String postType, String postSubType,
                                                                            Set<String> statusToBeIgnored, Pageable pageable, Long userId,
                                                                            Long tenantId);

    @Query("SELECT p FROM #{#entityName} p WHERE p.id = :id AND p.tenantId= :tenantId AND p.isActive = 1")
    Optional<Post> findById(Long id, Long tenantId);

    @Query("SELECT p FROM #{#entityName} p WHERE p.uuid = :uuid AND p.isActive = 1")
    Optional<Post> findByUUID(String uuid);

    @Query("FROM #{#entityName} p WHERE p.id IN (:ids) ")
    Set<Post> findByIds(Set<Long> ids);

    @Query(value = "SELECT p FROM #{#entityName} p WHERE (:tenantId IS NULL OR p.tenantId = :tenantId) ")
    Page<Post> getPostsByTenantId(Long tenantId, Pageable pageable);


    @Query("SELECT new com.platformcommons.platform.service.post.domain.vo.PostActiveMemberVO( " +
            "p.id AS postId, a.name, a.iconpic ) " +
            "FROM Post p " +
            "LEFT JOIN Reaction r ON r.reactedOnEntityId = p.id " +
            "    AND r.reactedOnEntityType = 'ENTITY_TYPE.POST' " +
            "    AND r.reactionStatus = 'ACTIVE' " +
            "    AND r.isActive = 1 " +
            "LEFT JOIN Response rs ON rs.responseOnEntityId = p.id " +
            "    AND rs.responseOnEntityType = 'ENTITY_TYPE.POST' " +
            "    AND rs.isActive = 1 " +
            "JOIN PostActor a ON (a.id = p.postedBy " +
            "    OR a.id = r.reactedBy " +
            "    OR a.id = rs.responseBy) " +
            "WHERE p.id IN (:postIds) " +
            "AND p.tenantId = :tenantId " +
            "AND p.isActive = 1 " +
            "AND a.isActive = 1 " +
            "GROUP BY p.id, a.id " )
    List<PostActiveMemberVO> findActiveMembersForPosts(Set<Long> postIds, Long tenantId);

    @Query(value = "SELECT p FROM #{#entityName} p  " +
            " LEFT JOIN p.titles titles " +
            " LEFT JOIN p.payloads payloads " +
            " LEFT JOIN p.contents contents " +
            " LEFT JOIN p.causes causes " +
            " WHERE " +
            " p.isPublic=1 AND " +
            " p.isActive = 1 AND ( "  +
            "(  coalesce(:causes) is NULL OR causes IN (:causes) ) AND " +
            "( :postType is NULL OR p.postType = :postType ) AND " +
            "( coalesce(:postSubTypes) is NULL OR p.postSubType IN (:postSubTypes) ) AND " +
            "( p.appContext = :appContext  OR :appContext IS NULL ) AND " +
            "( p.domainContext = :domainContext  OR :domainContext IS NULL ) AND " +
            "( :languageContext IS NULL OR ( ( :includeBaseColumnForLanguage IS TRUE AND  p.languageContext = :languageContext ) " +
            " OR (titles.languageContext = :languageContext OR payloads.languageContext = :languageContext OR contents.languageContext = :languageContext ) ) ) AND " +
            "( p.locationContext = :locationContext  OR :locationContext IS NULL ) AND " +
            "( p.taggedToEntityType = :taggedEntityType OR :taggedEntityType IS NULL) AND "+
            "( p.taggedToEntityId = :taggedEntityId OR :taggedEntityId IS NULL) AND " +
            "( :category IS NULL OR p.category = :category ) AND " +
            "( :subCategory IS NULL OR p.subCategory = :subCategory) AND " +
            "( :status IS NULL OR p.status = :status ) AND "+
            "( :actorId IS NULL OR p.postedBy.actorId = :actorId ) AND " +
            "( :actorType IS NULL OR p.postedBy.actorType = :actorType ) AND " +
            "( p.status IS NULL OR p.status NOT IN (:statusToBeIgnored) ) AND " +
            "( :isModerated IS NULL OR p.isModerated = :isModerated ) AND "+
            "( :responseCount IS NULL OR p.commentCount = :responseCount ) AND "+
            "( :excludeReportedPost IS NULL OR p.isReported = :excludeReportedPost )" +
            " ) " +
            " GROUP BY p.id " )
    Page<Post> fetchTimelineV3(String appContext, String domainContext,
                               String languageContext, String locationContext,
                               String postType,Set<String> postSubTypes,String taggedEntityType,Long taggedEntityId,
                               String category,String subCategory, String status ,Set<String> causes,String actorType,Long actorId,
                               Set<String> statusToBeIgnored,Pageable pageable,
                               Boolean isModerated,Boolean includeBaseColumnForLanguage,Long responseCount, Boolean excludeReportedPost);

}
