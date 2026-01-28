package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HHLoanSource;
import com.platformcommons.platform.service.blockprofile.dto.HHLoanSourceDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        HouseholdDTOAssembler.class,
    })
public interface HHLoanSourceDTOAssembler {

    HHLoanSourceDTO toDTO(HHLoanSource entity);

    HHLoanSource fromDTO(HHLoanSourceDTO dto);
}