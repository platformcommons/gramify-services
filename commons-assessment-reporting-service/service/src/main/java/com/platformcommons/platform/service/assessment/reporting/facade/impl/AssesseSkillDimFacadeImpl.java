package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.AssesseSkillDimService;
import com.platformcommons.platform.service.assessment.reporting.facade.AssesseSkillDimFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AssesseSkillDimFacadeImpl implements AssesseSkillDimFacade {

    private final AssesseSkillDimService assesseSkillDimService;

    @Override
    public void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        assesseSkillDimService.createAssessmentInstanceAssesseEvent(assesseDTO,context);
    }

    @Override
    public void deleteDimByAssesssesId(Set<Long> collect) {
        assesseSkillDimService.deleteDimByAssesssesId(collect);
    }
}
