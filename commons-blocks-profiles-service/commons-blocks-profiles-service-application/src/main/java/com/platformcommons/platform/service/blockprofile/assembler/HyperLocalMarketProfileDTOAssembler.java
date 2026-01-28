package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.HyperLocalMarketProfile;
import com.platformcommons.platform.service.blockprofile.dto.HyperLocalMarketProfileDTO;
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
public interface HyperLocalMarketProfileDTOAssembler {

    HyperLocalMarketProfileDTO toDTO(HyperLocalMarketProfile entity);

    HyperLocalMarketProfile fromDTO(HyperLocalMarketProfileDTO dto);
}