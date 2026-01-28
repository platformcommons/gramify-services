package com.platformcommons.platform.service.assessment.reporting.application.context.config;

import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.QuestionDimFacade;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfig {

    public ContextConfig(QuestionDimFacade questionDimFacade) {
        AssessmentReportSyncContext.setQuestionDimFacade(questionDimFacade);
    }

}
