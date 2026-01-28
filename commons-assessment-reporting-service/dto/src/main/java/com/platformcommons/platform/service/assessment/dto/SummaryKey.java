package com.platformcommons.platform.service.assessment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public final class SummaryKey {

    private final Long assessmentInstanceId;
    private final Long assessmentId;
    private final String actorType;
    private final Long hierarchyLevel;
    private final String resolvedValue;

    public SummaryKey(Long assessmentInstanceId, String actorType, Long hierarchyLevel, String resolvedValue,Long assessmentId) {
        this.assessmentInstanceId = assessmentInstanceId;
        this.actorType = actorType;
        this.hierarchyLevel = hierarchyLevel;
        this.resolvedValue = resolvedValue;
        this.assessmentId = assessmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SummaryKey that = (SummaryKey) o;
        return Objects.equals(assessmentInstanceId, that.assessmentInstanceId) &&
                Objects.equals(actorType, that.actorType) &&
                Objects.equals(hierarchyLevel, that.hierarchyLevel) &&
                Objects.equals(resolvedValue, that.resolvedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessmentInstanceId, actorType, hierarchyLevel, resolvedValue);
    }
}