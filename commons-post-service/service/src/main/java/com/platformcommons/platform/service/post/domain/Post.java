package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.UoMValue;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import lombok.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.hibernate.annotations.BatchSize;
import org.hibernate.search.engine.backend.types.ObjectStructure;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.platformcommons.platform.service.post.application.constant.PostConstant.POST_VISIBILITY_VISIBLE;

@Entity
@Getter
@Setter
@Indexed
@Table(name = "post",
        indexes = {
                @Index(name = "IDX_POST_TYPE",
                        columnList = "post_type"),
                @Index(name = "IDX_POST_LOCATION",
                        columnList = "location_context"),
                @Index(name = "IDX_POST_DOMAIN",
                        columnList = "domain_context"),
                @Index(name = "IDX_POST_APP",
                        columnList = "app_context"),
                @Index(name = "IDX_POST_LANGUAGE",
                        columnList = "language_context"),
                @Index(name = "IDX_POST_PUBLIC",
                        columnList = "is_public")
        })
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post extends BaseTransactionalEntity implements DomainEntity<Post> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    @GenericField(sortable = Sortable.YES)
    private Long id;

    @IndexedEmbedded
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @NotNull(message = "postedBy must not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "posted_by", nullable = false, updatable = false)
    private PostActor postedBy;

    @Column(name = "title")
    @FullTextField(analyzer = "post_indexing",searchAnalyzer = "post_searching")
    private String title;

    @NotNull(message = "payload must not be null")
    @Column(name = "payload", columnDefinition = "TEXT")
    @FullTextField(analyzer = "post_indexing",searchAnalyzer = "post_searching")
    private String payload;

    @Column(name = "comment_count")
    private Long commentCount;

    @Column(name = "react_count")
    private Long reactCount;

    @Column(name = "emotion")
    private String emotion;

    @Column(name = "expert_rank")
    private Long expertRank;

    @Column(name = "is_pinned_post")
    private Boolean isPinnedPost;

    @GenericField(sortable = Sortable.YES)
    @Column(name = "post_time")
    private Date postTime;

    @KeywordField
    @Column(name = "post_type")
    private String postType;

    @KeywordField
    @Column(name = "post_sub_type")
    private String postSubType;

    @KeywordField
    @Column(name = "visibility")
    private String visibility;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "location_context")
    private String locationContext;

    @KeywordField
    @NotNull(message = "appContext must not be null")
    @Column(name = "app_context")
    private String appContext;

    @KeywordField
    @Column(name = "language_context")
    private String languageContext;

    @KeywordField
    @Column(name = "domain_context")
    private String domainContext;

    @GenericField(indexNullAs = "false")
    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "is_sensitive")
    private Boolean isSensitive;

    @Column(name = "is_modified")
    private Boolean isModified;

    @Column(name = "is_reported")
    private Boolean isReported;

    @GenericField(indexNullAs = "false")
    @Column(name = "is_moderated")
    private Boolean isModerated;

    @Column(name="has_attachment")
    private Boolean hasAtttachment;

    @Column(name = "tagged_to_entity_type")
    private String taggedToEntityType;
    @Column(name = "tagged_to_entity_id")
    private Long taggedToEntityId;

    @Column(name = "content",columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "read_count")
    private Long readCount;

    @Column(name = "read_time_estimate")
    private LocalTime readTimeEstimate;

    @Column(name = "content_type")
    private String contentType;

    @KeywordField
    @Column(name = "category")
    private String category;

    @KeywordField
    @Column(name = "sub_category")
    private String subCategory;

    @KeywordField
    @Column(name = "status")
    private String status;

    @Column(name = "status_updated_date")
    private Date statusUpdatedDate;

    @Column(name = "post_start_date")
    private Date postStartDate;

    @Column(name = "post_end_date")
    private Date postEndDate;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "effort",referencedColumnName = "id")
    private UoMValue effort;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "approved_effort",referencedColumnName = "id")
    private UoMValue approvedEffort;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "address",columnDefinition = "TEXT")
    private String address;

    @Column(name = "linked_entity_code")
    private String linkedEntityCode;

    @Column(name = "points_awarded")
    private Long pointsAwarded;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
    @BatchSize(size = 20)
    private Set<PostImpact> postImpacts;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
    @BatchSize(size = 20)
    private Set<PostContent> postContents;

    @Column(name = "evaluation_remarks")
    private String evaluationRemarks;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_causes",
            joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "cause")
    @KeywordField(searchable = Searchable.YES,sortable = Sortable.YES)
    @BatchSize(size = 20)
    private Set<String> causes;

    @IndexedEmbedded(targetType = MultiLanguage.class,structure = ObjectStructure.NESTED)
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinTable(
            name = "title_multi_language",
            joinColumns = @JoinColumn(name = "title"),
            inverseJoinColumns = @JoinColumn(name = "multi_language_id"))
    private Set<MultiLanguage> titles;

    @IndexedEmbedded(targetType = MultiLanguage.class,structure = ObjectStructure.NESTED)
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinTable(
            name = "payload_multi_language",
            joinColumns = @JoinColumn(name = "payload"),
            inverseJoinColumns = @JoinColumn(name = "multi_language_id"))
    private Set<MultiLanguage> payloads;

    @IndexedEmbedded(targetType = MultiLanguage.class,structure = ObjectStructure.NESTED)
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinTable(
            name = "content_multi_language",
            joinColumns = @JoinColumn(name = "content"),
            inverseJoinColumns = @JoinColumn(name = "multi_language_id"))
    private Set<MultiLanguage> contents;

    @Transient
    private boolean isNew;

    @Transient
    private PostActorCertificate postActorCertificate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
    @BatchSize(size = 20)
    private Set<PostTag> postTags;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
    @BatchSize(size = 20)
    private Set<PostTagActor> postTagActors;


    @Builder
    public Post(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp,
                Long appLastModifiedTimestamp, Long id, PostActor postedBy, String payload,
                Long commentCount, Long reactCount, String emotion, Long expertRank, Boolean isPinnedPost,
                Date postTime, String postType, String visibility, String attachmentUrl, String locationContext,
                String appContext, String languageContext, String domainContext, Boolean isPublic, Boolean isSensitive,
                Boolean isModified, Boolean isModerated, boolean isNew,Boolean hasAtttachment,
                String taggedToEntityType,Long taggedToEntityId, String content, Long readCount, LocalTime readTimeEstimate,
                String contentType,String category,String subCategory, String status, Date statusUpdatedDate,
                String postSubType, UoMValue effort, Set<PostImpact> postImpacts, Double latitude, Double longitude,
                String address,String title,UoMValue approvedEffort, String evaluationRemarks,PostActorCertificate postActorCertificate,
                Set<String> causes,Set<MultiLanguage> titles,Set<MultiLanguage> payloads,Set<MultiLanguage> contents,Set<PostTag> postTags,
                Set<PostContent> postContents,String linkedEntityCode, Long pointsAwarded, Date postStartDate, Date postEndDate, Boolean isReported, Set<PostTagActor> postTagActors) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.postedBy = postedBy;
        this.payload = payload;
        this.commentCount = commentCount;
        this.reactCount = reactCount;
        this.emotion = emotion;
        this.expertRank = expertRank;
        this.isPinnedPost = isPinnedPost;
        this.postTime = postTime;
        this.postType = postType;
        this.visibility = visibility;
        this.attachmentUrl = attachmentUrl;
        this.locationContext = locationContext;
        this.appContext = appContext;
        this.languageContext = languageContext;
        this.domainContext = domainContext;
        this.isPublic = isPublic;
        this.isSensitive = isSensitive;
        this.isModified = isModified;
        this.isModerated = isModerated;
        this.isNew = isNew;
        this.hasAtttachment=hasAtttachment;
        this.taggedToEntityType=taggedToEntityType;
        this.taggedToEntityId=taggedToEntityId;
        this.content = content;
        this.readCount = readCount;
        this.readTimeEstimate = readTimeEstimate;
        this.contentType = contentType;
        this.category = category;
        this.subCategory = subCategory;
        this.status = status;
        this.statusUpdatedDate = statusUpdatedDate;
        this.postStartDate = postStartDate;
        this.postEndDate = postEndDate;
        this.postSubType = postSubType;
        this.effort = effort;
        this.postImpacts = postImpacts;
        if(postImpacts != null) {
            this.postImpacts.forEach(it->it.setPost(this));
        }
        this.postContents = postContents;
        if(postContents!=null){
            this.postContents.forEach(it-> it.setPost(this));
        }
        this.postTags = postTags;
        if(postTags != null) {
            this.postTags.forEach(it->it.setPost(this));
        }
        this.postTagActors = postTagActors;
        if(postTagActors != null) {
            this.postTagActors.forEach(it->it.setPost(this));
        }
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.title = title;
        this.approvedEffort = approvedEffort;
        this.evaluationRemarks = evaluationRemarks;
        this.postActorCertificate = postActorCertificate;
        this.causes = causes;
        this.titles = titles;
        this.payloads = payloads;
        this.contents = contents;
        this.linkedEntityCode = linkedEntityCode;
        this.pointsAwarded = pointsAwarded;
        this.isReported = isReported;
    }

    public void init(PostActor postActor) {
        this.id = 0L;
        this.expertRank = 0L;
        this.commentCount = 0L;
        this.reactCount = 0L;
        this.postTime = new Date();
        this.postedBy = postActor;
        this.visibility = POST_VISIBILITY_VISIBLE;
        this.isModified = false;
        this.isSensitive = false;
        this.hasAtttachment=false;
        this.readCount = 0L;
        if(Objects.equals(this.postSubType, PostConstant.POST_SUB_TYPE_IMPACT_TYPE)) {
            this.status = PostConstant.POST_STATUS_PENDING_APPROVAL;
        }
        if(this.status == null) {
            this.status = PostConstant.POST_STATUS_APPROVED;
        }
        if (this.status != null) this.statusUpdatedDate = new Date();
        this.approvedEffort = null;
    }


    public void isAuthorizedForUpdate() {
        boolean isAllowed = this.getCreatedByUser()
                .equals(PlatformSecurityUtil.getCurrentUserId())
                || PlatformSecurityUtil.isTenantAdmin();
        if (!isAllowed)
            throw new UnAuthorizedAccessException("User is not authorized to update this post.");
    }

    public Date getPost_time() {
        return new Date(postTime != null ? postTime.getTime() : new Date().getTime());
    }

    public void setPost_time(Date postTime) {
        this.postTime = new Date(postTime != null ? postTime.getTime() : new Date().getTime());
    }

    public void update(Post post, boolean mapNulls) {
        if (!StringUtils.isEmpty(post.getPayload()))
            this.payload = post.getPayload();
        if (mapNulls) {
            putUpdate(post);
        } else {
            patchUpdate(post);
        }
        if (!StringUtils.isEmpty(post.getContent())) this.content = post.getContent();
    }

    private void putUpdate(Post post) {
        this.emotion = post.getEmotion();
        this.locationContext = post.getLocationContext();
        this.domainContext = post.getDomainContext();
        this.postType = post.getPostType();
        this.readTimeEstimate = post.getReadTimeEstimate();
        this.contentType = post.getContentType();
        if (post.getStatus() != null && !this.status.equals(post.getStatus())) {
            this.status = post.getStatus();
            this.statusUpdatedDate = new Date();
        }
        this.postSubType = post.getPostSubType();
        this.effort = post.getEffort();
        if(post.getPostImpacts() != null) {
            post.getPostImpacts().forEach(it->it.setPost(this));
            this.getPostImpacts().clear();
            this.getPostImpacts().addAll(post.getPostImpacts());
        }
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
        this.address = post.getAddress();
        this.title = post.getTitle();
        this.approvedEffort = post.getApprovedEffort();
        this.evaluationRemarks = post.getEvaluationRemarks();
        this.linkedEntityCode = post.getLinkedEntityCode();
        this.pointsAwarded = post.getPointsAwarded();
        this.postStartDate = post.getPostStartDate();
        this.postEndDate = post.getPostEndDate();
        this.causes = post.getCauses();
        {
            this.getTitles().clear();
            this.getTitles().addAll(post.getTitles());
        }
        {
            this.getPayloads().clear();
            this.getPayloads().addAll(post.getPayloads());
        }
        {
            this.getContents().clear();
            this.getContents().addAll(post.getContents());
        }
        this.getPostTags().clear();
        this.getPostTags().addAll(post.getPostTags());

    }

    public void patchUpdate(Post post) {
        if (!StringUtils.isEmpty(post.getEmotion())) {
            this.emotion = post.getEmotion();
        }
        if (!StringUtils.isEmpty(post.getLocationContext())) {
            this.locationContext = post.getLocationContext();
        }
        if (!StringUtils.isEmpty(post.getDomainContext())) {
            this.domainContext = post.getDomainContext();
        }
        if (!StringUtils.isEmpty(post.getPostType())) {
            this.postType = post.getPostType();
        }
        if(post.getIsPinnedPost()!=null) {
            this.isPinnedPost=post.getIsPinnedPost();
        }
        if(post.getExpertRank()!=null) {
            this.expertRank=post.getExpertRank();
        }
        if (!StringUtils.isEmpty(post.getVisibility())) {
            this.visibility = post.getVisibility();
        }
        if (!StringUtils.isEmpty(post.getAttachmentUrl())) {
            this.attachmentUrl = post.getAttachmentUrl();
        }
        if (!StringUtils.isEmpty(post.getAppContext())) {
            this.appContext = post.getAppContext();
        }
        if (!StringUtils.isEmpty(post.getLanguageContext())) {
            this.languageContext = post.getLanguageContext();
        }
        if(post.getIsPublic()!=null) {
            this.isPublic=post.getIsPublic();
        }
        if(post.getIsSensitive()!=null) {
            this.isSensitive=post.getIsSensitive();
        }
        if(post.getIsModified()!=null) {
            this.isModified=post.getIsModified();
        }
        if(post.getIsModerated()!=null) {
            this.isModerated=post.getIsModerated();
        }
        if(post.getHasAtttachment()!=null) {
            this.hasAtttachment=post.getHasAtttachment();
        }
        if (post.getReadTimeEstimate() != null) {
            this.readTimeEstimate = post.getReadTimeEstimate();
        }
        if (post.getContentType() != null) {
            this.contentType = post.getContentType();
        }
        if (post.getLinkedEntityCode() != null) {
            this.linkedEntityCode = post.getLinkedEntityCode();
        }
        if (post.getPointsAwarded() != null) {
            this.pointsAwarded = post.getPointsAwarded();
        }
        if (post.getPostStartDate() != null) {
            this.postStartDate = post.getPostStartDate();
        }
        if (post.getPostEndDate() != null) {
            this.postEndDate = post.getPostEndDate();
        }
        this.category = post.getCategory() != null ? post.getCategory():this.category;
        this.subCategory = post.getSubCategory() != null ? post.getSubCategory():this.subCategory;
        if (post.getStatus() != null && !Objects.equals(this.status,post.getStatus())) {
            this.status = post.getStatus();
            this.statusUpdatedDate = new Date();
        }
        if(post.getPostSubType()!=null) {
            this.postSubType=post.getPostSubType();
        }
        if(post.getEffort()!=null) {
            this.effort=post.getEffort();
        }
        if(post.getApprovedEffort()!=null) {
            this.approvedEffort=post.getApprovedEffort();
        }
        if(post.getLatitude()!=null) {
            this.latitude=post.getLatitude();
        }
        if(post.getLongitude()!=null) {
            this.longitude=post.getLongitude();
        }
        if(post.getAddress()!=null) {
            this.address=post.getAddress();
        }
        if(post.getTitle()!=null) {
            this.title=post.getTitle();
        }
        if(post.getPostImpacts() != null && !post.getPostImpacts().isEmpty()) {
            this.postImpacts.forEach(savedPostImpact->
                    post.getPostImpacts().forEach(toBeUpdatePostImpact->
                    {
                        if(Objects.equals(savedPostImpact.getId(),toBeUpdatePostImpact.getId())) {
                            savedPostImpact.patchUpdate(toBeUpdatePostImpact);
                        }
                    }));
            post.getPostImpacts().forEach(toBeSavedPostImpact->{
                if(toBeSavedPostImpact.getId() == null || toBeSavedPostImpact.getId() == 0L) {
                    toBeSavedPostImpact.init(this);
                    this.getPostImpacts().add(toBeSavedPostImpact);
                }
            });
        }
        if(!CollectionUtils.isEmpty(post.getPostTags())){
            Map<Long, PostTag> dbTagMap = getPostTags().stream().collect(Collectors.toMap(PostTag::getId, Function.identity()));
            post.getPostTags().forEach(postTag -> {
                if(postTag.getId() == null || postTag.getId() == 0L){
                    postTag.init();
                    postTag.setPost(this);
                    getPostTags().add(postTag);
                }else {
                    PostTag dbPostTag = dbTagMap.get(postTag.getId());
                    if(dbPostTag!=null){
                        dbPostTag.patchUpdate(postTag);
                    }
                }
            });
        }
        if(!StringUtils.isBlank(post.getEvaluationRemarks())) {
            this.evaluationRemarks = post.getEvaluationRemarks();
        }
        if(post.getCauses() !=null) {
            this.causes = post.getCauses();
        }
        patchUpdateMultiLanguageFields(post);
    }

    public void patchUpdateMultiLanguageFields(Post post) {
        if(post.getTitles() != null) {
            this.titles.forEach(it-> post.getTitles().forEach(toBeUpdated->{
                if(Objects.equals(it.getLanguageContext(),toBeUpdated.getLanguageContext())) {
                    it.patchUpdate(toBeUpdated);
                }
            }));
            post.getTitles().forEach(toBeSaved->{
                if(toBeSaved.getId() == null || toBeSaved.getId().equals(0L)) {
                    this.titles.add(toBeSaved);
                }
            });
        }
        if(post.getPayloads() != null) {
            this.payloads.forEach(it-> post.getPayloads().forEach(toBeUpdated->{
                if(Objects.equals(it.getLanguageContext(),toBeUpdated.getLanguageContext())) {
                    it.patchUpdate(toBeUpdated);
                }
            }));
            post.getPayloads().forEach(toBeSaved->{
                if(toBeSaved.getId() == null || toBeSaved.getId().equals(0L)) {
                    this.payloads.add(toBeSaved);
                }
            });
        }
        if(post.getContents() != null) {
            this.contents.forEach(it-> post.getContents().forEach(toBeUpdated->{
                if(Objects.equals(it.getLanguageContext(),toBeUpdated.getLanguageContext())) {
                    it.patchUpdate(toBeUpdated);
                }
            }));
            post.getContents().forEach(toBeSaved->{
                if(toBeSaved.getId() == null || toBeSaved.getId().equals(0L)) {
                    this.contents.add(toBeSaved);
                }
            });
        }

    }

    public void updateStatus(String status) {
        if (this.status == null || !this.status.equals(status)) this.statusUpdatedDate = new Date();
        this.status = status;
    }

    public void patchUpdateForPostCreator(Post post) {
        if (!StringUtils.isEmpty(post.getEmotion())) {
            this.emotion = post.getEmotion();
        }
        if (!StringUtils.isEmpty(post.getVisibility())) {
            this.visibility = post.getVisibility();
        }
        if (!StringUtils.isEmpty(post.getAttachmentUrl())) {
            this.attachmentUrl = post.getAttachmentUrl();
        }
        if(post.getIsPublic()!=null) {
            this.isPublic=post.getIsPublic();
        }
        if(post.getIsSensitive()!=null) {
            this.isSensitive=post.getIsSensitive();
        }
        this.isModified=Boolean.TRUE;
        if(post.getIsModerated()!=null) {
            this.isModerated=post.getIsModerated();
        }
        if (post.getReadTimeEstimate() != null) {
            this.readTimeEstimate = post.getReadTimeEstimate();
        }
        if (post.getContentType() != null) {
            this.contentType = post.getContentType();
        }
        if (post.getCategory() != null) {
            this.category = post.getCategory();
        }
        if (post.getSubCategory() != null) {
            this.contentType = post.getSubCategory();
        }
        if(post.getEffort()!=null) {
            this.effort=post.getEffort();
        }
        if(post.getLatitude()!=null) {
            this.latitude=post.getLatitude();
        }
        if(post.getLongitude()!=null) {
            this.longitude=post.getLongitude();
        }
        if(post.getAddress()!=null) {
            this.address=post.getAddress();
        }
        if(post.getTitle()!=null) {
            this.title=post.getTitle();
        }
        if(!StringUtils.isBlank(post.getPayload())) {
            this.payload=post.getPayload();
        }
        if(!StringUtils.isBlank(post.getContent())) {
            this.content=post.getContent();
        }
        if(post.getPostImpacts() != null && !post.getPostImpacts().isEmpty()) {
            this.postImpacts.forEach(savedPostImpact->
                    post.getPostImpacts().forEach(toBeUpdatePostImpact->
                    {
                        if(Objects.equals(savedPostImpact.getId(),toBeUpdatePostImpact.getId())) {
                            savedPostImpact.patchUpdateForPostImpactCreator(toBeUpdatePostImpact);
                        }
                    }));
            post.getPostImpacts().forEach(toBeSavedPostImpact->{
                if(toBeSavedPostImpact.getId() == null || toBeSavedPostImpact.getId() == 0L) {
                    toBeSavedPostImpact.init(this);
                    this.getPostImpacts().add(toBeSavedPostImpact);
                }
            });
        }
        if(post.getCauses() !=null) {
            this.causes = post.getCauses();
        }
        patchUpdateMultiLanguageFields(post);
    }

    public void initDailyLogs(PostActor postActor) {
        this.id=null;
        if(!CollectionUtils.isEmpty(this.postTags))
            this.postTags.forEach(PostTag::init);
        this.isPublic = false;
        if(Objects.isNull(payload))  this.payload="";
        this.postTime = getPost_time();
        this.postedBy = postActor;
        this.visibility = POST_VISIBILITY_VISIBLE;
        this.isModified = false;
        this.isSensitive = false;
        this.hasAtttachment=false;
    }
}