package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonPoc;
import com.platformcommons.platform.service.profile.dto.PersonPocDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PersonPocDTOAssembler {

    PersonPocDTO toDTO(PersonPoc entity);

    PersonPoc fromDTO(PersonPocDTO dto);
}