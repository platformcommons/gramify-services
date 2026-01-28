package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.OptionFact;

import java.util.Set;

public interface OptionFactAssembler {
    Set<OptionFact> toOptionFacts(AssessmentReportSyncContext context);

    Set<OptionFact> toOptionFacts(QuestionDTO questionDTO, AssessmentReportSyncContext syncContext);
}
