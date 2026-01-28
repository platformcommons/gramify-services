package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.changemaker.dto.QuestionResponseDTO;
import com.platformcommons.platform.service.search.domain.QuestionResponse;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class QuestionResponseEventMapper {

    public QuestionResponse fromEventDTO(QuestionResponseDTO questionResponseDTO) {
        if (questionResponseDTO == null) {
            return null;
        }

        return QuestionResponse.builder()
                .questionId(questionResponseDTO.getQuestionId())
                .questionType(questionResponseDTO.getQuestionType())
                .responseIds(questionResponseDTO.getResponseIds())
                .build();
    }

    public Set<QuestionResponse> fromEventDTOSet(Set<QuestionResponseDTO> questionResponseDTOS) {
        if (questionResponseDTOS == null || questionResponseDTOS.isEmpty()) {
            return null;
        }

        Set<QuestionResponse> set = new HashSet<>();
        for (QuestionResponseDTO dto : questionResponseDTOS) {
            QuestionResponse questionResponse = fromEventDTO(dto);
            if (dto != null) {
                set.add(questionResponse);
            }
        }
        return set;
    }

}
