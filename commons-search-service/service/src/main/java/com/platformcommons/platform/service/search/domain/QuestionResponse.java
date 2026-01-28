package com.platformcommons.platform.service.search.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

    @Field
    private Long questionId;

    @Field
    private String questionType;

    @Field
    private Set<Long> responseIds;

}
