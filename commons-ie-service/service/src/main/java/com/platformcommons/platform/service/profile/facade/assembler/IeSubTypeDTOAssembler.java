package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.IeSubType;
import com.platformcommons.platform.service.profile.dto.IeSubTypeDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface IeSubTypeDTOAssembler {

    IeSubTypeDTO toDTO(IeSubType entity);

    IeSubType fromDTO(IeSubTypeDTO dto);

    Set<IeSubTypeDTO> toDTOs(Set<IeSubType> ieSubTypes);
}