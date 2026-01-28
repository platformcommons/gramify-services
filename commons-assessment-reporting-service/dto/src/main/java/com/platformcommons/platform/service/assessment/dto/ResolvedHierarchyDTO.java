package com.platformcommons.platform.service.assessment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResolvedHierarchyDTO {

    private String resolvedValue;

    public ResolvedHierarchyDTO(String resolvedValue) {
        this.resolvedValue = resolvedValue;
    }
}
