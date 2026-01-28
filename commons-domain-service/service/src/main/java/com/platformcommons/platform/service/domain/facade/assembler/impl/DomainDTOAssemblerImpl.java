package com.platformcommons.platform.service.domain.facade.assembler.impl;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.domain.domain.App;
import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.dto.AppDTO;
import com.platformcommons.platform.service.domain.dto.DomainDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.facade.assembler.AuthorDTOAssembler;
import com.platformcommons.platform.service.domain.facade.assembler.DomainDTOAssembler;
import com.platformcommons.platform.service.domain.facade.assembler.TagDTOAssembler;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DomainDTOAssemblerImpl implements DomainDTOAssembler {

    @Autowired
    private TagDTOAssembler tagDTOAssembler;

    @Autowired
    private AuthorDTOAssembler authorDTOAssembler;
    @Autowired
    private MLTextDTOAssembler mlTextDTOAssembler;

    @Override
    public DomainDTO toDTO(Domain entity) {
        if ( entity == null ) {
            return null;
        }

        DomainDTO.DomainDTOBuilder domainDTO = DomainDTO.builder();

        domainDTO.uuid( entity.getUuid() );
        domainDTO.id( entity.getId() );
        domainDTO.code( entity.getCode() );
        domainDTO.name( entity.getName() );
        domainDTO.description( entity.getDescription() );
        domainDTO.isRoot( entity.getIsRoot() );
        domainDTO.context( entity.getContext() );
        domainDTO.icon( entity.getIcon() );
        domainDTO.banner( entity.getBanner() );
        domainDTO.tagList( tagSetToTagDTOSet( entity.getTagList() ) );
        domainDTO.names(entity.getNames().stream().map(mlTextDTOAssembler::toDTO).collect(Collectors.toSet()));
        domainDTO.descriptions(entity.getDescriptions().stream().map(mlTextDTOAssembler::toDTO).collect(Collectors.toSet()));
        domainDTO.colorCode(entity.getColorCode());
        domainDTO.type(entity.getType());
        return domainDTO.build();
    }


    @Override
    public DomainDTO toDTOWithSuggestedApp(Domain entity,Boolean withSuggestedApp) {
        if ( entity == null ) {
            return null;
        }

        Set<AppDTO> suggestedAppDTOs = null;
        if(entity.getSuggestedAppList()!=null && !entity.getSuggestedAppList().isEmpty()){
            suggestedAppDTOs = entity.getSuggestedAppList().stream().map(this::suggestedAppMapper)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }

        DomainDTO.DomainDTOBuilder domainDTO = DomainDTO.builder();

        domainDTO.uuid( entity.getUuid() );
        domainDTO.id( entity.getId() );
        domainDTO.code( entity.getCode() );
        domainDTO.name( entity.getName() );
        domainDTO.description( entity.getDescription() );
        domainDTO.isRoot( entity.getIsRoot() );
        domainDTO.context( entity.getContext() );
        domainDTO.icon( entity.getIcon() );
        domainDTO.banner( entity.getBanner() );
        domainDTO.tagList( tagSetToTagDTOSet( entity.getTagList() ) );
        domainDTO.names(entity.getNames().stream().map(mlTextDTOAssembler::toDTO).collect(Collectors.toSet()));
        domainDTO.descriptions(entity.getDescriptions().stream().map(mlTextDTOAssembler::toDTO).collect(Collectors.toSet()));
        domainDTO.colorCode(entity.getColorCode());
        domainDTO.type(entity.getType());
        if(withSuggestedApp){
            domainDTO.suggestedAppList(suggestedAppDTOs);
        }

        return domainDTO.build();
    }

    @Override
    public Domain fromDTO(DomainDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Domain.DomainBuilder domain = Domain.builder();

        domain.uuid( dto.getUuid() );
        domain.id( dto.getId() );
        domain.code( dto.getCode() );
        domain.name( dto.getName() );
        domain.description( dto.getDescription() );
        domain.isRoot( dto.getIsRoot() );
        domain.context( dto.getContext() );
        domain.icon( dto.getIcon() );
        domain.banner( dto.getBanner() );
        domain.tagList( tagDTOSetToTagSet( dto.getTagList() ) );
        domain.names(dto.getNames().stream().map(mlTextDTOAssembler::fromDTO).collect(Collectors.toSet()));
        domain.descriptions(dto.getDescriptions().stream().map(mlTextDTOAssembler::fromDTO).collect(Collectors.toSet()));
        domain.colorCode(dto.getColorCode());
        domain.type(dto.getType());

        return domain.build();
    }

    @Override
    public AttachmentDTO toDTO(Attachment attachment) {
        if ( attachment == null ) {
            return null;
        }

        AttachmentDTO.AttachmentDTOBuilder attachmentDTO = AttachmentDTO.builder();

        attachmentDTO.id( attachment.getId() );
        attachmentDTO.fileName( attachment.getFileName() );
        attachmentDTO.mimeType( attachment.getMimeType() );
        attachmentDTO.createdTimestamp( attachment.getCreatedTimestamp() );
        attachmentDTO.createdByUser( attachment.getCreatedByUser() );
        attachmentDTO.fileSizeInBytes( attachment.getFileSizeInBytes() );
        attachmentDTO.completeURL( attachment.getCompleteURL() );

        return attachmentDTO.build();
    }

    protected Set<TagDTO> tagSetToTagDTOSet(Set<Tag> set) {
        if ( set == null ) {
            return null;
        }

        Set<TagDTO> set1 = new HashSet<TagDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Tag tag : set ) {
            set1.add( tagDTOAssembler.toDTO( tag ) );
        }

        return set1;
    }

    protected Set<Tag> tagDTOSetToTagSet(Set<TagDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Tag> set1 = new HashSet<Tag>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( TagDTO tagDTO : set ) {
            set1.add( tagDTOAssembler.fromDTO( tagDTO ) );
        }

        return set1;
    }

    protected AppDTO suggestedAppMapper(App app){
        return AppDTO.builder()
                .id(app.getId())
                .author(app.getAuthor()!=null ? authorDTOAssembler.toDTO(app.getAuthor()):null)
                .name(app.getName())
                .shortDescription(app.getShortDescription())
                .logo(app.getLogo())
                .slug(app.getSlug())
                .build();
    }
}
