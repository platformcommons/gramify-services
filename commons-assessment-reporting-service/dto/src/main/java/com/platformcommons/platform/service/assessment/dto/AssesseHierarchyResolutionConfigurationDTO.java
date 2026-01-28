package com.platformcommons.platform.service.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssesseHierarchyResolutionConfigurationDTO {

    private Long id;
    private String entityType;

    @NotNull(message = "maxHierarchyLevel must not be null")
    private Long maxHierarchyLevel;

    @NotEmpty(message = "hierarchyResolvers must not be empty")
    private List<AssessseHierarchyResolverDTO> hierarchyResolvers;

}