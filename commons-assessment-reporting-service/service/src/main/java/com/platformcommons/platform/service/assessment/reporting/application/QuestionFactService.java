package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;

import java.util.Set;

public interface QuestionFactService {

    void assessmentInstanceEvent(AssessmentInstanceDTO dto, AssessmentReportSyncContext context);

    void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context);

    void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, AssessmentReportSyncContext syncContext);

    void assessmentInstanceDeleteEvent(Long id);

    void syncAssessmentData(AssessmentInstanceDTO assessmentInstanceDTO, AssessmentReportSyncContext reportSyncContext);

    Set<Long> getAssessmentIds(QuestionDTO questionDTO);

    void questionUpdateEvent(QuestionDTO questionDTO, AssessmentReportSyncContext syncContext);
}
