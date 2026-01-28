package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionPaperSectionDTO;
import com.platformcommons.platform.service.assessment.dto.SectionQuestionsDTO;
import com.platformcommons.platform.service.assessment.reporting.domain.SectionQuestionDim;

import java.util.Set;

public interface SectionQuestionDimAssembler {
    Set<SectionQuestionDim> assessmentQuestionPaperDTOToSectionQuestionDim(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    void updateSectionQuestion(SectionQuestionDim sectionQuestionDim, AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, SectionQuestionsDTO sectionQuestions);

    SectionQuestionDim toQuestionPaperSectionDim(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, QuestionPaperSectionDTO questionPaperSectionDTO, SectionQuestionsDTO sectionQuestion);
}
