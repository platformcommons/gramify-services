package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.Reaction;
import com.platformcommons.platform.service.post.dto.ReactionDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface ReactionDTOAssembler {

    ReactionDTO toDTO(Reaction entity);

    Reaction fromDTO(ReactionDTO dto);
}