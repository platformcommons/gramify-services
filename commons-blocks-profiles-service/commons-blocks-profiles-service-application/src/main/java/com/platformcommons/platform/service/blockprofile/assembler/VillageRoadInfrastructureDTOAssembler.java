package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageRoadInfrastructure;
import com.platformcommons.platform.service.blockprofile.dto.VillageRoadInfrastructureDTO;
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
public interface VillageRoadInfrastructureDTOAssembler {

    VillageRoadInfrastructureDTO toDTO(VillageRoadInfrastructure entity);

    VillageRoadInfrastructure fromDTO(VillageRoadInfrastructureDTO dto);
}