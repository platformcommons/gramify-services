package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonProfile;
import com.platformcommons.platform.service.profile.dto.PersonProfileDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PersonProfileDTOAssembler {

    PersonProfileDTO toDTO(PersonProfile entity);

    PersonProfile fromDTO(PersonProfileDTO dto);
}