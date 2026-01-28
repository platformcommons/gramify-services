package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageHealthInfrastructure;
import com.platformcommons.platform.service.blockprofile.dto.VillageHealthInfrastructureDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        CommonHealthIssueDTOAssembler.class,
    })
public interface VillageHealthInfrastructureDTOAssembler {

    VillageHealthInfrastructureDTO toDTO(VillageHealthInfrastructure entity);

    VillageHealthInfrastructure fromDTO(VillageHealthInfrastructureDTO dto);
}