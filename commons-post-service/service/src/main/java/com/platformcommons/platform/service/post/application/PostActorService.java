package com.platformcommons.platform.service.post.application;

import com.mindtree.bridge.platform.dto.UserDTO;
import com.platformcommons.platform.service.post.domain.PostActor;

public interface PostActorService {
    PostActor getOrAddForCurrentUser();


    void updatePostActorDetails(UserDTO userDTO);

    PostActor patchUpdateForLoggedInUser(PostActor postActor);
}