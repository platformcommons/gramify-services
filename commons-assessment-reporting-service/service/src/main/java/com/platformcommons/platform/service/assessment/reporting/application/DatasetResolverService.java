package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.reporting.domain.AssesseDim;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessseHierarchyResolver;

public interface DatasetResolverService {
    String resolveValue(AssessseHierarchyResolver resolver, AssesseDim assesseDim);
}
