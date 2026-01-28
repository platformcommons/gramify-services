package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseResponseFact;

import java.util.Set;

public interface AssesseResponseFactAssembler {
    Set<AssesseResponseFact> toAssesseResponseFact(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context);
}
