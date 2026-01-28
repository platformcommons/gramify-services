package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.domain.Assessment;
import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.ConfigDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicMLTextDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicRefDataDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssessmentDTOAssemblerImpl implements AssessmentDTOAssembler {

    private final MimicMLTextDTOAssembler mLTextDTOAssembler;
    private final MimicRefDataDTOAssembler refDataDTOAssembler;
    private final RefDataDTOValidator refDataDTOValidator;
    private final ConfigDTOAssembler configDTOAssembler;
    @Override
    public Assessment fromDTO(AssessmentDTO assessmentDTO) {
        if (assessmentDTO == null) return null;
        return Assessment.builder()
                         .uuid(assessmentDTO.getUuid())
                         .appCreatedTimestamp(assessmentDTO.getAppCreatedAt())
                         .appLastModifiedTimestamp(assessmentDTO.getAppLastModifiedAt())
                         .id(assessmentDTO.getId())
                         .domain(assessmentDTO.getDomain())
                         .tenant(assessmentDTO.getTenant())
                         .assessmentCode(assessmentDTO.getAssessmentCode())
                         .assessmentKind(refDataDTOValidator.fromDTO(refDataDTOAssembler.toRefDataDTO(assessmentDTO.getAssessmentKind())))
                         .assessmentSubKind(refDataDTOValidator.fromDTO(refDataDTOAssembler.toRefDataDTO(assessmentDTO.getAssessmentSubKind())))
                         .assessmentType(refDataDTOValidator.fromDTO(refDataDTOAssembler.toRefDataDTO(assessmentDTO.getAssessmentType())))
                         .assessmentSubType(refDataDTOValidator.fromDTO(refDataDTOAssembler.toRefDataDTO(assessmentDTO.getAssessmentSubType())))
                         .assessmentMode(refDataDTOValidator.fromDTO(refDataDTOAssembler.toRefDataDTO(assessmentDTO.getAssessmentMode())))
                         .frequency(assessmentDTO.getFrequency())
                         .totallMarks(assessmentDTO.getTotallMarks())
                         .context(assessmentDTO.getContext())
                         .levelCode(assessmentDTO.getLevelCode())
                         .subjectCode(assessmentDTO.getSubjectCode())
                         .assessmentDesc(mLTextDTOAssembler.fromDTOs(assessmentDTO.getAssessmentDesc()))
                         .assessmentName(mLTextDTOAssembler.fromDTOs(assessmentDTO.getAssessmentName()))
                         .baseLanguage(assessmentDTO.getBaseLanguage())
                         .subDomain(assessmentDTO.getSubDomain())
                         .assessmentConfigs(assessmentDTO.getAssessmentConfigs()==null?
                                 new HashSet<>():
                                 assessmentDTO.getAssessmentConfigs()
                                         .stream()
                                         .map(configDTOAssembler::fromDTO)
                                         .collect(Collectors.toSet()))
                         .build();
    }

    @Override
    public AssessmentDTO toDTO(Assessment assessment) {
        if (assessment == null) return null;
        return AssessmentDTO.builder()
                            .uuid(assessment.getUuid())
                            .appCreatedAt(assessment.getAppCreatedTimestamp())
                            .appLastModifiedAt(assessment.getAppLastModifiedTimestamp())
                            .tenant(assessment.getTenantId())
                            .id(assessment.getId())
                            .domain(assessment.getDomain())
                            .tenantId(assessment.getTenantId())
                            .createdAt(assessment.getCreatedTimestamp())
                            .createdBy(assessment.getCreatedByUser())
                            .assessmentCode(assessment.getAssessmentCode())
                            .assessmentKind(refDataDTOAssembler.toMimicRefDataDTO(refDataDTOValidator.toDTO(assessment.getAssessmentKind())))
                            .assessmentSubKind(refDataDTOAssembler.toMimicRefDataDTO(refDataDTOValidator.toDTO(assessment.getAssessmentSubKind())))
                            .assessmentType(refDataDTOAssembler.toMimicRefDataDTO(refDataDTOValidator.toDTO(assessment.getAssessmentType())))
                            .assessmentSubType(refDataDTOAssembler.toMimicRefDataDTO(refDataDTOValidator.toDTO(assessment.getAssessmentSubType())))
                            .assessmentMode(refDataDTOAssembler.toMimicRefDataDTO(refDataDTOValidator.toDTO(assessment.getAssessmentMode())))
                            .frequency(assessment.getFrequency())
                            .totallMarks(assessment.getTotallMarks())
                            .context(assessment.getContext())
                            .levelCode(assessment.getLevelCode())
                            .subjectCode(assessment.getSubjectCode())
                            .assessmentDesc(mLTextDTOAssembler.toDTOs(assessment.getAssessmentDesc()))
                            .assessmentName(mLTextDTOAssembler.toDTOs(assessment.getAssessmentName()))
                            .subDomain(assessment.getSubDomain())
                            .baseLanguage(assessment.getBaseLanguage())
                            .duplicatedCount(assessment.getDuplicatedCount())
                            .duplicatedFrom(assessment.getDuplicatedFrom())
                            .assessmentConfigs(assessment.getAssessmentConfigs()==null?null:
                                            assessment.getAssessmentConfigs()
                                                    .stream()
                                                    .map(configDTOAssembler::toDTO)
                                                    .collect(Collectors.toSet())
                                    )
                            .build();
    }
}
