package com.platformcommons.platform.service.assessment.application;


import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAssesse;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseFilterDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface AssessmentInstanceAssesseService {

    Page<AssessmentInstanceAssesse> getAssessmentInstanceAssesse(Long assessmentId, Integer page, Integer size);

    Long getAssessmentInstanceAssesseCount(Long assessmentId);

    void deleteAssessmentInstanceAssesse(Long id, String reason);

    Set<AssessmentInstanceAssesse> getAssessmentInstanceAssesseByAssessedForEntityIds(List<String> assessedForEntityId, String assessedForEntityType, List<Long> assessmentInstanceId);

    AssessmentInstanceAssesse updateAssessmentInstanceAssesseWithResponse(AssessmentInstanceAssesse assessmentInstanceAssesse);

    AssessmentInstanceAssesse getAssessmentInstanceAssesseById(Long id);

    PageDTO<AssessmentInstanceAssesseDTO> getAssessmentInstanceAssesseByAccessorId(AssessmentInstanceAssesseFilterDTO filterDTO);

    AssessmentInstanceAssesse patchUpdateAssesse(AssessmentInstanceAssesse assessmentInstanceAssesse);

    AssessmentInstanceAssesse createAssessmentInstanceAssesseV3(AssessmentInstanceAssesse assessmentInstanceAssesse);

    Set<AssessmentInstanceAssesse> getAssessmentInstanceAssesseByAssesse(List<String> actorIds, String actorType, List<Long> assessmentInstanceId);
}
