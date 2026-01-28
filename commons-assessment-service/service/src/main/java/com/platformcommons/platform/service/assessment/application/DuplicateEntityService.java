package com.platformcommons.platform.service.assessment.application;

import com.platformcommons.platform.service.assessment.domain.Question;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;

public interface DuplicateEntityService {
    AssessmentInstanceDTO duplicateAssessmentInstance(Long assessmentInstance, String name);

    Question duplicateQuestion(Long questionId);
}
