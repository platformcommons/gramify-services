package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.CommonConsumerGoodsPurchased;
import com.platformcommons.platform.service.blockprofile.dto.CommonConsumerGoodsPurchasedDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageConsumptionPatternDTOAssembler.class,
    })
public interface CommonConsumerGoodsPurchasedDTOAssembler {

    CommonConsumerGoodsPurchasedDTO toDTO(CommonConsumerGoodsPurchased entity);

    CommonConsumerGoodsPurchased fromDTO(CommonConsumerGoodsPurchasedDTO dto);
}