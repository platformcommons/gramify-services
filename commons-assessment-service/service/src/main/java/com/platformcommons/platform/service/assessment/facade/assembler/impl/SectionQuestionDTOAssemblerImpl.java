package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.domain.SectionQuestions;
import com.platformcommons.platform.service.assessment.dto.SectionQuestionsDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicMLTextDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.SectionQuestionsDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class SectionQuestionDTOAssemblerImpl implements SectionQuestionsDTOAssembler {

    private final MimicMLTextDTOAssembler mLTextDTOAssembler;

    @Override
    public SectionQuestions fromDTO(SectionQuestionsDTO sectionQuestions) {
        if (sectionQuestions == null) return null;
        return SectionQuestions.builder()
                .isActive(sectionQuestions.getIsActive())
                .uuid(sectionQuestions.getUuid())
                .appCreatedTimestamp(sectionQuestions.getAppCreatedAt())
                .appLastModifiedTimestamp(sectionQuestions.getAppLastModifiedAt())
                .id(sectionQuestions.getId())
                .questionId(sectionQuestions.getQuestionId())
                .questionCode(sectionQuestions.getQuestionCode())
                .tenant(sectionQuestions.getTenant())
                .questionNumber(sectionQuestions.getQuestionNumber() != null ? sectionQuestions.getQuestionNumber() : null)
                .orderNo(sectionQuestions.getOrderNo())
                .sectionQuestionMeta(sectionQuestions.getSectionQuestionMeta())
                .isMandatory(sectionQuestions.getIsMandatory() != null ? sectionQuestions.getIsMandatory() : true)
                .build();
    }

    @Override
    public Set<SectionQuestionsDTO> toDTOs(Set<SectionQuestions> sectionquestionsList) {
        if(CollectionUtils.isEmpty(sectionquestionsList)) return new HashSet<>();
        return sectionquestionsList.stream()
                .map(this::toDTO)
                .sorted((sectionQuestions1, sectionQuestions2) -> {
                    if (sectionQuestions1.getOrderNo() == null || sectionQuestions2.getOrderNo() == null) return 0;
                    return sectionQuestions1.getOrderNo().compareTo(sectionQuestions2.getOrderNo());
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public SectionQuestionsDTO toDTO(SectionQuestions sectionQuestions) {
        if (sectionQuestions == null) return null;
        return SectionQuestionsDTO.builder()
                .createdAt(sectionQuestions.getCreatedTimestamp())
                .createdBy(sectionQuestions.getCreatedByUser())
                .tenantId(sectionQuestions.getTenantId())
                .uuid(sectionQuestions.getUuid())
                .appCreatedAt(sectionQuestions.getAppCreatedTimestamp())
                .appLastModifiedAt(sectionQuestions.getAppLastModifiedTimestamp())
                .tenant(sectionQuestions.getTenantId())
                .id(sectionQuestions.getId())
                .tenant(sectionQuestions.getTenant())
                .questionNumber(sectionQuestions.getQuestionNumber() != null ? sectionQuestions.getQuestionNumber() : null)
                .questionId(sectionQuestions.getQuestionId())
                .questionCode(sectionQuestions.getQuestionCode())
                .orderNo(sectionQuestions.getOrderNo())
                .isActive(sectionQuestions.getIsActive())
                .isMandatory(sectionQuestions.getIsMandatory())
                .sectionQuestionMeta(sectionQuestions.getSectionQuestionMeta())
                .build();
    }
}
