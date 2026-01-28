package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssesseDimHierarchyResolvedEventDTO;

import java.util.List;

public interface AssessmentResponseHierarchyTimelineSummaryService {
    void createAssessmentResponseHierarchySummaryTimeline(List<AssesseDimHierarchyResolvedEventDTO> assessee);

    void resetAll(Long assessment);
}
