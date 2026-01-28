package com.platformcommons.platform.service.assessment.reporting.facade.assembler.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.MimicAssessmentActorDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseDim;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssesseDimAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.UtilityAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssesseDimAssemblerImpl implements AssesseDimAssembler {

    private final UtilityAssembler utilityAssembler;

    // TODO: Change User Name on Update Event
    @Override
    public AssesseDim toAssesseDim(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        Set<MimicAssessmentActorDTO> set = (assesseDTO.getAssessmentActorList() != null && !assesseDTO.getAssessmentActorList().isEmpty())?
                assesseDTO.getAssessmentActorList()
                    .stream()
                    .sorted((o1, o2) -> (int) (o1.getId() - o2.getId()))
                    .collect(Collectors.toCollection(LinkedHashSet::new)):null;

        return AssesseDim.builder()
                .assesseId(assesseDTO.getId())
                .assessmentInstanceAssesseCreatedAt(assesseDTO.getCreatedAt())
                .assessmentInstanceId(assesseDTO.getAssessmentInstance().getId())
                .assessmentId(assesseDTO.getAssessmentInstance().getAssessment().getId())
                .assessedForEntityId(assesseDTO.getAssessedForEntityId())
                .assessedForEntityType(assesseDTO.getAssessedForEntityType())
                .assesseeActorId(Optional.ofNullable(assesseDTO.getAssessee())
                        .map(MimicAssessmentActorDTO::getActorId)
                        .orElseGet(() -> set==null?null:set.stream()
                                    .map(mimicAssessmentActorDTO -> convertNull(mimicAssessmentActorDTO.getActorId()))
                                    .collect(Collectors.joining(","))))
                .assesseeActorType(Optional.ofNullable(assesseDTO.getAssessee())
                        .map(MimicAssessmentActorDTO::getActorType)
                        .orElseGet(() -> set==null?null:set.stream()
                                    .map(mimicAssessmentActorDTO -> convertNull(mimicAssessmentActorDTO.getActorType()))
                                    .collect(Collectors.joining(","))))
                .assesseeActorName(Optional.ofNullable(assesseDTO.getAssessee())
                        .map(MimicAssessmentActorDTO::getName)
                        .orElseGet(() -> set==null?null:set.stream()
                                    .map(mimicAssessmentActorDTO -> convertNull(mimicAssessmentActorDTO.getName()))
                                    .collect(Collectors.joining(","))))
                .assessmentTakenOn(assesseDTO.getAssessmentTakenOn())
                .assessorActorId(assesseDTO.getAssessor()==null?null:assesseDTO.getAssessor().getActorId())
                .assessorActorType(assesseDTO.getAssessor()==null?null:assesseDTO.getAssessor().getActorType())
                .assessorActorName(assesseDTO.getAssessor()==null?null:assesseDTO.getAssessor().getName())
                .score(utilityAssembler.getScore(assesseDTO, context))
                .correctQuestion(utilityAssembler.getCorrectQuestions(assesseDTO))
                .totalQuestion(Integer.toUnsignedLong(context.getSectionQuestionDims().size()))
                .name(PlatformSecurityUtil.getCurrentUserName())
                .build();
    }

    private String convertNull(String actorId) {
        return actorId==null?"N/A":actorId;
    }
}
