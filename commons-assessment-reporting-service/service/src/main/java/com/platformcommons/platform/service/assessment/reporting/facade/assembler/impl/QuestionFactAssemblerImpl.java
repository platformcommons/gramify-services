package com.platformcommons.platform.service.assessment.reporting.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.reporting.application.constant.ApplicationConstants;
import com.platformcommons.platform.service.assessment.reporting.application.constant.DimType;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionFact;
import com.platformcommons.platform.service.assessment.reporting.dto.AssessmentInstanceDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.SectionQuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.QuestionFactAssembler;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
@Component
public class QuestionFactAssemblerImpl implements QuestionFactAssembler {

    @Override
    public Set<QuestionFact> toQuestionFactAssembler(AssessmentReportSyncContext context) {

        Set<QuestionFact> questionFacts = new HashSet<>();
        Set<SectionQuestionDimDTO> sectionQuestionDims = context.getSectionQuestionDims();
        AssessmentInstanceDimDTO assessmentInstanceDim = context.getAssessmentInstanceDim();
        Map<Long, QuestionDimDTO> questionDimMap = context.getQuestionDimsMap();
        for (SectionQuestionDimDTO sectionQuestionDim : sectionQuestionDims) {
            QuestionDimDTO questionDim = questionDimMap.get(sectionQuestionDim.getQuestionId());
            questionFacts.add(toQuestionFactAssembler(sectionQuestionDim,questionDim,assessmentInstanceDim));
            Set<QuestionDimDTO> childQuestionDims = context.getChildQuestions(questionDim.getQuestionId());
            addChildQuestionDim(questionFacts,context,childQuestionDims,sectionQuestionDim,assessmentInstanceDim);
        }
        return questionFacts;
    }

    private void addChildQuestionDim(Set<QuestionFact> questionFacts, AssessmentReportSyncContext context, Set<QuestionDimDTO> questionDims, SectionQuestionDimDTO sectionQuestionDim, AssessmentInstanceDimDTO assessmentInstanceDim) {
        if(questionDims==null || questionDims.isEmpty()) return;
        for (QuestionDimDTO childQuestionDim : questionDims) {
            Set<QuestionDimDTO> childQuestionDims = context.getChildQuestions(childQuestionDim.getQuestionId());
            questionFacts.add(toQuestionFactAssembler(sectionQuestionDim,childQuestionDim,assessmentInstanceDim));
            addChildQuestionDim(questionFacts,context,childQuestionDims, sectionQuestionDim, assessmentInstanceDim);
        }
    }

    @Override
    public QuestionFact toQuestionFactAssembler(SectionQuestionDimDTO sectionQuestionDim, QuestionDimDTO questionDim, AssessmentInstanceDimDTO assessmentInstanceDim) {
        return QuestionFact.builder()
                .id(0L)
                .assessmentId(assessmentInstanceDim.getAssessmentId())
                .assessmentInstanceId(assessmentInstanceDim.getAssessmentInstanceId())
                .assessmentQuestionPaperId(assessmentInstanceDim.getQuestionPaperId())
                .questionPaperSectionId(sectionQuestionDim.getQuestionPaperSectionId())
                .sectionQuestionId(sectionQuestionDim.getSectionQuestionId())
                .questionId(questionDim.getQuestionId())
                .parentQuestionId(questionDim.getParentQuestionId())
                .childDefaultOptionId(questionDim.getChildDefaultOptionId())
                .questionSkills(questionDim.getSkillIds().stream().map(String::valueOf).collect(Collectors.joining(ApplicationConstants.PRIMARY_DELIMITER)))
                .tenantId(assessmentInstanceDim.getTenantId())
                .respondedCount(0L)
                .correctCount(0L)
                .incorrectCount(0L)
                .skippedCount(0L)
                .dimType(DimType.NONE)
                .build();
    }
}
