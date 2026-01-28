package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageMarketProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageMarketProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        OperatingDayDTOAssembler.class,
    })
public interface VillageMarketProfileDTOAssembler {

    VillageMarketProfileDTO toDTO(VillageMarketProfile entity);

    VillageMarketProfile fromDTO(VillageMarketProfileDTO dto);
}