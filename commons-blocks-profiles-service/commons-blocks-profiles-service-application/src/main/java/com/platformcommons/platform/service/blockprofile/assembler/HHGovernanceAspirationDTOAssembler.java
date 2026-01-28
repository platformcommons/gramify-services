package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HHGovernanceAspiration;
import com.platformcommons.platform.service.blockprofile.dto.HHGovernanceAspirationDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HouseholdAspirationsProfileDTOAssembler.class,
    })
public interface HHGovernanceAspirationDTOAssembler {

    HHGovernanceAspirationDTO toDTO(HHGovernanceAspiration entity);

    HHGovernanceAspiration fromDTO(HHGovernanceAspirationDTO dto);
}