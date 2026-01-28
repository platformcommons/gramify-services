package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.WherePeopleGoForCommonIllness;
import com.platformcommons.platform.service.blockprofile.dto.WherePeopleGoForCommonIllnessDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageServiceDemandProfileDTOAssembler.class,
    })
public interface WherePeopleGoForCommonIllnessDTOAssembler {

    WherePeopleGoForCommonIllnessDTO toDTO(WherePeopleGoForCommonIllness entity);

    WherePeopleGoForCommonIllness fromDTO(WherePeopleGoForCommonIllnessDTO dto);
}