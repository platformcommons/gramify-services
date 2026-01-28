package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.CommonsLocation;
import com.platformcommons.platform.service.search.dto.CommonsLocationDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CommonsLocationDTOAssembler {

    CommonsLocationDTO toDTO(CommonsLocation commonsLocation);

    Set<CommonsLocationDTO> toDTOs(Set<CommonsLocation> entities);

    CommonsLocation fromDTO(CommonsLocationDTO commonsLocationDTO);
}
