package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.RenewableInfraType;
import com.platformcommons.platform.service.blockprofile.dto.RenewableInfraTypeDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageElectricityInfrastructurDTOAssembler.class,
    })
public interface RenewableInfraTypeDTOAssembler {

    RenewableInfraTypeDTO toDTO(RenewableInfraType entity);

    RenewableInfraType fromDTO(RenewableInfraTypeDTO dto);
}