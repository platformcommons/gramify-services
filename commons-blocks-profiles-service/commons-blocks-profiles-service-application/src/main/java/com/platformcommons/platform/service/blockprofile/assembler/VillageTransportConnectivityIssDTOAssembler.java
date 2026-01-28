package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageTransportConnectivityIss;
import com.platformcommons.platform.service.blockprofile.dto.VillageTransportConnectivityIssDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageInfrastructureConstraintDTOAssembler.class,
    })
public interface VillageTransportConnectivityIssDTOAssembler {

    VillageTransportConnectivityIssDTO toDTO(VillageTransportConnectivityIss entity);

    VillageTransportConnectivityIss fromDTO(VillageTransportConnectivityIssDTO dto);
}