package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.ExportPotentialNicheProductBuye;
import com.platformcommons.platform.service.blockprofile.dto.ExportPotentialNicheProductBuyeDTO;
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
public interface ExportPotentialNicheProductBuyeDTOAssembler {

    ExportPotentialNicheProductBuyeDTO toDTO(ExportPotentialNicheProductBuye entity);

    ExportPotentialNicheProductBuye fromDTO(ExportPotentialNicheProductBuyeDTO dto);
}