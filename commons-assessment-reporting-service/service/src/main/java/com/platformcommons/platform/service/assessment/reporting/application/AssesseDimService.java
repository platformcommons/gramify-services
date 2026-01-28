package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseDim;

import java.util.Optional;
import java.util.Set;

public interface AssesseDimService {
    void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context);

    boolean responseExist(Long id);

    void deleteDimByAssessmentId(Long assessment);

    String getUserIdsForInstance(Set<Long> id);

    void deleteDimByAssesseId(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context);

    Optional<AssesseDim> getAssesseDimByAssesseId(Long assesseeId);

    AssesseDim save(AssesseDim assesseDim);
}
