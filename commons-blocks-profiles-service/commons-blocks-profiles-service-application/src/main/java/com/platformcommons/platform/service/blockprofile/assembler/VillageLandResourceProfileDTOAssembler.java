package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageLandResourceProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageLandResourceProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageSoilTypeDTOAssembler.class,
    })
public interface VillageLandResourceProfileDTOAssembler {

    VillageLandResourceProfileDTO toDTO(VillageLandResourceProfile entity);

    VillageLandResourceProfile fromDTO(VillageLandResourceProfileDTO dto);
}