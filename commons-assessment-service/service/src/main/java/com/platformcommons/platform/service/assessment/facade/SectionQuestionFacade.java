package com.platformcommons.platform.service.assessment.facade;

import com.platformcommons.platform.service.assessment.dto.QuestionContextCacheDTO;

import java.util.Map;

public interface SectionQuestionFacade {
    Map<Long, QuestionContextCacheDTO> getSectionQuestions(Long assessmentInstanceId);
}
