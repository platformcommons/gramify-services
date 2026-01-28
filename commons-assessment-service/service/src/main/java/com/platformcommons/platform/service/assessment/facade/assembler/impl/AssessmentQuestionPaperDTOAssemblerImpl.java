package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.domain.AssessmentQuestionPaper;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssessmentQuestionPaperDTOAssemblerImpl implements AssessmentQuestionPaperDTOAssembler {

    private final MimicMLTextDTOAssembler mLTextDTOAssembler;
    private final QuestionPaperSectionDTOAssembler questionPaperSectionDTOAssembler;
    private final AssessmentDTOAssembler assessmentDTOAssembler;

    @Override
    public AssessmentQuestionPaper fromDTO(AssessmentQuestionPaperDTO assessmentquestionpaperDTO) {
        if (assessmentquestionpaperDTO == null) return null;
        return AssessmentQuestionPaper.builder()
                .uuid(assessmentquestionpaperDTO.getUuid())
                .appCreatedTimestamp(assessmentquestionpaperDTO.getAppCreatedAt())
                .appLastModifiedTimestamp(assessmentquestionpaperDTO.getAppLastModifiedAt())
                .id(assessmentquestionpaperDTO.getId())
                .assessment((assessmentDTOAssembler.fromDTO(assessmentquestionpaperDTO.getAssessment())))
                .tenant(assessmentquestionpaperDTO.getTenant())
                .assessmentQuestionPaperCode(assessmentquestionpaperDTO.getAssessmentQuestionPaperCode())
                .aqpDesc(mLTextDTOAssembler.fromDTOs(assessmentquestionpaperDTO.getAqpDesc()))
                .aqpName(mLTextDTOAssembler.fromDTOs(assessmentquestionpaperDTO.getAqpName()))
                .questionpapersectionList(questionPaperSectionDTOAssembler.fromDTOs(assessmentquestionpaperDTO.getQuestionPaperSectionList()))
                .build();
    }

    @Override
    public AssessmentQuestionPaperDTO toDTO(AssessmentQuestionPaper assessmentQuestionPaper) {
        if (assessmentQuestionPaper == null) return null;
        return AssessmentQuestionPaperDTO.builder()
                .createdAt(assessmentQuestionPaper.getCreatedTimestamp())
                .createdBy(assessmentQuestionPaper.getCreatedByUser())
                .tenantId(assessmentQuestionPaper.getTenantId())
                .uuid(assessmentQuestionPaper.getUuid())
                .appCreatedAt(assessmentQuestionPaper.getAppCreatedTimestamp())
                .appLastModifiedAt(assessmentQuestionPaper.getAppLastModifiedTimestamp())
                .tenant(assessmentQuestionPaper.getTenantId())
                .id(assessmentQuestionPaper.getId())
                .tenant(assessmentQuestionPaper.getTenant())
                .assessmentQuestionPaperCode(assessmentQuestionPaper.getAssessmentQuestionPaperCode())
                .aqpDesc(mLTextDTOAssembler.toDTOs(assessmentQuestionPaper.getAqpDesc()))
                .aqpName(mLTextDTOAssembler.toDTOs(assessmentQuestionPaper.getAqpName()))
                .assessment(assessmentDTOAssembler.toDTO(assessmentQuestionPaper.getAssessment()))
                .questionPaperSectionList(questionPaperSectionDTOAssembler.toDTOs(assessmentQuestionPaper.getQuestionpapersectionList()))
                .duplicatedCount(assessmentQuestionPaper.getDuplicatedCount())
                .duplicatedFrom(assessmentQuestionPaper.getDuplicatedFrom())
                .build();
    }


}
