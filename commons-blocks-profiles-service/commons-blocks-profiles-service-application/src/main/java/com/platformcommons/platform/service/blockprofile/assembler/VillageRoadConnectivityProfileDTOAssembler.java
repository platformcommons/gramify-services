package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageRoadConnectivityProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageRoadConnectivityProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
    })
public interface VillageRoadConnectivityProfileDTOAssembler {

    VillageRoadConnectivityProfileDTO toDTO(VillageRoadConnectivityProfile entity);

    VillageRoadConnectivityProfile fromDTO(VillageRoadConnectivityProfileDTO dto);
}