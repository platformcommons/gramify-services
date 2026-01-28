package com.platformcommons.platform.service.post.controller;

import com.platformcommons.platform.service.post.client.PostActorAPI;
import com.platformcommons.platform.service.post.dto.PostActorDTO;
import com.platformcommons.platform.service.post.facade.PostActorFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "PostActor")
public class PostActorController implements PostActorAPI {

    @Autowired
    private PostActorFacade facade;

    @Override
    public ResponseEntity<PostActorDTO> getForLoggedInUser() {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getOrCreateForLoggedInUser());
    }

    @Override
    public ResponseEntity<PostActorDTO> patchUpdateForLoggedInUser(PostActorDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.patchUpdateForLoggedInUser(body));
    }

}
