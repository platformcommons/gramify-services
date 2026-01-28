package com.platformcommons.platform.service.assessment.application.validator;


import com.platformcommons.platform.service.assessment.domain.SectionQuestions;

import java.util.Set;

public interface SectionQuestionValidator {

    void validateOnCreate(Set<SectionQuestions> sectionquestionsList);

    void validateOnUpdate(Set<SectionQuestions> sectionQuestions);
}
