package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.OperatingDay;
import com.platformcommons.platform.service.blockprofile.dto.OperatingDayDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageMarketProfileDTOAssembler.class,
    })
public interface OperatingDayDTOAssembler {

    OperatingDayDTO toDTO(OperatingDay entity);

    OperatingDay fromDTO(OperatingDayDTO dto);
}