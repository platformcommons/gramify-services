package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.LocalArtFormType;
import com.platformcommons.platform.service.blockprofile.dto.LocalArtFormTypeDTO;
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
public interface LocalArtFormTypeDTOAssembler {

    LocalArtFormTypeDTO toDTO(LocalArtFormType entity);

    LocalArtFormType fromDTO(LocalArtFormTypeDTO dto);
}