package com.platformcommons.platform.service.assessment.reporting.facade;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;

import java.util.Set;

public interface AssesseDimFacade {
    void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context);

    boolean responseExist(Long id);

    void deleteDimByAssessmentId(Long assessment);

    String getUserIdsForInstance(Set<Long> id);

    void deleteDimByAssesseId(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context);
}
