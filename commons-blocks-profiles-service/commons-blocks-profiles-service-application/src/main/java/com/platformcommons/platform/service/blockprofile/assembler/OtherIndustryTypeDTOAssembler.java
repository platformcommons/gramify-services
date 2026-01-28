package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.OtherIndustryType;
import com.platformcommons.platform.service.blockprofile.dto.OtherIndustryTypeDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        NonAgriculturalMarketActiviesDTOAssembler.class,
    })
public interface OtherIndustryTypeDTOAssembler {

    OtherIndustryTypeDTO toDTO(OtherIndustryType entity);

    OtherIndustryType fromDTO(OtherIndustryTypeDTO dto);
}