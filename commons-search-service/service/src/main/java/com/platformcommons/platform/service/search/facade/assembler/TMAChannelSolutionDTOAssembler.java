package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.TMAChannelSolution;
import com.platformcommons.platform.service.search.dto.TMAChannelSolutionDTO;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
public interface TMAChannelSolutionDTOAssembler {

    TMAChannelSolutionDTO toDTO(TMAChannelSolution tmaChannelSolution);

    TMAChannelSolution fromDTO(TMAChannelSolutionDTO tmaChannelSolutionDTO);
}
