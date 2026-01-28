package com.platformcommons.platform.service.post.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.post.client.LinkedPostAPI;
import com.platformcommons.platform.service.post.dto.LinkedPostDTO;
import com.platformcommons.platform.service.post.facade.LinkedPostFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "LinkedPost")
public class LinkedPostController implements LinkedPostAPI {

    @Autowired
    LinkedPostFacade linkedPostFacade;

    @Override
    public ResponseEntity<Long> createLinkPost(LinkedPostDTO body) {
        return new ResponseEntity<>(linkedPostFacade.createLinkPost(body), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteLinkedPostById(Long linkedPostId, String reason) {
        linkedPostFacade.deleteLinkedPostById(linkedPostId, reason);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<PageDTO<LinkedPostDTO>> getAllLinkedPost(Integer page, Integer size, String sortBy, String direction, String linkingType, Long postId) {
       PageDTO<LinkedPostDTO> result = linkedPostFacade.getAllLinkedPost(page, size, sortBy, direction, linkingType, postId);
        return new ResponseEntity<>(result, result.hasNext()? HttpStatus.PARTIAL_CONTENT: HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LinkedPostDTO> getLinkedPostById(Long linkedPostId) {
        return new ResponseEntity<>(linkedPostFacade.getLinkedPostById(linkedPostId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LinkedPostDTO> patchLinkedPost(LinkedPostDTO body) {
        return new ResponseEntity<>(linkedPostFacade.patchLinkedPost(body), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LinkedPostDTO> updateLinkedPost(LinkedPostDTO body) {
        return new ResponseEntity<>(linkedPostFacade.updateLinkedPost(body), HttpStatus.OK);
    }
}