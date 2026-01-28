package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HHMigrationMonth;
import com.platformcommons.platform.service.blockprofile.dto.HHMigrationMonthDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HouseholdLivelihoodProfileDTOAssembler.class,
    })
public interface HHMigrationMonthDTOAssembler {

    HHMigrationMonthDTO toDTO(HHMigrationMonth entity);

    HHMigrationMonth fromDTO(HHMigrationMonthDTO dto);
}