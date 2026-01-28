package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonLanguage;
import com.platformcommons.platform.service.profile.dto.PersonLanguageDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PersonLanguageDTOAssembler {

    PersonLanguageDTO toDTO(PersonLanguage entity);

    PersonLanguage fromDTO(PersonLanguageDTO dto);
}