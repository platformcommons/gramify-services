package com.platformcommons.platform.service.app.facade.assembler;


import com.platformcommons.platform.service.app.domain.App;
import com.platformcommons.platform.service.app.dto.AppDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AppVersionDTOAssembler.class})
public interface AppDTOAssembler {

    AppDTO toDTO(App app);

    App fromDTO(AppDTO appDTO);
}
