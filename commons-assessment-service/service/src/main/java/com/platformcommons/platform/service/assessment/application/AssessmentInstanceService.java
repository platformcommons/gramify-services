package com.platformcommons.platform.service.assessment.application;

import com.platformcommons.platform.service.assessment.application.constant.OwnedBy;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;

import com.platformcommons.platform.service.assessment.domain.vo.AssessmentInstanceAccessibilityVO;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentInstanceDetailVO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceSearchDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.data.domain.Page;

import java.util.*;

public interface AssessmentInstanceService {

    AssessmentInstance createAssessmentInstance(AssessmentInstance assessmentInstance);

    Page<AssessmentInstance> getAllAssessmentInstances(Integer page, Integer size);

    AssessmentInstance getAssessmentInstanceByIdv2(Long assessmentInstanceId);

    PageDTO<AssessmentInstanceDetailVO> getAssessmentInstanceInfo(String domain, OwnedBy ownedBy, String subdomain, String status, String sortBy, String sortOrder, Integer page, Integer size, String language, String text, String context);

    AssessmentInstance updateAssessmentInstanceV2(AssessmentInstance assessmentInstance);

    Long deleteAssessmentInstanceV2(Long assessmentInstanceId, String reason);

    Map<Long, String> getAssessmentInstanceCreatedByName(Set<String> userIds);

    AssessmentInstance getAssessmentInstanceByAssessmentId(Long asssessmentId);

    Set<AssessmentInstance> getAssessmentInstanceByAssessmentType(String assessmentType, Boolean assessmentIsActive, Boolean isActive);

    Page<AssessmentInstanceSearchDTO> getAllAssessmentInstanceSearchDTO(Integer page, Integer size);

    PageDTO<AssessmentInstanceDTO> getAssessmentInstanceByAssessmentTypes(Set<String> assessmentTypes, Set<String> assessmentKind, String assessmentSubType, Boolean assessmentIsActive, String context, Boolean isActive, Integer page, Integer size, String forEntityId, String forEntityType);

    Long getAssessmentInstanceIdByAssessmentId(Long id);

    AssessmentInstanceAccessibilityVO getAssessmentInstanceAccessibilityVOById(Long assessmentInstanceId);

    boolean getAssessmentInstanceByLinkedSystemIdAndLinkedSystemType(Long linkedSystemId, String linkedSystemType);
}
