package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.NGOThematicArea;
import com.platformcommons.platform.service.blockprofile.dto.NGOThematicAreaDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageInstitutionalResourceProDTOAssembler.class,
    })
public interface NGOThematicAreaDTOAssembler {

    NGOThematicAreaDTO toDTO(NGOThematicArea entity);

    NGOThematicArea fromDTO(NGOThematicAreaDTO dto);
}