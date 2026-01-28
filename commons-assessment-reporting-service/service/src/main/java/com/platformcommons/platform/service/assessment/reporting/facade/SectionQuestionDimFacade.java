package com.platformcommons.platform.service.assessment.reporting.facade;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.SectionQuestionDim;

import java.util.Set;

public interface SectionQuestionDimFacade {
    void assessmentQuestionPaperCreate(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    Set<SectionQuestionDim> getByAssessmentId(Long assessmentId);

    void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    void assessmentInstanceDeleteEvent(Long id);

    void syncAssessmentData(AssessmentSyncContext syncContext);
}
