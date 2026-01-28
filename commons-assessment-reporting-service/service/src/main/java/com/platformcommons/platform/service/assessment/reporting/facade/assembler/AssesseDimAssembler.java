package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseDim;

public interface AssesseDimAssembler {
    AssesseDim toAssesseDim(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context);
}
