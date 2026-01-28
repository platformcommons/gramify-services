package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.SectionQuestionDim;

import java.util.List;
import java.util.Set;

public interface SectionQuestionDimService {
    void assessmentQuestionPaperCreate(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    Set<SectionQuestionDim> getByAssessmentId(Long assessmentId);

    void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    void assessmentInstanceDeleteEvent(Long id);

    void syncAssessmentData(AssessmentSyncContext syncContext);

    List<Long> getSequencedSectionQuestion(Set<Long> id);
}
