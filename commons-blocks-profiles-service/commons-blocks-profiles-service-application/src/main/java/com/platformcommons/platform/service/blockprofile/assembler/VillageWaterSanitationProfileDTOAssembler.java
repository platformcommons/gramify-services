package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageWaterSanitationProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageWaterSanitationProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
    })
public interface VillageWaterSanitationProfileDTOAssembler {

    VillageWaterSanitationProfileDTO toDTO(VillageWaterSanitationProfile entity);

    VillageWaterSanitationProfile fromDTO(VillageWaterSanitationProfileDTO dto);
}