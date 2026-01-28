package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageInfrastructureConstraint;
import com.platformcommons.platform.service.blockprofile.dto.VillageInfrastructureConstraintDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageTopInfrastructureNeedDTOAssembler.class,
        VillageTransportConnectivityIssDTOAssembler.class,
        VillageWaterSupplyGapDTOAssembler.class,
    })
public interface VillageInfrastructureConstraintDTOAssembler {

    VillageInfrastructureConstraintDTO toDTO(VillageInfrastructureConstraint entity);

    VillageInfrastructureConstraint fromDTO(VillageInfrastructureConstraintDTO dto);
}