package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.PostActor;
import com.platformcommons.platform.service.post.dto.PostActorDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
          PostDTOAssembler.class,
          ReactionDTOAssembler.class,
          ResponseDTOAssembler.class,
    })
public interface PostActorDTOAssembler {

    PostActorDTO toDTO(PostActor entity);

    PostActor fromDTO(PostActorDTO dto);
}