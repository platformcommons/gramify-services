package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.AssesseDimDTO;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseDim;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssesseDimDTOAssembler {

    AssesseDimDTO toDto(AssesseDim assesseDim);
    AssesseDim toEntity(AssesseDimDTO dto);

}
