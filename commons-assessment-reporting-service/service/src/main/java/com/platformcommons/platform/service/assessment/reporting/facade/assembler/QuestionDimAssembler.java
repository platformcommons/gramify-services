package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;

import java.util.Set;

public interface QuestionDimAssembler {

    Set<QuestionDim> toQuestionDim(QuestionDTO questionDTO);

    void updateQuestionDim(QuestionDTO questionDTO, QuestionDim questionDim);

    QuestionDim toQuestionDim(QuestionDTO questionDTO, String language);

    QuestionDimDTO toDTO(QuestionDim questionDim);
}
