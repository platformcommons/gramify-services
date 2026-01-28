package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.SurplusProduceType;
import com.platformcommons.platform.service.blockprofile.dto.SurplusProduceTypeDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageExportPotentialProfileDTOAssembler.class,
        VillageSurplusProduceProfileDTOAssembler.class,
    })
public interface SurplusProduceTypeDTOAssembler {

    SurplusProduceTypeDTO toDTO(SurplusProduceType entity);

    SurplusProduceType fromDTO(SurplusProduceTypeDTO dto);
}