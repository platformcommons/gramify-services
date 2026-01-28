package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HHEconomicConstraints;
import com.platformcommons.platform.service.blockprofile.dto.HHEconomicConstraintsDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HouseholdConstraintsProfileDTOAssembler.class,
    })
public interface HHEconomicConstraintsDTOAssembler {

    HHEconomicConstraintsDTO toDTO(HHEconomicConstraints entity);

    HHEconomicConstraints fromDTO(HHEconomicConstraintsDTO dto);
}