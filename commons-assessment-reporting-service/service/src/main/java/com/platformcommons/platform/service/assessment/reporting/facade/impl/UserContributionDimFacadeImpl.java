package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.UserContributionDimService;
import com.platformcommons.platform.service.assessment.reporting.facade.UserContributionDimFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserContributionDimFacadeImpl implements UserContributionDimFacade {

    private final UserContributionDimService service;

    @Override
    public void createQuestionContributions(QuestionDTO questionDTO) {
        service.createQuestionContributions(questionDTO);
    }

    @Override
    public void createAssessmentContributions(AssessmentDTO assessmentDTO) {
        service.createAssessmentContributions(assessmentDTO);
    }

}
