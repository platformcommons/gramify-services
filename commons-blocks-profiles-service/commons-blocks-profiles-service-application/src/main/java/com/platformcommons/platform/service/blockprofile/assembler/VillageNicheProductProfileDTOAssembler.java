package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageNicheProductProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageNicheProductProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        MainNicheMarketDTOAssembler.class,
        SupportNeededForNicheGrowthDTOAssembler.class,
        NicheProductsAvailabilityDTOAssembler.class,
        NicheProductBuyerTypeDTOAssembler.class,
    })
public interface VillageNicheProductProfileDTOAssembler {

    VillageNicheProductProfileDTO toDTO(VillageNicheProductProfile entity);

    VillageNicheProductProfile fromDTO(VillageNicheProductProfileDTO dto);
}