package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HHSavingsMode;
import com.platformcommons.platform.service.blockprofile.dto.HHSavingsModeDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HouseholdFinancialBehaviourDTOAssembler.class,
    })
public interface HHSavingsModeDTOAssembler {

    HHSavingsModeDTO toDTO(HHSavingsMode entity);

    HHSavingsMode fromDTO(HHSavingsModeDTO dto);
}