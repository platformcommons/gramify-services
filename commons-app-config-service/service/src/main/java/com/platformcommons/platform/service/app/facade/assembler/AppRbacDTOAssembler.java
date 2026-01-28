package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.AppRbac;
import com.platformcommons.platform.service.app.dto.AppRbacDTO;


import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring",uses = {
          AppMenuDTOAssembler.class,
          AppOperationDTOAssembler.class,
    })
public interface AppRbacDTOAssembler {

    AppRbacDTO toDTO(AppRbac entity);

    AppRbac fromDTO(AppRbacDTO dto);

    Set<AppRbac> fromDTOs(Set<AppRbacDTO> appRbacDTOS);
}