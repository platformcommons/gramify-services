package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssesseDimHierarchyResolvedEventDTO;

import java.util.List;

public interface AssessmentResponseHierarchySummaryService {

    void createOrUpdateSummariesInBatch(List<AssesseDimHierarchyResolvedEventDTO> events);

    void resetAll(Long assessment);
}
