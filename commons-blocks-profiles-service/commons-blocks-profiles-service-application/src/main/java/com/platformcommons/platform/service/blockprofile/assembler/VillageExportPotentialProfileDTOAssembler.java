package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageExportPotentialProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageExportPotentialProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        NicheProductBuyerTypeDTOAssembler.class,
        SurplusProduceTypeDTOAssembler.class,
        MainSurplusMarketsOutsideBlockDTOAssembler.class,
        NicheProductsAvailabilityDTOAssembler.class,
    })
public interface VillageExportPotentialProfileDTOAssembler {

    VillageExportPotentialProfileDTO toDTO(VillageExportPotentialProfile entity);

    VillageExportPotentialProfile fromDTO(VillageExportPotentialProfileDTO dto);
}