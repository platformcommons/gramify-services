package com.platformcommons.platform.service.assessment.application.validator;


import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAssesse;


public interface AssessmentInstanceAssesseValidator {


    void validateOnUpdate(AssessmentInstanceAssesse existingAssesse);

    void validateOnCreate(AssessmentInstanceAssesse assessmentInstanceAssesse);
}
