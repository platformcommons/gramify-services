package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.Tag;
import com.platformcommons.platform.service.domain.dto.TagDTO;


import com.platformcommons.platform.service.domain.dto.TagV2DTO;
import org.mapstruct.Mapper;

import java.util.List;

//@Mapper(componentModel = "spring",uses = {
//          AppDTOAssembler.class,
//          UseCaseDTOAssembler.class,
//          DomainDTOAssembler.class,
//    })
public interface TagDTOAssembler {

    TagDTO toDTO(Tag entity);

    Tag fromDTO(TagDTO dto);

    List<TagDTO> toDTOs(List<Tag> tagList);

    TagV2DTO toV2DTO(Tag tag);


    TagDTO toDTO(Tag entity,Boolean includeRefLabels);
}