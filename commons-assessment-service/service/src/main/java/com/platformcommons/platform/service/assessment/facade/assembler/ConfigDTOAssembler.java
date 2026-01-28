package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.AssessmentConfig;
import com.platformcommons.platform.service.assessment.domain.QuestionConfig;
import com.platformcommons.platform.service.assessment.dto.ConfigDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ConfigDTOAssembler {
    ConfigDTO toDTO(AssessmentConfig entity);
    AssessmentConfig fromDTO(ConfigDTO dto);

    ConfigDTO toDTO(QuestionConfig entity);
    QuestionConfig fromDTOToQuestionConfig(ConfigDTO dto);

}
