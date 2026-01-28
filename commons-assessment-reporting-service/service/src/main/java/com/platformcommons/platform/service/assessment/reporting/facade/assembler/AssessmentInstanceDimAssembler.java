package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentInstanceDim;

public interface AssessmentInstanceDimAssembler {
    AssessmentInstanceDim assessmentDTOToAssessmentInstanceDim(AssessmentDTO assessmentDTO);

    AssessmentInstanceDim assessmentInstanceDTOtoAssessmentInstanceDim(AssessmentInstanceDTO dto);

    AssessmentInstanceDim assessmentQuestionPaperDTOtoAssessmentInstanceDim(AssessmentQuestionPaperDTO assessment);

    AssessmentInstanceDim createAssessmentInstanceDimFromSyncContext(AssessmentSyncContext syncContext);
}
