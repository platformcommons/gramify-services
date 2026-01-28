package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.Question;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;

public interface QuestionDTOAssembler {

    QuestionDTO toDTO(Question entity);

    Question fromDTO(QuestionDTO dto);
}