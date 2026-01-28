package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.NicheProductsAvailability;
import com.platformcommons.platform.service.blockprofile.dto.NicheProductsAvailabilityDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageNicheProductProfileDTOAssembler.class,
        VillageExportPotentialProfileDTOAssembler.class,
    })
public interface NicheProductsAvailabilityDTOAssembler {

    NicheProductsAvailabilityDTO toDTO(NicheProductsAvailability entity);

    NicheProductsAvailability fromDTO(NicheProductsAvailabilityDTO dto);
}