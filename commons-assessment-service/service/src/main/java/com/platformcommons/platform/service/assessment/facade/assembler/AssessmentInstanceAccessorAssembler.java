package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAccessor;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAccessorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssessmentInstanceAccessorAssembler {

    AssessmentInstanceAccessorDTO toDto(AssessmentInstanceAccessor assessmentInstanceAccessor);

}
