package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;

public interface UserContributionDimService {
    void createQuestionContributions(QuestionDTO questionDTO);

    void createAssessmentContributions(AssessmentDTO assessmentDTO);
}
