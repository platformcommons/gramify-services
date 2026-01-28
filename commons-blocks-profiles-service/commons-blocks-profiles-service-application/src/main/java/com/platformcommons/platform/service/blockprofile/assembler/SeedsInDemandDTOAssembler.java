package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.SeedsInDemand;
import com.platformcommons.platform.service.blockprofile.dto.SeedsInDemandDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageAgriInputDemandProfileDTOAssembler.class,
    })
public interface SeedsInDemandDTOAssembler {

    SeedsInDemandDTO toDTO(SeedsInDemand entity);

    SeedsInDemand fromDTO(SeedsInDemandDTO dto);
}