package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageCommonFlora;
import com.platformcommons.platform.service.blockprofile.dto.VillageCommonFloraDTO;
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
public interface VillageCommonFloraDTOAssembler {

    VillageCommonFloraDTO toDTO(VillageCommonFlora entity);

    VillageCommonFlora fromDTO(VillageCommonFloraDTO dto);
}