package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.PostTagActor;
import com.platformcommons.platform.service.post.dto.PostTagActorDTO;

public interface PostTagActorDTOAssembler {
    PostTagActorDTO toDTO(PostTagActor entity);

    PostTagActor fromDTO(PostTagActorDTO dto);
}