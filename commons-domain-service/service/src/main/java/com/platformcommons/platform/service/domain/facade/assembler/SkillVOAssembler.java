package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.Skill;
import com.platformcommons.platform.service.domain.domain.vo.SkillVO;
import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = MLTextDTOAssembler.class)
public interface SkillVOAssembler {

    SkillVO toVO(Skill dto);

}
