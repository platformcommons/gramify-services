package com.platformcommons.platform.service.assessment.reporting.facade;


import com.platformcommons.platform.service.assessment.dto.CachedSectionQuestionResponse;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;

import java.util.Map;

public interface OptionsDimFacade {

    void createOptionDim(QuestionDTO questionDTO);

    void updateOptionDim(QuestionDTO questionDTO);

    void syncAssessmentData(AssessmentSyncContext syncContext);

    Map<Long, CachedSectionQuestionResponse> getOptionResponseByAssessmentId(Long assessmentInstanceID);

    void unlink(Long id);
}
