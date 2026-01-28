package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.WherePeopleGoForRepairs;
import com.platformcommons.platform.service.blockprofile.dto.WherePeopleGoForRepairsDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageServiceDemandProfileDTOAssembler.class,
    })
public interface WherePeopleGoForRepairsDTOAssembler {

    WherePeopleGoForRepairsDTO toDTO(WherePeopleGoForRepairs entity);

    WherePeopleGoForRepairs fromDTO(WherePeopleGoForRepairsDTO dto);
}