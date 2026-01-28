package com.platformcommons.platform.service.post.facade.assembler.impl;

import com.platformcommons.platform.service.facade.assembler.UoMDTOAssembler;
import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.PostImpact;
import com.platformcommons.platform.service.post.dto.PostImpactDTO;
import com.platformcommons.platform.service.post.facade.assembler.PostImpactDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PostImpactDTOAssemblerImpl implements PostImpactDTOAssembler {

    @Autowired
    private UoMDTOAssembler uoMDTOAssembler;

    @Override
    public PostImpactDTO toDTO(PostImpact entity) {
        if ( entity == null ) {
            return null;
        }

        PostImpactDTO.PostImpactDTOBuilder postImpactDTO = PostImpactDTO.builder();

        postImpactDTO.uuid( entity.getUuid() );
        postImpactDTO.id( entity.getId() );
        postImpactDTO.postId( entity.getPost().getId() );
        postImpactDTO.beneficiaryType( entity.getBeneficiaryType() );
        postImpactDTO.label( entity.getLabel() );
        postImpactDTO.actualImpact( uoMDTOAssembler.toDTO( entity.getActualImpact() ) );
        postImpactDTO.approvedImpact( uoMDTOAssembler.toDTO( entity.getApprovedImpact() ));

        return postImpactDTO.build();
    }

    @Override
    public Set<PostImpactDTO> toDTO(Set<PostImpact> entities) {
        if ( entities == null ) {
            return null;
        }

        Set<PostImpactDTO> set = new HashSet<PostImpactDTO>( Math.max( (int) ( entities.size() / .75f ) + 1, 16 ) );
        for ( PostImpact postImpact : entities ) {
            set.add( toDTO( postImpact ) );
        }

        return set;
    }

    @Override
    public PostImpact fromDTO(PostImpactDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PostImpact.PostImpactBuilder postImpact = PostImpact.builder();

        postImpact.uuid( dto.getUuid() );
        postImpact.id( dto.getId() );
        postImpact.post( Post.builder().id(dto.getPostId()).build() );
        postImpact.beneficiaryType( dto.getBeneficiaryType() );
        postImpact.label( dto.getLabel() );
        postImpact.actualImpact( uoMDTOAssembler.fromDTO( dto.getActualImpact() ) );
        postImpact.approvedImpact( uoMDTOAssembler.fromDTO( dto.getApprovedImpact() ));

        return postImpact.build();
    }

    @Override
    public Set<PostImpact> fromDTO(Set<PostImpactDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        Set<PostImpact> set = new HashSet<PostImpact>( Math.max( (int) ( dtos.size() / .75f ) + 1, 16 ) );
        for ( PostImpactDTO postImpactDTO : dtos ) {
            set.add( fromDTO( postImpactDTO ) );
        }

        return set;
    }
}
