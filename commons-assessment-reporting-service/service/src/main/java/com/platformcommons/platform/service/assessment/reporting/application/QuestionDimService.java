package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentInstanceDim;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;

import java.util.List;
import java.util.Set;

public interface QuestionDimService {

    List<QuestionDim> createQuestionDim(Set<QuestionDim> questionDims);
    Set<QuestionDim> getByQuestionIds(Set<Long> distinctQuestionIds, AssessmentDTO language);

    void updateQuestionDim(QuestionDTO questionDim);

    void syncAssessmentData(AssessmentSyncContext syncContext);

    Set<QuestionDim> getByQuestionIds(Set<Long> distinctQuestionIds, AssessmentInstanceDim assessmentInstanceDim);

    QuestionDim getByQuestionId(Long questionId, String baseLanguage);

    void createParentLink(Set<QuestionDTO> questionDTOS);

    List<Long> getChildQuestions(Long questionId);

    void unlink(Long id);

    Set<QuestionDim> getChildQuestionsDimById(Long questionId);

    Set<QuestionDim> getByQuestionIds(Set<Long> distinctQuestionIds, Long assessmentId, String baseLanguage);
}
