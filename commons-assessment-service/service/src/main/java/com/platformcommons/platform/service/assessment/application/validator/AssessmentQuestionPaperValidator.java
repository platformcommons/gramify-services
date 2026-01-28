package com.platformcommons.platform.service.assessment.application.validator;

import com.platformcommons.platform.service.assessment.domain.AssessmentQuestionPaper;

public interface AssessmentQuestionPaperValidator {
    void validateOnCreate(AssessmentQuestionPaper init);

    void validateOnUpdate(AssessmentQuestionPaper assessmentQuestionPaper);
}
