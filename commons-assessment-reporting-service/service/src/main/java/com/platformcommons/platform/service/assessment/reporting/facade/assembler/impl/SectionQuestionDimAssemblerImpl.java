package com.platformcommons.platform.service.assessment.reporting.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionPaperSectionDTO;
import com.platformcommons.platform.service.assessment.dto.SectionQuestionsDTO;
import com.platformcommons.platform.service.assessment.reporting.application.constant.DimType;
import com.platformcommons.platform.service.assessment.reporting.domain.SectionQuestionDim;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.SectionQuestionDimAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SectionQuestionDimAssemblerImpl implements SectionQuestionDimAssembler {

    private final UtilityAssemblerImpl utilityAssembler;

    @Override
    public Set<SectionQuestionDim> assessmentQuestionPaperDTOToSectionQuestionDim(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        Set<SectionQuestionDim> dims = new LinkedHashSet<>();
        for (QuestionPaperSectionDTO questionPaperSectionDTO : assessmentQuestionPaperDTO.getQuestionPaperSectionList()) {
            for (SectionQuestionsDTO sectionQuestionsDTO : questionPaperSectionDTO.getSectionQuestionsList()) {
                dims.add(toQuestionPaperSectionDim(assessmentQuestionPaperDTO,questionPaperSectionDTO,sectionQuestionsDTO));
            }
        }
        return dims;
    }

    @Override
    public void updateSectionQuestion(SectionQuestionDim sectionQuestionDim, AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, SectionQuestionsDTO sectionQuestions) {
        sectionQuestionDim.setQuestionId(sectionQuestions.getQuestionId());
        sectionQuestionDim.setSequence(sectionQuestions.getOrderNo());
        sectionQuestionDim.setDescription(utilityAssembler.filterMLTexts(sectionQuestions.getSecQuestDesc(),utilityAssembler.getBaseLanguage(assessmentQuestionPaperDTO.getAssessment())));
        sectionQuestionDim.setTotalWeight(sectionQuestions.getWeight());
        sectionQuestionDim.setMandatoryQuestion(sectionQuestions.getIsMandatory());
        sectionQuestionDim.setLanguage(utilityAssembler.getBaseLanguage(assessmentQuestionPaperDTO.getAssessment()));
    }

    @Override
    public SectionQuestionDim toQuestionPaperSectionDim(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, QuestionPaperSectionDTO questionPaperSectionDTO, SectionQuestionsDTO sectionQuestion) {
        return SectionQuestionDim.builder()
                .sectionQuestionId(sectionQuestion.getId())
                .questionId(sectionQuestion.getQuestionId())
                .createdAt(sectionQuestion.getCreatedAt())
                .createdBy(sectionQuestion.getCreatedBy())
                .sequence(sectionQuestion.getOrderNo())
                .description(utilityAssembler.filterMLTexts(sectionQuestion.getSecQuestDesc(),assessmentQuestionPaperDTO.getAssessment().getBaseLanguage()))
                .totalWeight(sectionQuestion.getWeight())
                .mandatoryQuestion(sectionQuestion.getIsMandatory())
                .language(utilityAssembler.getBaseLanguage(assessmentQuestionPaperDTO.getAssessment()))
                .tenantId(sectionQuestion.getTenantId())
                .dimType(DimType.NONE)
                .questionPaperId(assessmentQuestionPaperDTO.getId())
                .questionPaperSectionId(questionPaperSectionDTO.getId())
                .assessmentId(assessmentQuestionPaperDTO.getAssessment().getId())
                .build();
    }
}
