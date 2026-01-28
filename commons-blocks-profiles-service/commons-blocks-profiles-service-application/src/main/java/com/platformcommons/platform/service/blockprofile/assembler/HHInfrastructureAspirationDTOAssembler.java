package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HHInfrastructureAspiration;
import com.platformcommons.platform.service.blockprofile.dto.HHInfrastructureAspirationDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HHInfrastructureAspirationDTOAssembler.class,
    })
public interface HHInfrastructureAspirationDTOAssembler {

    HHInfrastructureAspirationDTO toDTO(HHInfrastructureAspiration entity);

    HHInfrastructureAspiration fromDTO(HHInfrastructureAspirationDTO dto);
}