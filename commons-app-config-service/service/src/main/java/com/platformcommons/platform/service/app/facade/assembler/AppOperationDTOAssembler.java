package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.AppOperation;
import com.platformcommons.platform.service.app.dto.AppOperationDTO;


import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring",uses = {
    })
public interface AppOperationDTOAssembler {

    AppOperationDTO toDTO(AppOperation entity);

    AppOperation fromDTO(AppOperationDTO dto);

    Set<AppOperation> fromDTOs(Set<AppOperationDTO> appOperationDTOS);
}