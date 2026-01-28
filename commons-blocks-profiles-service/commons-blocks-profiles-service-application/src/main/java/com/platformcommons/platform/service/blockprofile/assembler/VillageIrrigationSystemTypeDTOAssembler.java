package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageIrrigationSystemType;
import com.platformcommons.platform.service.blockprofile.dto.VillageIrrigationSystemTypeDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageWaterResourceProfileDTOAssembler.class,
    })
public interface VillageIrrigationSystemTypeDTOAssembler {

    VillageIrrigationSystemTypeDTO toDTO(VillageIrrigationSystemType entity);

    VillageIrrigationSystemType fromDTO(VillageIrrigationSystemTypeDTO dto);
}