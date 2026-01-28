package com.platformcommons.platform.service.assessment.reporting.facade;

import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;

public interface LTLDFacade {
    AssessmentSyncContext getAssessmentContext(Long assessment, String token);
}
