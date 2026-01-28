package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.SectionQuestions;
import com.platformcommons.platform.service.assessment.dto.SectionQuestionsDTO;

import java.util.Set;

public interface SectionQuestionsDTOAssembler {

    SectionQuestionsDTO toDTO(SectionQuestions entity);

    SectionQuestions fromDTO(SectionQuestionsDTO dto);

    Set<SectionQuestionsDTO> toDTOs(Set<SectionQuestions> sectionquestionsList);
}