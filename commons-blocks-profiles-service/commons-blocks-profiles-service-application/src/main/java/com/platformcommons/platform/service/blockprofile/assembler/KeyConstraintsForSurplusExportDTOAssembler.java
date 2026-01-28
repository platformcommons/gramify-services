package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.KeyConstraintsForSurplusExport;
import com.platformcommons.platform.service.blockprofile.dto.KeyConstraintsForSurplusExportDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageSurplusProduceProfileDTOAssembler.class,
    })
public interface KeyConstraintsForSurplusExportDTOAssembler {

    KeyConstraintsForSurplusExportDTO toDTO(KeyConstraintsForSurplusExport entity);

    KeyConstraintsForSurplusExport fromDTO(KeyConstraintsForSurplusExportDTO dto);
}