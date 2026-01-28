package com.platformcommons.platform.service.assessment.application;

import com.platformcommons.platform.service.assessment.domain.Assessment;
import com.platformcommons.platform.service.assessment.domain.AssessmentConfig;

import java.util.Map;
import java.util.Set;

public interface AssessmentService {

    Assessment createAssessment(Assessment assessment);
    Assessment getAssessmentById(Long id);
    Assessment getAssessmentByCode(String code);
    Assessment updateAssessmentV2(Assessment assessment);


    Map<String, String> getConfigs(Long assessmentInstanceId);

    Set<Long> getAssessmentIdsByUUIDsAndCodes(Set<String> uuids, Set<String> codes);

    Assessment addAssessmentConfig(Long assessmentId, AssessmentConfig configDTO);
}
