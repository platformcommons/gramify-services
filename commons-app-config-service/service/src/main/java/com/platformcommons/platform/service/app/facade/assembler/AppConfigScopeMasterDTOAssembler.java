package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.AppConfigScopeMaster;
import com.platformcommons.platform.service.app.dto.AppConfigScopeMasterDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppConfigScopeMasterDTOAssembler {

     AppConfigScopeMasterDTO toDTO(AppConfigScopeMaster appConfigScopeMaster);

     AppConfigScopeMaster fromDTO(AppConfigScopeMasterDTO appConfigScopeMasterDTO);

}
