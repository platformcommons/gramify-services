package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.reporting.application.QuestionPaperSectionDimService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.QuestionPaperSectionDimFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionPaperSectionDimFacadeImpl implements QuestionPaperSectionDimFacade {

    private final QuestionPaperSectionDimService questionPaperSectionDimService;

    @Override
    public void assessmentQuestionPaperCreate(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        questionPaperSectionDimService.assessmentQuestionPaperCreate(assessmentQuestionPaperDTO);
    }

    @Override
    public void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        questionPaperSectionDimService.assessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO);
    }

    @Override
    public void assessmentInstanceDeleteEvent(Long id) {
        questionPaperSectionDimService.assessmentInstanceDeleteEvent(id);
    }

    @Override
    public void syncAssessmentData(AssessmentSyncContext syncContext) {
        questionPaperSectionDimService.syncAssessmentData(syncContext);
    }
}
