package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.changemaker.dto.ApplicantAssessmentResponseDTO;
import com.platformcommons.platform.service.search.domain.ApplicantAssessmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ApplicantAssessmentResponseEventMapper {

    @Autowired
    private QuestionResponseEventMapper questionResponseEventMapper;

    public ApplicantAssessmentResponse fromEventDTO(ApplicantAssessmentResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        return ApplicantAssessmentResponse.builder()
                .assessmentId(dto.getAssessmentId())
                .assessmentInstanceId(dto.getAssessmentInstanceId())
                .assessmentInstanceAssesseeId(dto.getAssessmentInstanceAssesseeId())
                .questionResponses(questionResponseEventMapper.fromEventDTOSet(dto.getQuestionResponse()))
                .build();
    }

   public Set<ApplicantAssessmentResponse> fromEventDTOSet(Set<ApplicantAssessmentResponseDTO> dtoSet) {
        if (dtoSet == null || dtoSet.isEmpty()) {
            return null;
        }

        Set<ApplicantAssessmentResponse> set = new java.util.HashSet<>();
        for (ApplicantAssessmentResponseDTO dto : dtoSet) {
            ApplicantAssessmentResponse entity = fromEventDTO(dto);
            if (entity != null) {
                set.add(entity);
            }
        }
        return set;
    }


}
