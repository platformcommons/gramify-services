package com.platformcommons.platform.service.assessment.application.validator.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.assessment.application.validator.AssessmentQuestionPaperValidator;
import com.platformcommons.platform.service.assessment.application.validator.CollectionsValidator;
import com.platformcommons.platform.service.assessment.application.validator.MLTextValidator;
import com.platformcommons.platform.service.assessment.application.validator.QuestionPaperSectionValidator;
import com.platformcommons.platform.service.assessment.domain.AssessmentQuestionPaper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class AssessmentQuestionPaperValidatorImpl implements AssessmentQuestionPaperValidator {


    private final MLTextValidator mLtextValidator;
    private final QuestionPaperSectionValidator questionpaperSectionValidator;
    private final CollectionsValidator collectionsValidator;

    @Value("${commons.platform.service.commons-assessment-service.validation.question-paper.enable:true}")
    private boolean enableQuestionPaperValidation;

    @Override
    public void validateOnCreate(AssessmentQuestionPaper paper) {
        if(!enableQuestionPaperValidation) return;
        validateState(paper);
        questionpaperSectionValidator.validateOnCreate(paper.getQuestionpapersectionList());
    }

    @Override
    public void validateOnUpdate(AssessmentQuestionPaper paper) {
        if(!enableQuestionPaperValidation) return;
        validateState(paper);
        questionpaperSectionValidator.validateOnUpdate(paper.getQuestionpapersectionList());
    }

    private void validateState(AssessmentQuestionPaper paper) {
        assessmentNotNull(paper);
        assessmentQuestionPaperNameNotNull(paper);
     //   assessmentQuestionPaperDescNotNull(paper);
        assessmentQuestionPaperSectionNotNull(paper);
        mLtextValidator.validate(paper.getAqpName(), "Question Paper name");
        mLtextValidator.validateNonNull(paper.getAqpName(), "Question Paper desc");
    }



    private void assessmentQuestionPaperSectionNotNull(AssessmentQuestionPaper paper) {
        collectionsValidator.validateCollectionIsEmptyOrNull(
                paper.getQuestionpapersectionList()
                        .stream()
                        .filter(section -> Objects.isNull(section.getIsActive()) || Objects.equals(section.getIsActive(), Boolean.TRUE))
                        .collect(Collectors.toList()), "Question Paper Section are mandatory");
    }

    private void assessmentQuestionPaperDescNotNull(AssessmentQuestionPaper paper) {
        collectionsValidator.validateCollectionIsEmptyOrNull(paper.getAqpDesc(), "Question Paper must have description");
    }

    private void assessmentQuestionPaperNameNotNull(AssessmentQuestionPaper paper) {
        collectionsValidator.validateCollectionIsEmptyOrNull(paper.getAqpName(), "Question Paper must have Name");
    }

    private void assessmentNotNull(AssessmentQuestionPaper paper) {
        if (paper.getAssessment() == null || paper.getAssessment().getId() == null) {
            throw new InvalidInputException("Assessment is mandatory for AssessmentQuestionPaper");
        }
    }

}
