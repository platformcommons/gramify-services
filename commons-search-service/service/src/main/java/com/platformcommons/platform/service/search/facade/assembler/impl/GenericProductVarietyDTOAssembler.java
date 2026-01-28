package com.platformcommons.platform.service.search.facade.assembler.impl;

import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.dto.GenericProductVarietySearchDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface GenericProductVarietyDTOAssembler {

    GenericProductVarietySearchDTO toDTO(GenericProductVariety genericProductVariety);

    GenericProductVariety fromDTO(GenericProductVarietySearchDTO genericProductVarietySearchDTO);

    Set<GenericProductVarietySearchDTO> toDTOs(Set<GenericProductVariety> genericProductVarietySet);
}
