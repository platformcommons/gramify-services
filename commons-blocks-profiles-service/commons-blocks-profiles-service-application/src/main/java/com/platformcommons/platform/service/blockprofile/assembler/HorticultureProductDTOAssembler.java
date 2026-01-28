package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HorticultureProduct;
import com.platformcommons.platform.service.blockprofile.dto.HorticultureProductDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageHorticultureProfileDTOAssembler.class,
    })
public interface HorticultureProductDTOAssembler {

    HorticultureProductDTO toDTO(HorticultureProduct entity);

    HorticultureProduct fromDTO(HorticultureProductDTO dto);
}