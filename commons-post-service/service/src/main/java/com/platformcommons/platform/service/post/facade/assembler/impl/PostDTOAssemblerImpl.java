package com.platformcommons.platform.service.post.facade.assembler.impl;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO.AttachmentDTOBuilder;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.entity.common.MLText;
import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.assembler.UoMDTOAssembler;
import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.Post.PostBuilder;
import com.platformcommons.platform.service.post.dto.MultiLanguageDTO;
import com.platformcommons.platform.service.post.dto.PostDTO;
import com.platformcommons.platform.service.post.dto.PostDTO.PostDTOBuilder;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.*;
import java.util.stream.Collectors;

import com.platformcommons.platform.service.post.facade.assembler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostDTOAssemblerImpl implements PostDTOAssembler {

    @Autowired
    private PostTagDTOAssemblerImpl postTagDTOAssembler;
    @Autowired
    private PostTagActorDTOAssemblerImpl postTagActorDTOAssembler;
    @Autowired
    private PostImpactDTOAssembler postImpactDTOAssembler;
    @Autowired
    private UoMDTOAssembler uoMDTOAssembler;
    @Autowired
    private PostActorCertificateDTOAssembler postActorCertificateDTOAssembler;
    @Autowired
    private PostActorDTOAssembler postActorDTOAssembler;
    @Autowired
    private MultiLanguageDTOAssembler multiLanguageDTOAssembler;

    @Autowired
    private PostContentDTOAssembler contentDTOAssembler;

    @Override
    public PostDTO toDTO(Post entity) {
        if ( entity == null ) {
            return null;
        }

        PostDTOBuilder postDTO = PostDTO.builder();

        postDTO.uuid( entity.getUuid() );
        postDTO.id( entity.getId() );
        postDTO.postedBy( postActorDTOAssembler.toDTO( entity.getPostedBy() ) );
        postDTO.payload( entity.getPayload() );
        postDTO.commentCount( entity.getCommentCount() );
        postDTO.reactCount( entity.getReactCount() );
        postDTO.emotion( entity.getEmotion() );
        postDTO.expertRank( entity.getExpertRank() );
        postDTO.isPinnedPost( entity.getIsPinnedPost() );
        postDTO.postTime( entity.getPostTime() );
        postDTO.postType( entity.getPostType() );
        postDTO.visibility( entity.getVisibility() );
        postDTO.attachmentUrl( entity.getAttachmentUrl() );
        postDTO.locationContext( entity.getLocationContext() );
        postDTO.appContext( entity.getAppContext() );
        postDTO.languageContext( entity.getLanguageContext() );
        postDTO.domainContext( entity.getDomainContext() );
        postDTO.isPublic( entity.getIsPublic() );
        postDTO.isSensitive( entity.getIsSensitive() );
        postDTO.isModified( entity.getIsModified() );
        postDTO.isModerated( entity.getIsModerated() );
        postDTO.taggedToEntityType( entity.getTaggedToEntityType() );
        postDTO.taggedToEntityId( entity.getTaggedToEntityId() );
        postDTO.linkedEntityCode( entity.getLinkedEntityCode() );
        postDTO.pointsAwarded( entity.getPointsAwarded() );
        postDTO.postStartDate( entity.getPostStartDate() );
        postDTO.postEndDate( entity.getPostEndDate() );
        postDTO.tenantId( entity.getTenantId() );
        postDTO.content( entity.getContent() );
        postDTO.readCount( entity.getReadCount() );
        if ( entity.getReadTimeEstimate() != null ) {
            postDTO.readTimeEstimate( DateTimeFormatter.ISO_LOCAL_TIME.format( entity.getReadTimeEstimate() ) );
        }
        postDTO.contentType( entity.getContentType() );
        postDTO.category( entity.getCategory() );
        postDTO.subCategory( entity.getSubCategory() );
        postDTO.status( entity.getStatus() );
        postDTO.statusUpdatedDate( entity.getStatusUpdatedDate() );
        postDTO.postSubType( entity.getPostSubType() );
        postDTO.effort( uoMDTOAssembler.toDTO( entity.getEffort() ) );
        postDTO.approvedEffort( uoMDTOAssembler.toDTO( entity.getApprovedEffort() ) );
        postDTO.latitude( entity.getLatitude() );
        postDTO.longitude( entity.getLongitude() );
        postDTO.address( entity.getAddress() );
        postDTO.postImpacts( postImpactDTOAssembler.toDTO( entity.getPostImpacts() ) );
        postDTO.title( entity.getTitle() );
        postDTO.evaluationRemarks( entity.getEvaluationRemarks() );
        postDTO.causes( entity.getCauses() );
        postDTO.titles(multiLanguageDTOAssembler.toDTOs(entity.getTitles()));
        postDTO.payloads(multiLanguageDTOAssembler.toDTOs(entity.getPayloads()));
        postDTO.contents(multiLanguageDTOAssembler.toDTOs(entity.getContents()));
        postDTO.tags(entity.getPostTags()!=null?
                entity.getPostTags().stream().map(postTagDTOAssembler::toDTO).collect(Collectors.toSet()):
                null);
        postDTO.postContents(entity.getPostContents()!=null && !entity.getPostContents().isEmpty() ?
                entity.getPostContents().stream().map(contentDTOAssembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)): null);

        postDTO.postTagActors(entity.getPostTagActors()!=null ?
                entity.getPostTagActors().stream().map(postTagActorDTOAssembler::toDTO).collect(Collectors.toSet()):
                null);

        return postDTO.build();
    }


    @Override
    public Post fromDTO(PostDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PostBuilder post = Post.builder();

        post.uuid( dto.getUuid() );
        post.id( dto.getId() );
        post.postedBy( postActorDTOAssembler.fromDTO( dto.getPostedBy() ) );
        post.payload( dto.getPayload() );
        post.commentCount( dto.getCommentCount() );
        post.reactCount( dto.getReactCount() );
        post.emotion( dto.getEmotion() );
        post.expertRank( dto.getExpertRank() );
        post.isPinnedPost( dto.getIsPinnedPost() );
        post.postTime( dto.getPostTime() );
        post.postType( dto.getPostType() );
        post.visibility( dto.getVisibility() );
        post.attachmentUrl( dto.getAttachmentUrl() );
        post.locationContext( dto.getLocationContext() );
        post.appContext( dto.getAppContext() );
        post.languageContext( dto.getLanguageContext() );
        post.domainContext( dto.getDomainContext() );
        post.isPublic( dto.getIsPublic() );
        post.isSensitive( dto.getIsSensitive() );
        post.isModified( dto.getIsModified() );
        post.isModerated( dto.getIsModerated() );
        post.taggedToEntityType( dto.getTaggedToEntityType() );
        post.taggedToEntityId( dto.getTaggedToEntityId() );
        post.linkedEntityCode( dto.getLinkedEntityCode() );
        post.pointsAwarded( dto.getPointsAwarded() );
        post.postStartDate( dto.getPostStartDate() );
        post.postEndDate( dto.getPostEndDate() );
        post.tenantId( dto.getTenantId() );
        post.content( dto.getContent() );
        post.readCount( dto.getReadCount() );
        if ( dto.getReadTimeEstimate() != null ) {
            post.readTimeEstimate( LocalTime.parse( dto.getReadTimeEstimate() ) );
        }
        post.contentType( dto.getContentType() );
        post.category( dto.getCategory() );
        post.subCategory( dto.getSubCategory() );
        post.status( dto.getStatus() );
        post.statusUpdatedDate( dto.getStatusUpdatedDate() );
        post.postSubType( dto.getPostSubType() );
        post.effort( uoMDTOAssembler.fromDTO( dto.getEffort() ) );
        post.postImpacts( postImpactDTOAssembler.fromDTO( dto.getPostImpacts() ) );
        post.latitude( dto.getLatitude() );
        post.longitude( dto.getLongitude() );
        post.address( dto.getAddress() );
        post.title( dto.getTitle() );
        post.approvedEffort( uoMDTOAssembler.fromDTO( dto.getApprovedEffort() ) );
        post.evaluationRemarks( dto.getEvaluationRemarks() );
        post.postActorCertificate( postActorCertificateDTOAssembler.fromDTO( dto.getPostActorCertificate() ) );
        post.causes( dto.getCauses() );
        post.titles(multiLanguageDTOAssembler.fromDTOs(dto.getTitles()));
        post.payloads(multiLanguageDTOAssembler.fromDTOs(dto.getPayloads()));
        post.contents(multiLanguageDTOAssembler.fromDTOs(dto.getContents()));
        post.postTags(dto.getTags()!=null ?
                dto.getTags().stream().map(postTagDTOAssembler::fromDTO).collect(Collectors.toSet()):
                null);

        post.postContents(dto.getPostContents()!=null && !dto.getPostContents().isEmpty() ?
                dto.getPostContents().stream().map(contentDTOAssembler::fromDTO).collect(Collectors.toCollection(LinkedHashSet::new)): null);

        post.postTagActors(dto.getPostTagActors()!=null ?
                dto.getPostTagActors().stream().map(postTagActorDTOAssembler::fromDTO).collect(Collectors.toSet()):
                null);

        return post.build();
    }

    @Override
    public AttachmentDTO toDTO(Attachment attachment) {
        if ( attachment == null ) {
            return null;
        }

        AttachmentDTOBuilder attachmentDTO = AttachmentDTO.builder();

        attachmentDTO.id( attachment.getId() );
        attachmentDTO.fileName( attachment.getFileName() );
        attachmentDTO.mimeType( attachment.getMimeType() );
        attachmentDTO.createdTimestamp( attachment.getCreatedTimestamp() );
        attachmentDTO.createdByUser( attachment.getCreatedByUser() );
        attachmentDTO.fileSizeInBytes( attachment.getFileSizeInBytes() );
        attachmentDTO.completeURL( attachment.getCompleteURL() );
        attachmentDTO.attachmentKind( attachment.getAttachmentKind() );
        attachmentDTO.attachmentKindMeta( attachment.getAttachmentKindMeta() );
        attachmentDTO.attachmentKindIdentifier( attachment.getAttachmentKindIdentifier() );
        attachmentDTO.sequence( attachment.getSequence() );
        attachmentDTO.name( attachment.getName() );

        return attachmentDTO.build();
    }

    @Override
    public List<PostDTO> toDTOs(List<Post> entities) {
        if (entities == null) {
            return null;
        }
        List<PostDTO> list = new ArrayList<>( Math.max( (int) ( entities.size() / .75f ) + 1, 16 ) );
        for ( Post post : entities ) {
            list.add( toDTO( post ) );
        }

        return list;
    }


}
