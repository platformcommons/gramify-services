package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.LocalFestival;
import com.platformcommons.platform.service.blockprofile.dto.LocalFestivalDTO;
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
public interface LocalFestivalDTOAssembler {

    LocalFestivalDTO toDTO(LocalFestival entity);

    LocalFestival fromDTO(LocalFestivalDTO dto);
}