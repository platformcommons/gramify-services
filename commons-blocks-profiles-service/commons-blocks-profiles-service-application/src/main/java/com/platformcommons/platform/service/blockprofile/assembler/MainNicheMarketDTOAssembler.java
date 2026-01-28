package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.MainNicheMarket;
import com.platformcommons.platform.service.blockprofile.dto.MainNicheMarketDTO;
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
public interface MainNicheMarketDTOAssembler {

    MainNicheMarketDTO toDTO(MainNicheMarket entity);

    MainNicheMarket fromDTO(MainNicheMarketDTO dto);
}