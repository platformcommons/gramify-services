package com.platformcommons.platform.service.assessment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class CachedSectionQuestionResponse {

    private Long assessmentInstanceId;
    private Long sectionQuestionId;
    private Long questionId;
    private Boolean childQuestion;

    private Map<Long,Long> optionResponse;
    @Builder
    public CachedSectionQuestionResponse(Long assessmentInstanceId,
                                         Long questionId,
                                         Long sectionQuestionId,
                                         Map<Long,Long> optionresponse,
                                         Boolean childQuestion){
        this.assessmentInstanceId = assessmentInstanceId;
        this.sectionQuestionId = sectionQuestionId;
        this.questionId = questionId;
        this.optionResponse = optionresponse;
        this.childQuestion = childQuestion;
    }

    public void increaseOptionResponseCount(List<OptionResponseDTO> responseDTOS) {
        for (OptionResponseDTO option : responseDTOS) {
            optionResponse.put(option.getOptionId(),optionResponse.getOrDefault(option.getOptionId(),0L)+option.getResponseCount());
        }
    }
}