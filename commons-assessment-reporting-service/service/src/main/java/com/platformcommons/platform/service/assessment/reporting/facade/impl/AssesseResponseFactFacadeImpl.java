package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.AssesseResponseFactService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.AssesseResponseFactFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssesseResponseFactFacadeImpl implements AssesseResponseFactFacade {
    private final AssesseResponseFactService assesseResponseFactService;
    @Override
    public void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        assesseResponseFactService.createAssessmentInstanceAssesseEvent(assesseDTO,context);
    }

    @Override
    public void deleteDimByAssessmentId(Long assessment) {
        assesseResponseFactService.deleteDimByAssessmentId(assessment);
    }

    @Override
    public void deleteDimByAssesseId(Long id) {
        assesseResponseFactService.deleteDimByAssesseId(id);
    }
}
