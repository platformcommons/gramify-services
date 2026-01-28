package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonSocialMedia;
import com.platformcommons.platform.service.profile.dto.PersonSocialMediaDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PersonSocialMediaDTOAssembler {

    PersonSocialMediaDTO toDTO(PersonSocialMedia entity);

    PersonSocialMedia fromDTO(PersonSocialMediaDTO dto);
}