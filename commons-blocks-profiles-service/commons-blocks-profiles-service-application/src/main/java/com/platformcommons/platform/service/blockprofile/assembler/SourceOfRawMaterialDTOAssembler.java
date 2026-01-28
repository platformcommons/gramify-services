package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.SourceOfRawMaterial;
import com.platformcommons.platform.service.blockprofile.dto.SourceOfRawMaterialDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageAgriInputDemandProfileDTOAssembler.class,
    })
public interface SourceOfRawMaterialDTOAssembler {

    SourceOfRawMaterialDTO toDTO(SourceOfRawMaterial entity);

    SourceOfRawMaterial fromDTO(SourceOfRawMaterialDTO dto);
}