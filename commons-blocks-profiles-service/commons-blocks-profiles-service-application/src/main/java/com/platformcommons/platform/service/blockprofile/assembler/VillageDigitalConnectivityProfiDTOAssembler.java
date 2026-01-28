package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageDigitalConnectivityProfi;
import com.platformcommons.platform.service.blockprofile.dto.VillageDigitalConnectivityProfiDTO;
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
public interface VillageDigitalConnectivityProfiDTOAssembler {

    VillageDigitalConnectivityProfiDTO toDTO(VillageDigitalConnectivityProfi entity);

    VillageDigitalConnectivityProfi fromDTO(VillageDigitalConnectivityProfiDTO dto);
}