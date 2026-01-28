package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageSkillShortageType;
import com.platformcommons.platform.service.blockprofile.dto.VillageSkillShortageTypeDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageHumanCapitalProfileDTOAssembler.class,
    })
public interface VillageSkillShortageTypeDTOAssembler {

    VillageSkillShortageTypeDTO toDTO(VillageSkillShortageType entity);

    VillageSkillShortageType fromDTO(VillageSkillShortageTypeDTO dto);
}