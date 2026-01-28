package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.GovtSchemes;
import com.platformcommons.platform.service.blockprofile.dto.GovtSchemesDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        SchemeImplementationChallengeDTOAssembler.class,
        CentralSchemeListDTOAssembler.class,
    })
public interface GovtSchemesDTOAssembler {

    GovtSchemesDTO toDTO(GovtSchemes entity);

    GovtSchemes fromDTO(GovtSchemesDTO dto);
}