package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;

import java.util.Set;

public interface AssesseSkillDimService {
    void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context);

    void deleteDimByAssesssesId(Set<Long> collect);
}
