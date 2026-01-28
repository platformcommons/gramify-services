package com.platformcommons.platform.service.assessment.reporting.facade;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;

public interface SkillFactFacade {
    void assessmentInstanceEvent(AssessmentInstanceDTO dto, AssessmentReportSyncContext context, PlatformToken platformToken);

    void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, AssessmentReportSyncContext syncContext);

    void assessmentInstanceDeleteEvent(Long id);

    void syncAssessmentData(AssessmentInstanceDTO assessmentInstanceDTO, AssessmentReportSyncContext reportSyncContext, PlatformToken context);

    void questionUpdateEvent(QuestionDTO questionDTO, AssessmentReportSyncContext syncContext);
}
