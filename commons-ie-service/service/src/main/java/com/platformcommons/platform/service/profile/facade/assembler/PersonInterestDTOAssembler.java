package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonInterest;
import com.platformcommons.platform.service.profile.dto.PersonInterestDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PersonInterestDTOAssembler {

    PersonInterestDTO toDTO(PersonInterest entity);

    PersonInterest fromDTO(PersonInterestDTO dto);
}