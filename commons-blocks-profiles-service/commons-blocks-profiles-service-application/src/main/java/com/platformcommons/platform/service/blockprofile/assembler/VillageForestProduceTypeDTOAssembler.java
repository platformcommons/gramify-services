package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageForestProduceType;
import com.platformcommons.platform.service.blockprofile.dto.VillageForestProduceTypeDTO;
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
public interface VillageForestProduceTypeDTOAssembler {

    VillageForestProduceTypeDTO toDTO(VillageForestProduceType entity);

    VillageForestProduceType fromDTO(VillageForestProduceTypeDTO dto);
}