package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentInstanceDim;

import java.util.Set;

public interface AssessmentInstanceDimService {
    void assessmentCreateEvent(AssessmentInstanceDim assessmentInstanceDim);

    void assessmentInstanceCreateEvent(AssessmentInstanceDTO assessmentInstanceDim);

    void assessmentQuestionPaperCreate(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    AssessmentInstanceDim getByAssessmentId(Long id);

    AssessmentReportSyncContext getSyncContext(AssessmentDTO assessmentDTO, boolean cacheDisable);
    void updateSyncContext(Long assessmentId);
    void assessmentUpdateEvent(AssessmentDTO assessmentDTO);

    void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    void assessmentInstanceUpdateEvent(AssessmentInstanceDTO assessmentInstanceDTO);

    void assessmentInstanceDeleteEvent(Long id);

    void syncAssessmentData(AssessmentSyncContext syncContext);

    Set<AssessmentReportSyncContext> getSyncContext(QuestionDTO questionDTO);

    AssessmentReportSyncContext getSyncContext(Long assessmentId);

    void updateSyncContext(QuestionDTO questionDTO);

    Set<AssessmentReportSyncContext> getAssessmentReportSyncContextByAssessmentInstanceIds(Set<Long> assessmentInstanceIds);
}
