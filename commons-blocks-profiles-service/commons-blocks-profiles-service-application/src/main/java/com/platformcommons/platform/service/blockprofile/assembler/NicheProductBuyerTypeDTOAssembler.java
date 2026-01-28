package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.NicheProductBuyerType;
import com.platformcommons.platform.service.blockprofile.dto.NicheProductBuyerTypeDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageExportPotentialProfileDTOAssembler.class,
        VillageNicheProductProfileDTOAssembler.class,
    })
public interface NicheProductBuyerTypeDTOAssembler {

    NicheProductBuyerTypeDTO toDTO(NicheProductBuyerType entity);

    NicheProductBuyerType fromDTO(NicheProductBuyerTypeDTO dto);
}