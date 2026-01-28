package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.ArtisanType;
import com.platformcommons.platform.service.blockprofile.dto.ArtisanTypeDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageHumanResourceProfileDTOAssembler.class,
    })
public interface ArtisanTypeDTOAssembler {

    ArtisanTypeDTO toDTO(ArtisanType entity);

    ArtisanType fromDTO(ArtisanTypeDTO dto);
}