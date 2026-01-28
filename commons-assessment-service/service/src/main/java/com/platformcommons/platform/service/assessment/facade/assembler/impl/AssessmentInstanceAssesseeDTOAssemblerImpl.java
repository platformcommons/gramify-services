package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.assessment.domain.AiaDefaultResponse;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAssesse;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.MimicAssessmentActorDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.*;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssessmentInstanceAssesseeDTOAssemblerImpl implements AssessmentInstanceAssesseDTOAssembler {

    private final MimicMLTextDTOAssembler mlTextDTOAssembler;
    private final AiaDefaultResponseDTOAssembler aiaDefaultResponseDTOAssembler;
    private final MimicAssessmentActorDTOAssembler actorDTOAssembler;
    private final AssessmentInstanceDTOAssembler assessmentInstanceDTOAssembler;
    private final MimicRefDataDTOAssembler refDataDTOAssembler;
    private final RefDataDTOValidator refDataDTOValidator;

    @Override
    public AssessmentInstanceAssesse fromDTO(AssessmentInstanceAssesseDTO assessmentInstanceAssesseeDTO) {
        if (assessmentInstanceAssesseeDTO == null) return null;
        if(
                (nullOrEmpty(assessmentInstanceAssesseeDTO.getAssessee())) &&
                (nullOrEmpty(assessmentInstanceAssesseeDTO.getAssessmentActorList()) || nullOrEmpty(assessmentInstanceAssesseeDTO.getAssessmentActorList().stream().findFirst().get()))

        ){
            throw new InvalidInputException("Assessee or AssessmentActorList any one should contain assessee");
        }

        return AssessmentInstanceAssesse.builder()
                .uuid(assessmentInstanceAssesseeDTO.getUuid())
                .appCreatedTimestamp(assessmentInstanceAssesseeDTO.getAppCreatedAt())
                .appLastModifiedTimestamp(assessmentInstanceAssesseeDTO.getAppLastModifiedAt())
                .id(assessmentInstanceAssesseeDTO.getId())
                .assessee(actorDTOAssembler.fromDTO(assessmentInstanceAssesseeDTO.getAssessee()))
                .assessor(actorDTOAssembler.fromDTO(assessmentInstanceAssesseeDTO.getAssessor()))
                .assessmentActorList(assessmentInstanceAssesseeDTO.getAssessmentActorList() != null ?
                        assessmentInstanceAssesseeDTO.getAssessmentActorList().stream()
                                .map(actorDTOAssembler::fromDTOV2)
                                .collect(Collectors.toList())
                        : null)
                .assessmentInstance(assessmentInstanceDTOAssembler.fromDTO(assessmentInstanceAssesseeDTO.getAssessmentInstance()))
                .tenant(assessmentInstanceAssesseeDTO.getTenant())
                .assessedForEntityId(assessmentInstanceAssesseeDTO.getAssessedForEntityId())
                .assessedForEntityType(assessmentInstanceAssesseeDTO.getAssessedForEntityType())
                .assessorType(assessmentInstanceAssesseeDTO.getAssessorType())
                .assessmentTaken(refDataDTOValidator.fromDTO(refDataDTOAssembler.toRefDataDTO(assessmentInstanceAssesseeDTO.getAssessmentTaken())))
                .result(assessmentInstanceAssesseeDTO.getResult())
                .statusOfResult(refDataDTOValidator.fromDTO(refDataDTOAssembler.toRefDataDTO(assessmentInstanceAssesseeDTO.getStatusOfResult())))
                .marksObtained(assessmentInstanceAssesseeDTO.getMarksObtained())
                .grade(assessmentInstanceAssesseeDTO.getGrade())
//                .aiaAsseseeDesc(mlTextDTOAssembler.fromDTOs(assessmentInstanceAssesseeDTO.getAiaAsseseeDesc()))
                .isLatest(assessmentInstanceAssesseeDTO.getIsLatest())
                .assessmentTakenOn(assessmentInstanceAssesseeDTO.getAssessmentTakenOn())
                .notes(assessmentInstanceAssesseeDTO.getNotes())
                .location(assessmentInstanceAssesseeDTO.getLocation())
                .isGroupAssessmentResponse(assessmentInstanceAssesseeDTO.getIsGroupAssessmentResponse())
                .aiadefaultresponseList(
                        assessmentInstanceAssesseeDTO.getAiadefaultResponseList() != null ?
                                assessmentInstanceAssesseeDTO.getAiadefaultResponseList().stream()
                                        .map(aiaDefaultResponseDTOAssembler::fromDTO)
                                        .collect(Collectors.toCollection(LinkedHashSet::new))
                                : null)
                .assessmentTakenForDate(assessmentInstanceAssesseeDTO.getAssessmentTakenForDate())
                .build();

    }

    private <T> boolean nullOrEmpty(Set<T> set) {
        return set==null || set.isEmpty();
    }

    private boolean nullOrEmpty(MimicAssessmentActorDTO assessee) {
        return assessee==null || assessee.getActorId() == null || assessee.getActorType() == null;
    }

    @Override
    public AssessmentInstanceAssesseDTO toDTO(AssessmentInstanceAssesse assessmentInstanceAssesse) {
        if (assessmentInstanceAssesse == null) return null;
        return AssessmentInstanceAssesseDTO.builder()
                .uuid(assessmentInstanceAssesse.getUuid())
                .appCreatedAt(assessmentInstanceAssesse.getAppCreatedTimestamp())
                .appLastModifiedAt(assessmentInstanceAssesse.getAppLastModifiedTimestamp())
                .createdAt(assessmentInstanceAssesse.getCreatedTimestamp())
                .lastModifiedAt(assessmentInstanceAssesse.getLastModifiedTimestamp())
                .tenant(assessmentInstanceAssesse.getTenantId())
                .id(assessmentInstanceAssesse.getId())
                .tenant(assessmentInstanceAssesse.getTenant())
                .assessee(actorDTOAssembler.toDTO(assessmentInstanceAssesse.getAssessee()))
                .assessor(actorDTOAssembler.toDTO(assessmentInstanceAssesse.getAssessor()))
                .assessorType(assessmentInstanceAssesse.getAssessorType())
                .assessmentInstance(assessmentInstanceDTOAssembler.toDTO(assessmentInstanceAssesse.getAssessmentInstance(), null))
                .assessmentTaken(refDataDTOAssembler.toMimicRefDataDTO(refDataDTOValidator.toDTO(assessmentInstanceAssesse.getAssessmentTaken())))
                .result(assessmentInstanceAssesse.getResult())
                .statusOfResult(refDataDTOAssembler.toMimicRefDataDTO(refDataDTOValidator.toDTO(assessmentInstanceAssesse.getStatusOfResult())))
                .marksObtained(assessmentInstanceAssesse.getMarksObtained())
                .grade(assessmentInstanceAssesse.getGrade())
                .isLatest(assessmentInstanceAssesse.getIsLatest())
                .assessmentTakenOn(assessmentInstanceAssesse.getAssessmentTakenOn())
                .notes(assessmentInstanceAssesse.getNotes())
                .location(assessmentInstanceAssesse.getLocation())
                .isGroupAssessmentResponse(assessmentInstanceAssesse.getIsGroupAssessmentResponse())
                .assessmentActorList(assessmentInstanceAssesse.getAssessmentActorList() != null ?
                        assessmentInstanceAssesse.getAssessmentActorList().stream()
                                .map(actorDTOAssembler::toDTOV2)
                                .collect(Collectors.toCollection(LinkedHashSet::new)) : null)
                .assessedForEntityId(assessmentInstanceAssesse.getAssessedForEntityId())
                .assessedForEntityType(assessmentInstanceAssesse.getAssessedForEntityType())
//                .aiaAsseseeDesc(mlTextDTOAssembler.toDTOs(assessmentInstanceAssesse.getAiaAsseseeDesc()))
                .aiadefaultResponseList(assessmentInstanceAssesse.getAiadefaultresponseList() != null ?
                        assessmentInstanceAssesse.getAiadefaultresponseList().stream()
                                .filter(aiaDefaultResponse -> !Objects.equals(Boolean.FALSE, aiaDefaultResponse.getIsActive()))
                                .map(aiaDefaultResponseDTOAssembler::toDTO)
                                .collect(Collectors.toCollection(LinkedHashSet::new)) : null)
                .tenantId(assessmentInstanceAssesse.getTenantId())
                .createdAt(assessmentInstanceAssesse.getCreatedTimestamp())
                .createdBy(assessmentInstanceAssesse.getCreatedByUser())
                .assessmentTakenForDate(assessmentInstanceAssesse.getAssessmentTakenForDate())
                .build();
    }
}
