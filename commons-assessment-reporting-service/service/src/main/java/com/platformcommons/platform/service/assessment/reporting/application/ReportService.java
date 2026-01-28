package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssessmentPivotReportZipDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ReportService {
    AssessmentPivotReportZipDTO getAssessmentReportQuery(Set<Long> assessmentInstanceId, String delimiter, String language);


    AssessmentPivotReportZipDTO getAssessmentReportQueryV2(Long assessmentInstanceId, String delimiter, String language, Set<Long> aiaIds, Date startDate, Date endDate);

    List<Map<String,String>> getAssessmentReportQueryV3(Long assessmentInstanceId, String language, String marketCode, boolean cacheDisabled);

    AssessmentPivotReportZipDTO getAssessmentReportQueryV4(Long assessmentInstanceId, String delimiter, String language);

    AssessmentPivotReportZipDTO getAssessmentReportQueryV5(Set<Long> assessmentInstanceId, String delimiter, String language);
}
