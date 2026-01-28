package com.platformcommons.platform.service.assessment.reporting.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.constant.DimType;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.OptionFact;
import com.platformcommons.platform.service.assessment.reporting.dto.OptionsDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.SectionQuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.OptionFactAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.UtilityAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OptionFactAssemblerImpl implements OptionFactAssembler {


    @Override
    public Set<OptionFact> toOptionFacts(AssessmentReportSyncContext context) {


        List<OptionFact> facts = new ArrayList<>();
        MultiValueMap<Long, OptionsDimDTO> optionDimMap = context.getOptionsDims()
                .stream()
                .collect(Collectors.toMap(OptionsDimDTO::getQuestionId, optionsDimDTO -> {
                    List<OptionsDimDTO> optionsDims = new ArrayList<>();
                    optionsDims.add(optionsDimDTO);
                    return optionsDims;
                }, UtilityAssembler.getOperator(), LinkedMultiValueMap::new));
        String baseLang = context.getAssessmentInstanceDim().getBaseLanguage();
        Long assessmentInstanceId = context.getAssessmentInstanceDim().getAssessmentInstanceId();
        Long assessmentId = context.getAssessmentInstanceDim().getAssessmentId();
        Long assessmentQuestionPaperId = context.getAssessmentInstanceDim().getQuestionPaperId();

        for (SectionQuestionDimDTO sectionQuestionDim : context.getSectionQuestionDims()) {
            List<OptionsDimDTO> optionsDimDTOS = optionDimMap.get(sectionQuestionDim.getQuestionId());
            if (optionsDimDTOS == null) continue;
            List<OptionsDimDTO> optionsDimDTOList = optionsDimDTOS.stream().filter(optionsDimDTO -> optionsDimDTO.getLanguage().equals(baseLang)).collect(Collectors.toList());
            for (OptionsDimDTO optionsDim : optionsDimDTOList) {
                facts.add(OptionFact.builder()
                        .id(0L)
                        .assessmentInstanceId(assessmentInstanceId)
                        .assessmentId(assessmentId)
                        .questionPaperId(assessmentQuestionPaperId)
                        .questionPaperSectionId(sectionQuestionDim.getQuestionPaperSectionId())
                        .sectionQuestionId(sectionQuestionDim.getSectionQuestionId())
                        .questionId(optionsDim.getQuestionId())
                        .defaultOptionId(optionsDim.getDefaultOptionId())
                        .optionsId(optionsDim.getOptionsId())
                        .tenantId(optionsDim.getTenantId())
                        .parentQuestionId(optionsDim.getParentQuestionId())
                        .childDefaultOptionId(optionsDim.getChildDefaultOptionId())
                        .mtfOptionId(optionsDim.getMtfOptionId())
                        .responseCount(0L)
                        .dimType(DimType.NONE)
                        .build());
            }
            OptionFact dummy = facts.isEmpty() ? null : facts.get(0).duplicate();
            if(dummy!=null) getChildOptionFact(facts, dummy, context, sectionQuestionDim.getQuestionId());

        }
        return new HashSet<>(facts);
    }

    private void getChildOptionFact(List<OptionFact> facts, OptionFact dummy, AssessmentReportSyncContext context, Long questionId) {
        context.getChildOptions(questionId)
                .forEach(optionsDimDTO -> {
            OptionFact optionFact = dummy.duplicate();
            optionFact.setOptionsId(optionsDimDTO.getOptionsId());
            optionFact.setMtfOptionId(optionsDimDTO.getMtfOptionId());
            optionFact.setDefaultOptionId(optionsDimDTO.getDefaultOptionId());
            optionFact.setQuestionId(optionsDimDTO.getQuestionId());
            optionFact.setParentQuestionId(optionsDimDTO.getParentQuestionId());
            optionFact.setChildDefaultOptionId(optionsDimDTO.getChildDefaultOptionId());
            facts.add(optionFact);
        });
        context.getChildQuestions(questionId)
                .forEach(questionDimDTO -> getChildOptionFact(facts,dummy,context,questionDimDTO.getQuestionId()));
    }

    @Override
    public Set<OptionFact> toOptionFacts(QuestionDTO questionDTO, AssessmentReportSyncContext context) {
        List<OptionFact> facts = new ArrayList<>();

        List<OptionsDimDTO> s = context.getOptionsDims()
                .stream()
                .filter(optionsDimDTO -> optionsDimDTO.getQuestionId().equals(questionDTO.getId()))
                .collect(Collectors.toList());

        Long assessmentInstanceId = context.getAssessmentInstanceDim().getAssessmentInstanceId();
        Long assessmentId = context.getAssessmentInstanceDim().getAssessmentId();
        Long assessmentQuestionPaperId = context.getAssessmentInstanceDim().getQuestionPaperId();

        for (SectionQuestionDimDTO sectionQuestionDim : context.getSectionQuestionDims()
                .stream()
                .filter(sectionQuestionDimDTO -> sectionQuestionDimDTO.getQuestionId()
                        .equals(questionDTO.getId()))
                .collect(Collectors.toSet())
        ) {
            for (OptionsDimDTO optionsDim : s) {
                facts.add(OptionFact.builder()
                        .id(0L)
                        .assessmentInstanceId(assessmentInstanceId)
                        .assessmentId(assessmentId)
                        .questionPaperId(assessmentQuestionPaperId)
                        .questionPaperSectionId(sectionQuestionDim.getQuestionPaperSectionId())
                        .sectionQuestionId(sectionQuestionDim.getSectionQuestionId())
                        .questionId(optionsDim.getQuestionId())
                        .defaultOptionId(optionsDim.getDefaultOptionId())
                        .optionsId(optionsDim.getOptionsId())
                        .tenantId(optionsDim.getTenantId())
                        .responseCount(0L)
                        .dimType(DimType.NONE)
                        .build());
            }
            OptionFact dummy = facts.isEmpty() ? null : facts.get(0).duplicate();
            if(dummy!=null)
                context.getChildQuestions(questionDTO.getId())
                    .forEach(questionDimDTO -> getChildOptionFact(facts,dummy,context,questionDimDTO.getQuestionId()));
        }
        return new HashSet<>(facts);
    }

}
