package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.*;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class AssessmentInstanceDTOAssemblerImpl implements AssessmentInstanceDTOAssembler {

    private final MimicMLTextDTOAssembler mLTextDTOAssembler;
    private final AssessmentDTOAssembler assessmentDTOAssembler;
    private final AssessmentInstanceActorDTOAssembler actorDTOAssembler;
    private final MimicRefDataDTOAssembler refDataDTOAssembler;
    private final RefDataDTOValidator refDataDTOValidator;

    @Override
    public AssessmentInstance fromDTO(AssessmentInstanceDTO assessmentinstanceDTO) {
        if (assessmentinstanceDTO == null) return null;
        return AssessmentInstance.builder()
                .uuid(assessmentinstanceDTO.getUuid())
                .appCreatedTimestamp(assessmentinstanceDTO.getAppCreatedAt())
                .appLastModifiedTimestamp(assessmentinstanceDTO.getAppLastModifiedAt())
                .id(assessmentinstanceDTO.getId())
                .assessment((assessmentDTOAssembler.fromDTO(assessmentinstanceDTO.getAssessment())))
                .conductedBy(actorDTOAssembler.fromDTO(assessmentinstanceDTO.getConductedBy()))
                .monitoredBy(actorDTOAssembler.fromDTO(assessmentinstanceDTO.getMonitoredBy()))
                .startDate(assessmentinstanceDTO.getStartDate())
                .endDate(assessmentinstanceDTO.getEndDate())
                .forEntityType(assessmentinstanceDTO.getForEntityType())
                .forEntityId(assessmentinstanceDTO.getForEntityId())
                .duration(assessmentinstanceDTO.getDuration())
                .school(assessmentinstanceDTO.getSchool())
                .testNumber(assessmentinstanceDTO.getTestNumber())
                .testType(refDataDTOValidator.fromDTO(refDataDTOAssembler.toRefDataDTO(assessmentinstanceDTO.getTestType())))
                .academicSession(assessmentinstanceDTO.getAcademicSession())
                .scheduleStatus(refDataDTOValidator.fromDTO(refDataDTOAssembler.toRefDataDTO(assessmentinstanceDTO.getScheduleStatus())))
                .confirmationDate(assessmentinstanceDTO.getConfirmationDate())
                .tenant(assessmentinstanceDTO.getTenant())
                .imageURL(assessmentinstanceDTO.getImageURL())
                .asmtInstDesc(mLTextDTOAssembler.fromDTOs(assessmentinstanceDTO.getAsmtInstDesc()))
                .asmtInstName(mLTextDTOAssembler.fromDTOs(assessmentinstanceDTO.getAsmtInstName()))
                .isPublic(assessmentinstanceDTO.getIsPublic())
                .specificVisibility(assessmentinstanceDTO.getSpecificVisibility())
                .sequence(assessmentinstanceDTO.getSequence())
                .build();

    }

    @Override
    public Set<AssessmentInstanceDTO> toDTOs(Set<AssessmentInstance> entities) {
        return entities.stream().map(assessmentInstance -> toDTO(assessmentInstance,null)).collect(Collectors.toSet());
    }

    @Override
    public AssessmentInstanceDTO toDTO(AssessmentInstance assessmentInstance, String authorName) {
        if (assessmentInstance == null) return null;
        return AssessmentInstanceDTO.builder()
                .uuid(assessmentInstance.getUuid())
                .createdAt(assessmentInstance.getCreatedTimestamp())
                .createdBy(assessmentInstance.getCreatedByUser())
                .tenantId(assessmentInstance.getTenantId())
                .appCreatedAt(assessmentInstance.getAppCreatedTimestamp())
                .appLastModifiedAt(assessmentInstance.getAppLastModifiedTimestamp())
                .tenantId(assessmentInstance.getTenantId())
                .id(assessmentInstance.getId())
                .asmtInstDesc(mLTextDTOAssembler.toDTOs(assessmentInstance.getAsmtInstDesc()))
                .asmtInstName(mLTextDTOAssembler.toDTOs(assessmentInstance.getAsmtInstName()))
                .assessment(assessmentDTOAssembler.toDTO(assessmentInstance.getAssessment()))
                .conductedBy(actorDTOAssembler.toDTO(assessmentInstance.getConductedBy()))
                .monitoredBy(actorDTOAssembler.toDTO(assessmentInstance.getMonitoredBy()))
                .startDate(assessmentInstance.getStartDate())
                .endDate(assessmentInstance.getEndDate())
                .forEntityType(assessmentInstance.getForEntityType())
                .forEntityId(assessmentInstance.getForEntityId())
                .duration(assessmentInstance.getDuration())
                .school(assessmentInstance.getSchool())
                .testNumber(assessmentInstance.getTestNumber())
                .testType(refDataDTOAssembler.toMimicRefDataDTO(refDataDTOValidator.toDTO(assessmentInstance.getTestType())))
                .scheduleStatus(refDataDTOAssembler.toMimicRefDataDTO(refDataDTOValidator.toDTO(assessmentInstance.getScheduleStatus())))
                .academicSession(assessmentInstance.getAcademicSession())
                .confirmationDate(assessmentInstance.getConfirmationDate())
                .tenant(assessmentInstance.getTenant())
                .isActive(assessmentInstance.getIsActive())
                .imageURL(assessmentInstance.getImageURL())
                .isPublic(assessmentInstance.getIsPublic())
                .specificVisibility(assessmentInstance.getSpecificVisibility())
                .editable(assessmentInstance.getCreatedByUser() != null &&
                        assessmentInstance.getCreatedByUser().equals(getUserId()))
                .author(authorName)
                .duplicatedFrom(assessmentInstance.getDuplicatedFrom())
                .duplicatedCount(assessmentInstance.getDuplicatedCount())
                .sequence(assessmentInstance.getSequence())
                .build();
    }

    private Long getUserId() {
        return PlatformSecurityUtil.isPlatformAppToken()?null:PlatformSecurityUtil.getCurrentUserId();
    }
}
