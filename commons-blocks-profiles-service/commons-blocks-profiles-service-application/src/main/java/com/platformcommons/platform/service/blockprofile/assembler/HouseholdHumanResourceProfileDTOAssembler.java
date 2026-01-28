package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HouseholdHumanResourceProfile;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdHumanResourceProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HHEmploymentTypeDTOAssembler.class,
        HHEnterpriseTypeDTOAssembler.class,
        HHArtisanTypeDTOAssembler.class,
    })
public interface HouseholdHumanResourceProfileDTOAssembler {

    HouseholdHumanResourceProfileDTO toDTO(HouseholdHumanResourceProfile entity);

    HouseholdHumanResourceProfile fromDTO(HouseholdHumanResourceProfileDTO dto);
}