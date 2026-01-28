package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.reporting.application.SectionQuestionDimService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.SectionQuestionDim;
import com.platformcommons.platform.service.assessment.reporting.facade.SectionQuestionDimFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class SectionQuestionDimFacadeImpl implements SectionQuestionDimFacade {

    private final SectionQuestionDimService sectionQuestionDimService;
    @Override
    public void assessmentQuestionPaperCreate(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        sectionQuestionDimService.assessmentQuestionPaperCreate(assessmentQuestionPaperDTO);
    }

    @Override
    public Set<SectionQuestionDim> getByAssessmentId(Long assessmentId) {
        return sectionQuestionDimService.getByAssessmentId(assessmentId);
    }

    @Override
    public void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        sectionQuestionDimService.assessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO);
    }

    @Override
    public void assessmentInstanceDeleteEvent(Long id) {
        sectionQuestionDimService.assessmentInstanceDeleteEvent(id);
    }

    @Override
    public void syncAssessmentData(AssessmentSyncContext syncContext) {
        sectionQuestionDimService.syncAssessmentData(syncContext);
    }
}
