package com.platformcommons.platform.service.post.facade.assembler.impl;

import com.platformcommons.platform.service.post.domain.PostTagActor;
import com.platformcommons.platform.service.post.dto.PostTagActorDTO;
import com.platformcommons.platform.service.post.facade.assembler.PostTagActorDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostTagActorDTOAssemblerImpl implements PostTagActorDTOAssembler {

    @Override
    public PostTagActorDTO toDTO(PostTagActor entity) {
        if (entity == null) {
            return null;
        }
        return PostTagActorDTO.builder()
                .id(entity.getId())
                .actorId(entity.getActorId())
                .actorIdentifier(entity.getActorIdentifier())
                .actorName(entity.getActorName())
                .sequence(entity.getSequence())
                .build();
    }

    @Override
    public PostTagActor fromDTO(PostTagActorDTO dto) {
        if (dto == null) {
            return null;
        }

        return PostTagActor.builder()
                .id(dto.getId())
                .actorId(dto.getActorId())
                .actorIdentifier(dto.getActorIdentifier())
                .actorName(dto.getActorName())
                .sequence(dto.getSequence())
                .build();
    }
}