package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageWaterResourceProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageWaterResourceProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageIrrigationSystemTypeDTOAssembler.class,
    })
public interface VillageWaterResourceProfileDTOAssembler {

    VillageWaterResourceProfileDTO toDTO(VillageWaterResourceProfile entity);

    VillageWaterResourceProfile fromDTO(VillageWaterResourceProfileDTO dto);
}