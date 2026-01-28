package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.AppConfig;
import com.platformcommons.platform.service.app.dto.AppConfigDTO;
import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {MLTextDTOAssembler.class})
public interface AppConfigDTOAssembler {

    AppConfigDTO toDTO(AppConfig appConfig);

    AppConfig fromDTO(AppConfigDTO appConfigDTO);
}
