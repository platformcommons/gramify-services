package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.SkillFact;

import java.util.Set;

public interface SkillFactAssembler {
    Set<SkillFact> getSkillFacts(AssessmentReportSyncContext context);

    Set<SkillFact> getSkillFacts(QuestionDTO questionDTO, AssessmentReportSyncContext syncContext);
}
