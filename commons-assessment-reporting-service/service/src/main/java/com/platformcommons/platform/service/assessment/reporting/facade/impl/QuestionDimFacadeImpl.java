package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.QuestionDimService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.QuestionDimFacade;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.QuestionDimAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class QuestionDimFacadeImpl implements QuestionDimFacade {

    private final QuestionDimAssembler questionDimAssembler;

    private final QuestionDimService questionDimService;

    @Override
    @Transactional
    public void createQuestionDim(QuestionDTO questionDTO) {
        questionDimService.createQuestionDim(questionDimAssembler.toQuestionDim(questionDTO));
        questionDimService.createParentLink(Collections.singleton(questionDTO));
    }

    @Override
    public void updateQuestionDim(QuestionDTO questionDTO) {
        questionDimService.updateQuestionDim(questionDTO);
        questionDimService.createParentLink(Collections.singleton(questionDTO));
    }


    @Override
    public Set<QuestionDim> getByQuestionIds(Set<Long> distinctQuestionIds, AssessmentDTO assessment) {
        return questionDimService.getByQuestionIds(distinctQuestionIds,assessment);
    }

    @Override
    public void syncAssessmentData(AssessmentSyncContext syncContext) {
        questionDimService.syncAssessmentData(syncContext);
    }

    @Override
    public QuestionDimDTO getByQuestionId(Long questionId, String baseLanguage) {
        return questionDimAssembler.toDTO(questionDimService.getByQuestionId(questionId,baseLanguage));
    }

    @Override
    public void unlink(Long id) {
        questionDimService.unlink(id);
    }

}
