package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.CommonRepairNeed;
import com.platformcommons.platform.service.blockprofile.dto.CommonRepairNeedDTO;
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
public interface CommonRepairNeedDTOAssembler {

    CommonRepairNeedDTO toDTO(CommonRepairNeed entity);

    CommonRepairNeed fromDTO(CommonRepairNeedDTO dto);
}