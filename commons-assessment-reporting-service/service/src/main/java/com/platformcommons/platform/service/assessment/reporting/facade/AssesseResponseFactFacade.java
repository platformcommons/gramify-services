package com.platformcommons.platform.service.assessment.reporting.facade;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;

public interface AssesseResponseFactFacade {
    void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context);

    void deleteDimByAssessmentId(Long assessment);

    void deleteDimByAssesseId(Long id);
}
