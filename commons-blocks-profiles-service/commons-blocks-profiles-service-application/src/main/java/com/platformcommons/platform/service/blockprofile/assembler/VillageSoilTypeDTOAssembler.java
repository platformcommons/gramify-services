package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageSoilType;
import com.platformcommons.platform.service.blockprofile.dto.VillageSoilTypeDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageLandResourceProfileDTOAssembler.class,
    })
public interface VillageSoilTypeDTOAssembler {

    VillageSoilTypeDTO toDTO(VillageSoilType entity);

    VillageSoilType fromDTO(VillageSoilTypeDTO dto);
}