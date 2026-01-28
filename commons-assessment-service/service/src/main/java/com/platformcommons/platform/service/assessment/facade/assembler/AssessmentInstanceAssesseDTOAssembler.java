package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAssesse;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;

public interface AssessmentInstanceAssesseDTOAssembler {

    AssessmentInstanceAssesseDTO toDTO(AssessmentInstanceAssesse entity);

    AssessmentInstanceAssesse fromDTO(AssessmentInstanceAssesseDTO dto);

}