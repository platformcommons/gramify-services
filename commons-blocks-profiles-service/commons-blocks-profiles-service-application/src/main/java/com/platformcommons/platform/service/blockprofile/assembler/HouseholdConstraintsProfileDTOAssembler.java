package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HouseholdConstraintsProfile;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdConstraintsProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HHSocialConstraintsDTOAssembler.class,
        HHEconomicConstraintsDTOAssembler.class,
    })
public interface HouseholdConstraintsProfileDTOAssembler {

    HouseholdConstraintsProfileDTO toDTO(HouseholdConstraintsProfile entity);

    HouseholdConstraintsProfile fromDTO(HouseholdConstraintsProfileDTO dto);
}