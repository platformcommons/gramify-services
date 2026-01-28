package com.platformcommons.platform.service.domain.facade.assembler.impl;

import com.platformcommons.platform.service.domain.domain.Domain;
import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.domain.Tag.TagBuilder;
import com.platformcommons.platform.service.domain.dto.DomainV2DTO;
import com.platformcommons.platform.service.domain.dto.DomainV2DTO.DomainV2DTOBuilder;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO.TagDTOBuilder;
import com.platformcommons.platform.service.domain.dto.TagV2DTO;
import com.platformcommons.platform.service.domain.dto.TagV2DTO.TagV2DTOBuilder;
import com.platformcommons.platform.service.domain.facade.assembler.TagDTOAssembler;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.dto.commons.MLTextDTO.MLTextDTOBuilder;
import com.platformcommons.platform.service.entity.common.MLText;
import com.platformcommons.platform.service.entity.common.MLText.MLTextBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

import com.platformcommons.platform.service.facade.RefDataFacade;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-13T13:23:14+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_442 (Private Build)"
)
@Component
public class TagDTOAssemblerImpl implements TagDTOAssembler {


    @Autowired
    private RefDataDTOValidator refDataDTOValidator;

    @Autowired
    private RefDataFacade refDataFacade;

    @Override
    public TagDTO toDTO(Tag entity) {
        if ( entity == null ) {
            return null;
        }

        TagDTOBuilder tagDTO = TagDTO.builder();

        tagDTO.uuid( entity.getUuid() );
        tagDTO.id( entity.getId() );
        tagDTO.code( entity.getCode() );
        tagDTO.name( entity.getName() );
        tagDTO.type( entity.getType() );
        tagDTO.description( entity.getDescription() );
        tagDTO.icon( entity.getIcon() );
        tagDTO.banner( entity.getBanner() );
        tagDTO.bannerIcon( entity.getBannerIcon() );
        tagDTO.identifier( entity.getIdentifier() );
        tagDTO.sequence( entity.getSequence() );
        tagDTO.color( entity.getColor() );
        tagDTO.backgroundColor( entity.getBackgroundColor() );
        tagDTO.nameML( mLTextSetToMLTextDTOSet( entity.getNameML() ) );
        tagDTO.descriptionML( mLTextSetToMLTextDTOSet( entity.getDescriptionML() ) );
        tagDTO.categoryCode( entity.getCategoryCode() );
        tagDTO.subCategoryCode( entity.getSubCategoryCode() );
        tagDTO.context( entity.getContext() );

        return tagDTO.build();
    }

    @Override
    public Tag fromDTO(TagDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TagBuilder tag = Tag.builder();

        tag.uuid( dto.getUuid() );
        tag.isActive( dto.getIsActive() );
        tag.id( dto.getId() );
        tag.code( dto.getCode() );
        tag.name( dto.getName() );
        tag.type( dto.getType() );
        tag.description( dto.getDescription() );
        tag.icon( dto.getIcon() );
        tag.banner( dto.getBanner() );
        tag.sequence( dto.getSequence() );
        tag.bannerIcon( dto.getBannerIcon() );
        tag.identifier( dto.getIdentifier() );
        tag.color( dto.getColor() );
        tag.backgroundColor( dto.getBackgroundColor() );
        tag.nameML( mLTextDTOSetToMLTextSet( dto.getNameML() ) );
        tag.descriptionML( mLTextDTOSetToMLTextSet( dto.getDescriptionML() ) );
        tag.categoryCode( dto.getCategoryCode() );
        tag.subCategoryCode( dto.getSubCategoryCode() );
        tag.context( dto.getContext() );

        return tag.build();
    }

    @Override
    public List<TagDTO> toDTOs(List<Tag> tagList) {
        if ( tagList == null ) {
            return null;
        }

        List<TagDTO> list = new ArrayList<TagDTO>( tagList.size() );
        for ( Tag tag : tagList ) {
            list.add( toDTO( tag ) );
        }

        return list;
    }

    @Override
    public TagV2DTO toV2DTO(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagV2DTOBuilder tagV2DTO = TagV2DTO.builder();

        tagV2DTO.uuid( tag.getUuid() );
        tagV2DTO.id( tag.getId() );
        tagV2DTO.code( tag.getCode() );
        tagV2DTO.name( tag.getName() );
        tagV2DTO.type( tag.getType() );
        tagV2DTO.description( tag.getDescription() );
        tagV2DTO.icon( tag.getIcon() );
        tagV2DTO.banner( tag.getBanner() );
        tagV2DTO.bannerIcon( tag.getBannerIcon() );
        tagV2DTO.identifier( tag.getIdentifier() );
        tagV2DTO.sequence( tag.getSequence() );
        tagV2DTO.color( tag.getColor() );
        tagV2DTO.backgroundColor( tag.getBackgroundColor() );
        tagV2DTO.nameML( mLTextSetToMLTextDTOSet( tag.getNameML() ) );
        tagV2DTO.descriptionML( mLTextSetToMLTextDTOSet( tag.getDescriptionML() ) );
        tagV2DTO.domainList( domainSetToDomainV2DTOSet( tag.getDomainList() ) );
        tagV2DTO.categoryCode( tag.getCategoryCode() );
        tagV2DTO.subCategoryCode( tag.getSubCategoryCode() );
        tagV2DTO.context( tag.getContext() );

        return tagV2DTO.build();
    }

    @Override
    public TagDTO toDTO(Tag entity, Boolean includeRefLabels) {
        if ( entity == null ) {
            return null;
        }

        TagDTOBuilder tagDTO = TagDTO.builder();

        tagDTO.uuid( entity.getUuid() );
        tagDTO.id( entity.getId() );
        tagDTO.code( entity.getCode() );
        tagDTO.name( entity.getName() );
        tagDTO.type( entity.getType() );
        tagDTO.description( entity.getDescription() );
        tagDTO.icon( entity.getIcon() );
        tagDTO.banner( entity.getBanner() );
        tagDTO.bannerIcon( entity.getBannerIcon() );
        tagDTO.identifier( entity.getIdentifier() );
        tagDTO.sequence( entity.getSequence() );
        tagDTO.color( entity.getColor() );
        tagDTO.backgroundColor( entity.getBackgroundColor() );
        tagDTO.nameML( mLTextSetToMLTextDTOSet( entity.getNameML() ) );
        tagDTO.descriptionML( mLTextSetToMLTextDTOSet( entity.getDescriptionML() ) );
        tagDTO.categoryCode( entity.getCategoryCode() );
        tagDTO.subCategoryCode( entity.getSubCategoryCode() );
        tagDTO.context( entity.getContext() );
        if(includeRefLabels){
            tagDTO.categoryRef(entity.getCategoryCode()!=null ? refDataFacade.getRefDataByCode(entity.getCategoryCode(),null) : null );
            tagDTO.subCategoryRef(entity.getSubCategoryCode()!=null ? refDataFacade.getRefDataByCode(entity.getSubCategoryCode(),null) : null);
        }

        return tagDTO.build();
    }

    protected MLTextDTO mLTextToMLTextDTO(MLText mLText) {
        if ( mLText == null ) {
            return null;
        }

        MLTextDTOBuilder mLTextDTO = MLTextDTO.builder();

        mLTextDTO.id( mLText.getId() );
        mLTextDTO.text( mLText.getText() );
        mLTextDTO.languageCode( mLText.getLanguageCode() );

        return mLTextDTO.build();
    }

    protected Set<MLTextDTO> mLTextSetToMLTextDTOSet(Set<MLText> set) {
        if ( set == null ) {
            return null;
        }

        Set<MLTextDTO> set1 = new HashSet<MLTextDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( MLText mLText : set ) {
            set1.add( mLTextToMLTextDTO( mLText ) );
        }

        return set1;
    }

    protected MLText mLTextDTOToMLText(MLTextDTO mLTextDTO) {
        if ( mLTextDTO == null ) {
            return null;
        }

        MLTextBuilder mLText = MLText.builder();

        mLText.id( mLTextDTO.getId() );
        mLText.text( mLTextDTO.getText() );
        mLText.languageCode( mLTextDTO.getLanguageCode() );

        return mLText.build();
    }

    protected Set<MLText> mLTextDTOSetToMLTextSet(Set<MLTextDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<MLText> set1 = new HashSet<MLText>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( MLTextDTO mLTextDTO : set ) {
            set1.add( mLTextDTOToMLText( mLTextDTO ) );
        }

        return set1;
    }

    protected DomainV2DTO domainToDomainV2DTO(Domain domain) {
        if ( domain == null ) {
            return null;
        }

        DomainV2DTOBuilder domainV2DTO = DomainV2DTO.builder();

        domainV2DTO.uuid( domain.getUuid() );
        domainV2DTO.id( domain.getId() );
        domainV2DTO.code( domain.getCode() );
        domainV2DTO.name( domain.getName() );
        domainV2DTO.description( domain.getDescription() );
        domainV2DTO.names( mLTextSetToMLTextDTOSet( domain.getNames() ) );
        domainV2DTO.descriptions( mLTextSetToMLTextDTOSet( domain.getDescriptions() ) );
        domainV2DTO.isRoot( domain.getIsRoot() );
        domainV2DTO.context( domain.getContext() );
        domainV2DTO.icon( domain.getIcon() );
        domainV2DTO.banner( domain.getBanner() );
        domainV2DTO.colorCode( domain.getColorCode() );
        domainV2DTO.type( domain.getType() );

        return domainV2DTO.build();
    }

    protected Set<DomainV2DTO> domainSetToDomainV2DTOSet(Set<Domain> set) {
        if ( set == null ) {
            return null;
        }

        Set<DomainV2DTO> set1 = new HashSet<DomainV2DTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Domain domain : set ) {
            set1.add( domainToDomainV2DTO( domain ) );
        }

        return set1;
    }
}
