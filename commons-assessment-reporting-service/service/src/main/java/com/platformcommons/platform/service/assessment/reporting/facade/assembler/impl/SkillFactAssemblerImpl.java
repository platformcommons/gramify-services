package com.platformcommons.platform.service.assessment.reporting.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.constant.DimType;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.SkillFact;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.SectionQuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.SkillFactAssembler;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SkillFactAssemblerImpl implements SkillFactAssembler {


    @Override
    public Set<SkillFact> getSkillFacts(AssessmentReportSyncContext context) {

        Set<SkillFact> skillFacts = new HashSet<>();
        Map<Long, QuestionDimDTO> questionDimMap=context.getQuestionDimsMap();


        Long assessmentId = context.getAssessmentInstanceDim().getAssessmentId();
        Long assessmentInstanceId = context.getAssessmentInstanceDim().getAssessmentInstanceId();
        Long questionPaperId = context.getAssessmentInstanceDim().getQuestionPaperId();
        for (SectionQuestionDimDTO sectionQuestionDim : context.getSectionQuestionDims()) {
            Long questionPaperSectionId = sectionQuestionDim.getQuestionPaperSectionId();
            Long sectionQuestionId = sectionQuestionDim.getSectionQuestionId();
            Long questionId = sectionQuestionDim.getQuestionId();

            QuestionDimDTO questionDim=questionDimMap.get(sectionQuestionDim.getQuestionId());
            if(questionDim==null||questionDim.getSkillIds()==null) continue;
            for (Long skillId : questionDim.getSkillIds()) {
                skillFacts.add(SkillFact.builder()
                                .id(0L)
                                .dimType(DimType.NONE)
                                .skillId(skillId)
                                .assessmentId(assessmentId)
                                .assessmentInstanceId(assessmentInstanceId)
                                .questionPaperId(questionPaperId)
                                .questionPaperSectionId(questionPaperSectionId)
                                .sectionQuestionId(sectionQuestionId)
                                .questionId(questionId)
                                .totalWeightage(sectionQuestionDim.getTotalWeight())
                                .build());
            }
        }

        return skillFacts;
    }

    @Override
    public Set<SkillFact> getSkillFacts(QuestionDTO questionDTO, AssessmentReportSyncContext context) {

        Set<SkillFact> skillFacts = new HashSet<>();
        Map<Long, QuestionDimDTO> questionDimMap=context.getQuestionDimsMap();
        QuestionDimDTO questionDim=questionDimMap.get(questionDTO.getId());

        Long assessmentId = context.getAssessmentInstanceDim().getAssessmentId();
        Long assessmentInstanceId = context.getAssessmentInstanceDim().getAssessmentInstanceId();
        Long questionPaperId = context.getAssessmentInstanceDim().getQuestionPaperId();

        for (SectionQuestionDimDTO sectionQuestionDim : context.getSectionQuestionDims()
                                                               .stream()
                                                               .filter(sectionQuestionDimDTO -> sectionQuestionDimDTO.getQuestionId().equals(questionDTO.getId()))
                                                               .collect(Collectors.toSet())
        ) {
            Long questionPaperSectionId = sectionQuestionDim.getQuestionPaperSectionId();
            Long sectionQuestionId = sectionQuestionDim.getSectionQuestionId();
            Long questionId = sectionQuestionDim.getQuestionId();

            if(questionDim==null||questionDim.getSkillIds()==null) continue;
            for (Long skillId : questionDim.getSkillIds()) {
                skillFacts.add(SkillFact.builder()
                        .id(0L)
                        .dimType(DimType.NONE)
                        .skillId(skillId)
                        .assessmentId(assessmentId)
                        .assessmentInstanceId(assessmentInstanceId)
                        .questionPaperId(questionPaperId)
                        .questionPaperSectionId(questionPaperSectionId)
                        .sectionQuestionId(sectionQuestionId)
                        .questionId(questionId)
                        .totalWeightage(sectionQuestionDim.getTotalWeight())
                        .build());
            }
        }

        return skillFacts;
    }

}
