package com.platformcommons.platform.service.post.facade;

import com.platformcommons.platform.service.post.dto.PostActorDTO;

public interface PostActorFacade {

    PostActorDTO getOrCreateForLoggedInUser();

    PostActorDTO patchUpdateForLoggedInUser(PostActorDTO postActorDTO);
}
