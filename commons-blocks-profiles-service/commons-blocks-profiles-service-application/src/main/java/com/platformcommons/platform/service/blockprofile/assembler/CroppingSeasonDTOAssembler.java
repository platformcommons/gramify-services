package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.CroppingSeason;
import com.platformcommons.platform.service.blockprofile.dto.CroppingSeasonDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageCropProfileDTOAssembler.class,
    })
public interface CroppingSeasonDTOAssembler {

    CroppingSeasonDTO toDTO(CroppingSeason entity);

    CroppingSeason fromDTO(CroppingSeasonDTO dto);
}