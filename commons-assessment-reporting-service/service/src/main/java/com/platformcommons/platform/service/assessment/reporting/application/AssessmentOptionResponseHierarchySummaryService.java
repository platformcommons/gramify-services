package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssesseDimHierarchyResolvedEventDTO;

import java.util.List;

public interface AssessmentOptionResponseHierarchySummaryService {
    void createAssessmentOptionResponseHierarchySummary(List<AssesseDimHierarchyResolvedEventDTO> payload);

    void resetAll(Long assessment);
}
