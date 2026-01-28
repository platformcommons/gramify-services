package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonProfession;
import com.platformcommons.platform.service.profile.dto.PersonProfessionDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PersonProfessionDTOAssembler {

    PersonProfessionDTO toDTO(PersonProfession entity);

    PersonProfession fromDTO(PersonProfessionDTO dto);
}