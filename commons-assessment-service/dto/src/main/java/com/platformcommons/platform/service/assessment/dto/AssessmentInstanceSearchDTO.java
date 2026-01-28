package com.platformcommons.platform.service.assessment.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssessmentInstanceSearchDTO extends BaseTransactionalDTO {
    
    private Long id;
    private Long assessmentId;
    private String domain;
    private String subDomain;
    private String name;
    private String assessmentType;
    private String context;
    private String scheduleStatus;
    private String image;
    private Long createdTimestamp;
    private Long tenantId;
    private Long createdByUser;

    @Builder
    public AssessmentInstanceSearchDTO(Long id, Long assessmentId, String domain, String subDomain, String name, String assessmentType, String context, String scheduleStatus, String image, Long createdTimestamp, Long tenantId, Long createdByUser) {
        this.id = id;
        this.assessmentId = assessmentId;
        this.domain = domain;
        this.subDomain = subDomain;
        this.name = name;
        this.assessmentType = assessmentType;
        this.context = context;
        this.scheduleStatus = scheduleStatus;
        this.image = image;
        this.createdTimestamp = createdTimestamp;
        this.tenantId = tenantId;
        this.createdByUser = createdByUser;
    }
    
}
