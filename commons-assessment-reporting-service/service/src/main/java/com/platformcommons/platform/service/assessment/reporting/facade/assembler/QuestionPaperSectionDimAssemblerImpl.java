package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionPaperSectionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.constant.DimType;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionPaperSectionDim;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionPaperSectionDimAssemblerImpl implements QuestionPaperSectionDimAssembler {

    @Autowired
    private final UtilityAssembler utilityAssembler;


    @Override
    public Set<QuestionPaperSectionDim> toQuestionPaperSectionDim(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        return assessmentQuestionPaperDTO
                        .getQuestionPaperSectionList()
                        .stream()
                        .map(questionPaperSectionDTO -> toQuestionPaperSectionDim(assessmentQuestionPaperDTO,questionPaperSectionDTO))
                        .collect(Collectors.toSet());
    }

    @Override
    public void updateQuestionPaperSection(QuestionPaperSectionDim questionPaperSectionDim, AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, QuestionPaperSectionDTO section) {
        questionPaperSectionDim.setSequence(section.getOrderNo());
        questionPaperSectionDim.setDescription(utilityAssembler.filterMLTexts(section.getQpsDesc(),utilityAssembler.getBaseLanguage(assessmentQuestionPaperDTO.getAssessment())));
        questionPaperSectionDim.setName(utilityAssembler.filterMLTexts(section.getQpsName(),utilityAssembler.getBaseLanguage(assessmentQuestionPaperDTO.getAssessment())));
        questionPaperSectionDim.setText(utilityAssembler.filterMLTexts(section.getQpsText(),utilityAssembler.getBaseLanguage(assessmentQuestionPaperDTO.getAssessment())));
        questionPaperSectionDim.setTotalWeight(utilityAssembler.getTotalWeight(section));
        questionPaperSectionDim.setTotalQuestions(Integer.toUnsignedLong(section.getSectionQuestionsList().size()));
        questionPaperSectionDim.setMandatoryQuestions(utilityAssembler.getMandatoryQuestion(section));
        questionPaperSectionDim.setLanguage(utilityAssembler.getBaseLanguage(assessmentQuestionPaperDTO.getAssessment()));
    }

    @Override
    public QuestionPaperSectionDim toQuestionPaperSectionDim(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, QuestionPaperSectionDTO section) {
        return QuestionPaperSectionDim.builder()
                .id(0L)
                .questionPaperId(assessmentQuestionPaperDTO.getId())
                .questionPaperSectionId(section.getId())
                .assessmentId(assessmentQuestionPaperDTO.getAssessment().getId())
                .createdAt(section.getCreatedAt())
                .createdBy(section.getCreatedBy())
                .sequence(section.getOrderNo())
                .description(utilityAssembler.filterMLTexts(section.getQpsDesc(),assessmentQuestionPaperDTO.getAssessment().getBaseLanguage()))
                .name(utilityAssembler.filterMLTexts(section.getQpsName(),assessmentQuestionPaperDTO.getAssessment().getBaseLanguage()))
                .text(utilityAssembler.filterMLTexts(section.getQpsText(),assessmentQuestionPaperDTO.getAssessment().getBaseLanguage()))
                .totalWeight(utilityAssembler.getTotalWeight(section))
                .totalQuestions(section.getTotalQuestions())
                .mandatoryQuestions(utilityAssembler.getMandatoryQuestion(section))
                .language(utilityAssembler.getBaseLanguage(assessmentQuestionPaperDTO.getAssessment()))
                .tenantId(section.getTenantId())
                .dimType(DimType.NONE)
                .build();
    }
}
