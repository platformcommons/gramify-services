package com.platformcommons.platform.service.search.domain;

import io.micrometer.core.instrument.util.StringUtils;
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
public class OpportunityApplicantTask {

    @Field
    private Long opportunityTaskId;

    @Field
    private Long selectionStepId;

    @Field
    private String opportunityApplicantTaskStatus;

    @Transient
    private Long opportunityApplicantId;

    public void patchUpdate(OpportunityApplicantTask toBeUpdated) {
        if (toBeUpdated.getOpportunityTaskId() != null) {
            this.opportunityTaskId = toBeUpdated.getOpportunityTaskId();
        }
        if (toBeUpdated.getSelectionStepId() != null) {
            this.selectionStepId = toBeUpdated.getSelectionStepId();
        }
        if (!StringUtils.isBlank(toBeUpdated.getOpportunityApplicantTaskStatus())) {
            this.opportunityApplicantTaskStatus = toBeUpdated.getOpportunityApplicantTaskStatus();
        }
    }

}
