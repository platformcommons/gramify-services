package com.platformcommons.platform.service.search.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantAssessmentResponse {

    @Field
    private Long assessmentId;

    @Field
    private Long assessmentInstanceId;

    @Field
    private Long assessmentInstanceAssesseeId;

    @Field
    private Set<QuestionResponse> questionResponses;

}
