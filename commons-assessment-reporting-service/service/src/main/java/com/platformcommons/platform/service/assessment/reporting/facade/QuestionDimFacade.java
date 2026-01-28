package com.platformcommons.platform.service.assessment.reporting.facade;


import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;

import java.util.Set;

public interface QuestionDimFacade {

    void createQuestionDim(QuestionDTO questionDTO);

    void updateQuestionDim(QuestionDTO questionDTO);

    Set<QuestionDim> getByQuestionIds(Set<Long> distinctQuestionIds, AssessmentDTO assessment);

    void syncAssessmentData(AssessmentSyncContext syncContext);

    QuestionDimDTO getByQuestionId(Long questionId, String baseLanguage);

    void unlink(Long id);
}
