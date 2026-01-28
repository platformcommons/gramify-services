package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionFact;
import com.platformcommons.platform.service.assessment.reporting.dto.AssessmentInstanceDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.SectionQuestionDimDTO;

import java.util.Set;

public interface QuestionFactAssembler {
    Set<QuestionFact> toQuestionFactAssembler(AssessmentReportSyncContext context);

    QuestionFact toQuestionFactAssembler(SectionQuestionDimDTO sectionQuestionDim, QuestionDimDTO questionDim, AssessmentInstanceDimDTO assessmentInstanceDim);
}
