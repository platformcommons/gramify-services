package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageConsumptionPattern;
import com.platformcommons.platform.service.blockprofile.dto.VillageConsumptionPatternDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        CommonConsumerGoodsPurchasedDTOAssembler.class,
        ItemsUsuallyBoughtFromOutsideDTOAssembler.class,
        StapleFoodsConsumedDTOAssembler.class,
        ItemsUsuallyBoughtLocallyDTOAssembler.class,
    })
public interface VillageConsumptionPatternDTOAssembler {

    VillageConsumptionPatternDTO toDTO(VillageConsumptionPattern entity);

    VillageConsumptionPattern fromDTO(VillageConsumptionPatternDTO dto);
}