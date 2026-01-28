package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonEducation;
import com.platformcommons.platform.service.profile.dto.PersonEducationDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PersonEducationDTOAssembler {

    PersonEducationDTO toDTO(PersonEducation entity);

    PersonEducation fromDTO(PersonEducationDTO dto);
}