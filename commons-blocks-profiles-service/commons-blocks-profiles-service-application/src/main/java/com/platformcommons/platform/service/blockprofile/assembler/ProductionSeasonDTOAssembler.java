package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.ProductionSeason;
import com.platformcommons.platform.service.blockprofile.dto.ProductionSeasonDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageFisheriesProfileDTOAssembler.class,
        VillageHorticultureProfileDTOAssembler.class,
    })
public interface ProductionSeasonDTOAssembler {

    ProductionSeasonDTO toDTO(ProductionSeason entity);

    ProductionSeason fromDTO(ProductionSeasonDTO dto);
}