package com.platformcommons.platform.service.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessseHierarchyResolverDTO {
    private Long id;
    private Long hierarchyLevel;
    private String datasetCode;
    private String defaultParams;
}