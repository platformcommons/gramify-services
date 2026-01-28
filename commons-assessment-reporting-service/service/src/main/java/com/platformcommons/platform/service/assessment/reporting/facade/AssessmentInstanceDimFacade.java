package com.platformcommons.platform.service.assessment.reporting.facade;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;

import java.util.Set;

public interface AssessmentInstanceDimFacade {
    void assessmentCreateEvent(AssessmentDTO assessmentDTO);

    void assessmentInstanceCreateEvent(AssessmentInstanceDTO dto);

    void assessmentQuestionPaperCreate(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    AssessmentReportSyncContext getSyncContext(AssessmentDTO assessmentDTO, boolean cacheDisable);


    void assessmentUpdateEvent(AssessmentDTO assessmentDTO);

    void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    void assessmentInstanceUpdateEvent(AssessmentInstanceDTO assessmentInstanceDTO);

    void assessmentInstanceDeleteEvent(Long id);

    void syncAssessmentData(AssessmentSyncContext syncContext);

    Set<AssessmentReportSyncContext> getSyncContext(QuestionDTO questionDTO);

    void updateSyncContext(Long assessmentId);

    void updateSyncContext(QuestionDTO questionDTO);

    void cleanUpAssessmentData(Long assessmentId);

    AssessmentSyncContext getAssessmentContext(Long assessment);

    AssessmentInstanceDTO getAssessmentInstanceById(Long assessmentInstanceId);

    Set<AssessmentReportSyncContext> getAssessmentReportSyncContextByAssessmentInstanceIds(Set<Long> assessmentInstanceIds);
}
