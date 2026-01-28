package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.AssessmentQuestionPaper;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;

public interface AssessmentQuestionPaperDTOAssembler {

    AssessmentQuestionPaperDTO toDTO(AssessmentQuestionPaper entity);

    AssessmentQuestionPaper fromDTO(AssessmentQuestionPaperDTO dto);
}