package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonSkill;
import com.platformcommons.platform.service.profile.dto.PersonSkillDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PersonSkillDTOAssembler {

    PersonSkillDTO toDTO(PersonSkill entity);

    PersonSkill fromDTO(PersonSkillDTO dto);
}