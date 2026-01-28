package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageCommonFauna;
import com.platformcommons.platform.service.blockprofile.dto.VillageCommonFaunaDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageMineralAndBiodiversityPrDTOAssembler.class,
    })
public interface VillageCommonFaunaDTOAssembler {

    VillageCommonFaunaDTO toDTO(VillageCommonFauna entity);

    VillageCommonFauna fromDTO(VillageCommonFaunaDTO dto);
}