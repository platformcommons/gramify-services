package com.platformcommons.platform.service.assessment.reporting.facade.assembler.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.AiaDefaultResponseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.MimicAssessmentActorDTO;
import com.platformcommons.platform.service.assessment.reporting.application.constant.DimType;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseResponseFact;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssesseResponseFactAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.UtilityAssembler;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssesseResponseFactAssemblerImpl implements AssesseResponseFactAssembler {

    private final UtilityAssembler utilityAssembler;


    @Override
    public Set<AssesseResponseFact> toAssesseResponseFact(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {

        Set<AssesseResponseFact> assesseResponseFacts = new HashSet<>();
        Set<MimicAssessmentActorDTO> set = (assesseDTO.getAssessmentActorList() != null && !assesseDTO.getAssessmentActorList().isEmpty())?
                assesseDTO.getAssessmentActorList()
                        .stream()
                        .sorted((o1, o2) -> (int) (o1.getId() - o2.getId()))
                        .collect(Collectors.toCollection(LinkedHashSet::new)):null;

        for (AiaDefaultResponseDTO aiaDefaultResponseDTO : assesseDTO.getAiadefaultResponseList()) {

            String multiselectResponseIds = utilityAssembler.getMultiselectResponseIds(aiaDefaultResponseDTO);
            String multiselectResponse    = utilityAssembler.getMultiselectResponse(aiaDefaultResponseDTO,assesseDTO.getAssessmentInstance().getAssessment());
            String subjectiveResponseIds  = utilityAssembler.getSubjectiveResponseIds(aiaDefaultResponseDTO);
            String subjectiveResponses    = utilityAssembler.getSubjectiveResponses(aiaDefaultResponseDTO);
            String optionsText            = utilityAssembler.getOptionText(aiaDefaultResponseDTO.getOptionId(),assesseDTO.getAssessmentInstance().getAssessment());
            Long optionsId                = aiaDefaultResponseDTO.getOptionId()==null?null:aiaDefaultResponseDTO.getOptionId().getId();
            Long questionId = aiaDefaultResponseDTO.getSectionQuestion()==null?aiaDefaultResponseDTO.getChildQuestionId():aiaDefaultResponseDTO.getSectionQuestion().getQuestionId();
            assesseResponseFacts.add(
                    AssesseResponseFact.builder()
                            .id(0L)
                            .assesseCreatedAt(assesseDTO.getCreatedAt())
                            .responseCreatedAt(aiaDefaultResponseDTO.getCreatedAt())
//                            .responseUpdatedAt(aiaDefaultResponseDTO.getAppLastModifiedAt())
                            .assessmentId(context.getAssessmentInstanceDim().getAssessmentId())
                            .assessmentInstanceId(context.getAssessmentInstanceDim().getAssessmentInstanceId())
                            .assessmentInstanceAssesseId(assesseDTO.getId())
                            .sectionQuestionsId(aiaDefaultResponseDTO.getSectionQuestion()==null?null:aiaDefaultResponseDTO.getSectionQuestion().getId())
                            .questionId(questionId)
                            .assessorName(assesseDTO.getAssessor()==null?null:assesseDTO.getAssessor().getName())
                            .assessedForEntityId(assesseDTO.getAssessedForEntityId())
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
                            .assessedForEntityType(assesseDTO.getAssessedForEntityType())
                            .assessmentTakenOn(assesseDTO.getAssessmentTakenOn())
                            .assessorId(assesseDTO.getAssessor()==null?null:assesseDTO.getAssessor().getActorId())
                            .assessorType(assesseDTO.getAssessor()==null?null:assesseDTO.getAssessor().getActorType())
                            .questionText(context.getQuestionDim(questionId).getQuestionText())
                            .aiaDefaultResponseId(aiaDefaultResponseDTO.getId())
                            .multiSelectResponseIds(multiselectResponseIds)
                            .optionId(optionsId)
                            .objectiveResponse(optionsText)
                            .multiSelectResponse(multiselectResponse)
                            .subjectiveResponseIds(subjectiveResponseIds)
                            .subjectiveResponses(subjectiveResponses)
                            .questionResponse(utilityAssembler.getQuestionResponse(optionsText,multiselectResponse,subjectiveResponses))
                            .correctQuestion(utilityAssembler.getCorrectQuestions(aiaDefaultResponseDTO,context))
                            .score(utilityAssembler.getScore(aiaDefaultResponseDTO,context))
                            .userDisplayName(PlatformSecurityUtil.getCurrentUserName())
                            .userLogin(PlatformSecurityUtil.getCurrentUserLogin())
                            .assesseCreatedAt(assesseDTO.getCreatedAt())
                            .responseCreatedAt(aiaDefaultResponseDTO.getCreatedAt())
                            .language(utilityAssembler.getBaseLanguage(assesseDTO.getAssessmentInstance().getAssessment()))
                            .dimType(DimType.NONE)
                            .build()
            );
        }

        return assesseResponseFacts;
    }

    private String convertNull(String actorId) {
        return actorId==null?"N/A":actorId;
    }
}
