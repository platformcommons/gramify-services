package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.NonAgriculturalMarketActivies;
import com.platformcommons.platform.service.blockprofile.dto.NonAgriculturalMarketActiviesDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        OtherIndustryTypeDTOAssembler.class,
    })
public interface NonAgriculturalMarketActiviesDTOAssembler {

    NonAgriculturalMarketActiviesDTO toDTO(NonAgriculturalMarketActivies entity);

    NonAgriculturalMarketActivies fromDTO(NonAgriculturalMarketActiviesDTO dto);
}