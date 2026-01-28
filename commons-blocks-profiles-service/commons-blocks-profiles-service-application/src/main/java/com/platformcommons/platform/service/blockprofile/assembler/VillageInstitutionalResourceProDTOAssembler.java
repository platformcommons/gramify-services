package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageInstitutionalResourcePro;
import com.platformcommons.platform.service.blockprofile.dto.VillageInstitutionalResourceProDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        NGOThematicAreaDTOAssembler.class,
    })
public interface VillageInstitutionalResourceProDTOAssembler {

    VillageInstitutionalResourceProDTO toDTO(VillageInstitutionalResourcePro entity);

    VillageInstitutionalResourcePro fromDTO(VillageInstitutionalResourceProDTO dto);
}