package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HHArtisanType;
import com.platformcommons.platform.service.blockprofile.dto.HHArtisanTypeDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HouseholdHumanResourceProfileDTOAssembler.class,
    })
public interface HHArtisanTypeDTOAssembler {

    HHArtisanTypeDTO toDTO(HHArtisanType entity);

    HHArtisanType fromDTO(HHArtisanTypeDTO dto);
}