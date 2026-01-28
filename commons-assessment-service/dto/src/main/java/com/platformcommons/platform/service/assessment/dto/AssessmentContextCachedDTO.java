package com.platformcommons.platform.service.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Builder
@AllArgsConstructor
@Getter @Setter
public class AssessmentContextCachedDTO implements Serializable {

    private Long assessmentInstanceId;
    private Map<String,String> config;
    private Map<Long, QuestionContextCacheDTO> sectionQuestionsMap;
    private Map<Long, QuestionContextCacheDTO> childQuestionMap;

}
