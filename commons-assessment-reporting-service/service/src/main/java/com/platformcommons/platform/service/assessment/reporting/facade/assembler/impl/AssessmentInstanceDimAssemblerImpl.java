package com.platformcommons.platform.service.assessment.reporting.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.reporting.application.constant.DimType;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentInstanceDim;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssessmentInstanceDimAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.UtilityAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssessmentInstanceDimAssemblerImpl implements AssessmentInstanceDimAssembler {

    // TODO: Created At to be Added
    private final UtilityAssembler utilityAssembler;
    @Override
    public AssessmentInstanceDim assessmentDTOToAssessmentInstanceDim(AssessmentDTO assessmentDTO) {
        return fillAssessmentDtoDetails(assessmentDTO).build();
    }

    @Override
    public AssessmentInstanceDim assessmentInstanceDTOtoAssessmentInstanceDim(AssessmentInstanceDTO dto) {
        return fillAssessmentDtoDetails(dto.getAssessment())
                .assessmentInstanceCreatedAt(dto.getCreatedAt())
                .assessmentInstanceId(dto.getId())
                .duplicatedCount(dto.getDuplicatedCount())
                .duplicatedFrom(dto.getDuplicatedFrom())
                .scheduleStatus(utilityAssembler.getRefDataCode(dto.getScheduleStatus()))
                .assessmentInstanceEndDate(dto.getEndDate())
                .assessmentInstanceStartDate(dto.getStartDate())
                .isPublic(dto.getIsPublic())
                .isSpecificVisibility(dto.getSpecificVisibility())
                .moniteredByActorId(utilityAssembler.getActorId(dto.getMonitoredBy()))
                .moniteredByActorType(utilityAssembler.getActorType(dto.getMonitoredBy()))
                .conductedByActorId(utilityAssembler.getActorId(dto.getConductedBy()))
                .conductedByActorType(utilityAssembler.getActorType(dto.getConductedBy()))
                .forEntityId(dto.getForEntityId())
                .forEntityType(dto.getForEntityType())
                .assessmentInstanceName(utilityAssembler.filterMLTexts(dto.getAsmtInstName(),dto.getAssessment().getBaseLanguage()))
                .sequence(dto.getSequence())
                .build();
    }

    @Override
    public AssessmentInstanceDim assessmentQuestionPaperDTOtoAssessmentInstanceDim(AssessmentQuestionPaperDTO questionPaper) {
        return fillAssessmentDtoDetails(questionPaper.getAssessment())
                .questionPaperId(questionPaper.getId())
                .questionPaperIdCreatedAt(questionPaper.getCreatedAt())
                .questionPaperCode(questionPaper.getAssessmentQuestionPaperCode())
                .questionPaperName(utilityAssembler.filterMLTexts(questionPaper.getAqpName(),questionPaper.getAssessment().getBaseLanguage()))
                .questionPaperDescription(questionPaper.getAssessmentQuestionPaperCode())
                .responseCount(0L)
                .totalWeight(utilityAssembler.getTotalWeight(questionPaper))
                .totalQuestion(utilityAssembler.getTotalQuestions(questionPaper))
                .build();
    }

    @Override
    public AssessmentInstanceDim createAssessmentInstanceDimFromSyncContext(AssessmentSyncContext syncContext) {
        AssessmentInstanceDTO dto = syncContext.getAssessmentInstanceDTO();
        AssessmentQuestionPaperDTO questionPaper = syncContext.getAssessmentQuestionPaperDTO();
        return fillAssessmentDtoDetails(syncContext.getAssessment())
                .sequence(syncContext.getAssessmentInstanceDTO().getSequence())
                .assessmentInstanceId(dto.getId())
                .duplicatedCount(dto.getDuplicatedCount())
                .duplicatedFrom(dto.getDuplicatedFrom())
                .scheduleStatus(utilityAssembler.getRefDataCode(dto.getScheduleStatus()))
                .assessmentInstanceEndDate(dto.getEndDate())
                .assessmentInstanceStartDate(dto.getStartDate())
                .isPublic(dto.getIsPublic())
                .isSpecificVisibility(dto.getSpecificVisibility())
                .moniteredByActorId(utilityAssembler.getActorId(dto.getMonitoredBy()))
                .moniteredByActorType(utilityAssembler.getActorType(dto.getMonitoredBy()))
                .conductedByActorId(utilityAssembler.getActorId(dto.getConductedBy()))
                .conductedByActorType(utilityAssembler.getActorType(dto.getConductedBy()))
                .forEntityId(dto.getForEntityId())
                .forEntityType(dto.getForEntityType())
                .assessmentInstanceCreatedAt(dto.getCreatedAt())
                .assessmentInstanceName(utilityAssembler.filterMLTexts(dto.getAsmtInstName(),dto.getAssessment().getBaseLanguage()))
                .questionPaperId(questionPaper.getId())
                .questionPaperIdCreatedAt(questionPaper.getCreatedAt())
                .questionPaperCode(questionPaper.getAssessmentQuestionPaperCode())
                .questionPaperName(utilityAssembler.filterMLTexts(questionPaper.getAqpName(),questionPaper.getAssessment().getBaseLanguage()))
                .questionPaperDescription(questionPaper.getAssessmentQuestionPaperCode())
                .responseCount(0L)
                .totalWeight(utilityAssembler.getTotalWeight(questionPaper))
                .totalQuestion(utilityAssembler.getTotalQuestions(questionPaper))
                .build();
    }

    private AssessmentInstanceDim.AssessmentInstanceDimBuilder fillAssessmentDtoDetails(AssessmentDTO assessmentDTO){
        return AssessmentInstanceDim.builder()
                .id(0L)
                .assessmentId(assessmentDTO.getId())
                .assessmentCode(assessmentDTO.getAssessmentCode())
                .context(assessmentDTO.getContext())
                .domain(assessmentDTO.getDomain())
                .assessmentName(utilityAssembler.filterMLTexts(assessmentDTO.getAssessmentName(),assessmentDTO.getBaseLanguage()))
                .assessmentDesc(utilityAssembler.filterMLTexts(assessmentDTO.getAssessmentDesc(),assessmentDTO.getBaseLanguage()))
                .subDomain(assessmentDTO.getSubDomain())
                .assessmentMode(utilityAssembler.getRefDataCode(assessmentDTO.getAssessmentMode()))
                .assessmentType(utilityAssembler.getRefDataCode(assessmentDTO.getAssessmentType()))
                .assessmentSubtype(utilityAssembler.getRefDataCode(assessmentDTO.getAssessmentSubType()))
                .baseLanguage(utilityAssembler.getBaseLanguage(assessmentDTO))
                .language(utilityAssembler.getBaseLanguage(assessmentDTO))
                .createdBy(assessmentDTO.getCreatedBy())
                .assessmentCreatedAt(assessmentDTO.getCreatedAt())
                .tenantId(assessmentDTO.getTenantId())
                .dimType(DimType.NONE);
    }

}
