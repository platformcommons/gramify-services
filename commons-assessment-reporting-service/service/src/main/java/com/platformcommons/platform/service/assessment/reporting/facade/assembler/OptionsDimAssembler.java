package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.DefaultOptionsDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.domain.OptionsDim;

import java.util.Set;

public interface OptionsDimAssembler {
    Set<OptionsDim> toOptionDims(QuestionDTO questionDims);

    Set<OptionsDim> toOptionDims(QuestionDTO questionDTO, DefaultOptionsDTO defaultOptionsDTO);

    OptionsDim toOptionDim(QuestionDTO questionDTO, DefaultOptionsDTO defaultOptionsDTO, String language);

    void updateOptionDim(QuestionDTO questionDTO, DefaultOptionsDTO defaultOptionsDTO, OptionsDim dim);
}
