package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.CentralSchemeList;
import com.platformcommons.platform.service.blockprofile.dto.CentralSchemeListDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        GovtSchemesDTOAssembler.class,
    })
public interface CentralSchemeListDTOAssembler {

    CentralSchemeListDTO toDTO(CentralSchemeList entity);

    CentralSchemeList fromDTO(CentralSchemeListDTO dto);
}