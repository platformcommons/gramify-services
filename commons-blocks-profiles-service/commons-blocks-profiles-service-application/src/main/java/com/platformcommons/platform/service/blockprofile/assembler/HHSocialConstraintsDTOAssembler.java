package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HHSocialConstraints;
import com.platformcommons.platform.service.blockprofile.dto.HHSocialConstraintsDTO;
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
public interface HHSocialConstraintsDTOAssembler {

    HHSocialConstraintsDTO toDTO(HHSocialConstraints entity);

    HHSocialConstraints fromDTO(HHSocialConstraintsDTO dto);
}