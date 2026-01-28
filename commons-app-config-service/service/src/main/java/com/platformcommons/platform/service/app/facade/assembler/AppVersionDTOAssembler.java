package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.AppVersion;
import com.platformcommons.platform.service.app.dto.AppVersionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses ={AppConfigDTOAssembler.class})
public interface AppVersionDTOAssembler {

    AppVersionDTO toDTO(AppVersion appVersion);

    AppVersion fromDTO(AppVersionDTO appVersion);
}
