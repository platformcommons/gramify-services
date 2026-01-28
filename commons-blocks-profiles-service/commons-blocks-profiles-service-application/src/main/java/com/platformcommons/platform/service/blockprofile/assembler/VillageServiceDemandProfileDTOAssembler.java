package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageServiceDemandProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageServiceDemandProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        WherePeopleGoForRepairsDTOAssembler.class,
        WherePeopleGoForCommonIllnessDTOAssembler.class,
        CommonRepairNeedDTOAssembler.class,
    })
public interface VillageServiceDemandProfileDTOAssembler {

    VillageServiceDemandProfileDTO toDTO(VillageServiceDemandProfile entity);

    VillageServiceDemandProfile fromDTO(VillageServiceDemandProfileDTO dto);
}