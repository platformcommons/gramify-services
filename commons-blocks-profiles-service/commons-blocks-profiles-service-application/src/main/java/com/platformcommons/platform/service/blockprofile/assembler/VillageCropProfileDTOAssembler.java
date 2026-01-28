package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageCropProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageCropProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        CropDTOAssembler.class,
        CroppingSeasonDTOAssembler.class,
    })
public interface VillageCropProfileDTOAssembler {

    VillageCropProfileDTO toDTO(VillageCropProfile entity);

    VillageCropProfile fromDTO(VillageCropProfileDTO dto);
}