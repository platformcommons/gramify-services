package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.AppFeatureConfiguration;
import com.platformcommons.platform.service.app.dto.AppFeatureConfigurationDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = {
        ComponentConfigDTOAssembler.class
    })
public interface AppFeatureConfigurationDTOAssembler {

    AppFeatureConfigurationDTO toDTO(AppFeatureConfiguration entity);

    List<AppFeatureConfigurationDTO> toDTOs(List<AppFeatureConfiguration> entity);

    AppFeatureConfiguration fromDTO(AppFeatureConfigurationDTO dto);

    static void resetPrimaryKey(AppFeatureConfigurationDTO dto) {
        if (dto != null) {
            dto.setId(0L);
            dto.setUuid(null);

            if (dto.getComponentConfigSet() != null) {
                dto.getComponentConfigSet().forEach(ComponentConfigDTOAssembler::resetPrimaryKey);
            }
        }
    }
}