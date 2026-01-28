package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;

public interface QuestionPaperSectionDimService {
    void assessmentQuestionPaperCreate(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    void assessmentInstanceDeleteEvent(Long id);

    void syncAssessmentData(AssessmentSyncContext syncContext);
}
