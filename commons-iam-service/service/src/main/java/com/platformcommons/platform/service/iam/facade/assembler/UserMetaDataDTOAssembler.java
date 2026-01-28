package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.UserMetaData;
import com.platformcommons.platform.service.iam.dto.UserMetaDataDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMetaDataDTOAssembler {

    UserMetaData fromDTO(UserMetaDataDTO dto);

    UserMetaDataDTO toDTO(UserMetaData entity);

    Set<UserMetaDataDTO> toDTOs(Set<UserMetaData> entities);

    Set<UserMetaData> fromDTOs(Set<UserMetaDataDTO> dtos);
}
