package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.StapleFoodsConsumed;
import com.platformcommons.platform.service.blockprofile.dto.StapleFoodsConsumedDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageConsumptionPatternDTOAssembler.class,
    })
public interface StapleFoodsConsumedDTOAssembler {

    StapleFoodsConsumedDTO toDTO(StapleFoodsConsumed entity);

    StapleFoodsConsumed fromDTO(StapleFoodsConsumedDTO dto);
}