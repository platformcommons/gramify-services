package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseSkillDim;

import java.util.Set;

public interface AssesseSkillDimAssembler {
    Set<AssesseSkillDim> toAssesseSkillDim(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context);
}
