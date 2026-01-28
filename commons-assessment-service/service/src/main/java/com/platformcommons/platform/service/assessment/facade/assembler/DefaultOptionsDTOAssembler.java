package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.DefaultOptions;
import com.platformcommons.platform.service.assessment.dto.DefaultOptionsDTO;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {
          MimicMLTextDTOAssembler.class,
          OptionsDTOAssembler.class
    })
public interface DefaultOptionsDTOAssembler {

    @Mapping(source = "createdTimestamp", target = "createdAt")
    @Mapping(source = "createdByUser", target = "createdBy")
    @Mapping(source = "tenantId", target = "tenantId")
    @Mapping(target = "defaultOptExpl", ignore = true)
    DefaultOptionsDTO toDTO(DefaultOptions entity);

    @Mapping(target = "defaultOptExpl", ignore = true)
    DefaultOptions fromDTO(DefaultOptionsDTO dto);
}