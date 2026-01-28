package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.SeasonalityOfSurplus;
import com.platformcommons.platform.service.blockprofile.dto.SeasonalityOfSurplusDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageSurplusProduceProfileDTOAssembler.class,
    })
public interface SeasonalityOfSurplusDTOAssembler {

    SeasonalityOfSurplusDTO toDTO(SeasonalityOfSurplus entity);

    SeasonalityOfSurplus fromDTO(SeasonalityOfSurplusDTO dto);
}