package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.Contact;
import com.platformcommons.platform.service.profile.dto.ContactDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface ContactDTOAssembler {

    ContactDTO toDTO(Contact entity);

    Contact fromDTO(ContactDTO dto);
}