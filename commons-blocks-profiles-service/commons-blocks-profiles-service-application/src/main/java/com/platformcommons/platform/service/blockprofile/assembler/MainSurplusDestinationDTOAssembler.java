package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.MainSurplusDestination;
import com.platformcommons.platform.service.blockprofile.dto.MainSurplusDestinationDTO;
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
public interface MainSurplusDestinationDTOAssembler {

    MainSurplusDestinationDTO toDTO(MainSurplusDestination entity);

    MainSurplusDestination fromDTO(MainSurplusDestinationDTO dto);
}