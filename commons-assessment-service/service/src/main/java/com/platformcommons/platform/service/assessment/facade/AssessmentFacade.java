package com.platformcommons.platform.service.assessment.facade;

import com.platformcommons.platform.service.assessment.dto.AssessmentContextDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.ConfigDTO;

import java.util.Set;

public interface AssessmentFacade {

    Long createAssessment(AssessmentDTO assessmentDTO);
    AssessmentDTO getAssessmentById(Long assessmentId);
    void updateAssessmentV2(AssessmentDTO assessmentDTO);

    AssessmentContextDTO getContext(Long asssessmentId);

    void evictCacheContext(Long assessmentInstanceId);


    AssessmentDTO addAssessmentConfig(Long assessmentId, ConfigDTO configDTO);
    Set<AssessmentContextDTO> getContexts(Set<String> uuids, Set<String> codes);

    void triggerReviewAssessmentReminder();
}
