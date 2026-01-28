package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.AppRbac;
import com.platformcommons.platform.service.app.domain.AppRbacMeta;
import com.platformcommons.platform.service.app.dto.AppRbacDTO;
import com.platformcommons.platform.service.app.dto.AppRbacMetaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
          AppMenuDTOAssembler.class,
    })
public interface AppRbacMetaDTOAssembler {

    AppRbacMetaDTO toDTO(AppRbacMeta entity);

    AppRbacMeta fromDTO(AppRbacMetaDTO dto);
}