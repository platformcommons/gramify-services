package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.ExportPotentialSurplusProduceTy;
import com.platformcommons.platform.service.blockprofile.dto.ExportPotentialSurplusProduceTyDTO;
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
public interface ExportPotentialSurplusProduceTyDTOAssembler {

    ExportPotentialSurplusProduceTyDTO toDTO(ExportPotentialSurplusProduceTy entity);

    ExportPotentialSurplusProduceTy fromDTO(ExportPotentialSurplusProduceTyDTO dto);
}