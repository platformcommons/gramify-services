package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.domain.MtfOption;
import com.platformcommons.platform.service.assessment.dto.MTFOptionDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.OptionsDTOAssembler;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {OptionsDTOAssembler.class})
public interface MTFOptionDTOAssembler {

    MTFOptionDTO toDTO(MtfOption entity);

    MtfOption fromDTO(MTFOptionDTO dto);

}
