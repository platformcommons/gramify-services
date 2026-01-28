package com.platformcommons.platform.service.search.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantOwnerResult {

    @Field
    private Long id;

    @Field
    private Long ownerUserId;

    @Field
    private String stepCode;

    @Field
    private String stepResultCode;

    @Transient
    private Long opportunityApplicantId;

    public void patchUpdate(ApplicantOwnerResult toBeUpdated) {
        if(toBeUpdated.getOwnerUserId() != null) {
            this.ownerUserId = toBeUpdated.getOwnerUserId();
        }
        if(!StringUtils.isBlank(toBeUpdated.getStepCode())) {
            this.stepCode = toBeUpdated.getStepCode();
        }
        if(!StringUtils.isBlank(toBeUpdated.getStepResultCode())) {
            this.stepResultCode = toBeUpdated.getStepResultCode();
        }
    }

}
