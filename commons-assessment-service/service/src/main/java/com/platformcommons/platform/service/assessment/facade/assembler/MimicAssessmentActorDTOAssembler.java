package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.AssessmentActor;
import com.platformcommons.platform.service.assessment.domain.EmbeddableAssessmentActor;
import com.platformcommons.platform.service.assessment.dto.MimicAssessmentActorDTO;

public interface MimicAssessmentActorDTOAssembler {

    MimicAssessmentActorDTO toDTO(EmbeddableAssessmentActor assessmentActor);
    EmbeddableAssessmentActor fromDTO(MimicAssessmentActorDTO mimicAssessmentActorDTO);

    MimicAssessmentActorDTO toDTOV2(AssessmentActor assessmentActor1);

    AssessmentActor fromDTOV2(MimicAssessmentActorDTO mimicAssessmentActorDTO1);

}
