package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.domain.QuestionPaperSection;
import com.platformcommons.platform.service.assessment.dto.QuestionPaperSectionDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicMLTextDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.QuestionPaperSectionDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.SectionQuestionsDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
/*
   Removed Question Text as its unused
   Removed total marks, total question, total question to be answered from the payload
*/
public class QuestionPaperSectionDTOAssemblerImpl implements QuestionPaperSectionDTOAssembler {


    private final MimicMLTextDTOAssembler mLTextDTOAssembler;
    private final SectionQuestionsDTOAssembler sectionQuestionsDTOAssembler;


    @Override
    public QuestionPaperSection fromDTO(QuestionPaperSectionDTO questionPaperSectionDTO) {
        if (questionPaperSectionDTO == null) {
            return null;
        }
        return QuestionPaperSection.builder()
                .isActive(questionPaperSectionDTO.getIsActive())
                .uuid(questionPaperSectionDTO.getUuid())
                .appCreatedTimestamp(questionPaperSectionDTO.getAppCreatedAt())
                .appLastModifiedTimestamp(questionPaperSectionDTO.getAppLastModifiedAt())
                .id(questionPaperSectionDTO.getId())
                .tenant(questionPaperSectionDTO.getTenant())
                .orderNo(questionPaperSectionDTO.getOrderNo())
                .sectionDesc(questionPaperSectionDTO.getSectionDesc())
                .qpsDesc(mLTextDTOAssembler.fromDTOs(questionPaperSectionDTO.getQpsDesc()))
                .qpsName(mLTextDTOAssembler.fromDTOs(questionPaperSectionDTO.getQpsName()))
                .sectionquestionsList(questionPaperSectionDTO.getSectionQuestionsList() != null ?
                        questionPaperSectionDTO.getSectionQuestionsList().stream()
                                .map(sectionQuestionsDTOAssembler::fromDTO)
                                .collect(Collectors.toCollection(LinkedHashSet::new)) : null)
                .build();
    }

    @Override
    public Set<QuestionPaperSection> fromDTOs(Set<QuestionPaperSectionDTO> questionPaperSectionList) {
        return questionPaperSectionList!=null?questionPaperSectionList.stream().map(this::fromDTO).collect(Collectors.toCollection(LinkedHashSet::new)):null;
    }

    @Override
    public Set<QuestionPaperSectionDTO> toDTOs(Set<QuestionPaperSection> questionpapersectionList) {
        return questionpapersectionList!=null?questionpapersectionList.stream()
                .map(this::toDTO)
                .sorted((questionPaperSection1, questionPaperSection2) -> {
                    if(questionPaperSection1.getOrderNo()==null || questionPaperSection2.getOrderNo()==null) return 0;
                    return questionPaperSection1.getOrderNo().compareTo(questionPaperSection2.getOrderNo());
                })
                .collect(Collectors.toCollection(LinkedHashSet::new)):null;
    }

    @Override
    public QuestionPaperSectionDTO toDTO(QuestionPaperSection questionpapersection) {
        if (questionpapersection == null) {
            return null;
        }
        return QuestionPaperSectionDTO.builder()
                .id(questionpapersection.getId())
                .isActive(questionpapersection.getIsActive())
                .createdAt(questionpapersection.getCreatedTimestamp())
                .createdBy(questionpapersection.getCreatedByUser())
                .tenantId(questionpapersection.getTenantId())
                .uuid(questionpapersection.getUuid())
                .appCreatedAt(questionpapersection.getAppCreatedTimestamp())
                .appLastModifiedAt(questionpapersection.getAppLastModifiedTimestamp())
                .tenant(questionpapersection.getTenantId())
                .orderNo(questionpapersection.getOrderNo())
                .qpsDesc(mLTextDTOAssembler.toDTOs(questionpapersection.getQpsDesc()))
                .qpsName(mLTextDTOAssembler.toDTOs(questionpapersection.getQpsName()))
                .tenant(questionpapersection.getTenant())
                .sectionDesc(questionpapersection.getSectionDesc())
                .sectionQuestionsList(sectionQuestionsDTOAssembler.toDTOs(questionpapersection.getSectionquestionsList()))
                // Service managed
                .totalMarks(questionpapersection.getTotalMarks())
                .totalQuestions(questionpapersection.getTotalQuestions())
                .totalQuestionsToBeAnswered(questionpapersection.getTotalQuestionsToBeAnswered())
                .build();
    }
}

