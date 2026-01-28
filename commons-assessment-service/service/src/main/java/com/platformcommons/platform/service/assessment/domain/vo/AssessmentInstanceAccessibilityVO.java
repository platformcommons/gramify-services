package com.platformcommons.platform.service.assessment.domain.vo;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AssessmentInstanceAccessibilityVO {

    private Long assessmentInstanceId;

    private Long tenantId;
    private String scheduleStatus;
    private Boolean isPublic;
    private Boolean specificVisibility;
    private Long createdUserId;

    public AssessmentInstanceAccessibilityVO(Long assessmentInstanceId,
                                             Long tenantId, String scheduleStatus, Boolean isPublic,
                                             Boolean specificVisibility, Long createdUserId) {
        this.assessmentInstanceId = assessmentInstanceId;
        this.tenantId = tenantId;
        this.scheduleStatus = scheduleStatus;
        this.isPublic = isPublic;
        this.specificVisibility = specificVisibility;
        this.createdUserId = createdUserId;
    }
}
