package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.vo.PostAttachmentResourceVO;
import com.platformcommons.platform.service.post.domain.vo.PostAttachmentVO;
import com.platformcommons.platform.service.post.domain.vo.PostTypeCountVO;
import com.platformcommons.platform.service.post.domain.vo.PostVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends BaseRepository<Post, Long> {

    @Query("FROM #{#entityName} p WHERE p.uuid=:uuid ")
    Optional<Post> findByUUID(String uuid);

    @Query(value = "FROM #{#entityName} p ")
    Slice<Post> findAllViaSlice(@NotNull Pageable pageable);

    @Query("SELECT p.id from #{#entityName} p where p.id IN (:postIds) ")
    Set<Long> findAllWhichExist(Set<Long> postIds);

    @Query("SELECT p from #{#entityName} p where p.id IN (:postIds) ")
    Set<Post> findByIds(Set<Long> postIds);

    @Query("SELECT count(p)>0 from #{#entityName} p where (p.id=:postId OR p.uuid=:uuid) ")
    Boolean existsByPostIdORUUId(Long postId, String uuid);

    // @formatter:off
    @Query(value = "FROM #{#entityName} p WHERE " +
                   " p.isPublic=1 AND ( "  +
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
                        " ) " )
    Slice<Post> fetchTimeline(String appContext, String domainContext,
                              String languageContext, String locationContext,
                              String postType,String taggedEntityType,Long taggedEntityId,
                              String category,String subCategory, String status ,Pageable pageRequest);

    @Query(value = "SELECT p FROM #{#entityName} p  " +
            " LEFT JOIN p.titles titles " +
            " LEFT JOIN p.payloads payloads " +
            " LEFT JOIN p.contents contents " +
            " LEFT JOIN p.causes causes " +
            " WHERE " +
            " p.isPublic=1 AND ( "  +
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
    Page<Post> fetchTimelineV2(String appContext, String domainContext,
                              String languageContext, String locationContext,
                              String postType,Set<String> postSubTypes,String taggedEntityType,Long taggedEntityId,
                              String category,String subCategory, String status ,Set<String> causes,String actorType,Long actorId,
                                Set<String> statusToBeIgnored,Pageable pageable,
                                Boolean isModerated,Boolean includeBaseColumnForLanguage,Long responseCount, Boolean excludeReportedPost);

    @Query(value = "SELECT new com.platformcommons.platform.service.post.domain.vo.PostAttachmentVO(p.id, p.postTime, p.postedBy) " +
            "FROM #{#entityName} p WHERE " +
            "p.isPublic=1 AND p.hasAtttachment=1 AND ( "  +
            "( p.postType = :postType  OR :postType IS NULL ) AND " +
            "( p.appContext = :appContext  OR :appContext IS NULL ) AND " +
            "( p.domainContext = :domainContext  OR :domainContext IS NULL ) AND " +
            "( p.languageContext = :languageContext  OR :languageContext IS NULL ) AND " +
            "( p.locationContext = :locationContext  OR :locationContext IS NULL ) AND " +
            "( p.taggedToEntityType = :taggedEntityType OR :taggedEntityType IS NULL) AND "+
            "( p.taggedToEntityId = :taggedEntityId OR :taggedEntityId IS NULL)"+
            " ) " )
    Slice<PostAttachmentVO> fetchTimelineWithAttachments(String appContext, String domainContext,
                                             String languageContext, String locationContext,
                                             String postType,String taggedEntityType,Long taggedEntityId ,Pageable pageRequest);

    @Modifying
    @Query("UPDATE #{#entityName} p SET p.readCount = (p.readCount + :count) where p.id = :postId")
    void updatePostReadCount(Long postId, Long count);


    @Query("SELECT new com.platformcommons.platform.service.post.domain.vo.PostAttachmentResourceVO( " +
            " p.payload, p.postedBy.name, p.contentType, p.postTime, a ) " +
            " FROM #{#entityName} p " +
            " LEFT JOIN Attachment a on a.entityId = p.id " +
            " WHERE " +
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
    Page<PostAttachmentResourceVO> getPostBlogResources(String appContext, String domainContext,
                                                        String languageContext, String locationContext,
                                                        String postType, String taggedEntityType, Long taggedEntityId,
                                                        String category,String subCategory,String status,String visibility,
                                                        Pageable pageRequest);

    @Query(value = "FROM #{#entityName} p WHERE " +
            " p.isPublic=1  "  +
            " AND ( :postType is NULL OR p.postType = :postType ) " +
            " AND ( coalesce(:postSubTypes) is NULL OR p.postSubType IN (:postSubTypes) ) " +
            " AND ( p.appContext = :appContext  OR :appContext IS NULL ) " +
            " AND ( :category IS NULL OR p.category = :category ) " +
            " AND ( :subCategory IS NULL OR p.subCategory = :subCategory) " +
            " AND ( :status IS NULL OR p.status = :status ) "+
            " AND ( :excludeStatus IS NULL OR p.status != :excludeStatus ) "+
            " AND ( :actorId IS NULL OR p.postedBy.actorId = :actorId ) " +
            " AND ( :actorType IS NULL OR p.postedBy.actorType = :actorType ) " )
    Page<Post> fetchPostForAdmin(String appContext, String postType, Set<String> postSubTypes, String category,
                                 String subCategory, String status, String actorType, Long actorId, String excludeStatus,
                                 Pageable pageable);

    @Query("SELECT p.reactCount FROM #{#entityName} p WHERE p.id = :postId")
    Long findReactCountByPostId(Long postId);

    @Query(" SELECT new com.platformcommons.platform.service.post.domain.vo.PostTypeCountVO(p.postedBy.actorId,p.postType,p.postSubType,COUNT(p.id)) " +
            " FROM #{#entityName} p " +
            " WHERE " +
            " ( coalesce(:postTypes) IS NULL OR p.postType IN (:postTypes) )" +
            " AND ( :appContext IS NULL OR p.appContext = :appContext ) " +
            " AND ( :domainContext IS NULL OR p.domainContext = :domainContext )" +
            " AND p.postedBy.actorId = :currentUserId " +
            " GROUP BY p.postType,p.postSubType ")
    List<PostTypeCountVO> findPostCountAnalyticsByActorId(Long currentUserId, Set<String> postTypes,
                                                          String appContext, String domainContext);

    @Query(" SELECT p FROM #{#entityName} p WHERE " +
            " p.postedBy.actorId = :currentUserId " +
            " AND p.postedBy.actorType = :actorContext " +
            " AND ( :appContext IS NULL OR p.appContext = :appContext ) " +
            " AND ( :domainContext IS NULL OR p.domainContext = :domainContext ) " +
            " AND ( :postType IS NULL OR p.postType = :postType ) " +
            " AND ( :postSubType IS NULL OR p.postSubType = :postSubType ) " +
            " AND ( :status IS NULL OR p.status = :status )" )
    Page<Post> findPostsByActorIdInPagination(String appContext, String domainContext, String postType, String postSubType,
                                              String status, Pageable pageable, Long currentUserId, String actorContext);

    @Query(" SELECT p FROM #{#entityName} p WHERE " +
            " p.postedBy.actorId = :currentUserId " +
            " AND p.postedBy.actorType = :actorContext " +
            " AND ( :appContext IS NULL OR p.appContext = :appContext ) " +
            " AND ( :domainContext IS NULL OR p.domainContext = :domainContext ) " +
            " AND ( :postType IS NULL OR p.postType = :postType ) " +
            " AND ( :postSubType IS NULL OR p.postSubType = :postSubType ) " +
            " AND ( coalesce(:includeStatusSet) IS NULL OR p.status IN (:includeStatusSet) ) " +
            " AND ( coalesce(:excludeStatusSet) IS NULL OR p.status NOT IN (:excludeStatusSet) )" )
    Page<Post> findPostsByActorIdInPaginationV2(String appContext, String domainContext, String postType, String postSubType,
                                                Set<String> includeStatusSet, Set<String> excludeStatusSet, Pageable pageable,
                                                Long currentUserId, String actorContext);

    @Query(" SELECT p FROM #{#entityName} p WHERE " +
            " p.postedBy.actorId = :userId " +
            " AND ( :appContext IS NULL OR p.appContext = :appContext ) " +
            " AND ( :domainContext IS NULL OR p.domainContext = :domainContext ) " +
            " AND ( :postType IS NULL OR p.postType = :postType ) " +
            " AND ( :postSubType IS NULL OR p.postSubType = :postSubType ) " +
            " AND ( p.status IS NULL OR p.status NOT IN (:statusToBeIgnored) ) " )
    Page<Post> findPublishedPostsByActorIdInPagination(String appContext, String domainContext, String postType, String postSubType,
                                                       Set<String> statusToBeIgnored, Pageable pageable, Long userId);

    @Query(" SELECT new com.platformcommons.platform.service.post.domain.vo.PostAttachmentVO(p.id, p.postTime) " +
            " FROM #{#entityName} p WHERE " +
            " p.isPublic = 1 AND p.hasAtttachment = 1  "  +
            " AND p.postedBy.actorId = :userId " +
            " AND ( :appContext IS NULL OR p.appContext = :appContext ) " +
            " AND ( :domainContext IS NULL OR p.domainContext = :domainContext ) " +
            " AND ( :postType IS NULL OR p.postType = :postType ) " +
            " AND ( :postSubType IS NULL OR p.postSubType = :postSubType ) " +
            " AND ( p.status IS NULL OR p.status NOT IN (:statusToBeIgnored) ) " )
    Page<PostAttachmentVO>  findPublishedPostsWithAttachmentPresentByUserId(String appContext, String domainContext, String postType, String postSubType,
                                                     Set<String> statusToBeIgnored, Pageable pageable, Long userId);


    @Query(" SELECT p FROM #{#entityName} p " +
            " WHERE p.id = :id " +
            " AND p.postedBy.actorId = :currentUserId " +
            " AND p.postedBy.actorType = :actorContext ")
    Optional<Post> findByIdByActorId(Long id, Long currentUserId, String actorContext);

    @Query(" SELECT new com.platformcommons.platform.service.post.domain.vo.PostVO(p.id, p.linkedEntityCode, p.status) " +
            " FROM #{#entityName} p WHERE " +
            " p.postedBy.actorId = :userId " +
            " AND p.linkedEntityCode IS NOT NULL " +
            " AND ( :postType IS NULL OR p.postType = :postType ) " +
            " AND ( :status IS NULL OR p.status = :status ) " +
            " AND ( :postSubType IS NULL OR p.postSubType = :postSubType ) ")
    Set<PostVO> findPostsWithLinkedEntityCodeByUserId(Long userId, String postType, String postSubType, String status);

}