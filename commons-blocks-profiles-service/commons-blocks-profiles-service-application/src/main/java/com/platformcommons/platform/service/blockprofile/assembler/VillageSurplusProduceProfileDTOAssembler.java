package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageSurplusProduceProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageSurplusProduceProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        SurplusMovedThroughDTOAssembler.class,
        SeasonalityOfSurplusDTOAssembler.class,
        KeyConstraintsForSurplusExportDTOAssembler.class,
        MainSurplusDestinationDTOAssembler.class,
        SurplusProduceTypeDTOAssembler.class,
    })
public interface VillageSurplusProduceProfileDTOAssembler {

    VillageSurplusProduceProfileDTO toDTO(VillageSurplusProduceProfile entity);

    VillageSurplusProduceProfile fromDTO(VillageSurplusProduceProfileDTO dto);
}