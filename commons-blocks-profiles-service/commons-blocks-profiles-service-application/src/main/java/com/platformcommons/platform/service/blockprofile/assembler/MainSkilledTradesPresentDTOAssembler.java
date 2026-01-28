package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.MainSkilledTradesPresent;
import com.platformcommons.platform.service.blockprofile.dto.MainSkilledTradesPresentDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageHumanResourceProfileDTOAssembler.class,
    })
public interface MainSkilledTradesPresentDTOAssembler {

    MainSkilledTradesPresentDTO toDTO(MainSkilledTradesPresent entity);

    MainSkilledTradesPresent fromDTO(MainSkilledTradesPresentDTO dto);
}