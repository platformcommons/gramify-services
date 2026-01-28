package com.platformcommons.platform.service.post.facade.impl;

import com.platformcommons.platform.service.post.application.PostActorService;
import com.platformcommons.platform.service.post.dto.PostActorDTO;
import com.platformcommons.platform.service.post.facade.PostActorFacade;
import com.platformcommons.platform.service.post.facade.assembler.PostActorDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PostActorFacadeImpl implements PostActorFacade {

    @Autowired
    private PostActorService service;

    @Autowired
    private PostActorDTOAssembler assembler;

    @Override
    public PostActorDTO getOrCreateForLoggedInUser() {
        return assembler.toDTO(service.getOrAddForCurrentUser());
    }

    @Override
    public PostActorDTO patchUpdateForLoggedInUser(PostActorDTO postActorDTO) {
        return assembler.toDTO(service.patchUpdateForLoggedInUser(assembler.fromDTO(postActorDTO)));
    }

}
