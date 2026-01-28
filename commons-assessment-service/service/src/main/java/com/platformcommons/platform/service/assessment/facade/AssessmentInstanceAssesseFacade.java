package com.platformcommons.platform.service.assessment.facade;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseFilterDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface AssessmentInstanceAssesseFacade {
    Long createAssessmentInstanceAssesseV2(AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO);

    PageDTO<AssessmentInstanceAssesseDTO> getAssessmentInstanceAssesse(Long assessmentId, Integer page, Integer size);
    Long getAssessmentInstanceAssesseCount(Long assessmentId);

    void deleteAssessmentInstanceAssesse(Long id, String reason);

    Set<AssessmentInstanceAssesseDTO> getAssessmentInstanceAssesseByAssessedForEntityIds(List<String> assessedForEntityId, String assessedForEntityType, List<Long> assessmentInstanceId);

    AssessmentInstanceAssesseDTO updateAssessmentInstanceAssesseWithResponse(AssessmentInstanceAssesseDTO body);

    AssessmentInstanceAssesseDTO getAssessmentInstanceAssesseById(Long id);

    PageDTO<AssessmentInstanceAssesseDTO> getAssessmentInstanceAssesseByAccessorId(AssessmentInstanceAssesseFilterDTO filterDTO);

    AssessmentInstanceAssesseDTO patchUpdateAssesse(AssessmentInstanceAssesseDTO body);

    AssessmentInstanceAssesseDTO createAssessmentInstanceAssesseV3(AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO);

    Map<String, AssessmentInstanceAssesseDTO> createAssessmentInstanceAssesseForComponents(@Valid Map<String, AssessmentInstanceAssesseDTO> body);

    Set<AssessmentInstanceAssesseDTO> getAssessmentInstanceAssesseByAssesse(List<String> actorIds, String actorType, List<Long> assessmentInstanceId);
}

