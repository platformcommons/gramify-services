package com.platformcommons.platform.service.assessment.facade;

import com.platformcommons.platform.service.assessment.application.constant.OwnedBy;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentInstanceDetailVO;
import com.platformcommons.platform.service.assessment.dto.AssessmentContextCachedDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceSearchDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.Set;

public interface AssessmentInstanceFacade {

    Long createAssessmentInstance(AssessmentInstanceDTO assessmentInstanceDTO);

    PageDTO<AssessmentInstanceDTO> getAllAssessmentInstances(Integer page, Integer size);

    AssessmentInstanceDTO getAssessmentInstanceByIdv2(Long assessmentInstanceId);

    PageDTO<AssessmentInstanceDetailVO> getAssessmentInstanceInfo(String domain, OwnedBy ownedBy, String subdomain, String status, String sortBy, String sortOrder, Integer page, Integer size, String language, String text, String context);

    void updateAssessmentInstanceV2(AssessmentInstanceDTO assessmentInstanceDTO);

    void deleteAssessmentInstanceV2(Long assessmentInstanceId, String reason);

    AssessmentInstanceDTO duplicateAssessmentInstance(Long assessmentInstanceId, String name);


    AssessmentInstanceDTO getAssessmentInstanceByAssessmentId(Long asssessmentId);

    Set<AssessmentInstanceDTO> getAssessmentInstanceByAssessmentType(String assessmentType, Boolean assessmentIsActive, Boolean isActive);

    PageDTO<AssessmentInstanceSearchDTO> getAllAssessmentInstanceSearchDTO(Integer page, Integer size);

    PageDTO<AssessmentInstanceDTO> getAssessmentInstanceByAssessmentTypes(Set<String> assessmentTypes, Set<String> assessmentKind, String assessmentSubType, Boolean assessmentIsActive, String context, Boolean isActive, Integer page, Integer size, String forEntityId, String forEntityType);

    Long getAssessmentInstanceIdByAssessmentId(Long assessmentId);

    AssessmentContextCachedDTO getContextCache(Long assessmentInstanceId);
}
