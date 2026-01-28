package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;

public interface SkillFactService {
    void assessmentInstanceEvent(AssessmentInstanceDTO dto, AssessmentReportSyncContext context);

    void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, AssessmentReportSyncContext syncContext);

    void assessmentInstanceDeleteEvent(Long id);

    void syncAssessmentData(AssessmentInstanceDTO assessmentInstanceDTO, AssessmentReportSyncContext reportSyncContext);

    void questionUpdateEvent(QuestionDTO questionDTO, AssessmentReportSyncContext syncContext);
}
