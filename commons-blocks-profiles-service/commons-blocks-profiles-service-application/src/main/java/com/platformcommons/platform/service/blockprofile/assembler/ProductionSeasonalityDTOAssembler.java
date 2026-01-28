package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.ProductionSeasonality;
import com.platformcommons.platform.service.blockprofile.dto.ProductionSeasonalityDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageLivestockProfileDTOAssembler.class,
    })
public interface ProductionSeasonalityDTOAssembler {

    ProductionSeasonalityDTO toDTO(ProductionSeasonality entity);

    ProductionSeasonality fromDTO(ProductionSeasonalityDTO dto);
}