package com.platformcommons.platform.service.assessment.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

@Getter
@EqualsAndHashCode
public class TimelineSummaryKey {

    private final Long assessmentInstanceId;
    private final String actorType;
    private final Long hierarchyLevel;
    private final String resolvedValue;
    private final Long assessmentId;
    private final String timelineType;
    private final String timelineValue;

    @Builder
    public TimelineSummaryKey(Long assessmentInstanceId, String actorType, Long hierarchyLevel, String resolvedValue, Long assessmentId, String timelineType, String timelineValue) {
        this.assessmentInstanceId = assessmentInstanceId;
        this.actorType = actorType;
        this.hierarchyLevel = hierarchyLevel;
        this.resolvedValue = resolvedValue;
        this.assessmentId = assessmentId;
        this.timelineType = timelineType;
        this.timelineValue = timelineValue;
    }
}