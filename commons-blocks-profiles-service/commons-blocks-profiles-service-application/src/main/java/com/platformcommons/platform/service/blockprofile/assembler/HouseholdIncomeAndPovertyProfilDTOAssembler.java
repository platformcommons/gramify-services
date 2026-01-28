package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HouseholdIncomeAndPovertyProfil;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdIncomeAndPovertyProfilDTO;
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
public interface HouseholdIncomeAndPovertyProfilDTOAssembler {

    HouseholdIncomeAndPovertyProfilDTO toDTO(HouseholdIncomeAndPovertyProfil entity);

    HouseholdIncomeAndPovertyProfil fromDTO(HouseholdIncomeAndPovertyProfilDTO dto);
}