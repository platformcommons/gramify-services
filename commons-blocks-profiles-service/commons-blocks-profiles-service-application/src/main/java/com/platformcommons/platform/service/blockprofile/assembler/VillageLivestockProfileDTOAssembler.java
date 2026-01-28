package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageLivestockProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageLivestockProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        ProductionSeasonalityDTOAssembler.class,
    })
public interface VillageLivestockProfileDTOAssembler {

    VillageLivestockProfileDTO toDTO(VillageLivestockProfile entity);

    VillageLivestockProfile fromDTO(VillageLivestockProfileDTO dto);
}