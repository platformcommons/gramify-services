package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageMineralAndBiodiversityPr;
import com.platformcommons.platform.service.blockprofile.dto.VillageMineralAndBiodiversityPrDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageCommonFloraDTOAssembler.class,
        VillageCommonFaunaDTOAssembler.class,
    })
public interface VillageMineralAndBiodiversityPrDTOAssembler {

    VillageMineralAndBiodiversityPrDTO toDTO(VillageMineralAndBiodiversityPr entity);

    VillageMineralAndBiodiversityPr fromDTO(VillageMineralAndBiodiversityPrDTO dto);
}