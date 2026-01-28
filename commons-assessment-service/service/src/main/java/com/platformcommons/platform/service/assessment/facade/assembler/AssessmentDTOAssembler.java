package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.Assessment;
import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;

public interface AssessmentDTOAssembler {

    AssessmentDTO toDTO(Assessment entity);

    Assessment fromDTO(AssessmentDTO dto);
}