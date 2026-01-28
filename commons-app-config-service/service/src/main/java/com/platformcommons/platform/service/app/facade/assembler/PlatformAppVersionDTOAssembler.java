package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.PlatformAppVersion;
import com.platformcommons.platform.service.app.dto.PlatformAppVersionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses ={})
public interface PlatformAppVersionDTOAssembler {

    PlatformAppVersionDTO toDTO(PlatformAppVersion platformAppVersion);

    PlatformAppVersion fromDTO(PlatformAppVersionDTO platformAppVersionDTO);
}
