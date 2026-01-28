package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.QuestionPaperSection;
import com.platformcommons.platform.service.assessment.dto.QuestionPaperSectionDTO;

import java.util.Set;


public interface QuestionPaperSectionDTOAssembler {

    QuestionPaperSectionDTO toDTO(QuestionPaperSection entity);

    QuestionPaperSection fromDTO(QuestionPaperSectionDTO dto);

    Set<QuestionPaperSection> fromDTOs(Set<QuestionPaperSectionDTO> questionPaperSectionList);

    Set<QuestionPaperSectionDTO> toDTOs(Set<QuestionPaperSection> questionpapersectionList);
}