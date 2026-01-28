package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageYouthAspirations;
import com.platformcommons.platform.service.blockprofile.dto.VillageYouthAspirationsDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageHumanCapitalProfileDTOAssembler.class,
    })
public interface VillageYouthAspirationsDTOAssembler {

    VillageYouthAspirationsDTO toDTO(VillageYouthAspirations entity);

    VillageYouthAspirations fromDTO(VillageYouthAspirationsDTO dto);
}