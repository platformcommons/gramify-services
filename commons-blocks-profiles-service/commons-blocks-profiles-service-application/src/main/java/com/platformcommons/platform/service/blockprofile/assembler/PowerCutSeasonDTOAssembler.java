package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.PowerCutSeason;
import com.platformcommons.platform.service.blockprofile.dto.PowerCutSeasonDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageElectricityInfrastructurDTOAssembler.class,
    })
public interface PowerCutSeasonDTOAssembler {

    PowerCutSeasonDTO toDTO(PowerCutSeason entity);

    PowerCutSeason fromDTO(PowerCutSeasonDTO dto);
}