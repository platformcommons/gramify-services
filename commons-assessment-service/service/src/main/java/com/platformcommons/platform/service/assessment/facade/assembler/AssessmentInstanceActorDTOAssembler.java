package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceActor;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceActorDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
          AssessmentInstanceDTOAssembler.class,
    })
public interface AssessmentInstanceActorDTOAssembler {

    AssessmentInstanceActorDTO toDTO(AssessmentInstanceActor entity);

    AssessmentInstanceActor fromDTO(AssessmentInstanceActorDTO dto);
}