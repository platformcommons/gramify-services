package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.GenericSolution;
import com.platformcommons.platform.service.search.dto.GenericSolutionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenericSolutionDTOAssembler {

    GenericSolutionDTO toDTO(GenericSolution genericSolution);

    GenericSolution fromDTO(GenericSolutionDTO genericSolutionDTO);
}
