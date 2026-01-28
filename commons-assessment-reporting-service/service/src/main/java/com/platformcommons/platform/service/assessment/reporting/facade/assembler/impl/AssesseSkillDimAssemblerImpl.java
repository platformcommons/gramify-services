package com.platformcommons.platform.service.assessment.reporting.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.dto.AiaDefaultResponseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.PairDTO;
import com.platformcommons.platform.service.assessment.reporting.application.constant.ApplicationConstants;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseSkillDim;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.SectionQuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssesseSkillDimAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.UtilityAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssesseSkillDimAssemblerImpl implements AssesseSkillDimAssembler {

    private final UtilityAssembler utilityAssembler;
    @Override
    public Set<AssesseSkillDim> toAssesseSkillDim(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {

        Set<AssesseSkillDim> assesseSkillDims =  new HashSet<>();
        MultiValueMap<String, PairDTO<SectionQuestionDimDTO, QuestionDimDTO>> skillSectionDim = new LinkedMultiValueMap<>();
        for (SectionQuestionDimDTO sectionQuestionDim : context.getSectionQuestionDims()) {
            QuestionDimDTO questionDim = context.getQuestionDim(sectionQuestionDim.getQuestionId());
            if (questionDim.getSubSkillCode() != null && !questionDim.getSubSkillCode().isEmpty()) {
                skillSectionDim.add(questionDim.getSubSkillCode(), PairDTO.<SectionQuestionDimDTO, QuestionDimDTO>builder().first(sectionQuestionDim).second(questionDim).build());
            }
            if (questionDim.getSkillCode() != null && !questionDim.getSkillCode().isEmpty()) {
                skillSectionDim.add(questionDim.getSkillCode(), PairDTO.<SectionQuestionDimDTO, QuestionDimDTO>builder().first(sectionQuestionDim).second(questionDim).build());
            }
        }

        for (Map.Entry<String, List<PairDTO<SectionQuestionDimDTO, QuestionDimDTO>>> skillsMap : skillSectionDim.entrySet()) {

            Map<Long, Double> questionWeightMap = skillsMap.getValue()
                    .stream()
                    .collect(Collectors.toMap(
                            pairDto -> pairDto.getFirst().getQuestionId(), // SectionQuestionDimDTO Question ID
                            pairDto -> pairDto.getSecond().getQuestionWeight() // QuestionDimDTO Question Weight
                    ));


            Set<Long> questionIds = skillsMap.getValue()
                                                    .stream()
                                                    .map(pairDto -> pairDto.getFirst().getQuestionId())
                                                    .collect(Collectors.toSet());
            Set<AiaDefaultResponseDTO> responsesForSkill = assesseDTO.getAiadefaultResponseList()
                                                                     .stream()
                                                                     .filter(aiaDefaultResponseDTO -> questionIds.contains(aiaDefaultResponseDTO.getSectionQuestion().getQuestionId()))
                                                                     .collect(Collectors.toSet());
            Double score=responsesForSkill.stream().mapToDouble(value -> utilityAssembler.getScore(value,context)).sum();

            Set<AiaDefaultResponseDTO> correctQuestions  = utilityAssembler.getCorrectQuestions(responsesForSkill, questionWeightMap);
            Set<AiaDefaultResponseDTO> incorrectQuestion = utilityAssembler.getIncorrectQuestions(responsesForSkill, questionWeightMap);

            List<QuestionDimDTO> questionDimDTOs = skillsMap.getValue().stream().map(PairDTO::getSecond).collect(Collectors.toList());
            assesseSkillDims.add(AssesseSkillDim.builder()
                           .id(0L)
                           .skillCode(skillsMap.getKey())
                           .assesseId(assesseDTO.getId())
                           .assesseActorType(assesseDTO.getAssessee().getActorType())
                           .assesseActorId(assesseDTO.getAssessee().getActorId())
                           .correctQuestionIds(correctQuestions.stream().map(aiaDefaultResponseDTO -> aiaDefaultResponseDTO.getSectionQuestion().getQuestionId().toString()).collect(Collectors.joining(ApplicationConstants.PRIMARY_DELIMITER)))
                           .incorrectQuestionIds(incorrectQuestion.stream().map(aiaDefaultResponseDTO -> aiaDefaultResponseDTO.getSectionQuestion().getQuestionId().toString()).collect(Collectors.joining(ApplicationConstants.PRIMARY_DELIMITER)))
                           .correctQuestions(Integer.toUnsignedLong(correctQuestions.size()))
                           .totalQuestions(Integer.toUnsignedLong(skillsMap.getValue().size()))
                           .scored(score)
                           .totalWeight(utilityAssembler.getTotalWeightQuestionDimDTO(questionDimDTOs))
                           .build());

        }

        return  assesseSkillDims;
    }

}
