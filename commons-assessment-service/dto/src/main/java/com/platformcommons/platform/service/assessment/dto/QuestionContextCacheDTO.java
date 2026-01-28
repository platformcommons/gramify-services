package com.platformcommons.platform.service.assessment.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter @Getter
@Builder
public class QuestionContextCacheDTO {

    private Long questionId;
    private Boolean isMandatory;
    private Boolean isChildQuestion;
    private Set<OptionCacheDTO> options;

    @Builder
    public QuestionContextCacheDTO(Long questionId,  Boolean isMandatory, Boolean isChildQuestion, Set<OptionCacheDTO> options) {
        this.questionId = questionId;
        this.isMandatory = isMandatory;
        this.isChildQuestion = isChildQuestion;
        this.options = options;
    }



}
