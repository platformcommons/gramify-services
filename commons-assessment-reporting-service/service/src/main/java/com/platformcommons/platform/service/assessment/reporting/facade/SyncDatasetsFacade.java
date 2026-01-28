package com.platformcommons.platform.service.assessment.reporting.facade;

public interface SyncDatasetsFacade {
    void syncAssessmentData(Long assessment);

    void syncAssesseData(Long assessment, Boolean sa);
}
