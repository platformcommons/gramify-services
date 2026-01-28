package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonIdentifier;
import com.platformcommons.platform.service.profile.dto.PersonIdentifierDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PersonIdentifierDTOAssembler {

    PersonIdentifierDTO toDTO(PersonIdentifier entity);

    PersonIdentifier fromDTO(PersonIdentifierDTO dto);
}