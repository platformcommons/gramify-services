package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.MajorFestival;
import com.platformcommons.platform.service.blockprofile.dto.MajorFestivalDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageCulturalResourceProfileDTOAssembler.class,
    })
public interface MajorFestivalDTOAssembler {

    MajorFestivalDTO toDTO(MajorFestival entity);

    MajorFestival fromDTO(MajorFestivalDTO dto);
}