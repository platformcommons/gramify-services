package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.IEIdCardTemplate;
import com.platformcommons.platform.service.profile.dto.IEIdCardTemplateDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface IEIdCardTemplateDTOAssembler {

    IEIdCardTemplateDTO toDTO(IEIdCardTemplate entity);
    Set<IEIdCardTemplateDTO> toDTOs(Set<IEIdCardTemplate> entities);

    IEIdCardTemplate fromDTO(IEIdCardTemplateDTO dto);
    Set<IEIdCardTemplate> fromDTOs(Set<IEIdCardTemplateDTO> dtos);
}
