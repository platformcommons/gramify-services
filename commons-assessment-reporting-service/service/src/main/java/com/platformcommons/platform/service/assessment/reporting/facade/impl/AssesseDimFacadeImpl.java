package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.AssesseDimService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.AssesseDimFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AssesseDimFacadeImpl implements AssesseDimFacade {

    private final AssesseDimService service;

    @Override
    public void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        service.createAssessmentInstanceAssesseEvent(assesseDTO, context);
    }

    @Override
    public boolean responseExist(Long id) {
        return service.responseExist(id);
    }

    @Override
    public void deleteDimByAssessmentId(Long assessment) {
        service.deleteDimByAssessmentId(assessment);
    }

    @Override
    public String getUserIdsForInstance(Set<Long> ids) {
        return service.getUserIdsForInstance(ids);
    }

    @Override
    public void deleteDimByAssesseId(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        service.deleteDimByAssesseId(assesseDTO, context);
    }
}
