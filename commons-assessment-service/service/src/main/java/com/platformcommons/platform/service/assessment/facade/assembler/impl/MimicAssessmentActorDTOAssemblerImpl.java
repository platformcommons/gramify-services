package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.domain.AssessmentActor;
import com.platformcommons.platform.service.assessment.domain.EmbeddableAssessmentActor;
import com.platformcommons.platform.service.assessment.dto.MimicAssessmentActorDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicAssessmentActorDTOAssembler;
import org.springframework.stereotype.Component;

@Component
public class MimicAssessmentActorDTOAssemblerImpl implements MimicAssessmentActorDTOAssembler {
    @Override
    public MimicAssessmentActorDTO toDTO(EmbeddableAssessmentActor assessmentActor) {
        if(assessmentActor==null) return null;
        return MimicAssessmentActorDTO.builder()
                .actorId(assessmentActor.getActorId())
                .actorIeId(assessmentActor.getActorIeId())
                .groupId(assessmentActor.getGroupId())
                .actorType(assessmentActor.getActorType())
                .name(assessmentActor.getName())
                .groupCode(assessmentActor.getGroupCode())
                .build();
    }

    @Override
    public EmbeddableAssessmentActor fromDTO(MimicAssessmentActorDTO mimicAssessmentActorDTO) {
        if(null==mimicAssessmentActorDTO) return null;
        return EmbeddableAssessmentActor.builder()
                .actorId(mimicAssessmentActorDTO.getActorId())
                .actorIeId(mimicAssessmentActorDTO.getActorIeId())
                .actorType(mimicAssessmentActorDTO.getActorType())
                .groupId(mimicAssessmentActorDTO.getGroupId())
                .groupCode(mimicAssessmentActorDTO.getGroupCode())
                .name(mimicAssessmentActorDTO.getName())
                .build();
    }

    @Override
    public MimicAssessmentActorDTO toDTOV2(AssessmentActor assessmentActor1) {
        if (assessmentActor1 == null) return null;
        return MimicAssessmentActorDTO.builder()
                .id(assessmentActor1.getId())
                .actorId(assessmentActor1.getActorId())
                .actorType(assessmentActor1.getActorType())
                .name(assessmentActor1.getName())
                .groupCode(assessmentActor1.getGroupCode())
                .build();
    }

    @Override
    public AssessmentActor fromDTOV2(MimicAssessmentActorDTO mimicAssessmentActorDTO1) {
        if (mimicAssessmentActorDTO1 == null) return null;
        return AssessmentActor.builder()
                .id(mimicAssessmentActorDTO1.getId())
                .actorId(mimicAssessmentActorDTO1.getActorId())
                .actorType(mimicAssessmentActorDTO1.getActorType())
                .name(mimicAssessmentActorDTO1.getName())
                .groupCode(mimicAssessmentActorDTO1.getGroupCode())
                .build();
    }
}
