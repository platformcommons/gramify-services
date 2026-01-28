package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonFamily;
import com.platformcommons.platform.service.profile.dto.PersonFamilyDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PersonFamilyDTOAssembler {

    PersonFamilyDTO toDTO(PersonFamily entity);

    PersonFamily fromDTO(PersonFamilyDTO dto);
}