package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageCommonWildlife;
import com.platformcommons.platform.service.blockprofile.dto.VillageCommonWildlifeDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageForestResourceProfileDTOAssembler.class,
    })
public interface VillageCommonWildlifeDTOAssembler {

    VillageCommonWildlifeDTO toDTO(VillageCommonWildlife entity);

    VillageCommonWildlife fromDTO(VillageCommonWildlifeDTO dto);
}