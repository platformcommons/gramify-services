package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.Platform;
import com.platformcommons.platform.service.domain.dto.PlatformDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PlatformDTOAssembler {

    PlatformDTO toDTO(Platform entity);

    Platform fromDTO(PlatformDTO dto);
}