package com.platformcommons.platform.service.post.facade.assembler.impl;

import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.PostActor;
import com.platformcommons.platform.service.post.domain.PostActorCertificate;
import com.platformcommons.platform.service.post.domain.PostActorCertificate.PostActorCertificateBuilder;
import com.platformcommons.platform.service.post.dto.PostActorCertificateDTO;
import com.platformcommons.platform.service.post.dto.PostActorCertificateDTO.PostActorCertificateDTOBuilder;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;

import com.platformcommons.platform.service.post.facade.assembler.PostActorCertificateDTOAssembler;
import org.springframework.stereotype.Component;


@Component
public class PostActorCertificateDTOAssemblerImpl implements PostActorCertificateDTOAssembler {

    @Override
    public PostActorCertificateDTO toDTO(PostActorCertificate entity) {
        if ( entity == null ) {
            return null;
        }

        PostActorCertificateDTOBuilder postActorCertificateDTO = PostActorCertificateDTO.builder();

        postActorCertificateDTO.uuid( entity.getUuid() );
        postActorCertificateDTO.id( entity.getId() );
        postActorCertificateDTO.postId( entity.getPost().getId() );
        postActorCertificateDTO.postActorId( entity.getPostActor().getId() );
        postActorCertificateDTO.url( entity.getUrl() );
        postActorCertificateDTO.title( entity.getTitle() );
        postActorCertificateDTO.certificateTemplateCode( entity.getCertificateTemplateCode() );
        postActorCertificateDTO.awardedBy( entity.getAwardedBy() );
        postActorCertificateDTO.isPublic( entity.getIsPublic() );
        postActorCertificateDTO.issuedDate( entity.getIssuedDate() );
        postActorCertificateDTO.validTillDate( entity.getValidTillDate() );

        return postActorCertificateDTO.build();
    }

    @Override
    public Set<PostActorCertificateDTO> toDTOs(Set<PostActorCertificate> entities) {
        if ( entities == null ) {
            return null;
        }

        Set<PostActorCertificateDTO> set = new HashSet<PostActorCertificateDTO>( Math.max( (int) ( entities.size() / .75f ) + 1, 16 ) );
        for ( PostActorCertificate postActorCertificate : entities ) {
            set.add( toDTO( postActorCertificate ) );
        }

        return set;
    }

    @Override
    public PostActorCertificate fromDTO(PostActorCertificateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PostActorCertificateBuilder postActorCertificate = PostActorCertificate.builder();

        postActorCertificate.uuid( dto.getUuid() );
        postActorCertificate.id( dto.getId() );
        postActorCertificate.post(Post.builder().id(dto.getId()).build() );
        postActorCertificate.postActor( PostActor.builder().id(dto.getPostActorId()).build() );
        postActorCertificate.url( dto.getUrl() );
        postActorCertificate.title( dto.getTitle() );
        postActorCertificate.certificateTemplateCode( dto.getCertificateTemplateCode() );
        postActorCertificate.awardedBy( dto.getAwardedBy() );
        postActorCertificate.isPublic( dto.getIsPublic() );
        postActorCertificate.issuedDate( dto.getIssuedDate() );
        postActorCertificate.validTillDate( dto.getValidTillDate() );

        return postActorCertificate.build();
    }

    @Override
    public Set<PostActorCertificate> fromDTOs(Set<PostActorCertificateDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Set<PostActorCertificate> set = new HashSet<PostActorCertificate>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );
        for ( PostActorCertificateDTO postActorCertificateDTO : dtos ) {
            set.add( fromDTO( postActorCertificateDTO ) );
        }

        return set;
    }
}
