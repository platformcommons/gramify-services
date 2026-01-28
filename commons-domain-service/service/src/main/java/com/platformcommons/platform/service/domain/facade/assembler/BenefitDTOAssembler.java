package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.Benefit;
import com.platformcommons.platform.service.domain.dto.BenefitDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface BenefitDTOAssembler {

    BenefitDTO toDTO(Benefit entity);

    Benefit fromDTO(BenefitDTO dto);
}