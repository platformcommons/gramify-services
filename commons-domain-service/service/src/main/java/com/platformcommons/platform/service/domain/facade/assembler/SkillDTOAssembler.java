package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.Skill;
import com.platformcommons.platform.service.domain.dto.SkillDTO;

public interface SkillDTOAssembler {

    Skill fromDTO(SkillDTO dto);
    SkillDTO toDTO(Skill dto);

}
