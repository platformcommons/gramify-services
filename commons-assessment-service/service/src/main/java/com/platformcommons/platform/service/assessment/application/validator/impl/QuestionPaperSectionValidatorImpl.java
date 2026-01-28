package com.platformcommons.platform.service.assessment.application.validator.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.assessment.application.validator.CollectionsValidator;
import com.platformcommons.platform.service.assessment.application.validator.MLTextValidator;
import com.platformcommons.platform.service.assessment.application.validator.QuestionPaperSectionValidator;
import com.platformcommons.platform.service.assessment.application.validator.SectionQuestionValidator;
import com.platformcommons.platform.service.assessment.domain.QuestionPaperSection;
import com.platformcommons.platform.service.assessment.domain.SectionQuestions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionPaperSectionValidatorImpl implements QuestionPaperSectionValidator {

    private final SectionQuestionValidator sectionQuestionValidator;
    private final MLTextValidator mlTextValidator;
    private final CollectionsValidator collectionsValidator;


    @Override
    public void validateOnCreate(Set<QuestionPaperSection> sections) {
        validateState(sections);
        sections.stream().map(QuestionPaperSection::getSectionquestionsList).forEach(sectionQuestionValidator::validateOnCreate);
    }

    @Override
    public void validateOnUpdate(Set<QuestionPaperSection> sections) {
        validateState(sections);
        sections.stream().map(QuestionPaperSection::getSectionquestionsList).forEach(sectionQuestionValidator::validateOnUpdate);
    }

    private void validateState(Set<QuestionPaperSection> sections) {
        for (QuestionPaperSection section : sections) {
            validateOrderNoNotNull(section);
            validateQuestionPaperSectionName(section);
            validateQuestionPaperSectionDesc(section);
            validateSectionQuestionsNotNull(section);
            mlTextValidator.validate(section.getQpsName(),"Section Name");
            mlTextValidator.validateNonNull(section.getQpsName(),"Section Name");
        }
        validateQuestions(sections);
       // validateOrderNo(sections);
    }

    private void validateQuestions(Set<QuestionPaperSection> sections) {
        List<SectionQuestions> sectionQuestions = sections.stream().flatMap(section -> section.getSectionquestionsList().stream()).collect(Collectors.toList());
        if(sectionQuestions.stream().map(SectionQuestions::getQuestionId).distinct().count()!=sectionQuestions.size()){
            throw new InvalidInputException("Questions must be unique");
        }
    }

    private void validateSectionQuestionsNotNull(QuestionPaperSection section) {
        collectionsValidator.validateCollectionIsEmptyOrNull(section.getSectionquestionsList(),"Section Questions must be present");
    }

    private void validateQuestionPaperSectionDesc(QuestionPaperSection section) {
        collectionsValidator.validateCollectionIsEmptyOrNull(section.getQpsName(),"Question Paper Section Desc must be present");
    }

    private void validateQuestionPaperSectionName(QuestionPaperSection qpsName) {
        collectionsValidator.validateCollectionIsEmptyOrNull(qpsName.getQpsName(),"Question Paper Section name must be present");
    }

    private void validateOrderNoNotNull(QuestionPaperSection section){
        if(section.getOrderNo()==null){
            throw new InvalidInputException("QuestionPaper Section Order No must not be null");
        }
    }
    private void validateOrderNo(Set<QuestionPaperSection> sections) {
        if(sections.stream().map(QuestionPaperSection::getOrderNo).distinct().count() != sections.size()){
            throw new InvalidInputException("Order no for each section must be unique");
        }
    }


}
