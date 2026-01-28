package com.platformcommons.platform.service.assessment.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class OptionSummaryKey {

    private final Long assessmentInstanceId;
    private final Long assessmentId;
    private final String actorType;
    private final Long hierarchyLevel;
    private final String resolvedValue;
    private final Long optionId;
    private final Long defaultOptionId;

    @Builder
    public OptionSummaryKey(Long assessmentInstanceId, String actorType, Long hierarchyLevel, String resolvedValue, Long optionId, Long defaultOptionId, Long assessmentId) {
        this.assessmentInstanceId = assessmentInstanceId;
        this.actorType = actorType;
        this.hierarchyLevel = hierarchyLevel;
        this.resolvedValue = resolvedValue;
        this.optionId = optionId;
        this.defaultOptionId = defaultOptionId;
        this.assessmentId = assessmentId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OptionSummaryKey that = (OptionSummaryKey) o;

        return Objects.equals(assessmentInstanceId, that.assessmentInstanceId) &&
                Objects.equals(assessmentId, that.assessmentId) &&
                Objects.equals(actorType, that.actorType) &&
                Objects.equals(hierarchyLevel, that.hierarchyLevel) &&
                Objects.equals(resolvedValue, that.resolvedValue) &&
                Objects.equals(optionId, that.optionId) &&
                Objects.equals(defaultOptionId, that.defaultOptionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessmentInstanceId, assessmentId, actorType,
                hierarchyLevel, resolvedValue, optionId, defaultOptionId);
    }
}