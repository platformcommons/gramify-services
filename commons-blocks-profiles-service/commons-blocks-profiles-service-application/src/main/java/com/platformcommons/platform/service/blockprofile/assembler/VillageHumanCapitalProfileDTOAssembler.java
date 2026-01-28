package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageHumanCapitalProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageHumanCapitalProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageYouthAspirationsDTOAssembler.class,
        VillageSkillShortageTypeDTOAssembler.class,
    })
public interface VillageHumanCapitalProfileDTOAssembler {

    VillageHumanCapitalProfileDTO toDTO(VillageHumanCapitalProfile entity);

    VillageHumanCapitalProfile fromDTO(VillageHumanCapitalProfileDTO dto);
}