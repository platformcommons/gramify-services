package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HHSkilledWorkerType;
import com.platformcommons.platform.service.blockprofile.dto.HHSkilledWorkerTypeDTO;
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
public interface HHSkilledWorkerTypeDTOAssembler {

    HHSkilledWorkerTypeDTO toDTO(HHSkilledWorkerType entity);

    HHSkilledWorkerType fromDTO(HHSkilledWorkerTypeDTO dto);
}