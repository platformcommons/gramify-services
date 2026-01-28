package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.ComponentConfig;
import com.platformcommons.platform.service.app.dto.ComponentConfigDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ComponentConfigDTOAssembler {

    ComponentConfigDTO toDTO(ComponentConfig entity);

    Set<ComponentConfigDTO> toDTOs(Set<ComponentConfig> entities);

    ComponentConfig fromDTO(ComponentConfigDTO dto);

    Set<ComponentConfig> fromDTOs(Set<ComponentConfigDTO> dtos);

    static void resetPrimaryKey(ComponentConfigDTO componentConfigDTO) {
        componentConfigDTO.setId(0L);
        componentConfigDTO.setUuid(null);

        if (componentConfigDTO.getSubComponentConfigSet() != null) {
            componentConfigDTO.getSubComponentConfigSet().forEach(ComponentConfigDTOAssembler::resetPrimaryKey);
        }
    }
}
