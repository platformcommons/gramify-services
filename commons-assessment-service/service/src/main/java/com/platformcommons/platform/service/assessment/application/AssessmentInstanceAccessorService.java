package com.platformcommons.platform.service.assessment.application;

import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAccessor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface AssessmentInstanceAccessorService {
    List<AssessmentInstanceAccessor> createAssessmentInstanceAccessor(Long instanceId, Set<String> logins);

    Boolean accessibleInstance(Long instance);

    Page<AssessmentInstanceAccessor> getAssessmentInstanceAccessors(Long instanceId, Integer page, Integer size);

    void removeAssessmentInstanceAccessors(Set<String> logins, Long instanceId);
}
