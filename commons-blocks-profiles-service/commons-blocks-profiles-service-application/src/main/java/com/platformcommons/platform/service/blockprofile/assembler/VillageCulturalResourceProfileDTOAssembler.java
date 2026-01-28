package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageCulturalResourceProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageCulturalResourceProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        MajorFestivalDTOAssembler.class,
        MainReligiousPlaceDTOAssembler.class,
        LocalFestivalDTOAssembler.class,
        LocalArtFormTypeDTOAssembler.class,
    })
public interface VillageCulturalResourceProfileDTOAssembler {

    VillageCulturalResourceProfileDTO toDTO(VillageCulturalResourceProfile entity);

    VillageCulturalResourceProfile fromDTO(VillageCulturalResourceProfileDTO dto);
}