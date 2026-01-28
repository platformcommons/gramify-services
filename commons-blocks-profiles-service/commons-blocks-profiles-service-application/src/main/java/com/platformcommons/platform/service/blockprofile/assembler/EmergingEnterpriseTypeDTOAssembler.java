package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.EmergingEnterpriseType;
import com.platformcommons.platform.service.blockprofile.dto.EmergingEnterpriseTypeDTO;
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
public interface EmergingEnterpriseTypeDTOAssembler {

    EmergingEnterpriseTypeDTO toDTO(EmergingEnterpriseType entity);

    EmergingEnterpriseType fromDTO(EmergingEnterpriseTypeDTO dto);
}