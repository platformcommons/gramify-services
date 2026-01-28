package com.platformcommons.platform.service.assessment.application.validator.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.assessment.application.validator.QuestionDTOValidator;
import com.platformcommons.platform.service.assessment.application.validator.SectionQuestionValidator;
import com.platformcommons.platform.service.assessment.domain.SectionQuestions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SectionQuestionValidatorImpl implements SectionQuestionValidator {


    private final QuestionDTOValidator questionDTOValidator;

    @Override
    public void validateOnCreate(Set<SectionQuestions> sectionQuestionsList) {
        validateState(sectionQuestionsList);
    }

    @Override
    public void validateOnUpdate(Set<SectionQuestions> sectionQuestions) {
        validateState(sectionQuestions);
    }

    private void validateState(Set<SectionQuestions> sectionQuestionsList) {
        for (SectionQuestions sectionQuestions : sectionQuestionsList) {
            validateOrderNoNotNull(sectionQuestions);
            validateQuestion(sectionQuestions);
        }
        //validateOrderNo(sectionQuestionsList);
    }

    private void validateQuestion(SectionQuestions sectionQuestions) {
//        TODO: Validate soft Reference of question ?
//              questionDTOValidator.validate(sectionQuestions);
    }

    private void validateOrderNo(Set<SectionQuestions> sectionQuestionsList) {
        Set<Long> sectionQuestionOrderNoSet = new HashSet<>();
        sectionQuestionsList.forEach(section -> {
            if (sectionQuestionOrderNoSet.contains(section.getOrderNo())) {
                throw new InvalidInputException("Order no for each section question must be unique");
            }
            sectionQuestionOrderNoSet.add(section.getOrderNo());
        });
    }

    private void validateOrderNoNotNull(SectionQuestions sectionQuestions) {
        if(sectionQuestions.getOrderNo()==null){
            throw new InvalidInputException("QuestionPaper Section Order No must not be null");
        }
    }
}
