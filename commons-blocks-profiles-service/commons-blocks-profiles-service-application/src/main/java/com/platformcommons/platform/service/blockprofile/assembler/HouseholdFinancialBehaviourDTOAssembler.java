package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HouseholdFinancialBehaviour;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdFinancialBehaviourDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HHSavingsModeDTOAssembler.class,
    })
public interface HouseholdFinancialBehaviourDTOAssembler {

    HouseholdFinancialBehaviourDTO toDTO(HouseholdFinancialBehaviour entity);

    HouseholdFinancialBehaviour fromDTO(HouseholdFinancialBehaviourDTO dto);
}