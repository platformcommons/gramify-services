package com.platformcommons.platform.service.assessment.facade;


import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAccessorDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.List;
import java.util.Set;

public interface AssessmentInstanceAccessorFacade {
    List<AssessmentInstanceAccessorDTO> createAssessmentInstanceAccessor(Long instanceId, Set<String> logins);

    PageDTO<AssessmentInstanceAccessorDTO> getAssessmentInstanceAccessors(Long instanceId, Integer page, Integer size);

    void removeAssessmentInstanceAccessors(Set<String> logins, Long instanceId);
}
