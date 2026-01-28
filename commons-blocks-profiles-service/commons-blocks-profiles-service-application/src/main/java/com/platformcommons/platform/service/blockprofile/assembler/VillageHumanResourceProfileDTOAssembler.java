package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageHumanResourceProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageHumanResourceProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HHSkilledWorkerTypeDTOAssembler.class,
        EmergingEnterpriseTypeDTOAssembler.class,
        MainSkilledTradesPresentDTOAssembler.class,
        ArtisanTypeDTOAssembler.class,
    })
public interface VillageHumanResourceProfileDTOAssembler {

    VillageHumanResourceProfileDTO toDTO(VillageHumanResourceProfile entity);

    VillageHumanResourceProfile fromDTO(VillageHumanResourceProfileDTO dto);
}