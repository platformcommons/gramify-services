package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentPivotReportZipDTO;
import com.platformcommons.platform.service.assessment.reporting.application.ReportService;
import com.platformcommons.platform.service.assessment.reporting.facade.ReportFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ReportFacadeImpl implements ReportFacade {
    private final ReportService reportService;

    @Override
    public AssessmentPivotReportZipDTO getAssessmentReportQuery(Set<Long> assessmentInstanceId, String delimiter, String language) {
        return reportService.getAssessmentReportQuery(assessmentInstanceId,delimiter,language);
    }

    @Override
    public AssessmentPivotReportZipDTO getAssessmentReportQueryV2(Long assessmentInstanceId, String delimiter, String language, Set<Long> aiaIds, Date startDate, Date endDate) {
        return reportService.getAssessmentReportQueryV2(assessmentInstanceId,delimiter,language,aiaIds,startDate,endDate);
    }

    @Override
    public List<Map<String,String>> getAssessmentReportQueryV3(Long assessmentInstanceId, String language, String marketCode, boolean cacheDisabled) {
        return reportService.getAssessmentReportQueryV3(assessmentInstanceId,language,marketCode,cacheDisabled);
    }
    @Override
    public AssessmentPivotReportZipDTO getAssessmentReportQueryV4(Long assessmentInstanceId, String delimiter, String language) {
        return reportService.getAssessmentReportQueryV4(assessmentInstanceId,delimiter,language);
    }

    @Override
    public AssessmentPivotReportZipDTO getAssessmentReportQueryV5(Set<Long> assessmentInstanceId, String delimiter, String language) {
        return reportService.getAssessmentReportQueryV5(assessmentInstanceId,delimiter,language);
    }
}
