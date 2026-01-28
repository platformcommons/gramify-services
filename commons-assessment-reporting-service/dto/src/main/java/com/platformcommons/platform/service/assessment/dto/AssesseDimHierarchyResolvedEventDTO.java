package com.platformcommons.platform.service.assessment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssesseDimHierarchyResolvedEventDTO {

    private AssesseDimDTO assesseDim;
    private AssesseHierarchyResolutionConfigurationDTO assesseHierarchyResolutionConfiguration;

    @Builder
    public AssesseDimHierarchyResolvedEventDTO(AssesseDimDTO assesseDim, AssesseHierarchyResolutionConfigurationDTO assesseHierarchyResolutionConfiguration) {
        this.assesseDim = assesseDim;
        this.assesseHierarchyResolutionConfiguration = assesseHierarchyResolutionConfiguration;
    }
}
