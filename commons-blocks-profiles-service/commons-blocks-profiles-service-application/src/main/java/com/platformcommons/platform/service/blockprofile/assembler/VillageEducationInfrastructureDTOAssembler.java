package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageEducationInfrastructure;
import com.platformcommons.platform.service.blockprofile.dto.VillageEducationInfrastructureDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        IssuesInHigherEducationAccessDTOAssembler.class,
    })
public interface VillageEducationInfrastructureDTOAssembler {

    VillageEducationInfrastructureDTO toDTO(VillageEducationInfrastructure entity);

    VillageEducationInfrastructure fromDTO(VillageEducationInfrastructureDTO dto);
}