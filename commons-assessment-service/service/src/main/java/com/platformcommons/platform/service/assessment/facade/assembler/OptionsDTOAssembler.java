package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.Options;
import com.platformcommons.platform.service.assessment.dto.OptionsDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {MimicMLTextDTOAssembler.class})
public interface OptionsDTOAssembler {

    OptionsDTO toDTO(Options entity);

    Options fromDTO(OptionsDTO dto);
}