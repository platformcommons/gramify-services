package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.SupportNeededForNicheGrowth;
import com.platformcommons.platform.service.blockprofile.dto.SupportNeededForNicheGrowthDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageNicheProductProfileDTOAssembler.class,
    })
public interface SupportNeededForNicheGrowthDTOAssembler {

    SupportNeededForNicheGrowthDTO toDTO(SupportNeededForNicheGrowth entity);

    SupportNeededForNicheGrowth fromDTO(SupportNeededForNicheGrowthDTO dto);
}