package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageIrrigationInfrastructure;
import com.platformcommons.platform.service.blockprofile.dto.VillageIrrigationInfrastructureDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
    })
public interface VillageIrrigationInfrastructureDTOAssembler {

    VillageIrrigationInfrastructureDTO toDTO(VillageIrrigationInfrastructure entity);

    VillageIrrigationInfrastructure fromDTO(VillageIrrigationInfrastructureDTO dto);
}