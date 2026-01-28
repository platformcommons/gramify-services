package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HHSocialAspiration;
import com.platformcommons.platform.service.blockprofile.dto.HHSocialAspirationDTO;
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
public interface HHSocialAspirationDTOAssembler {

    HHSocialAspirationDTO toDTO(HHSocialAspiration entity);

    HHSocialAspiration fromDTO(HHSocialAspirationDTO dto);
}