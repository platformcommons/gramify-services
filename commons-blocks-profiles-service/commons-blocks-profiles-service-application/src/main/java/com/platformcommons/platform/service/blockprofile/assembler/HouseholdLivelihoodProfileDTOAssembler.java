package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HouseholdLivelihoodProfile;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdLivelihoodProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HHMigrationMonthDTOAssembler.class,
    })
public interface HouseholdLivelihoodProfileDTOAssembler {

    HouseholdLivelihoodProfileDTO toDTO(HouseholdLivelihoodProfile entity);

    HouseholdLivelihoodProfile fromDTO(HouseholdLivelihoodProfileDTO dto);
}