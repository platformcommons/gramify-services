package com.platformcommons.platform.service.assessment.application.validator;

import com.platformcommons.platform.service.assessment.domain.QuestionPaperSection;

import java.util.Set;

public interface QuestionPaperSectionValidator {

    void validateOnCreate(Set<QuestionPaperSection> sections);

    void validateOnUpdate(Set<QuestionPaperSection> questionpapersectionList);
}
