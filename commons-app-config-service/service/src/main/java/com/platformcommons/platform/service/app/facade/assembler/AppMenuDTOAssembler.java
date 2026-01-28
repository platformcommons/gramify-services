package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.AppMenu;
import com.platformcommons.platform.service.app.dto.AppMenuDTO;


import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AppMenuDTOAssembler {

    AppMenuDTO toDTO(AppMenu entity);

    AppMenu fromDTO(AppMenuDTO dto);

    Set<AppMenu> fromDTOs(Set<AppMenuDTO> dtos);
}