package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HouseholdAspirationsProfile;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdAspirationsProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HHSocialAspirationDTOAssembler.class,
        HHEconomicAspirationDTOAssembler.class,
        HHGovernanceAspirationDTOAssembler.class,
    })
public interface HouseholdAspirationsProfileDTOAssembler {

    HouseholdAspirationsProfileDTO toDTO(HouseholdAspirationsProfile entity);

    HouseholdAspirationsProfile fromDTO(HouseholdAspirationsProfileDTO dto);
}