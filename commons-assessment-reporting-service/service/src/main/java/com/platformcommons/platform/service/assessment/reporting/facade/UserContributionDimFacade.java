package com.platformcommons.platform.service.assessment.reporting.facade;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;

public interface UserContributionDimFacade {

    void createQuestionContributions(QuestionDTO questionDTO);

    void createAssessmentContributions(AssessmentDTO assessmentDTO);
}
