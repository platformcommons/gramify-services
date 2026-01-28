package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;

import java.util.Set;

public interface AssessmentInstanceDTOAssembler {

    AssessmentInstanceDTO toDTO(AssessmentInstance entity,String authorName);

    AssessmentInstance fromDTO(AssessmentInstanceDTO dto);


    Set<AssessmentInstanceDTO> toDTOs(Set<AssessmentInstance> entities);
}