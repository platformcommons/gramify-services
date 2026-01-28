package com.platformcommons.platform.service.search.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleResult {

    @Field
    private String ruleCode;

    @Field
    private Boolean ruleOutcome;

    @Transient
    private Long opportunityApplicantId;

}
