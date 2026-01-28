package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionPaperSectionDTO;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionPaperSectionDim;

import java.util.Set;

public interface QuestionPaperSectionDimAssembler {

    Set<QuestionPaperSectionDim> toQuestionPaperSectionDim(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    void updateQuestionPaperSection(QuestionPaperSectionDim questionPaperSectionDim, AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, QuestionPaperSectionDTO section);

    QuestionPaperSectionDim toQuestionPaperSectionDim(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, QuestionPaperSectionDTO section);
}
